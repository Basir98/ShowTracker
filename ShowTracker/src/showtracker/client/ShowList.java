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
import javax.swing.text.Document;

import showtracker.Show;

public class ShowList extends JPanel {
 	static ClientController clientController = new ClientController();
	private JLabel infoLabel;
	private ArrayList<Show> show = new ArrayList<>();
	private JPanel panelShowList = new JPanel();
//	private JPanel searchBarPanel = new JPanel();
	private ArrayList<JButton> btnArrayList = new ArrayList<>();
	private JScrollPane scrollPanel = new JScrollPane();
//	private JTextField searchBarTextField;
//	private JLabel lbl;

	public ShowList(ClientController cc) throws FileNotFoundException {
		this.clientController = cc;
		clientController.fyllTVShows();
		this.show = clientController.getShow();
		showList(show);

		MyDocumentListener myDocumentListener = new MyDocumentListener();
		this.setLayout(new BorderLayout());
		add(myDocumentListener, BorderLayout.NORTH);

		add(scrollPanel, BorderLayout.CENTER);

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
			panelShowList.removeAll();
			btnArrayList.clear();
			showList(searchShows);
		}
	}

	private void showList(ArrayList<Show> inputShow) {
		if (show.size() > 5) {
			panelShowList.setLayout(new GridLayout(show.size(), 1));
		} else {
			panelShowList.setLayout(new GridLayout(5, 1));

		}
		panelShowList.removeAll();
		if (inputShow.size() > 0) {
			for (Show s : inputShow) {

				JPanel panel = new JPanel();

				panel.setPreferredSize(new Dimension(300, 60));

				JButton button = new JButton("Info");
				btnArrayList.add(button);
				button.setVisible(false);
				panel.setLayout(new GridLayout(1, 2));
				panel.add(infoLabel = new JLabel(s.getName()));
				panel.add(button);
				panelShowList.add(panel);

				infoLabel.setBorder(new LineBorder(Color.GRAY, 1));

				button.addMouseListener(new ButtonAdapter());
				infoLabel.addMouseListener(new LabelAdapter(button));

				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
			}

		} else {

//			lbl = new JLabel();
			panelShowList.add(new JLabel("   Kunde inte hitta show med angivet namn !!"));
		}
		scrollPanel.setViewportView(panelShowList);
		scrollPanel.setLayout(new ScrollPaneLayout());
		panelShowList.revalidate();
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

	private class MyDocumentListener extends JTextField implements DocumentListener {

		public MyDocumentListener() {
			javax.swing.text.Document doc = this.getDocument();
			doc.addDocumentListener(this);
			setBackground(Color.LIGHT_GRAY);

		}

		public void changedUpdate(DocumentEvent e) {
			searchShow();
		}

		public void insertUpdate(DocumentEvent e) {
			searchShow();
		}

		public void removeUpdate(DocumentEvent e) {
			searchShow();
		}

		public void searchShow() {
			ArrayList<Show> searchShows = new ArrayList<>();
			for (Show testshow : show) {
				if (testshow.getName().toLowerCase().contains(getText().toLowerCase()))
					searchShows.add(testshow);
			}
			panelShowList.removeAll();
			btnArrayList.clear();
			showList(searchShows);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

		ShowList shoList = new ShowList(clientController);
		JFrame frame = new JFrame();

		frame.setTitle("Show List");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(shoList);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.pack();

		frame.setSize(new Dimension(350, 400));

	}

}
