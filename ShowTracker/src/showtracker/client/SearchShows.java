
package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import showtracker.Show;
import showtracker.User;

public class SearchShows extends JPanel {

	private static ClientController cc  = new ClientController();

	private DatabasStub db = new DatabasStub();
	private User user;
	private ArrayList<Show> databasResponse = new ArrayList<Show>();

	private JTextField tfSearchBar = new JTextField("Enter name of the show here");

	private JPanel jpSearchBar = new JPanel();
	private JPanel jpSearchResult = new JPanel();
	private JPanel jpMyOwnShowPanel = new JPanel();
	private JPanel jpMyShow = new JPanel();

	private JScrollPane jspSearchResult = new JScrollPane();

	private ImageIcon image;

	private JButton btnCreateOwnShow;

	public SearchShows(ClientController cc) {
		this.cc = cc;
		this.user = cc.getUser();
		draw();
	}

	private void draw() {

		drawSearchBarPanel();

		setLayout(new BorderLayout());

		add(jpSearchBar, BorderLayout.NORTH);
		add(jspSearchResult, BorderLayout.CENTER);

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
		String[][] searchResults = cc.searchShows(searchRequest);
		Show showRequest = new Show(searchRequest);
		if (searchResults != null) {
			jpSearchResult.setLayout(new GridLayout(searchResults.length,2));
			System.out.println("SHOW HITTAT");
			updateSearchResults(searchResults);
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
			btnCreateOwnShow.addActionListener(e -> drawNoSearchResultPanel());
			jpSearchResult.add(lbl);
			jpSearchResult.add(btnCreateOwnShow);
		}

		jspSearchResult.setViewportView(jpSearchResult);
	}
	
	private void updateSearchResults(String [] [] searchResults) {
		
		for (String[] s: searchResults){
			jpSearchResult.add(new JLabel(s[0]));
			JButton btnAdd = new JButton("add");
			jpSearchResult.add(btnAdd);
			btnAdd.addActionListener(new ActionListener() {
				boolean add = true;
				private String id = s[1];
				@Override
				public void actionPerformed(ActionEvent e) {
					String showname = s[0];
					String showID = s[1];

					if(add) {
						add = false;
						cc.generateShow(showname,showID);
					}
					else
						add = true;
					addRemove(s[0], btnAdd, add);
				}
			});
		}
		

	}

	protected void drawNoSearchResultPanel() {
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
		submit.addActionListener(e->createMyOwnShowPanel(tfSearchBar.getText(),tfNbrOfSeasons.getText()));
		jpSearchResult.add(jpMyOwnShowPanel, BorderLayout.NORTH);
		jspSearchResult.setViewportView(jpSearchResult);

	}


	private void createMyOwnShowPanel(String showname, String input) {
		try 
		{ 		
			int nbrOfSeasons = Integer.parseInt(input); 
			GridBagConstraints gbc = new GridBagConstraints();
			jpMyShow.setLayout(new GridBagLayout());
			gbc.fill = GridBagConstraints.HORIZONTAL;
			jpMyShow.removeAll();

			JPanel panel;
			ArrayList <JTextField> tfSeasons = new ArrayList();
			JButton submit = new JButton("Submit");

			for(int i = 0 ; i< nbrOfSeasons ; i++) {
				if(nbrOfSeasons <=5) {
					panel = new JPanel();
					panel.setPreferredSize(new Dimension(300,40));
					panel.setLayout(new GridLayout(2,1));
					JTextField tfNbrOfEpisodes = new JTextField();
					panel.add(new JLabel("Season" + (i+1) + " :"));
					tfSeasons.add(tfNbrOfEpisodes); // sätter in varje textfield i en array
					panel.add(tfNbrOfEpisodes);
					gbc.gridx = 0;
					gbc.weightx = 1;
					jpMyShow.add(panel, gbc);

				}
				else {
					panel = new JPanel();
					panel.setPreferredSize(new Dimension(300,40));
					panel.setLayout(new GridLayout(2,1));
					JTextField tfNbrOfEpisodes = new JTextField();
					panel.add(new JLabel("Season" + (i+1) + " :"));
					panel.add(tfNbrOfEpisodes);
					tfSeasons.add(tfNbrOfEpisodes); // sätter in varje textfield i en array
					gbc.gridx = 0;
					gbc.weightx = 1;

					jpMyShow.add(panel, gbc);

				}
			} 

			submit.addActionListener(e->createMyShow(showname,tfSeasons));
			jpMyShow.add(submit, gbc); 
			jpSearchResult.add(jpMyShow);
			jspSearchResult.setViewportView(jpSearchResult);

			JPanel pnl = new JPanel();
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weighty = 1;
			jpMyShow.add(pnl, gbc);

		}catch (Exception e)  
		{ 
			System.out.println(input + " is not a valid integer number"); 
		}

	}


	private void createMyShow(String showname, ArrayList<JTextField> tfSeasons) {
		// TODO Auto-generated method stub

		int i = 0;
		int nbrOfEpisodes = 0;
		int totalnbrOfEpisodes = 0;
		int [] temp ;
		boolean isNumber = false;
		int [][] nbrOfSeasonsandEpisodes = null ;
		for(JTextField s :tfSeasons) {
			try {
				nbrOfEpisodes = Integer.parseInt(s.getText());
				isNumber=true;
			}catch(NumberFormatException e) {
				isNumber=false;
				JOptionPane.showMessageDialog(null, "please insert a number for Season" + (i+1));
			}

			if(isNumber) {
				System.out.println(showname + "SEASON " + (i+1) +" :"+ nbrOfEpisodes);
				totalnbrOfEpisodes += nbrOfEpisodes;
			}else
				break;
			i++;
		}
		int ii = 0;
		temp = new int[totalnbrOfEpisodes];
		for(JTextField s :tfSeasons) {
			try {
				nbrOfEpisodes = Integer.parseInt(s.getText());
				isNumber=true;
			}catch(NumberFormatException e) {
				isNumber=false;
				JOptionPane.showMessageDialog(null, "please insert a number for Season" + (ii+1));
			}

			if(isNumber) {
				System.out.println(showname + "SEASON " + (ii+1) +" :"+ nbrOfEpisodes);
				nbrOfSeasonsandEpisodes = new int [tfSeasons.size()][totalnbrOfEpisodes];
				for ( int x = 0 ; x < tfSeasons.size(); x++) {
					
				}
//				totalnbrOfEpisodes += nbrOfEpisodes;
			}else
				break;
			

			
			ii++;
		}
		System.out.println(totalnbrOfEpisodes);
		cc.createShow(showname, nbrOfSeasonsandEpisodes);


	}


	protected void addRemove(String showname, JButton btnAdd, boolean add) {
		// TODO Auto-generated method stub
		if(add == false) {
			btnAdd.setText("REMOVE");
			System.out.println(showname + " is added to list");
			//cc.addShow(showname);
		}
		else {
			btnAdd.setText("add");
			System.out.println(showname + " is removed from list");
			cc.removeShow(showname);
		}

	}

	public static void main(String[] args) {
		ClientController cc = new ClientController();
		User user = cc.getUser();
		SearchShows ss = new SearchShows(cc);

		JFrame frame = new JFrame();
		frame.add(ss);
		frame.setSize(new Dimension(350, 500));
		frame.setVisible(true);
	}

}