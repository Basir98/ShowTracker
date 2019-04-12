package showtracker.server;

import showtracker.Show;

import javax.swing.*;

public class Controller {
    private DatabaseReader dbr = new DatabaseReader();
    private GUI gui = new GUI(this);
    private Connection connection = new Connection(this);
    public static final boolean debug = true;

    public Controller() {
        gui.start();
    }

    public String[][] getShows(String search) {
        String[][] response = dbr.searchTheTVDBShows(search);
        return response;
    }

    public Show getEpisodes(String[] stShow) {
        Show show = dbr.generateShow(stShow);
        return show;
    }

    void startConnection(int threads) {
        connection.startConnection(threads);
    }

    void stopConnection() {
        System.out.println("Controller exiting...");
        connection.stopConnection();
        System.out.println("Controller exited.");
    }

    void setThreadCount(int i) {
        gui.setActiveThreads(i);
    }

    public String authenticateTheTVDB() {
        String token = dbr.authenticateTheTVDB();
        return token;
    }

    public static void main (String[] args) {
        Controller controller = new Controller();
    }
}
