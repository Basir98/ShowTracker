package showtracker;

public class Episode {
    private String id;
    private String name;
    private int episodeNumber;
    private int seasonNumber;
    private String description;

    public Episode(int episodeNumber, int seasonNumber) {
        this.episodeNumber = episodeNumber;
        this.seasonNumber = seasonNumber;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Episode))
            return false;

        Episode e = (Episode) o;

        return e.getId().equals(id);
    }
}
