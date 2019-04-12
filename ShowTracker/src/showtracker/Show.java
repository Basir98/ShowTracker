package showtracker;

import java.util.LinkedList;

public class Show {
	private String id;
	private String name;
	private LinkedList<Object> showParts;

	public Show(String name) 
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Show))
			return false;

		Show s = (Show) o;

		return s.getName().equals(name);
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