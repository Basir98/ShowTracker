package showtracker.server;

import showtracker.Envelope;
import showtracker.Helper;
import showtracker.Show;
import showtracker.User;

import java.io.File;
import java.util.HashMap;

public class Controller {
	private DatabaseReader dbr = new DatabaseReader();
	private GUI gui = new GUI(this);
	private Connection connection = new Connection(this);
	private HashMap<String, String> users = new HashMap<>();

	private Controller() {
		gui.start();
		File folFiles = new File("files/");
		File folUsers = new File("files/users/");
		if (!folFiles.exists())
			folFiles.mkdir();
		if (!folUsers.exists())
			folUsers.mkdir();

		if (new File("files/users.obj").exists())
			users = (HashMap<String, String>) Helper.readFromFile("files/users.obj");
		if (new File("files/token.obj").exists())
			dbr.setToken((String) Helper.readFromFile("files/token.obj"));
	}

	Envelope receiveEnvelope(Envelope envInput) {
		Envelope returnEnvelope = null;

		switch (envInput.getType()) {
			case "searchShows":
				String strSearchTerms = (String) envInput.getContent();
				String[][] strArrResponse = dbr.searchTheTVDBShows(strSearchTerms);
				returnEnvelope = new Envelope(strArrResponse, "shows");
				break;
			case "getShow":
				String[] strArrEpisodeQuery = (String[]) envInput.getContent();
				Show show = dbr.generateShow(strArrEpisodeQuery);
				returnEnvelope = new Envelope(show, "show");
				break;
			case "signUp":
				String[] strArrSignup = (String[]) envInput.getContent();
				returnEnvelope = signUp(strArrSignup);
				break;
			case "checkName":
				String username = (String) envInput.getContent();
				returnEnvelope = new Envelope(users.containsKey(username), "checkUsername");
				break;
			case "logIn":
				String[] strArrLogin = (String[]) envInput.getContent();
				returnEnvelope = loginUser(strArrLogin);
				break;
			case "updateUser":
				User usrUpdate = (User) envInput.getContent();
				returnEnvelope = updateUser(usrUpdate);
				break;
			case "updateShow":
				Show shwUpdate = (Show) envInput.getContent();
				shwUpdate = dbr.updateShow(shwUpdate);
				returnEnvelope = new Envelope(shwUpdate, "updated");
				break;
			case "updatePassword":
				String[] strArrPassword = (String[]) envInput.getContent();
				returnEnvelope = updatePass(strArrPassword);
				break;
		}
		return returnEnvelope;
	}

	private Envelope updatePass(String[] strArrUserInfo) {
		String strPassword = users.get(strArrUserInfo[0]);
		if (strPassword.equals(strArrUserInfo[1])) {
			users.put(strArrUserInfo[0], strArrUserInfo[2]);

			return new Envelope("Password changed", "reply");
		} else {
			return new Envelope("No match with current password!", "reply");
		}
	}

	private Envelope signUp(String[] strArrUserInfo) {
		String strUser = users.get(strArrUserInfo[0]);
		if (strUser == null) {
			User user = new User(strArrUserInfo[0], strArrUserInfo[2], null);
			synchronized (this) {
				users.put(strArrUserInfo[0], strArrUserInfo[1]);
				Helper.writeToFile(users, "files/users.obj");
				Helper.writeToFile(user, "files/users/" + strArrUserInfo[0] + ".usr");
			}
			return new Envelope("User registered", "signin");
		} else {
			return new Envelope("Username already taken", "signin");
		}
	}

	private Envelope loginUser(String[] strArrUserInfo) {
		User user = null;
		String strPassword = users.get(strArrUserInfo[0]);
		if (strPassword != null && strPassword.equals(strArrUserInfo[1]))
			user = (User) Helper.readFromFile("files/users/" + strArrUserInfo[0] + ".usr");
		return new Envelope(user, "user");
	}

	private Envelope updateUser(User user) {
		if (user != null) {
			Helper.writeToFile(user, "files/users/" + user.getUserName() + ".usr");
			return new Envelope("Profile saved", "confirmation");
		} else {
			return new Envelope("Failed to save profile.", "rejection");
		}
	}

	void startConnection(int threads) {
		connection.startConnection(threads);
	}

	void stopConnection() {
		System.out.println("Controller exiting...");
		connection.stopConnection();
		System.out.println("Controller exited.");
	}

	void setThreadCount(int intThreads) {
		gui.setActiveThreads(intThreads);
	}

	String authenticateTheTVDB() {
		String strToken = dbr.authenticateTheTVDB();
		Helper.writeToFile(strToken, "files/token.obj");
		return strToken;
	}
	
	public static void main (String[] args) {
		Controller c = new Controller ();

	}
}