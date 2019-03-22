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
    private static String[] showsColumns = {"ID varchar(9) NOT NULL","NAME varchar(30)","PRIMARY KEY (ID)"};
    private static String[] episodesColumns = {"ID VARCHAR(9) NOT NULL","NAME VARCHAR(30)","SEASON INT","EPISODE INT","PRIMARY KEY (ID)"};

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
            br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream("files/title-episode.tsv"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;
        for (int i = 0; i < 100; i++) {
            try {
                line = br.readLine();
                String[] lines = line.split("\\t");
                String showId = lines[0];
                Show show = new Show(showId);
                if (!controller.containsShow(show))
                    controller.addShow(show);


            } catch (IOException e) {
                System.out.println("DatabaseReader.readEpisodes: " + e);
            }
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
        //String[] columns = {"ID varchar(9) NOT NULL", "NAME VARCHAR(30)", "PRIMARY KEY (ID)"};
        dbr.updateSql("create table shows (ID varchar(9) not null,name varchar(30), primary key (ID));");
        String sqlStatement = "INSERT INTO shows (ID, NAME)\n" +
                "VALUES ('tt0000123', 'MyShow');";
        dbr.updateSql(sqlStatement);
        //dbr.readTitleBasics();
        //dbr.dropAllTables();
        //dbr.createTable("shows", showsColumns);
        //dbr.createTable("episodes", episodesColumns);
    }
}
