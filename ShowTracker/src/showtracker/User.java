package showtracker;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.*;
/**
 * 
 * @author Filip Spånberg
 * Represents a user
 */
public class User implements Serializable {

    private static final long serialVersionUID = -6358452193067562790L;
    private SerializableImage profilePicture = null;
    private String userName, userEmail;

    private ArrayList<Show> shows = new ArrayList<>();

    public User(String userName, String userEmail, String strImagePath) {
        this.userName = userName;
        if (strImagePath != null)
            this.profilePicture = new SerializableImage(strImagePath);
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setProfilePicture(String strImagePath) {
        this.profilePicture = new SerializableImage(strImagePath);
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getEmail() {
        return userEmail;
    }

    public ImageIcon getProfilePicture() {
        if (profilePicture != null)
            return profilePicture.getImageIcon();
        else
            return null;
    }

    /**
     * Adds a show to the user's library
     * @param show
     */
    public void addShow(Show show) {
        if (shows.contains(show)) {
            int i = 1;
            String newName;
            do {
                newName = show.getName() + " (" + i++ + ")";
            } while (shows.contains(new Show(newName)));
            do {
                newName = JOptionPane.showInputDialog("A show with that name already exists, please enter a new name.", newName);
            } while (shows.contains(new Show(newName)));
            if (newName != null)
                show.setName(newName);
        }
        shows.add(show);
    }

    /**
     * Updates a Show in the User's library
     * @param show
     */
    public void updateShow(Show show) {
        for (Show s : shows) {
            if (show.equals(s))
                for (Episode e : show.getEpisodes())
                    if (!s.containsById(e))
                        s.addEpisode(e);
            s.sortEpisodes();
        }
    }

    /**
     * Removes a Show from the User's library
     * @param show
     */
    public void removeShow(Show show) {
        if (shows.contains(show)) {
            shows.remove(show);
        }
    }

    public ArrayList<Show> getShows() {
        return shows;
    }

    /**
     * Checks if a User's library contains a show
     * @param show
     * @return
     */
    public boolean containsShow(Show show) {
        return shows.contains(show);
    }
}