package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import showtracker.Episode;
import showtracker.Show;

/**
 * 
 * @author Moustafa Al-louhaibi
 * Changes made by Filip, Basir & Adam
 * 
 * Represents the search panel
 *
 */
class SearchShows extends JPanel {

	private ClientController clientController;
	private JTextField txfSearchBar = new JTextField();
    private JTextField txfShowName = new JTextField();

	private JPanel pnlSearchBar;
	private JPanel pnlSearchResult = new JPanel();
	private JPanel pnlMyOwnShowPanel = new JPanel();
	private JPanel pnlMyShow = new JPanel();

	private JScrollPane spnSearchResult = new JScrollPane();

	SearchShows(ClientController clientController) {
		this.clientController = clientController;
		setLayout(new BorderLayout());
		pnlSearchBar = new JPanel();
		add(pnlSearchBar, BorderLayout.NORTH);
		add(spnSearchResult, BorderLayout.CENTER);
		pnlSearchBar.setBackground(Color.GREEN);
		pnlSearchBar.setSize(350, 100);
		pnlSearchBar.setLayout(new GridLayout(1,3));
		txfSearchBar.setPreferredSize(new Dimension(200,20));
		JButton btnSearchBar = new JButton("search");
		
		JButton btnCreateShow = new JButton("Create Show");
		btnCreateShow.addActionListener(e -> drawNoSearchResultPanel());
		btnSearchBar.addActionListener(e -> drawSearchResultPanel(txfSearchBar.getText()));
		txfSearchBar.addKeyListener(new EnterListener());
		pnlSearchBar.add(txfSearchBar);
		pnlSearchBar.add(btnSearchBar);
		pnlSearchBar.add(btnCreateShow);
		
	}

	void draw() {
		pnlSearchResult.removeAll();
		TextPrompt textPrompt = new TextPrompt("Enter show", txfSearchBar);
		textPrompt.changeAlpha(0.5f);
		textPrompt.changeStyle(Font.BOLD + Font.PLAIN);
	}

	private void drawSearchResultPanel(String strSearchRequest) {
		pnlSearchResult.removeAll();
		String[][] arrStrSearchResults = clientController.searchShows(strSearchRequest);
		if (arrStrSearchResults != null) {
			pnlSearchResult.setLayout(new GridLayout(arrStrSearchResults.length, 2));
			System.out.println("SHOW HITTAT");
			updateSearchResults(arrStrSearchResults);
		} else {
			pnlSearchResult.setLayout(new BorderLayout());

			System.out.println("SHOW EJ HITTAT");
			strSearchRequest = "<html>" + "Your Search '" + strSearchRequest + "' was not found <br>" + "tips:<br>"
					+ "- Make sure all word are spelled correctly<br>" + "- Try different keywords<br>"
					+ "- or click the button below to create your own tracker =)" + "</html>";

			JLabel label = new JLabel("<html><font size = '3', padding-left: 50px>" + strSearchRequest + "</font></html>");

			label.setPreferredSize(new Dimension(pnlSearchResult.getWidth() - 5, pnlSearchResult.getHeight() / 2));

			ImageIcon imiAdd = new ImageIcon("images/notes-add.png");
			Image imgAdd = imiAdd.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			imiAdd = new ImageIcon(imgAdd);

			JButton btnCreateOwnShow = new JButton(imiAdd);

			btnCreateOwnShow.addActionListener(e -> drawNoSearchResultPanel());
			pnlSearchResult.add(label, BorderLayout.CENTER);
			pnlSearchResult.add(btnCreateOwnShow, BorderLayout.SOUTH);
		}

		spnSearchResult.setViewportView(pnlSearchResult);
		pnlSearchResult.revalidate();
	}

	private void updateSearchResults(String[][] arrStrSearchResults) {

		GridBagConstraints gbc = new GridBagConstraints();
		pnlSearchResult.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.HORIZONTAL;

		for (String[] arrStr : arrStrSearchResults) {
			JPanel pnlMain = new JPanel();

			pnlMain.setPreferredSize(new Dimension(300, 30));
			pnlMain.setLayout(new BorderLayout());

			JButton btnAdd = new JButton("Add");

			btnAdd.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));

			btnAdd.addActionListener(new AddListener(arrStr[0], arrStr[1], btnAdd));
			pnlMain.add(btnAdd, BorderLayout.WEST);
			pnlMain.add(new JLabel(" " + arrStr[0]), BorderLayout.CENTER);

			gbc.gridx = 0;
			gbc.weightx = 1;
			pnlSearchResult.add(pnlMain, gbc);
		}

		JPanel panel = new JPanel();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weighty = 1;
		pnlSearchResult.add(panel, gbc);
	}

	private void drawNoSearchResultPanel() {
		pnlSearchResult.removeAll();
		pnlMyOwnShowPanel.removeAll();
		pnlSearchResult.setLayout(new BorderLayout());
		pnlMyOwnShowPanel.setLayout(new BoxLayout(pnlMyOwnShowPanel, BoxLayout.Y_AXIS));
		txfShowName.setText(txfSearchBar.getText());
		JButton btnSubmit = new JButton("Submit");
		JTextField txfNbrOfSeasons = new JTextField();
		pnlMyOwnShowPanel.add(new JLabel("Name: "));
		pnlMyOwnShowPanel.add(txfShowName);
		pnlMyOwnShowPanel.add(new JLabel("Number of Seasons"));
		pnlMyOwnShowPanel.add(txfNbrOfSeasons);
		pnlMyOwnShowPanel.add(btnSubmit, BorderLayout.SOUTH);
		btnSubmit.addActionListener(e -> createMyOwnShowPanel(txfNbrOfSeasons.getText()));
		pnlSearchResult.add(pnlMyOwnShowPanel, BorderLayout.NORTH);
		spnSearchResult.setViewportView(pnlSearchResult);
	}

	private void addRemoveShow(String strShowName, JButton btnAdd, boolean blnAdd) {
		if (!blnAdd) {
			btnAdd.setText("REMOVE");
			System.out.println(strShowName + " is added to list");
		} else {
			btnAdd.setText("Add");
			System.out.println(strShowName + " is removed from list");
			clientController.getUser().removeShow(new Show(strShowName));
		}
	}

	private void createMyOwnShowPanel(String strInput) {
		try {
			int intNbrOfSeasons = Integer.parseInt(strInput);
			GridBagConstraints gbc = new GridBagConstraints();
			pnlMyShow.setLayout(new GridBagLayout());
			gbc.fill = GridBagConstraints.HORIZONTAL;
			pnlMyShow.removeAll();

			JTextField[] arrTxfSeasons = new JTextField[intNbrOfSeasons];
			JButton btnCreate = new JButton("Create");

			for (int i = 0; i < intNbrOfSeasons; i++) {
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(300, 40));
				panel.setLayout(new GridLayout(2, 1));
				JTextField tfNbrOfEpisodes = new JTextField();
				panel.add(new JLabel("Season" + (i + 1) + " :"));
				panel.add(tfNbrOfEpisodes);
				arrTxfSeasons[i] = tfNbrOfEpisodes; // sätter in varje textfield i en array
				gbc.gridx = 0;
				gbc.weightx = 1;
				pnlMyShow.add(panel, gbc);
			}

			btnCreate.addActionListener(e -> createMyShow(arrTxfSeasons));
			pnlMyShow.add(btnCreate, gbc);
			pnlSearchResult.add(pnlMyShow);
			spnSearchResult.setViewportView(pnlSearchResult);

			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weighty = 1;
			pnlMyShow.add(new JPanel(), gbc);

		} catch (Exception e) {
			System.out.println(strInput + " is not a valid integer number");
		}
	}

	private void createMyShow(JTextField[] arrTxfSeasons) {
		boolean blnParseIntSuccess = false;
		int[] arrIntEpisodes = new int[arrTxfSeasons.length];
		String strSeasons = "";
		try {
			for (int i = 0; i < arrTxfSeasons.length; i++) {
				strSeasons = arrTxfSeasons[i].getText();
				arrIntEpisodes[i] = Integer.parseInt(strSeasons);
			}
			blnParseIntSuccess = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, strSeasons + " is not valid, please enter a number.");
		}
		if (blnParseIntSuccess) {
			Show show = new Show(txfShowName.getText());
			for (int s = 0; s < arrTxfSeasons.length; s++)
				for (int e = 0; e < arrIntEpisodes[s]; e++)
					show.addEpisode(new Episode(show, e + 1, s + 1));

			show.sortEpisodes();
			clientController.getUser().addShow(show);
			JOptionPane.showMessageDialog(null, "Show created successfully!");
		}
	}

	private class AddListener implements ActionListener {
		private boolean blnAdd = true;
		private String strShowName;
		private String strShowId;
		private JButton btnAdd;

		AddListener(String strShowName, String strShowId, JButton btnAdd) {
			this.strShowName = strShowName;
			this.strShowId = strShowId;
			this.btnAdd = btnAdd;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (blnAdd) {
				blnAdd = false;
				clientController.generateShow(strShowName, strShowId);
			} else
				blnAdd = true;
			addRemoveShow(strShowName, btnAdd, blnAdd);
		}
	}
	
	private class EnterListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) { // Activate ctrl-enter to send message
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                e.consume();
                drawSearchResultPanel(txfSearchBar.getText());
                
            }
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
        }

        @Override
        public void keyTyped(KeyEvent arg0) {
        }
    }
}