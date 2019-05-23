package showtracker.server;

import showtracker.Envelope;

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
class Connection {
    private boolean blnIsOnline = false;
    private Controller controller;
    private Buffer<Socket> socketBuffer = new Buffer<>();
    private int intActiveThreads = 0;
    private LinkedList<Thread> threads = new LinkedList<>();

    Connection(Controller controller) {
        this.controller = controller;
    }

    void startConnection(int inThreads) {
        if (!blnIsOnline) {
            blnIsOnline = true;
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

    void stopConnection() {
        System.out.println("Connection exiting...");
        if (blnIsOnline) {
            blnIsOnline = false;
        }
        for (Thread thread : threads) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                System.out.println("Connection: " + e);
            }
        }
        threads.clear();
        controller.setThreadCount(0);
        System.out.println("Connection exited.");
    }

    private synchronized void increaseThreadCount() {
        controller.setThreadCount(intActiveThreads++);
    }

    private synchronized void decreaseThreadCount() {
        controller.setThreadCount(intActiveThreads--);
    }

    private class SocketListener extends Thread {
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(5555)) {
                while (blnIsOnline) {
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
            while (blnIsOnline) {
                Socket socket = socketBuffer.get();
                System.out.println("Eventhandler got a socket. Processing...");
                increaseThreadCount();
                try {
                    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                    Envelope e = (Envelope) ois.readObject();
                    System.out.println("Envelope received. Type: " + e.getType());
                    Envelope returnEnvelope = controller.receiveEnvelope(e);
                    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    oos.writeObject(returnEnvelope);
                    oos.flush();
                    System.out.println("Return envelope sent.");
                } catch (Exception e) {
                    System.out.println("EventHandler: " + e);
                } finally {
                    decreaseThreadCount();
                }
            }
        }
    }
}