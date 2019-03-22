package Server;

import com.mysql.cj.jdbc.MysqlDataSource;
import showtracker.Show;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseReader {
    private Controller controller;
    private Connection dbConn;
    private static String createTableShows = "CREATE TABLE IMDB_SHOWS (ID VARCHAR(10) NOT NULL,NAME VARCHAR(30),PRIMARY KEY (ID));";
    private static String createTableEpisodes = "CREATE TABLE IMDB_EPISODES (ID VARCHAR(10) NOT NULL PRIMARY KEY,PARENT VARCHAR(9),NAME VARCHAR(30),SEASON SMALLINT,EPISODE INT);";

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

    private void readTitleBasics() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream("ShowTracker/files/title-basics.tsv"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;
        int i = 0;
        while (i < 10) {
            try {
                line = br.readLine();
                String[] lines = line.split("\\t");
                if (lines[1].equals("tvSeries")) {
                    System.out.println("ID: " + lines[0] + ", type: " + lines[1] + ", title: " + lines[2]);
                    i++;
                }

            } catch (IOException e) {
                e.printStackTrace();
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
            for (int i = 0; i < 1000; i++)
                try {
                    line = br.readLine();
                    String[] lines = line.split("\\t");
                    statement += String.format("('%s','%s',%s,%s),",
                            lines[0],
                            lines[1],
                            lines[2].equals("\\N") ? "NULL" : lines[2],
                            lines[3].equals("\\N") ? "NULL" : lines[3]);
                } catch (Exception e) {
                    System.out.println("DatabaseReader.readEpisodes: " + e + "\n" + statement);
                }
            statement = statement.substring(0, statement.length() - 1) + ";";
            updateSql(statement);
        }
    }

    public static void main(String[] args) {
        DatabaseReader dbr = new DatabaseReader(new Controller());
        dbr.setupDBConnection();
        /*File archive = new File("ShowTracker/files/title.episode.tsv.gz");
        File output = new File("ShowTracker/files/title_episode.txt");
        try {
            decompressGzip(archive, output);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        System.out.println("Connection started.");
        dbr.updateSql("use ai8934");
        System.out.println("DB selected");
        dbr.updateSql("drop table IMDB_EPISODES");
        //dbr.updateSql("truncate table IMDB_EPISODES");
        dbr.updateSql(createTableEpisodes);
        dbr.readEpisodes();
    }
}