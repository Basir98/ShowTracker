package showtracker.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import showtracker.User;

public class Connection {
	private ClientController clientConrtoller;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String ip;
	private int port;
	private boolean run = true;
	private ConnectionListener thread;

	public Connection(String ip, int port, ClientController clientController) throws UnknownHostException, IOException {
		this.clientConrtoller = clientController;

		socket = new Socket(ip, port);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		
		thread = new ConnectionListener();
		thread.start();
			

	}

	public void disconnect(User user) {
		try {
			oos.writeObject(user);
			oos.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

	private class ConnectionListener extends Thread {
		public void run() {
			try {
				oos.writeObject(clientConrtoller.getCurrentUser());
				oos.flush();

				while (run) {
					Object obj = ois.readObject();

//				if(obj )
					
					

				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
			}

		}

	}

}
