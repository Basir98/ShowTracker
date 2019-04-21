package showtracker.client;

import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import showtracker.Show;
import showtracker.User;

public class ClientController {

	private String userName, userPassword, email;
	private ArrayList<User> userAL = new ArrayList<User>();
	private ArrayList<Show> shows = new ArrayList<>();
	private User currentUser;

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

	public void signUp(String userName, String userPassword, String email) {

		User newUser = new User(userName, userPassword, email, new ImageIcon("images/defaultPicture.jpg"));
		userAL.add(newUser);
	}

	public void fyllUsers() {
		User user1 = new User("namn1", "Losenord1", "email1", new ImageIcon("images/defaultPicture.jpg"));
		User user2 = new User("namn2", "losenord2", "email2", new ImageIcon("images/defaultPicture.jpg"));
		User user3 = new User("namn3", "losenord3", "email3", new ImageIcon("images/defaultPicture.jpg"));
		userAL.add(user1);
		userAL.add(user2);
		userAL.add(user3);

	}

	public void fyllTVShows() {
		Show show1 = new Show("Game of Thrones");
		Show show2 = new Show("Game of Vikings");
		Show show3 = new Show("Prison break");
		Show show4 = new Show("Breaking bad");
		Show show5 = new Show("Vikings");
		Show show6 = new Show("Musti");
		Show show7 = new Show("1");
		Show show8 = new Show("2");
		Show show9 = new Show("3");
		Show show10 = new Show("4");
		Show show11 = new Show("5");
		Show show12 = new Show("6");
		Show show13 = new Show("7");
		Show show14 = new Show("8");
		Show show15 = new Show("9");
		Show show16 = new Show("10");


//		Show[] shows = { show1, show2, show3, show4, show5, show6, show7, show8, show9, show10, show11, show12,show13,show14,show15,show16 };
		Show[] shows = {show1,show2,show3,show4, show5 , show6};
//		Show[] shows = {show1,show2,show3,show4};
//		Show[] shows = {show1,show2};

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

	public void setEmail(String email) {
		userAL.get(0).setEmail(email);
	}

	public void setPassword(String pass) {
		userAL.get(0).setUserPassword(pass);
	}

	public User getUser(int index) {
		return userAL.get(index);
	}

	public ArrayList<Show> getShow() {
		return userAL.get(0).getShows();
	}

	public boolean containsShow(Show show) {
		return userAL.get(0).containsShow(show);
	}

	public User getCurrentUser() {
		return currentUser;
	}

}
