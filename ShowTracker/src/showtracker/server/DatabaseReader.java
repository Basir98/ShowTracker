package showtracker.server;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.http.HttpResponse;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.*;
import java.sql.*;

public class DatabaseReader {
    private Controller controller;
    private Connection dbConn;
    private static String createTableTitles = "CREATE TABLE IMDB_TITLES (ID VARCHAR(10) NOT NULL PRIMARY KEY,NAME VARCHAR(100));";
    private static String createTableEpisodes = "CREATE TABLE IMDB_EPISODES (ID VARCHAR(10) NOT NULL PRIMARY KEY,PARENT VARCHAR(10),SEASON SMALLINT,EPISODE INT);";
    private final int show = 1;
    private HttpClient httpClient = HttpClientBuilder.create().build();
    private String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTQzODM2MTcsImlkIjoiU2hvd1RyYWNrZXIiLCJvcmlnX2lhdCI6MTU1NDI5NzIxNywidXNlcmlkIjo1MjQzMDIsInVzZXJuYW1lIjoiZmlsaXAuc3BhbmJlcmdxcnMifQ.JvaYR_WvjyMsg3fVkqolLk4tkCY5V5gRFBRFnia5Lk7her65Q4sb0kYc34GLDpBCgrVmC1bGAsYGyR6eUQKi1b943oE8ikfeJB0pMNQUnao0_sJkn3-o5KgiTEjxWlasZ4nFIVkh3PCyMUYTcpP-oPmib78EUPNLghnN0LeBI2lqHf17Jo999S6ueZ3lvkSUV4n1DsozS0JhsgZjuQR1dCq-dzYNBzGEPpOY8WL-bCon4JL5E2soYZ8PszVSVvH-SXScQwnXj7bXlQaaM2xnzf0Z0k5U3BO60jd9O0Xk4dDXgymrQbdkA-B5fWNx9iBxYiPuM9Adi_QQdRRaOHY_dw";
    private JSONParser parser = new JSONParser();

    public DatabaseReader(Controller controller) {
        this.controller = controller;
    }

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
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream("ShowTracker/files/title-basics.tsv"))));
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
            String statement = "INSERT INTO IMDB_TITLES (ID, NAME) VALUES";
            int i = 0;
            while (i < 1000 && line != null)
                try {
                    line = br.readLine();
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
            br.close();
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

    public void authenticateTheTVDB() {
        JSONObject obj = new JSONObject();

        obj.put("apikey", "BK2A524N2MT0IJWU");
        obj.put("username", "filip.spanbergqrs");
        obj.put("userkey", "J52T5FJR4CUESBPF");
        //JSON string:  {"apikey":"BK2A524N2MT0IJWU","username":"filip.spanbergqrs","userkey":"J52T5FJR4CUESBPF"}
        StringEntity entity = new StringEntity(obj.toString(), ContentType.APPLICATION_JSON);

        HttpPost request = new HttpPost("https://api.thetvdb.com/login");
        request.setEntity(entity);

        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
            InputStreamReader isr = new InputStreamReader(response.getEntity().getContent());
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder sbResponse = new StringBuilder();
            while ((line = br.readLine()) != null)
                sbResponse.append(line);

            JSONParser parser = new JSONParser();
            JSONObject joResponse = (JSONObject) parser.parse(sbResponse.toString());
            token = (String) joResponse.get("token");
            System.out.println(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTheTVDBShows(String searchTerms) {

        String[] arSearchTerms = searchTerms.split(" ");
        StringBuilder sbSearchTerms = new StringBuilder(arSearchTerms[0]);
        for (int i = 1; i < arSearchTerms.length; i++)
            sbSearchTerms.append("%20").append(arSearchTerms[i]);
        System.out.println(sbSearchTerms);

        HttpGet request = new HttpGet("https://api.thetvdb.com/search/series?name=" + sbSearchTerms);

        request.setHeader("Authorization", "Bearer " + token);

        HttpResponse response = null;

        try {
            response = httpClient.execute(request);
            InputStreamReader isr = new InputStreamReader(response.getEntity().getContent());
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            System.out.println(line);
            JSONObject joResponse = (JSONObject) parser.parse(line);

            JSONArray jaResponse = (JSONArray) joResponse.get("data");

            for (int i = 0; i < jaResponse.size(); i++)
                System.out.println( ((JSONObject)jaResponse.get(i)).get("seriesName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTheTVDBShow(String id) {

    }

    public static void main(String[] args) {
        DatabaseReader dbr = new DatabaseReader(new Controller());
        dbr.setupDBConnection();

        /*File archive = new File("ShowTracker/files/title.episode.tsv.gz");
        File output = new File("ShowTracker/files/title_episode.txt");
        try {
            FileHandler.decompressGzip("ShowTracker/files/title.episode.tsv.gz");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //System.out.println("Connection started.");
        //dbr.updateSql("use ai8934");
        //System.out.println("DB selected");

        // Delete table
        //dbr.updateSql("drop table IMDB_EPISODES");
        //dbr.updateSql("drop table IMDB_SHOWS");

        // Empty table
        //dbr.updateSql("truncate table IMDB_EPISODES");
        //dbr.updateSql("truncate table IMDB_SHOWS");

        // Create table
        //dbr.updateSql(createTableEpisodes);
        //dbr.updateSql(createTableTitles);

        // Read all episodes (ID, parent, episode, season)
        //dbr.readEpisodes();
        //dbr.readTitles();

        //dbr.authenticateTheTVDB();

        dbr.searchTheTVDBShows("thrones");
        dbr.searchTheTVDBShow("121361");
    }
}