package showtracker;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

public class Show implements Serializable {
    private static final long serialVersionUID = -7641780883231752094L;
    private String id;
    private String name;
    private String description;
    private LinkedList<Episode> episodes = new LinkedList<>();

    public Show(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
        sortEpisodes();
    }

    public LinkedList<Episode> getEpisodes() {
        return episodes;
    }

    public void sortEpisodes() {
        Collections.sort(episodes);
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public LinkedList<Double> getSeasons() {
        LinkedList<Double> seasons = new LinkedList<>();
        for (Episode e : episodes)
            if (!seasons.contains(e.getSeasonNumber()))
                seasons.add(e.getSeasonNumber());
        Collections.sort(seasons);
        return seasons;
    }

    public LinkedList<Episode> getSeason(double d) {
        LinkedList<Episode> season = new LinkedList<>();
        for (Episode e : episodes)
            if (e.getSeasonNumber() == d)
                season.add(e);
        Collections.sort(season);
        return season;
    }

    public Episode getEpisode(double season, double episode) {
        for (Episode e : episodes)
            if (e.getSeasonNumber() == season && e.getEpisodeNumber() == episode)
                return e;
        return null;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Show))
            return false;

        Show s = (Show) o;

        return s.getName().equals(name);
    }
}