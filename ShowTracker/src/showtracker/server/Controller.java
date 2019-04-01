package showtracker.server;

import showtracker.Show;

import java.util.LinkedList;

public class Controller {
    private LinkedList<Show> shows = new LinkedList<>();

    public Controller() {
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public boolean containsShow(Show show) {
        return shows.contains(show);
    }
}
