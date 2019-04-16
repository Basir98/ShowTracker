package showtracker.server;

import showtracker.Show;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

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
        for (Thread t: threads) {
            try {
                t.interrupt();
            } catch (Exception e) {
                System.out.println("Connection: " + e);
            }
        }
        threads.clear();
        System.out.println("Connection exited.");
    }

    private synchronized void increaseThreadCount() {
        controller.setThreadCount(activeThreads++);
    }

    private synchronized void decreaseThreadCount() {
        controller.setThreadCount(activeThreads++);
    }

    private class SocketListener extends Thread{
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(5555)) {
                while (isOnline) {
                    Socket clientSocket = serverSocket.accept();
                    socketBuffer.put(clientSocket);
                }
            } catch (Exception e) {
                System.out.println("SocketListener: " + e);
            }
        }
    }

    private class EventHandler extends Thread {

        public void run() {
            while(isOnline) {
                Socket socket = socketBuffer.get();
                increaseThreadCount();
                try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                     ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
                    Object o = ois.readObject();
                    if (o instanceof String[]) {
                        String[] query = (String[]) o;
                        if (query[0].equals("shows")) {
                            String[][] response = controller.getShows(query[1]);
                            oos.writeObject(response);
                            oos.flush();
                        } else if (query[0].equals("episodes")) {
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
                } finally {
                    decreaseThreadCount();
                }
            }
        }
    }
}