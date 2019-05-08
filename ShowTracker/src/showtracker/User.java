package showtracker;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;

public class User implements Serializable {

	private static final long serialVersionUID = -6358452193067562790L;
	private transient ImageIcon profilePicture = null;
	private String userName, userPassword, userEmail;

	private ArrayList<Show> shows = new ArrayList<Show>();

	public User(String userName, String userPassword, String userEmail, ImageIcon profilePicture) {
		this.userName = userName;
		this.profilePicture = profilePicture;
		this.userEmail = userEmail;
	}

	public User(String userName, String userEmail, ImageIcon profilePicture) {
		this.userName = userName;
		this.profilePicture = profilePicture;
		this.userEmail = userEmail;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPass() {
		return userPassword;
	}

	public void setProfilePicture(ImageIcon profilePicture) {
		this.profilePicture = new ImageIcon();
	}

	public void setEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getEmail() {
		return userEmail;
	}

	public ImageIcon getProfilePicture() {
		return profilePicture;
	}
	
	public void addShow(Show show) {
		if (shows.contains(show)) {
			int i = 1;
			String newName;
			do {
				newName = show.getName() + " (" + i++ + ")";
			} while (shows.contains(new Show(newName)));
			do {
				newName = JOptionPane.showInputDialog("A show with that name already exists, please enter a new name.", newName);
			} while (shows.contains(new Show(newName)));
			if (newName != null)
				show.setName(newName);
		}
		shows.add(show);
	}

	public void removeShow(Show show) { // if satsen kanske inte behövs
		if (shows.contains(show)) {
			shows.remove(show);
		}
	}

	public ArrayList<Show> getShows() {
		return shows;
	}

//	public boolean containsShow(Show show) {
//		return shows.contains(show);
//	}

	public boolean containsShow(Show show) {
		return shows.contains(show);
	}

//	public ArrayLi

}