package Client;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class User {


	private ImageIcon profilePicture;
	@SuppressWarnings("unused")
	private String username, userPassword, userEmail;

	private ArrayList<String> shows;
	@SuppressWarnings("unused")
	private ClientController cc;
	
	public User(String name, String userPassword, String userEmail, ImageIcon profilePicture) {
		this.username = name;
		this.userPassword = userPassword;
		this.profilePicture = profilePicture;
		this.userEmail = userEmail;
	}
			
	
	public String getUserName() {
		return username;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = new ImageIcon(profilePicture);
	}
	public ImageIcon getProfilePicture() {
		return profilePicture;
	}
	public void setShows(String [] shows) {
		int counter = 0;
		while(shows.length!=counter) {
			this.shows.add(shows[counter]);
			counter++;
		}
	}
	public ArrayList<String> getShows() {
		return shows;
	}
}
