package showtracker.client;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import showtracker.Helper;
import showtracker.Show;
import showtracker.User;

public class Controller extends JFrame {

    private User user;
    private Profile pnlProfile;
    private ShowList pnlShowList;
    private Home pnlHome;
    private SearchShows pnlSearchShows;
    private ShowInfoNEp ShowInfoNEp;
    private JFrame frame = new JFrame();
    private JPanel centerPanel = new JPanel();
    private Connection connection = new Connection("127.0.0.1", 5555);
    
    public Controller() {
        user = new User("namn1", "losenord1", "email1", new ImageIcon("images/defaultPicture.jpg"));
        user.setShows(DatabasStub.getShowsFromFile());
        System.out.println(user.getShows().get(0).getName());
        pnlProfile = new Profile(this);
        pnlShowList = new ShowList(this);
        pnlHome = new Home(this);
        pnlSearchShows = new SearchShows(this);
        
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
        //userAL.add(newUser);
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
        Controller cc = new Controller();
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


    public void setPanel(String panel, Show s) {
        CardLayout cl = (CardLayout)(centerPanel.getLayout());
        if(panel=="Info") {
        	centerPanel.add(new ShowInfoNEp(s,this), "Info");
        }
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
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.startApplication();
    }

	public void generateShow(String showname,String showID) {
		String[] generateShowRequest  = {showname,showID};
		Show show = connection.getShow(generateShowRequest);
		user.addShow(show);
	}
}