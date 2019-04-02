package showtracker.client;


import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import showtracker.Show;
import showtracker.User;

public class ClientController {

	private String username, userPassword, email;
	private ArrayList<User> userAL = new ArrayList<User>();

	public ClientController() {
		fyllUsers();
	}

	public void signIn(String usernameInp, String userPasswordInp) throws FileNotFoundException {

		Scanner scan = new Scanner(new File("files/credentials.txt"));
		String user = scan.nextLine();
		String pass = scan.nextLine();

		if (usernameInp.equals(user) && userPasswordInp.equals(pass)) {
			JOptionPane.showMessageDialog(null, "Signed in!");
		} else {
			JOptionPane.showMessageDialog(null, "your error message");
		}
	}

	public void signUp(String username, String userPassword, String email) {

		User newUser = new User(username, userPassword, email, new ImageIcon("images/defaultPicture.jpg"));
		userAL.add(newUser);
	}

	public void fyllUsers() {
		User user1 = new User("namn1", "losenord1", "email1", new ImageIcon("images/defaultPicture.jpg"));
		User user2 = new User("namn2", "losenord2", "email2", new ImageIcon("images/defaultPicture.jpg"));
		User user3 = new User("namn3", "losenord3", "email3", new ImageIcon("images/defaultPicture.jpg"));
		userAL.add(user1);
		userAL.add(user2);
		userAL.add(user3);

	}
	public void fyllShows() {
		Show show1 = new Show("Game of Thrones");
		Show show2 = new Show("The Walking dead");
		Show show3 = new Show("Prison break");
		Show show4 = new Show("Breaking bad");

		Show [] shows = {show1,show2,show3,show4};

		userAL.get(0).setShows(shows);
	}

	public ImageIcon getProfilePicture() {
		return userAL.get(0).getProfilePicture();
	}

	public String getUserEmail() {
		return userAL.get(0).getEmail();
	}

	public String getUserName() {
		return userAL.get(0).getUserName();
	}
	public String getUserPassword() {
		return userAL.get(0).getUserPass();
	}

	public void setEmail(String mail) {
		userAL.get(0).setEmail(mail);
	}
	public void setPassword(String pass) {
		userAL.get(0).setUserPassword(pass);
	}
	
	public User getUser (int index) {
		return userAL.get(index);
	}
	
	public ArrayList<Show> getShow() {
		return userAL.get(0).getShows();
	}
}
