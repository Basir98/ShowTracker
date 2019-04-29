package showtracker.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import showtracker.Envelope;
import showtracker.Show;
import showtracker.User;

public class Connection {
    private String ip;
    private int port;

    public Connection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public User login(String username, String password) {
        String[] userInfo = {username, password};
        Envelope enLogin = new Envelope(userInfo, "login");
        Envelope returnEnvelope = sendEnvelope(enLogin);
        return (User) returnEnvelope.getContent();
    }

    public String signUp(String username, String password, String email) {
        String[] userInfo = {username, password, email};
        Envelope enSignUp = new Envelope(userInfo, "signup");
        Envelope returnEnvelope = sendEnvelope(enSignUp);
        return (String) returnEnvelope.getContent();
    }

    public String[][] searchShows(String searchTerms) {
        Envelope enSearchShows = new Envelope(searchTerms, "searchShows");
        Envelope returnEnvelope = sendEnvelope(enSearchShows);
        return (String[][]) returnEnvelope.getContent();
    }

    public Show getShow(String [] nameAndId) {
        Envelope enGetShows = new Envelope(nameAndId, "getShow");
        Envelope returnEnvelope = sendEnvelope(enGetShows);
        return (Show) returnEnvelope.getContent();
    }

    private Envelope sendEnvelope(Envelope envelope) {
        Envelope returnEnvelope = null;
        try {
        	Socket socket = null;
        	try {
                socket = new Socket(ip, port);
            } catch (IOException e) {
                System.out.println("Connection: " + e);
            }
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(envelope);
            oos.flush();
            System.out.println("Connection: Envelope sent.");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());  
            returnEnvelope = (Envelope) ois.readObject();
            System.out.println("Connection: Envelope received.");

        } catch (Exception e) {
            System.out.println("Connection: " + e);
        }
        return returnEnvelope;
    }

    public static void main(String[] args) {
        System.out.println("Connecting...");
        Connection c = new Connection("127.0.0.1", 5555);
        /*System.out.println("Signing up...");
        String s = c.signUp("Filip", "losenord", "f@s.se");
        System.out.println(s);*/
        User u = c.login("Filip", "losenord");
        if (u != null)
            System.out.println(u.getUserName() + " logged in");
        else
            System.out.println("There was a problem logging in!");
    }
}