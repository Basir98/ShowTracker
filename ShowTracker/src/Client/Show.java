package Client;

import java.util.LinkedList;

public class Show {
	private String showTitle;
	private int nbrOfSeasons;
	private String  showId;
	private LinkedList<Object>listOfSeasons = new LinkedList<Object>();
	
	public Show(String showTitle, String showId) {
		this.showTitle = showTitle;
		this.showId = showId;
	}
	public String getShowName(){
		return "Showname: ";
	}
	public int getNbrOfSeasons(){
	return 5;
	}
	public String getShowId(){
		return "ID000";
	}
	
	
}
