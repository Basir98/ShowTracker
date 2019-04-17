package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import showtracker.Show;
import showtracker.User;

public class SearchShows extends JPanel {

	private DatabasStub db = new DatabasStub();
	private User user;
	private ClientController cc = new ClientController();
	private ArrayList<Show> databasResponse = new ArrayList<Show>();

	private JTextField tfSearchBar = new JTextField("Enter name of the show here");

	private JPanel jpSearchBar = new JPanel();
	private JPanel jpSearchResult = new JPanel();
	private JPanel jpMyOwnShowPanel = new JPanel();
	private JPanel jpMyShow = new JPanel();
	
	private JScrollPane jspSearchResult = new JScrollPane();

	private ImageIcon image;

	private JButton button1 = new JButton("Profile");
	private JButton button2 = new JButton();
	private JButton button3 = new JButton("");
	private JButton button4 = new JButton("Exit");
	private JButton btnCreateOwnShow;
	public SearchShows(User user) {
		this.user = user;
		draw();
	}

	private void draw() {

		drawSearchBarPanel();
		drawButtonPanel();
		
		setLayout(new BorderLayout());
	
		add(jpSearchBar, BorderLayout.NORTH);
		add(jspSearchResult, BorderLayout.CENTER);
		add(bottomPanel(),BorderLayout.SOUTH);

	}

	public static void drawButtonPanel() {
		// TODO Auto-generated method stub

	}

	private void drawSearchBarPanel() {
		jpSearchBar.setBackground(Color.GREEN);
		jpSearchBar.setSize(350, 100);

		JButton searchBarBtn = new JButton("search");
		searchBarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				drawSearchResultPanel(tfSearchBar.getText());
			}
		});
		jpSearchBar.add(tfSearchBar);
		jpSearchBar.add(searchBarBtn);
	}

	private void drawSearchResultPanel(String searchRequest) {
		jpSearchResult.removeAll();
		Show showRequest = new Show(searchRequest);
		if (db.containsShow(showRequest)) {
			jpSearchResult.setLayout(new GridLayout(db.getShows().size(),2));
			System.out.println("SHOW HITTAT");
			updateSearchResults();
		} else {
			jpSearchResult.setSize(345, 300);// TODO: hitta bättre lösning
			jpSearchResult.setLayout(new GridLayout(2,1));
			System.out.println("SHOW EJ HITTAT");
			searchRequest = "<html>" + "Your Search '" + searchRequest + "' was not found <br>" 
					+ "tips:<br>" 
					+ "- Make sure all word are spelled correctly<br>"
					+ "- Try different keywords<br>"
					+ "- or click the button below to create your own tracker =)" + "</html>";

			JLabel lbl = new JLabel("<html><font size = '3', padding-left: 50px>"  +searchRequest +"</font></html>");
			//			lbl.setHorizontalAlignment(JLabel.CENTER);
			lbl.setPreferredSize(new Dimension(jpSearchResult.getWidth()-5,jpSearchResult.getHeight()/2));
			btnCreateOwnShow = new JButton();
			btnCreateOwnShow.setIcon(new ImageIcon(new ImageIcon("images/add.png").getImage().getScaledInstance((jpSearchResult.getWidth()/2-50), (jpSearchResult.getHeight()/2-50), Image.SCALE_SMOOTH)));
			btnCreateOwnShow.addActionListener(e -> createMyOwnShowPanel());
			jpSearchResult.add(lbl);
			jpSearchResult.add(btnCreateOwnShow);
		}

		jspSearchResult.setViewportView(jpSearchResult);

	}

	protected void createMyOwnShowPanel() {
		jpSearchResult.removeAll();
		jpMyOwnShowPanel.removeAll();
		jpSearchResult.setLayout(new BorderLayout());
		jpMyOwnShowPanel.setLayout(new BoxLayout(jpMyOwnShowPanel, BoxLayout.Y_AXIS));
		JTextField tfshowName = new JTextField(tfSearchBar.getText());
		JButton submit = new JButton("Submit");
		JTextField tfNbrOfSeasons = new JTextField();
		jpMyOwnShowPanel.add(new JLabel("name: "));
		jpMyOwnShowPanel.add(tfshowName);
		jpMyOwnShowPanel.add(new JLabel ("Number of Seasons"));
		jpMyOwnShowPanel.add(tfNbrOfSeasons);
		jpMyOwnShowPanel.add(submit, BorderLayout.SOUTH);
		submit.addActionListener(e->createMyShow(tfNbrOfSeasons.getText()));
		jpSearchResult.add(jpMyOwnShowPanel, BorderLayout.NORTH);
		jspSearchResult.setViewportView(jpSearchResult);

	}


	private void createMyShow(String input) {
		try 
		{ 
			jpMyShow.removeAll();
			int nbrOfSeasons = Integer.parseInt(input); 
			jpMyShow.setLayout(new BoxLayout(jpMyShow, BoxLayout.Y_AXIS));
			
			JButton submit = new JButton("Submit");
			
			
			for(int i = 0 ; i< nbrOfSeasons ; i++) {
				JTextField tfNbrOfEpisodes = new JTextField();
				tfNbrOfEpisodes.setSize(new Dimension(400,30));
				jpMyShow.add(new JLabel("Season" + (i+1) + " :"));
				jpMyShow.add(tfNbrOfEpisodes);
			}  
			jpMyShow.add(submit);
//			jspMyShow.setViewportView(jpMyShow);
			jpSearchResult.add(jpMyShow);
			jspSearchResult.setViewportView(jpSearchResult);


		}catch (NumberFormatException e)  
		{ 
			System.out.println(input + " is not a valid integer number"); 
		}

	}


	private void updateSearchResults() {
		// TODO se till att det max händer en gång.
		databasResponse = db.getShows();

		int i = 1;
		for (Show s : databasResponse) {
			JButton btnAdd = new JButton("add" + i);
			jpSearchResult.add(new JLabel(s.getName()));
			jpSearchResult.add(btnAdd);
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addRemove(s.getName(), btnAdd);
				}

			});

			i++;
		}
	}

	protected void addRemove(String show, JButton btnAdd) {
		// TODO Auto-generated method stub
		if(btnAdd.getText().contains(show.substring(show.length()-1))) {
			btnAdd.setText("REMOVE");
			System.out.println(show + " is added to list");
			user.setShows(new Show[] {new Show(show)});
		}
		else {
			btnAdd.setText("add"+show.substring(show.length()-1));
			System.out.println(show + " is removed from list");
			user.removeShow(new Show(show));
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
		
		JFrame frame = new JFrame();
		frame.add(ss);
		frame.setSize(new Dimension(350, 500));
		frame.setVisible(true);
	}

}