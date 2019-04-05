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
	
	
	private JPanel jpShowList = new JPanel();
	private JPanel searchBarJP = new JPanel();
	
	private JScrollPane jspShowList = new JScrollPane();
	
	
	private ImageIcon image;

	private JButton button1 = new JButton("Profile");
	private JButton button2 = new JButton();
	private JButton button3 = new JButton("");
	private JButton button4 = new JButton("Exit");
	
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
		add(jspShowList, BorderLayout.CENTER);
		add(bottomPanel(),BorderLayout.SOUTH);
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
				jpShowList.removeAll();
				search(searchBarTF.getText());
				jpShowList.repaint();
			}
		});

		searchBarJP.add(searchBarTF);
		searchBarJP.add(searchBarBtn);
		jspShowList.setViewportView(jpShowList);
		jspShowList.setLayout(new ScrollPaneLayout());

	}

	protected void search(String searchRequest) {

		Show showRequest = new Show(searchRequest);
		jpShowList.setLayout(new GridLayout(db.getShows().size(),2));
		if (db.containsShow(showRequest)) {
			databasResponse = db.getShows();
			System.out.println("SHOW HITTAT");
			updateShowListPanel();
		} else {
			System.out.println("SHOW EJ HITTAT");
			jpShowList.removeAll();
			jpShowList.repaint();

		}

	}

	private void updateShowListPanel() {
		// TODO se till att det max händer en gång.

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
		if(jb.getText().contains(s.substring(s.length()-1))) {
			jb.setText("REMOVE");
			System.out.println(s + " is added to list");
//			jpShowList.setVisible(false);
		}
		else {
			jb.setText("add"+s.substring(s.length()-1));
			System.out.println(s + " is removed from list");

		}

	}
	public JPanel bottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 4, 1, 1));
		image = new ImageIcon("images/home-screen.png");
		Image img = image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon imgIcon = new ImageIcon(img);
		button2.setIcon(imgIcon);
//		button1.setIcon(new ImageIcon("images/home-screen.png"));

		bottomPanel.add(button1);
		bottomPanel.add(button2);
		bottomPanel.add(button3);
		bottomPanel.add(button4);

		return bottomPanel;

	}
	
	public static void main(String[] args) {
		ClientController cc = new ClientController();
		User user = cc.getUser(2);
		SearchShows ss = new SearchShows(user);
	}

}
