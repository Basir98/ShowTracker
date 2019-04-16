package showtracker;

import java.util.Collections;
import java.util.LinkedList;

public class Show {
    private String id;
    private String name;
    private LinkedList<Season> seasons = new LinkedList<>();

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

    public LinkedList<Season> getSeasons() {
        return seasons;
    }

    public Season addSeason(int number) {
        Season s;
        if (!seasons.contains(new Season(number))) {
            s = new Season(number);
            seasons.add(s);
            Collections.sort(seasons);
        } else {
            s = getSeason(number);
        }
        return s;
    }

	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Show))
			return false;

		Show s = (Show) o;

		return s.getName().equals(name);
	}
	
    public Season getSeason(int number) {
        for (Season s : seasons)
            if (s.getNumber() == number)
                return s;
        return null;
    }
}