package showtracker.server;

import showtracker.Show;


public class Controller {
    private DatabaseReader dbr = new DatabaseReader();

    public String[][] getShows(String search) {
        String[][] response = dbr.searchTheTVDBShows(search);
        return response;
    }

    public Show getEpisodes(String[] stShow) {
        Show show = dbr.generateShow(stShow);
        return show;
    }
}
