package showtracker.server;

import showtracker.Helper;
import showtracker.Show;
import showtracker.User;

import java.io.File;
import java.util.HashMap;

public class Controller {
    private DatabaseReader dbr = new DatabaseReader();
    private GUI gui = new GUI(this);
    private Connection connection = new Connection(this);
    public static final boolean debug = true;
    private HashMap<String, String> users = new HashMap<String, String>();

    public Controller() {
        gui.start();
        File foFiles = new File("files/");
        File foUsers = new File("files/users/");
        if (!foFiles.exists())
            foFiles.mkdir();
        if (!foUsers.exists())
            foUsers.mkdir();

        if (new File("files/users.obj").exists())
            users = (HashMap<String, String>) Helper.readFromFile("files/users.obj");
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

    public User loginUser(String[] userInfo) {
        User user = null;
        String password = users.get(userInfo[0]);
        if (password.equals(userInfo[1]))
            user = (User) Helper.readFromFile("files/users/" + userInfo[0] + ".usr");
        return user;
    }

    public String signUp(String[] userInfo) {
        String stUser = users.get(userInfo[0]);
        if (stUser == null) {
            User user = new User(userInfo[0], userInfo[2], null);
            users.put(userInfo[0], userInfo[1]);
            Helper.writeToFile(users, "files/users.obj");
            Helper.writeToFile(user, "files/users/" + userInfo[0] + ".usr");
            return "User registered";
        } else {
            return "Username already taken";
        }
    }



    public static void main(String[] args) {
        Controller controller = new Controller();
    }
}