package showtracker.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import showtracker.Show;

public class ShowList extends JFrame {
	ClientController clientController = new ClientController();
	private JLabel infoLabel1;
	private ArrayList<Show> show = new ArrayList<>();
	private JPanel jpShowList = new JPanel();
	private JPanel searchBarJP = new JPanel();
	ArrayList<JButton> btnArrayList = new ArrayList<>();
	private JScrollPane scrollPanel = new JScrollPane();
	JTextField searchBarTF;

//	JTextField searchBarTF = new JTextField("Enter name of the show here");

	public ShowList() throws FileNotFoundException {
		clientController.fyllTVShows();
		this.show = clientController.getShow();
		drawSearchBarPanel();
		showList(show);
		add(scrollPanel, BorderLayout.CENTER);
		add(searchBarJP, BorderLayout.NORTH);

		setTitle("Show List");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();

		setSize(new Dimension(350, 400));

	}

	public void drawSearch() {
		drawSearchBarPanel();

	}

	private JPanel drawSearchBarPanel() {
		searchBarJP.setBackground(Color.GREEN);
		searchBarJP.setSize(350, 100);

		JButton searchBarBtn = new JButton("search");
		searchBarTF = new JTextField("Enter name of the show here!", 20);

		searchBarBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				search(searchBarTF.getText());
			}
		});

		searchBarJP.add(searchBarTF);
		searchBarJP.add(searchBarBtn);

		return searchBarJP;
	}

	protected void search(String search) {
		Show myShows = new Show(search);
		ArrayList<Show> searchShows = new ArrayList<>();
		for (Show testshow : show) {
			if (testshow.getName().toLowerCase().contains(search.toLowerCase()))
				searchShows.add(testshow);
		}
		if (searchShows.size() == 0) {
			System.out.println("Kunde inte hitta show med ordert '" + search + "' !!!");
		} else {
			jpShowList.removeAll();
			btnArrayList.clear();
			showList(searchShows);
		}
	}
	

	private void showList(ArrayList<Show> inputShow) {

		jpShowList.setLayout(new GridLayout(show.size(),1));
		
		int i = 0;
		for (Show s : inputShow) {
			JPanel panel = new JPanel();

			panel.setPreferredSize(new Dimension(300,30));

			JButton button = new JButton("Info");
			btnArrayList.add(button);
			button.setVisible(false);
			panel.setLayout(new GridLayout(1, 2));
			panel.add(infoLabel1 = new JLabel(s.getName()));
			panel.add(button);
			jpShowList.add(panel);

			infoLabel1.setBorder(new LineBorder(Color.GRAY, 1));

			button.addMouseListener(new ButtonAdapter());
			infoLabel1.addMouseListener(new LabelAdapter(button));

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {}
			});
			scrollPanel.setViewportView(jpShowList);
			scrollPanel.setLayout(new ScrollPaneLayout());
			jpShowList.revalidate();
			i++;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new ShowList();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private class LabelAdapter extends MouseAdapter {
		private JButton button;

		public LabelAdapter(JButton button) {
			this.button = button;
		}

		public void mouseEntered(MouseEvent e) {
			for (JButton b : btnArrayList)
				b.setVisible(false);
			button.setVisible(true);
		}
	}

	private class ButtonAdapter extends MouseAdapter {
		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setVisible(false);
		}
	}

	private class MyDocumentListener implements DocumentListener {

//		searchBarTF.getDocument().addDocumentListener(new DocumentListener());

		public void changedUpdate(DocumentEvent e) {

		}

		public void insertUpdate(DocumentEvent e) {
			send(e, "");
		}

		public void removeUpdate(DocumentEvent e) {
			send(e, "");
		}

		public void send(DocumentEvent e, String search) {
			Document doc = (Document) e.getDocument();
			int changeLength = e.getLength();
			Show myShows = new Show(search);
			ArrayList<Show> searchShows = new ArrayList<>();
			for (Show testshow : show) {
				if (testshow.getName().contains(search))
					searchShows.add(testshow);
			}
			if (searchShows.size() == 0) {
				
			} else {

			}
		}

	}

}
