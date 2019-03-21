package Client;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class User {
	
	private ImageIcon profilePicture;
	private String userName;
	private String userPassword;

	private ArrayList<String> shows;
//	private ClientController cc;
	
	public User(String name,String userPassword ,String profilePicture) {
		this.userName = name;
		this.userPassword = userPassword;
		this.profilePicture = new ImageIcon(profilePicture);
//		this.cc = cc;
	}

	public String getUserName() {
		return userName;
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
