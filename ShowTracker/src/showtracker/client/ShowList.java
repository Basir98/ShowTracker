package showtracker.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import showtracker.Helper;
import showtracker.Show;

public class ShowList extends JPanel {
 	private ClientController cc;
	private JLabel infoLabel;
	private JPanel panelShowList = new JPanel();
	private ArrayList<JButton> btnArrayList = new ArrayList<>();
	private JScrollPane scrollPanel = new JScrollPane();
	private int x = 0;

	public ShowList(ClientController cc) {
		this.cc = cc;
		drawShowList(cc.getUser().getShows());

		MyDocumentListener myDocumentListener = new MyDocumentListener();
		this.setLayout(new BorderLayout());
		add(myDocumentListener, BorderLayout.NORTH);

		add(scrollPanel, BorderLayout.CENTER);
	}


	void drawShowList(ArrayList<Show> shows) {
		GridBagConstraints gbc = new GridBagConstraints();
		panelShowList.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.HORIZONTAL;

		panelShowList.removeAll();
		if (shows.size() > 0) {
			for (Show s : shows) {
				JPanel panel = new JPanel();

				panel.setPreferredSize(new Dimension(300, 60));
				JButton button = new JButton("Info");
				btnArrayList.add(button);
				button.setVisible(false);
				panel.setLayout(new GridLayout(1, 2));
				panel.add(infoLabel = new JLabel(s.getName()));
				panel.add(button);

				infoLabel.setBorder(new LineBorder(Color.GRAY, 1));

				button.addMouseListener(new ButtonAdapter());
				infoLabel.addMouseListener(new LabelAdapter(button));

				button.addActionListener(new ActionListener() {
					private int counter = x;
					private Show tempShow = s;
					@Override
					public void actionPerformed(ActionEvent e) {
						cc.setPanel("Info", tempShow);
						
					}
				});
				gbc.gridx = 0;
				gbc.weightx = 1;

				panelShowList.add(panel, gbc);
				
			}
			JPanel what = new JPanel();
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weighty = 1;
			panelShowList.add(what, gbc);
		} else {

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
			for (Show testshow : cc.getUser().getShows()) {
				if (testshow.getName().toLowerCase().contains(getText().toLowerCase()))
					searchShows.add(testshow);
			}
			panelShowList.removeAll();
			btnArrayList.clear();
			drawShowList(searchShows);
		}
	}

	public static void main(String[] args) {

		ShowList shoList = new ShowList(new ClientController());
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
