package showtracker;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class User implements Serializable {

	private static final long serialVersionUID = -6358452193067562790L;
	private transient ImageIcon profilePicture;
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

	public void setShows(ArrayList<Show> shows) {
		for (Show s: shows) {
			this.shows.add(s);
		}
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