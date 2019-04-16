package showtracker;

public class Episode implements Comparable<Episode> {
    private String tvdbId;
    private String imdbId;
    private String name;
    private int number;
    private Season season;
    private String description;

    public Episode(int episodeNumber, Season season) {
        this.number = episodeNumber;
        this.season = season;
    }

    public void setTvdbId(String id) {
        tvdbId = id;
    }

    public String getTvdbId() {
        return tvdbId;
    }

    public void setImdbId(String id) {
        imdbId = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setSeason(Season s) {
        season = s;
    }

    public Season getSeason() {
        return season;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Episode))
            return false;

        Episode e = (Episode) o;

        return e.getNumber() == number;
    }

    @Override
    public int compareTo(Episode o) {
        if (number > o.getNumber())
            return 1;
        else if (number < o.getNumber())
            return -1;
        else
            return 0;
    }
}