package showtracker.server;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.http.HttpResponse;
import org.json.simple.parser.JSONParser;
import showtracker.Episode;
import showtracker.Helper;
import showtracker.Show;

import java.io.*;
import java.sql.*;

/**
 * @author Filip Spånberg
 * Changes made by Adam Joulak
 * 
 * DatabaseReader hanterar uppkoppling till MySQL-databasen,
 * samt hanterar förfrågningar till TheTVDB
 */
public class DatabaseReader {
    private java.sql.Connection dbConn;
    private static String createTableTitles = "CREATE TABLE IMDB_TITLES (ID VARCHAR(10) NOT NULL PRIMARY KEY,NAME VARCHAR(100));";
    private static String createTableEpisodes = "CREATE TABLE IMDB_EPISODES (ID VARCHAR(10) NOT NULL PRIMARY KEY,PARENT VARCHAR(10),SEASON SMALLINT,EPISODE INT);";
    private final int show = 1;
    private String strToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTY2MjUyOTksImlkIjoiU2hvd1RyYWNrZXIiLCJvcmlnX2lhdCI6MTU1NjUzODg5OSwidXNlcmlkIjo1MjQzMDIsInVzZXJuYW1lIjoiZmlsaXAuc3BhbmJlcmdxcnMifQ.NriC7481n32bFACSLLZwSAgf9Ll835_xHwxvuAHgTmqdYRs3RT0TJhetgCwRsCSNlRMmWYoROXOrYGCGLIz8izkMIS2_OwaygqiX4XBbYMwxjdcBtuhdhy-a34WureLEdGvqAUwx6tFNYWXH27x2evNGgbOMYFyN03idqQhyqHJBcXsRtAKD9NhmrL5R33y0O8jmXyu5QT-B0FWyGJ1dQ-15PK49feRauofZ1s72uaE_xTvwlyHSZbRTX5DiOtH8FZgNGMkqvARkR0B5MoqEat24-xUyjDb5VKNkhpr9oZsJwl_nZKMm8jZrKgPHHuZ6E4CUyip38EgbqPMipXqhMg";
    private String strLanguage = "en";

    public void setupDBConnection() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("ai8934");
        dataSource.setPassword("ShowTracker16");
        dataSource.setServerName("195.178.232.16");

        try {
            dbConn = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("DatabaseReader: " + e);
        }
    }

    public int updateSql(String statement) {
        Statement stmt = null;
        int res = -1;
        try {
            stmt = dbConn.createStatement();
            res = stmt.executeUpdate(statement);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return res;
    }

    public ResultSet selectSql(String statement) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = dbConn.createStatement();
            rs = stmt.executeQuery(statement);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return rs;
    }

    private void readTitles() {
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream("ShowTracker/files/title-basics.tsv"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = null;
        try {
            line = bfr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (line != null) {
            String statement = "INSERT INTO IMDB_TITLES (ID, NAME) VALUES";
            int i = 0;
            while (i < 1000 && line != null)
                try {
                    line = bfr.readLine();
                    String[] lines = line.split("\\t");
                    if (lines[1].equals("tvSeries") || lines[1].equals("tvEpisode")) {
                        statement += String.format("('%s','%s'),",
                                lines[0],
                                lines[2].replaceAll("'", "&apos;"));
                        i++;
                    }
                } catch (NullPointerException npe) {
                    System.out.println("DatabaseReader.readShows: End of text reached.");
                } catch (Exception e) {
                    System.out.println("DatabaseReader.readShows: " + e + "\n" + line);
                }

            statement = statement.substring(0, statement.length() - 1) + ";";
            try {
                updateSql(statement);
            } catch (Exception e) {
                System.out.println("DatabaseReader.readShows: " + e + "\n" + line);
            }
        }

        try {
            bfr.close();
        } catch (IOException e) {
            System.out.println("DatabaseReader.readTitleBasics: " + e);
        }
    }

    private void readEpisodes() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream("ShowTracker/files/title-episode.tsv"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (line != null) {
            String statement = "INSERT INTO IMDB_EPISODES (ID, PARENT, SEASON, EPISODE) VALUES";
            for (int i = 0; i < 1000 && line != null; i++)
                try {
                    line = br.readLine();
                    String[] lines = line.split("\\t");
                    statement += String.format("('%s','%s',%s,%s),",
                            lines[0],
                            lines[1],
                            lines[2].equals("\\N") ? "NULL" : lines[2],
                            lines[3].equals("\\N") ? "NULL" : lines[3]);
                } catch (NullPointerException npe) {
                    System.out.println("DatabaseReader.readEpisodes: End of text reached.");
                } catch (Exception e) {
                    System.out.println("DatabaseReader.readEpisodes: " + e + "\n" + line);
                }
            statement = statement.substring(0, statement.length() - 1) + ";";
            try {
                updateSql(statement);
            } catch (Exception e) {
                System.out.println("DatabaseReader.readEpisodes: " + e + "\n" + line);
            }
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String authenticateTheTVDB() {
        JSONObject obj = new JSONObject();

        //JSON string:  {"apikey":"BK2A524N2MT0IJWU","username":"filip.spanbergqrs","userkey":"J52T5FJR4CUESBPF"}
        obj.put("apikey", "BK2A524N2MT0IJWU");
        obj.put("username", "filip.spanbergqrs");
        obj.put("userkey", "J52T5FJR4CUESBPF");

        StringEntity stringEntity = new StringEntity(obj.toString(), ContentType.APPLICATION_JSON);

        HttpPost httpPost = new HttpPost("https://api.thetvdb.com/login");
        httpPost.setEntity(stringEntity);

        JSONObject jsoResponse = getJSONFromRequest(httpPost);

        strToken = (String) jsoResponse.get("token");
        return strToken;
    }

    void setToken(String token) {
        this.strToken = token;
    }

    JSONObject refreshToken() {
        HttpGet request = createGet("https://api.thetvdb.com/refresh_token");
        /*HttpClient httpClient = HttpClientBuilder.create().build();
        boolean status = false;
        try {
            HttpResponse response = httpClient.execute(request);
            status = (response.getStatusLine().getStatusCode() == 200);
        } catch (Exception	 e) {
            System.out.println(e);
        }
        if (status)*/
        JSONObject ret = getJSONFromRequest(request);


        return ret;
    }

    String[][] searchTheTVDBShows(String searchTerms) {
        String[] strArrSearchTerms = searchTerms.split(" ");
        StringBuilder sbSearchTerms = new StringBuilder(strArrSearchTerms[0]);
        for (int i = 1; i < strArrSearchTerms.length; i++)
            sbSearchTerms.append("%20").append(strArrSearchTerms[i]);

        HttpGet httpGet = createGet("https://api.thetvdb.com/search/series?name=" + sbSearchTerms);
        JSONObject jsoResponse = getJSONFromRequest(httpGet);
        String strError = (String) jsoResponse.get("Error");
        if (strError == null) {
            JSONArray jsaResponse = (JSONArray) jsoResponse.get("data");

            String[][] shows = new String[jsaResponse.size()][2];
            for (int i = 0; i < jsaResponse.size(); i++) {
                shows[i][0] = (String) ((JSONObject) jsaResponse.get(i)).get("seriesName");
                shows[i][1] = Long.toString((Long) ((JSONObject) jsaResponse.get(i)).get("id"));
            }
            return shows;
        } else {
            System.out.println(strError);
            return null;
        }
    }

    JSONObject searchTheTVDBShow(String id) {
        HttpGet httpGet = createGet("https://api.thetvdb.com/series/" + id);
        JSONObject jsoResponse = (JSONObject) getJSONFromRequest(httpGet).get("data");

        return jsoResponse;
    }

    JSONArray getEpisodesOfShow(String id, int page) {
        HttpGet request = createGet("https://api.thetvdb.com/series/" + id + "/episodes?page=" + page);
        JSONObject joResponse = getJSONFromRequest(request);
        String strError = (String) joResponse.get("Error");
        if (strError == null) {
            JSONArray jsaResponse = (JSONArray) joResponse.get("data");
            return jsaResponse;
        } else {
            System.out.println(strError);
            return null;
        }
    }

    Show generateShow(String[] arShow) {
        System.out.println("DatabaseReader: Generating show \"" + arShow[0] + "\"...");
        JSONObject jsoShow = searchTheTVDBShow(arShow[1]);
        Show show = new Show((String) jsoShow.get("seriesName"));
        show.setDescription((String) jsoShow.get("overview"));
        show.setTvdbId(Long.toString((Long) jsoShow.get("id")));
        show.setImdbId((String) jsoShow.get("imdbId"));

        int intPage = 1;

        JSONArray jsaEpisodes = getEpisodesOfShow(arShow[1], intPage);

        do {
            System.out.println(jsaEpisodes);
            for (Object obj : jsaEpisodes) {
                JSONObject jso = (JSONObject) obj;

                int intSeason = ((Long) jso.get("airedSeason")).intValue();
                int intEpisode = ((Long) jso.get("airedEpisodeNumber")).intValue();
                String strName = (String) jso.get("episodeName");
                String strTvdbId = Long.toString((Long) jso.get("id"));
                String strImdbId = (String) jso.get("imdbId");
                String strDescription = Helper.decodeUnicode((String) jso.get("overview"));

                Episode episode = new Episode(show, intEpisode, intSeason);
                episode.setTvdbId(strTvdbId);
                episode.setImdbId(strImdbId);
                episode.setName(strName);
                episode.setDescription(strDescription);
                show.addEpisode(episode);
            }
            intPage++;
            jsaEpisodes = getEpisodesOfShow(arShow[1], intPage);
        } while (jsaEpisodes != null);

        show.sortEpisodes();
        System.out.println("DatabaseReader: Show created.");
        for (Episode episode: show.getEpisodes())
            System.out.print(episode.getName() + ", ");
        return show;
    }

    private HttpGet createGet(String route) {
        HttpGet httpGet = new HttpGet(route);
        httpGet.setHeader("Authorization", "Bearer " + strToken);
        httpGet.setHeader("Accept-Language", strLanguage);
        return httpGet;
    }

    private JSONObject getJSONFromRequest(HttpUriRequest request) {
        JSONObject jsoResponse = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        JSONParser parser = new JSONParser();

        try {
            HttpResponse response = httpClient.execute(request);
            InputStreamReader isr = new InputStreamReader(response.getEntity().getContent());
            BufferedReader bfr = new BufferedReader(isr);
            String strLine = bfr.readLine();
            jsoResponse = (JSONObject) parser.parse(strLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsoResponse;
    }

    Show updateShow(Show show) {
        String[] strArrSearchRequest = {show.getName(), show.getTvdbId()};
        Show shwLatest = generateShow(strArrSearchRequest);
        for (Episode episode: shwLatest.getEpisodes())
            if (!show.containsById(episode))
                show.addEpisode(episode);
        show.sortEpisodes();
        return show;
    }
}