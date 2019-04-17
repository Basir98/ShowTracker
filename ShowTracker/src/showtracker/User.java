package showtracker;

import showtracker.client.ClientController;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import showtracker.client.ClientController;

public class User {

	private ImageIcon profilePicture;
	private String userName, userPassword, userEmail;

	private ArrayList<Show> shows = new ArrayList<Show>();
	private ClientController clientController;
	private String x;
	
	public User(String userName, String userPassword, String userEmail, ImageIcon profilePicture) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.profilePicture = profilePicture;
		this.userEmail = userEmail;
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

	public void setShows(Show[] shows) {
		for (int i = 0 ; i< shows.length; i++) {
			this.shows.add(shows[i]);
		}
	}
	public void removeShow(Show show) { // if satsen kanske inte behövs
		if( shows.contains(show)) {
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
