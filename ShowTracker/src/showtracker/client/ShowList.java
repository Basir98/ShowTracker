package showtracker.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import showtracker.Show;

public class ShowList extends JFrame {
	ClientController clientController = new ClientController();
	private JLabel infoLabel1;
	private ArrayList<Show> show = new ArrayList<>();
	private JPanel jpShowList = new JPanel();
	private JPanel searchBarJP = new JPanel();
	ArrayList<JButton> btnList = new ArrayList<>();
	private JScrollPane scrollPanel = new JScrollPane();
	JTextField searchBarTF;

//	JTextField searchBarTF = new JTextField("Enter name of the show here");

	public ShowList() throws FileNotFoundException {
		clientController.fyllTVShows();
		this.show = clientController.getShow();
		updateShowList();
		drawSearchBarPanel();
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
		searchBarTF = new JTextField("Enter name of the show here");

		searchBarTF.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				send();
			}

			public void insertUpdate(DocumentEvent e) {
				send();
			}

			public void removeUpdate(DocumentEvent e) {
				send();
			}

			public void send() {
				if (searchBarTF.getText().length() > 0) {

				}
			}
		});

		// changeListener där beroende på bokstaven på serien så e de en live/direkt
		// sökning

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
			if (testshow.getName().contains(search))
				searchShows.add(testshow);
		}
		if (searchShows.size() == 0) {
			System.out.println("Kunde inte hitta show med ordert " + search + "!!!");
		} else {
			System.out.println("Showen som hittades: " + search);
		}

//		if (show.contains(myShows)) {
//			show = clientController.getShow();
////			System.out.println("Show hittat!");
////			System.out.println(show);
//
////			updateShowList();
//		} else {
//			System.out.println("Show hittades inte!");
//		}
	}

	private void updateShowList() {
//		ArrayList<Show> myShows = clientController.getUser(0).getShows(); 

//		jpShowList.removeAll();
		jpShowList.setLayout(new GridLayout(show.size(), 2));
		int i = 1;
		for (Show s : show) {
			JButton button = new JButton("Info");
			btnList.add(button);
			button.setVisible(false);

			jpShowList.add(infoLabel1 = new JLabel(s.getName()));
			jpShowList.add(button);

			infoLabel1.setBorder(new LineBorder(Color.GRAY, 1));

			button.addMouseListener(new ButtonAdapter());
			infoLabel1.addMouseListener(new LabelAdapter(button));

			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

				}

			});
			scrollPanel.setViewportView(jpShowList);
			scrollPanel.setLayout(new ScrollPaneLayout());
			jpShowList.revalidate();
//			jpShowList.repaint();
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
			for (JButton b : btnList)
				b.setVisible(false);
			button.setVisible(true);
		}
	}

	private class ButtonAdapter extends MouseAdapter {
		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setVisible(false);
		}
	}
}
