package showtracker;

import showtracker.client.ClientController;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import showtracker.client.ClientController;

public class User {

	private ImageIcon profilePicture;
	private String username, userPassword, userEmail;

	private ArrayList<String> shows;
	private ClientController cc;
	private String x;

	public User(String name, String userPassword, String userEmail, ImageIcon profilePicture) {
		this.username = name;
		this.userPassword = userPassword;
		this.profilePicture = profilePicture;
		this.userEmail = userEmail;
	}
			
	public String getUserName() {
		return username;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPass() {
		return userPassword;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = new ImageIcon(profilePicture);
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

	public void setShows(String[] shows) {
		int counter = 0;
		while (shows.length != counter) {
			this.shows.add(shows[counter]);
			counter++;
		}
	}

	public ArrayList<String> getShows() {
		return shows;
	}
	
}
