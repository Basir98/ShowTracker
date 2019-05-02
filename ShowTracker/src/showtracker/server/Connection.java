package showtracker.server;

import showtracker.Envelope;
import showtracker.Helper;
import showtracker.Show;
import showtracker.User;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * @author Filip Spånberg
 * Connection hanterar kopplingen mellan klient och server
 */
public class Connection {
    private boolean isOnline = false;
    private Controller controller;
    private Buffer<Socket> socketBuffer = new Buffer<>();
    private int activeThreads = 0;
    private LinkedList<Thread> threads = new LinkedList<>();

    public Connection(Controller controller) {
        this.controller = controller;
    }

    public void startConnection(int inThreads) {
        if (!isOnline) {
            isOnline = true;
            for (int i = 0; i < inThreads; i++) {
                EventHandler thread = new EventHandler();
                thread.start();
                threads.add(thread);
            }
            SocketListener thread = new SocketListener();
            thread.start();
            threads.add(thread);
        }
    }

    public void stopConnection() {
        System.out.println("Connection exiting...");
        if (isOnline) {
            isOnline = false;
        }
        for (Thread t : threads) {
            try {
                t.interrupt();
            } catch (Exception e) {
                System.out.println("Connection: " + e);
            }
        }
        threads.clear();
        controller.setThreadCount(0);
        System.out.println("Connection exited.");
    }

    private synchronized void increaseThreadCount() {
        controller.setThreadCount(activeThreads++);
    }

    private synchronized void decreaseThreadCount() {
        controller.setThreadCount(activeThreads--);
    }

    private class SocketListener extends Thread {
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(5555)) {
                while (isOnline) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Found a connection");
                    socketBuffer.put(clientSocket);
                }
            } catch (Exception e) {
                System.out.println("SocketListener: " + e);
            }
        }
    }

    private class EventHandler extends Thread {

        public void run() {
            System.out.println("Starting eventhandler...");
            while (isOnline) {
                Socket socket = socketBuffer.get();
                System.out.println("Eventhandler got a socket. Processing...");
                increaseThreadCount();
                try {
                    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                    Envelope e = (Envelope) ois.readObject();
                    System.out.println("Envelope received. Type: " + e.getType());
                    Envelope returnEnvelope = null;
                    switch (e.getType()) {
                        case "searchShows":
                            String[][] response = controller.getShows((String) e.getContent());
                            returnEnvelope = new Envelope(response, "shows");
                            break;
                        case "getShow":
                            String[] episodeQuery = (String[]) e.getContent();
                            Show show = controller.getEpisodes(episodeQuery);
                            returnEnvelope = new Envelope(show, "show");
                            break;
                        case "login":
                            String[] login = (String[]) e.getContent();
                            User user = controller.loginUser(login);
                            returnEnvelope = new Envelope(user, "user");
                            break;
                        case "signup":
                            String[] signup = (String[]) e.getContent();
                            String res = controller.signUp(signup);
                            returnEnvelope = new Envelope(res, "signin");
                            break;
                        case "updateUser":
                            User usUpdate = (User) e.getContent();
                            if (usUpdate != null) {
                                Helper.writeToFile(usUpdate, "files/users/" + usUpdate.getUserName() + ".usr");
                                returnEnvelope = new Envelope("Profile saved", "confirmation");
                            } else {
                                returnEnvelope = new Envelope("Failed to save profile.", "rejection");
                            }
                            break;
                        case "updateShow":
                            Show shUpdate = (Show) e.getContent();
                            shUpdate = controller.updateShow(shUpdate);
                            returnEnvelope = new Envelope(shUpdate, "updated");
                            break;
                    }
                    System.out.println(returnEnvelope);
                    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    oos.writeObject(returnEnvelope);
                    oos.flush();
                } catch (Exception e) {
                    System.out.println("EventHandler: " + e);
                } finally {
                    decreaseThreadCount();
                }
            }
        }
    }
}