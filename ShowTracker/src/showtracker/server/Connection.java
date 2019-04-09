package showtracker.server;

import showtracker.Show;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    private boolean isOnline = false;
    private Controller controller;

    public Connection(Controller controller) {
        this.controller = controller;
        new SocketListener().start();
    }

    private class SocketListener extends Thread{
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket()) {
                while (isOnline) {
                    Socket clientSocket = serverSocket.accept();
                    new EventHandler(clientSocket).start();
                }
            } catch (Exception e) {
                System.out.println("SocketListener: " + e);
            }
        }
    }

    private class EventHandler extends Thread {
        private Socket socket;

        public EventHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                 ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
                Object o = ois.readObject();
                if (o instanceof String[]) {
                    String[] query = (String[]) o;
                    if (query[0].equals("shows")) {
                        String[][] response = controller.getShows(query[1]);
                        oos.writeObject(response);
                        oos.flush();
                    } else  if (query[0].equals("episodes")) {
                        String[] episodeQuery = new String[2];
                        episodeQuery[0] = query[1];
                        episodeQuery[1] = query[2];
                        Show show = controller.getEpisodes(episodeQuery);
                        oos.writeObject(show);
                        oos.flush();
                    }
                }
            } catch (Exception e) {
                System.out.println("EventHandler: " + e);
            }
        }
    }
}