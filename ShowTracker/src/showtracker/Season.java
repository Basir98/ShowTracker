package showtracker;

import java.util.Collections;
import java.util.LinkedList;

public class Season extends LinkedList<Episode> implements Comparable {
    private String name;
    private int number;

    public Season(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Episode getEpisode(int number) {
        for (Episode e : this)
            if (e.getNumber() == number)
                return e;
        return null;
    }

    public boolean addEpisode(Episode episode) {
        if (!this.contains(episode)) {
            episode.setSeason(this);
            this.add(episode);
            Collections.sort(this);
            return true;
        } else
            return false;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(number, ((Season) o).getNumber());
    }

    public boolean equals(Object o) {
        if (o instanceof Season)
            return ((Season) o).getNumber() == number;
        else
            return false;
    }
}