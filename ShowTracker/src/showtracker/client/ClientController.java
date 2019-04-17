package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;

import showtracker.Show;
import showtracker.User;

public class ClientController extends JFrame {

	private String userName, userPassword, email;
	private ArrayList<User> userAL = new ArrayList<User>();
	private ArrayList<Show> shows = new ArrayList<>();
	private User currentUser;
	private static JPanel panel = new JPanel();
	private static Profile pnlProfile;
	private static ShowList pnlShowList;
	private static ShowInfoNEp pnlShowInfo; //tillfällig ist för home
	//	private static Home pnlHome;
	private static SearchShows pnlSearchShows;

	public ClientController() {
		fyllUsers();
		try {
			pnlProfile = new Profile(this);
			pnlShowInfo = new ShowInfoNEp(new Show("Test"),this);
			//			pnlSearchShows = new SearchShows(this);
			//			pnlShowList = new ShowList(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

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
		User user1 = new User("namn1", "losenord1", "email1", new ImageIcon("images/defaultPicture.jpg"));
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

		Show[] shows = { show1, show2, show3, show4, show5, show6, show7, show8, show9, show10, show11, show12 };
		//		Show[] shows = {show1,show2,show3,show4, show5};
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

	public static void main (String [] args) {
		ClientController cc = new ClientController();
		JFrame frame = new JFrame();
		JPanel bottomPanel = new JPanel();
		ImageIcon image = new ImageIcon("images/home-screen.png");
		Image img = image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon imgIcon = new ImageIcon(img);

		JButton button1 = new JButton("Profile");
		JButton button2 = new JButton("My-List");
		JButton button3 = new JButton("Home");
		JButton button4 = new JButton("Search");
		JButton button5 = new JButton("Exit");

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					pnlProfile = new Profile(cc);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				panel.removeAll();
				panel = pnlProfile;
				frame.add(panel);
				frame.revalidate();
			}
		});

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				try {
					pnlShowList = new ShowList(cc);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				panel = pnlShowList;
				frame.add(panel);
				frame.revalidate();
			}

		});
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				pnlShowInfo = new ShowInfoNEp(new Show("test"), cc);
				panel = pnlShowInfo;
				frame.add(panel);
				frame.revalidate();
			}

		});
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				pnlSearchShows = new SearchShows(cc);
				panel = pnlSearchShows;
				frame.add(panel);
				frame.revalidate();
			}
		});
		//		button1.setIcon(image);
		//		button2.setIcon(imgIcon);
		//		button3.setIcon(imgIcon);
		//		button4.setIcon(imgIcon);
		//		button5.setIcon(defaultIcon);
		bottomPanel.setLayout(new GridLayout(1, 5, 1, 1));

		bottomPanel.add(button1);
		bottomPanel.add(button2);
		bottomPanel.add(button3);
		bottomPanel.add(button4);
		bottomPanel.add(button5);

		//		frame.setLayout(new BorderLayout());
		//		panel.setSize(new Dimension(250,300));
		frame.add(panel);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.setSize(new Dimension(350, 500));
		frame.setVisible(true);

	}
}
