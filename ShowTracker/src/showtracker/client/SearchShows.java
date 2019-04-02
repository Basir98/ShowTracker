package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import showtracker.Show;
import showtracker.User;

public class SearchShows extends JFrame {

	private DatabasStub db = new DatabasStub();
	private User user;
	private ClientController cc = new ClientController();
	private ArrayList<Show> databasResponse = new ArrayList<Show>();

	private JPanel jpShowList = new JPanel(new GridLayout(10, 2));
	private JPanel searchBarJP = new JPanel();

	public SearchShows(User user) {
		this.user = user;
		draw();
	}

	private void draw() {
		drawSearchBarPanel();

		drawButtonPanel();
		new JFrame(user.getUserName());
		setLayout(new BorderLayout());
		add(searchBarJP, BorderLayout.NORTH);
		add(jpShowList, BorderLayout.CENTER);

		// frame.add();
		// frame.add();

		setSize(new Dimension(350, 500));
		setVisible(true);

	}

	public static void drawButtonPanel() {
		// TODO Auto-generated method stub

	}

	private void drawSearchBarPanel() {
		searchBarJP.setBackground(Color.GREEN);
		searchBarJP.setSize(350, 100);
		JTextField searchBarTF = new JTextField("Enter name of the show here");
		JButton searchBarBtn = new JButton("search");
		searchBarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search(searchBarTF.getText());
			}
		});

		searchBarJP.add(searchBarTF);
		searchBarJP.add(searchBarBtn);

	}

	protected void search(String searchRequest) {

		Show showRequest = new Show(searchRequest);

		if (db.containsShow(showRequest)) {
			databasResponse = db.getShows();
			System.out.println("SHOW HITTAT");
			updateShowListPanel();
		} else {
			System.out.println("SHOW EJ HITTAT");

		}

	}

	private void updateShowListPanel() {
		// TODO se till att det max händer en gång.
		jpShowList.removeAll();
		int i = 1;
		for (Show s : databasResponse) {
			JButton jb = new JButton("add" + i);
			jpShowList.add(new JLabel(s.getName()));
			jpShowList.add(jb);
			jb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonMetod(s.getName(), jb);
				}

			});
			// jpShowList.add(new JCheckBox("ADD"));
			jpShowList.revalidate();
			i++;
		}
	}

	protected void buttonMetod(String s, JButton jb) {
		// TODO Auto-generated method stub
		System.out.print(s);
		jb.setText("REMOVE");

	}

	public static void main(String[] args) {
		ClientController cc = new ClientController();
		User user = cc.getUser(2);
		SearchShows ss = new SearchShows(user);
	}

}
