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
    private JPanel centerPanel = new JPanel();
    private Connection connection = new Connection("127.0.0.1", 5555);
    private JPanel bottomPanel;

    public void initiatePanels() {
        pnlProfile = new Profile(this);
        pnlShowList = new ShowList(this);
        pnlHome = new Home(this);
        pnlSearchShows = new SearchShows(this);

        CardLayout cl = (CardLayout) (centerPanel.getLayout());
        pnlShowList.draw();
        pnlHome.draw();
        pnlProfile.draw();  //ritar alla panelerna även om dem inte ska visas

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 5, 1, 1));

        generateNavigationButton("profile", "Profile", pnlProfile);
        generateNavigationButton("list", "ShowList", pnlShowList);
        generateNavigationButton("home", "Home", pnlHome);
        generateNavigationButton("search", "SearchShows", pnlSearchShows);
        generateNavigationButton("exit", "Logout", pnlLogin);

        cl.show(centerPanel, "Home"); // gör att home visas först
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void generateNavigationButton(String imagePath, String text, JPanel panel) {
        ImageIcon ii = new ImageIcon("images/" + imagePath + ".png");
        Image image = ii.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ii = new ImageIcon(image);
        JButton button = new JButton(ii);
        button.addActionListener(e -> setPanel(text, null));
        bottomPanel.add(button);
        centerPanel.add(panel, text);
    }

    public void startApplication() {

        centerPanel.setLayout(new CardLayout());

        pnlLogin = new Login(this);
        pnlLogin.draw();
        centerPanel.add(pnlLogin, "Logout");

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setSize(new Dimension(350, 500));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Making user the user is updated on exit
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                updateUser(user);
                ((JFrame) (e.getComponent())).dispose();
            }
        });
    }

    public void setPanel(String panel, Show s) {
        CardLayout cl = (CardLayout) (centerPanel.getLayout());

        if (panel.equals("Home"))
            pnlHome.draw();
        else if (panel.equals("ShowList"))
            pnlShowList.draw();
        else if (panel.equals("Logout")) {
            centerPanel.removeAll();
            bottomPanel.removeAll();
            bottomPanel.revalidate();
            pnlLogin.draw();
            pnlLogin.revalidate();
            new Thread(() -> updateUser(user)).run();
            startApplication();
        } else if (panel.equals("Info"))
            centerPanel.add(new ShowInfoNEp(s, this), "Info");

        cl.show(centerPanel, panel);
    }

    public User logIn(String username, String password) {
        String[] userInfo = {username, password};
        return (User) connection.packEnvelope(userInfo, "logIn");
    }

    public void signUp(String username, String password, String email) {
        String[] userInfo = {username, password, email};
        setUser((User) connection.packEnvelope(userInfo, "signUp"));
        logIn(username, password);
        initiatePanels();
    }

    public String updatePassword(String username, String oldPassword, String newPassword) {
        String[] updatePassword = {username, oldPassword, newPassword};
        return (String) connection.packEnvelope(updatePassword, "updatePassword");
    }

    public Show updateShow(Show show) {
        return (Show) connection.packEnvelope(show, "updateShow");
    }

    public String updateUser(User user) {
        return (String) connection.packEnvelope(user, "updateUser");
    }

    public String[][] searchShows(String searchTerms) {
        return (String[][]) connection.packEnvelope(searchTerms, "searchShows");
    }

    public void generateShow(String showname, String showID) {
        String[] generateShowRequest = {showname, showID};
        Show show = (Show) connection.packEnvelope(generateShowRequest, "getShow");
        user.addShow(show);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static void main(String[] args) {
        ClientController cc = new ClientController();
        cc.startApplication();
    }
}