package showtracker;

import java.util.LinkedList;

public class Show {
    private String id;
    private String name;
    private LinkedList<Object> showParts;

    public Show(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Show))
            return false;

        Show s = (Show) o;

        return s.getId().equals(id);
    }

    public boolean containsEpisode(Episode episode) {
        for (Object o: showParts) {
            if (o instanceof Episode) {
                Episode e = (Episode) o;
                if (o.equals(e))
                        return true;
            } else if (o instanceof Season) {
                Season s = (Season) o;
                if (s.containsEpisode(episode))
                    return true;
            }
        }
        return false;
    }
}