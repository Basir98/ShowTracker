package showtracker.client;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import showtracker.Show;

public class DatabasStub {
	private ArrayList <Show> shows = new ArrayList <Show>();

	public DatabasStub() {
		fyllShows();
	}

	public void fyllShows() {
		Show s1 =new Show ("show1");
		Show s2 = new Show ("show2");
		Show s3 = new Show ("show3");
		Show s4 = new Show ("show4");
		Show s5 = new Show ("show5");
		Show s6 = new Show ("show6");
		Show s7 = new Show ("show6");
		Show s8 = new Show ("show6");
		Show s9 = new Show ("show6");
		Show s11 = new Show ("show6");
		Show s23= new Show ("show6");
		Show s324= new Show ("show6");
		Show s342= new Show ("show6");
		Show s6342 = new Show ("show6");
		Show s6432 = new Show ("show6");
		Show s634322 = new Show ("show6");
		Show s64322 = new Show ("show6");

		shows.add(s1);
		shows.add(s2);
		shows.add(s3);
		shows.add(s4);
		shows.add(s5);
		shows.add(s6);
		shows.add(s324);
		shows.add(s342);
		shows.add(s6342);
		shows.add(s6432);
		shows.add(s634322);
		shows.add(s64322);
		shows.add(s7);
		shows.add(s8);
		shows.add(s9);
		shows.add(s11);
		shows.add(s23);
		shows.add(new Show("e"));
		shows.add(new Show("3"));
		shows.add(new Show("34"));
		shows.add(new Show("32"));
		shows.add(new Show("334"));
		shows.add(new Show("314"));

	}

	public ArrayList <Show> getShows() {

		return shows;
	}

	public boolean containsShow(Show show) {
		return shows.contains(show);
	}

	public static JPanel buttonPanel (JButton b1, JButton b2,JButton b3,JButton b4,JButton b5) {
		JPanel jp = new JPanel();

		return null;
	}

}
