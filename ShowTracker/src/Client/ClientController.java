package Client;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class ClientController {

	private String username, userPassword, email;
	
	
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
		
	}

}
