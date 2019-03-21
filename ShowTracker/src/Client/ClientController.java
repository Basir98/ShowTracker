package Client;

import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ClientController {
	//    private JLabel nameLabel = new JLabel();

	private String username, userPassword, email;
	private ArrayList<User> userAL = new ArrayList<User>();
	
	
	public void signIn(String usernameInp, String userPasswordInp) throws FileNotFoundException {

		Scanner scan = new Scanner (new File("files/befolkning.txt"));
	    String user = scan.nextLine();
	    String pass = scan.nextLine(); 

	    if (usernameInp.equals(user) && userPasswordInp.equals(pass)) {
	        JOptionPane.showMessageDialog(null, "Signed in!");
	    } else {
	    	 JOptionPane.showMessageDialog(null, "your error message");
	    }
	}

	public void signUp(String username, String userPassword, String email) {
		
		User newUser = new User( username, userPassword, email, new ImageIcon("files/defaultPicture.jpg"));
		userAL.add(newUser);
	}

}
