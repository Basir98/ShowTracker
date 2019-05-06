package showtracker.client;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import showtracker.Helper;
import showtracker.Show;
import showtracker.User;

public class ClientController extends JFrame {

    private User user;
    private Profile pnlProfile;
    private ShowList pnlShowList;
    private Home pnlHome;
    private SearchShows pnlSearchShows;
    private ShowInfoNEp ShowInfoNEp;
    private JFrame frame = new JFrame();
    private JPanel centerPanel = new JPanel();
    private Connection connection = new Connection("127.0.0.1", 5555);

    public void iniatePanels() {
      pnlProfile = new Profile(this);
      pnlShowList = new ShowList(this);
      pnlHome = new Home(this);
      pnlSearchShows = new SearchShows(this);
    }

    public User signIn(String username, String userPassword) {
    	return connection.login(username, userPassword);
    }

    public void signUp(String userName, String userPassword, String email) {
    	connection.signUp(userName, userPassword, email);
    	
    	setUser(signIn(userName,userPassword));
    	startApplication();
    }

    public ImageIcon getProfilePicture() {
        return user.getProfilePicture();
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    public ArrayList<Show> getShows() {
        return user.getShows();
    }

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
    	this.user= user;
    }

    public void startApplication() {
        ImageIcon image = new ImageIcon("images/home-screen.png");
        Image img = image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon imgIcon = new ImageIcon(img);

        JButton button1 = new JButton("Profile");
        JButton button2 = new JButton("My-List");
        JButton button3 = new JButton("Home");
        JButton button4 = new JButton("Search");
        JButton button5 = new JButton("Exit");

        button1.addActionListener(e -> setPanel("Profile", null));
        button2.addActionListener(e -> setPanel("ShowList", null));
        button3.addActionListener(e -> setPanel("Home", null));
        button4.addActionListener(e -> setPanel("SearchShows", null));

        //		button1.setIcon(image);
        //		button2.setIcon(imgIcon);
        //		button3.setIcon(imgIcon);
        //		button4.setIcon(imgIcon);
        //		button5.setIcon(defaultIcon);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 1, 1));

        bottomPanel.add(button1);
        bottomPanel.add(button2);
        bottomPanel.add(button3);
        bottomPanel.add(button4);
        bottomPanel.add(button5);

        //		frame.setLayout(new BorderLayout());
        //		panel.setSize(new Dimension(250,300));
        //frame.add(pnlHome, BorderLayout.CENTER);
        centerPanel.setLayout(new CardLayout());
        centerPanel.add(pnlProfile, "Profile");
        centerPanel.add(pnlShowList, "ShowList");
        centerPanel.add(pnlHome, "Home");
        centerPanel.add(pnlSearchShows, "SearchShows");
//    	centerPanel.add(new ShowInfoNEp((Show) Helper.readFromFile("files/venture_bros.obj"),this), "Info");
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setSize(new Dimension(350, 500));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void exit() {
        connection.updateUser(user);
        System.exit(0);
    }

    public void setPanel(String panel, Show s) {
        CardLayout cl = (CardLayout)(centerPanel.getLayout());
        if(panel.equals("Info")) {
        	centerPanel.add(new ShowInfoNEp(s,this), "Info");
        } else if (panel.equals("ShowList"))
            pnlShowList.drawShowList(user.getShows());
        else if (panel.equals("Home"))
            pnlHome.draw();
        cl.show(centerPanel, panel);
    }
    
    public String[][] searchShows(String searchTerms) {
//    	String [][]temp  = {{"show1" , "id1"},{"show2" , "id2"},{"show3" , "id3"},{"show4" , "id4"},{"show5" , "id5"},{"show6" , "id6"},{"show7" , "id7"},{"show8" , "id8"},{"show9" , "id9"},{"show10" , "id10"},{"show11" , "id11"},{"show12" , "id12"},{"show13" , "id13"},{"show14" , "id14"},{"show15" , "id15"},{"show16" , "id16"}};
    	return connection.searchShows(searchTerms);
//    	return temp;
    }
    public void addShow(String showname) {
    	Show show = new Show(showname);
		user.addShow(show);
    }

    public void removeShow(String showname) {
    	Show show = new Show(showname);
    	user.removeShow(show);
    }
    public void createShow(String showname, int[][] nbrOfSeasonsandEpisodes) {
    	
    }
	public void generateShow(String showname,String showID) {
		String[] generateShowRequest  = {showname,showID};
		Show show = connection.getShow(generateShowRequest);
		user.addShow(show);
	}
	public static void main(String[] args) {
        ClientController cc = new ClientController();
        cc.setUser(new User("test", "Test1234", "test@test.se", null));
        cc.iniatePanels();
        cc.startApplication();
//        showUsers() ;
    }
    public static void showUsers() {
        HashMap<String, String> users = (HashMap<String, String>) Helper.readFromFile("files/users.obj");
        for (Map.Entry<String, String> e: users.entrySet())
            System.out.println("Username: " + e.getKey() + ", password: " + e.getValue());
    }

}

