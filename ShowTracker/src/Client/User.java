package Client;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class User {
	
	private ImageIcon profilePicture;
	private String username;
	private String userPassword;

	private ArrayList<String> shows;
	private ClientController cc;
	
	public User(String name, String userPassword, String userEmail, ImageIcon profilePicture) {
		this.username = name;
		this.userPassword = userPassword;
		this.profilePicture = profilePicture;
		this.cc = cc;
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
	public void setShows(String [] show) {
		int counter = 0;
		while(show.length!=counter) {
			shows.add(show[counter]);
			counter++;
		}
	}
	public ArrayList<String> getShows() {
		return shows;
	}
}
