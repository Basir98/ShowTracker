package showtracker.client;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import showtracker.Show;
import showtracker.User;

public class ClientController {

    private User user;
    private Profile pnlProfile;
    private ShowList pnlShowList;
    private Home pnlHome;
    private SearchShows pnlSearchShows;
    private Login pnlLogin;
    private JFrame frame = new JFrame();
    private JPanel pnlCenter = new JPanel();
    private Connection connection = new Connection("127.0.0.1", 5555);
    private JPanel pnlBottom;

    /**
     * Method for setting all the main panels
     */
    private void initiatePanels() {
        pnlProfile = new Profile(this);
        pnlShowList = new ShowList(this);
        pnlHome = new Home(this);
        pnlSearchShows = new SearchShows(this);
        pnlLogin = new Login(this);

        pnlCenter.setLayout(new CardLayout());

        pnlBottom = new JPanel();
        pnlBottom.setLayout(new GridLayout(1, 5, 1, 1));

        generateNavigationButton("profile", "Profile", pnlProfile);
        generateNavigationButton("list", "ShowList", pnlShowList);
        generateNavigationButton("home", "Home", pnlHome);
        generateNavigationButton("search", "SearchShows", pnlSearchShows);
        generateNavigationButton("exit", "Logout", pnlLogin);

        setButtonsEnabled(false);

        frame.add(pnlBottom, BorderLayout.SOUTH);

        setPanel("Logout", null);
    }

    /**
     * Generate a menu button and adding it to the lower panel
     * @param strImagePath Path to the image used for the button
     * @param strText The String associated with the panel, for panel switch (see setPanel())
     * @param panel The panel to change to when the button is clicked
     */
    private void generateNavigationButton(String strImagePath, String strText, JPanel panel) {
        ImageIcon imageIcon = new ImageIcon("images/" + strImagePath + ".png");
        Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        JButton button = new JButton(imageIcon);
        button.addActionListener(e -> setPanel(strText, null));
        pnlBottom.add(button);
        pnlCenter.add(panel, strText);
    }

    /**
     * Starting a frame with the application
     */
    private void startApplication() {
        frame.add(pnlCenter, BorderLayout.CENTER);
        frame.setSize(new Dimension(350, 500));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setTitle("ShowTracker");

        // Making sure the user is updated on exit
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                updateUser(user);
                ((JFrame) (e.getComponent())).dispose();
            }
        });
    }

    /**
     * Setting which panel to show
     * @param strPanel String name of the panel (is set in generateNavigationButtons())
     * @param show Which Show to display (can be null in all panels except for ShowInfo)
     */
    void setPanel(String strPanel, Show show) {
        CardLayout cardLayout = (CardLayout) (pnlCenter.getLayout());

        switch (strPanel) {
            case "Home":
                pnlHome.draw();
                break;
            case "ShowList":
                pnlShowList.draw();
                break;
            case "Profile":
                pnlProfile.draw();
                break;
            case "Logout":
                setButtonsEnabled(false);
                pnlLogin.draw();
                pnlLogin.revalidate();
                pnlSearchShows.draw();
                if (user != null)
                    new Thread(() -> updateUser(user)).run();
                break;
            case "Info":
                pnlCenter.add(new ShowInfo(show), "Info");
                break;
        }

        cardLayout.show(pnlCenter, strPanel);
    }

    /**
     * Set the menu buttons to enabled or not
     * @param blnEnabled
     */
    private void setButtonsEnabled(boolean blnEnabled) {
        Component[] buttons = pnlBottom.getComponents();
        for (Component component: buttons)
            component.setEnabled(blnEnabled);
    }

    /**
     * Log in a user
     * @param strUsername The user's username
     * @param strPassword The user's password
     * @return The logged in user's User object
     */
    User logIn(String strUsername, String strPassword) {
        String[] arrStrUserInfo = {strUsername, strPassword};
        return (User) connection.packEnvelope(arrStrUserInfo, "logIn");
    }

    /**
     * Sign up a new user
     * @param strUsername The user's username
     * @param strPassword The user's password
     * @param strEmail The user's e-mail
     */
    void signUp(String strUsername, String strPassword, String strEmail, String strImagePath) {
        String[] arrStrUserInfo = {strUsername, strPassword, strEmail};
        connection.packEnvelope(arrStrUserInfo, "signUp");
        finalizeUser(new User(strUsername, strEmail, strImagePath));
    }

    /**
     * Setting the application in usable mode after a user has logged in
     * @param user The logged in user
     */
    void finalizeUser(User user) {
    	System.out.println(user.getUserName());
        setUser(user);
        setButtonsEnabled(true);
        setPanel("Home", null);
        pnlProfile.draw();
        System.out.println("Welcome back!");
    }

    /**
     * Update a user's password
     * @param strUsername The user's username
     * @param strOldPassword The user's old password
     * @param strNewPassword The user's new password
     * @return The result of the password update
     */
    String updatePassword(String strUsername, String strOldPassword, String strNewPassword) {
        String[] arrStrUpdatePassword = {strUsername, strOldPassword, strNewPassword};
        return (String) connection.packEnvelope(arrStrUpdatePassword, "updatePassword");
    }

    /**
     * Updates a show with new available episodes
     * @param show The show to update
     * @return The updated Show object
     */
    Show updateShow(Show show) {
        return (Show) connection.packEnvelope(show, "updateShow");
    }

    /**
     * Send a User object to the server to write over the old version
     * @param user The User object to update
     * @return The result of the user update
     */
    private String updateUser(User user) {
        return (String) connection.packEnvelope(user, "updateUser");
    }

    /**
     * Searches for a show
     * @param strSearchTerms The search terms
     * @return A String array with names of shows and their corresponding IDs
     */
    String[][] searchShows(String strSearchTerms) {
        return (String[][]) connection.packEnvelope(strSearchTerms, "searchShows");
    }

    /**
     * Generates a Show object
     * @param strShowName The name of the show
     * @param strShowId The ID of the show
     */
    void generateShow(String strShowName, String strShowId) {
        String[] arrStrGenerateShowRequest = {strShowName, strShowId};
        Show show = (Show) connection.packEnvelope(arrStrGenerateShowRequest, "getShow");
        user.addShow(show);
    }

    /**
     * Returns the current User
     * @return The current User
     */
    User getUser() {
        return user;
    }

    /**
     * Sets a new User
     * @param user The User to set
     */
    void setUser(User user) {
        this.user = user;
    }

    /**
     * Starting the program
     * @param args
     */
    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        clientController.initiatePanels();
        clientController.startApplication();
    }
}