package showtracker.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
		setLayout(new BorderLayout());
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
				JPanel mainPanel = new JPanel(new GridLayout(2,1));
				JPanel middlePanel = new JPanel(new FlowLayout());
				JPanel southPanel = new JPanel(new FlowLayout());
				
				JButton btnInfo = new JButton("Info");
				JButton btnUpdate = new JButton("Update");
				JButton btnRemove = new JButton("Remove");
				
				middlePanel.add(infoLabel = new JLabel(s.getName()));
			
				southPanel.add(btnInfo);
				southPanel.add(btnUpdate);
				southPanel.add(btnRemove);
				
				btnArrayList.add(btnInfo);
				btnArrayList.add(btnUpdate);
				btnArrayList.add(btnRemove);
				
				btnInfo.setVisible(false);
				btnUpdate.setVisible(false);
				btnRemove.setVisible(false);
				
				mainPanel.add(middlePanel);
				mainPanel.add(southPanel);
				
				infoLabel.setBorder(new LineBorder(Color.GRAY, 1));

				btnInfo.addMouseListener(new ButtonAdapter());
				btnUpdate.addMouseListener(new ButtonAdapter());
				btnRemove.addMouseListener(new ButtonAdapter());
				
				infoLabel.addMouseListener(new LabelAdapter(btnInfo));
				infoLabel.addMouseListener(new LabelAdapter(btnUpdate));
				infoLabel.addMouseListener(new LabelAdapter(btnRemove));
				
				

				btnInfo.addActionListener(new ActionListener() {
					private int counter = x;
					private Show tempShow = s;
					
					public void actionPerformed(ActionEvent e) {
						cc.setPanel("Info", tempShow);
						
					}
				});
				
				btnUpdate.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
					
					}
					
				});
				
				btnRemove.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
					}
					
				});
				
				gbc.gridx = 0;
				gbc.weightx = 1;

				panelShowList.add(mainPanel, gbc);

				
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
