package showtracker;

import java.util.LinkedList;

public class Season {
    private String name;
    private LinkedList<Episode> episodes;

    public boolean containsEpisode(Episode episode) {
        return episodes.contains(episode);
    }
}
