package showtracker.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import showtracker.Helper;
import showtracker.Show;
import showtracker.User;

public class ShowList extends JPanel {
	private ClientController cc;
	private JLabel infoLabel;
	private JPanel panelShowList = new JPanel();
	private ArrayList<JButton> btnArrayList = new ArrayList<>();
	private JScrollPane scrollPanel = new JScrollPane();
	private int x = 0;

	public ShowList(ClientController cc) {
		this.cc = cc;
	}

	public void draw() {
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
				JPanel middlePanel = new JPanel(new FlowLayout());
//				JPanel southPanel = new JPanel(new GridLayout(1,3,7,3));
				JPanel southPanel = new JPanel(new FlowLayout());

				ImageIcon infoImage = new ImageIcon("images/info.png");
				Image infoImg = infoImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				ImageIcon infoImageIcon = new ImageIcon(infoImg);

				JButton btnInfo = new JButton("info");
				JButton btnUpdate = new JButton("Update");
				JButton btnRemove = new JButton("Remove");

				middlePanel.add(infoLabel = new JLabel(s.getName()));

				southPanel.add(btnInfo);
				southPanel.add(btnUpdate);
				southPanel.add(btnRemove);

//				btnInfo.setVisible(false);
//				btnUpdate.setVisible(false);
//				btnRemove.setVisible(false);
//				
//				btnArrayList.add(btnInfo);
//				btnArrayList.add(btnUpdate);
//				btnArrayList.add(btnRemove);

				JPanel mainPanel = new JPanel(new BorderLayout());
				mainPanel.setBorder(new LineBorder(Color.DARK_GRAY));

				mainPanel.add(middlePanel, BorderLayout.CENTER);
				mainPanel.add(southPanel, BorderLayout.SOUTH);

//				infoLabel.setBorder(new LineBorder(Color.GRAY, 1));

//				btnInfo.addMouseListener(new ButtonAdapter());
//				btnUpdate.addMouseListener(new ButtonAdapter());
//				btnRemove.addMouseListener(new ButtonAdapter());

//				infoLabel.addMouseListener(new LabelAdapter(btnInfo, btnUpdate, btnRemove));

				btnInfo.addActionListener(new ActionListener() {
					private int counter = x;
					private Show tempShow = s;

					public void actionPerformed(ActionEvent e) {
						cc.setPanel("Info", tempShow);

					}
				});

				btnUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cc.updateShow();
					}
				});

				btnRemove.addActionListener(new ActionListener() {
					String showName = s.getName();

					public void actionPerformed(ActionEvent e) {
						cc.removeShow(showName);
//						System.out.print(cc.getUser().getShows().toString());
					}
				});

				gbc.gridx = 0;
				gbc.weightx = 1;

				panelShowList.add(mainPanel, gbc);

			}
			JPanel panel = new JPanel();
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.weighty = 1;
			panelShowList.add(panel, gbc);

		} else {
			panelShowList.add(new JLabel("   Kunde inte hitta show med angivet namn !!"));

		}
		scrollPanel.setViewportView(panelShowList);
		scrollPanel.setLayout(new ScrollPaneLayout());
		panelShowList.revalidate();

	}

	private class LabelAdapter extends MouseAdapter {
		private JButton button1;
		private JButton button2;
		private JButton button3;

		public LabelAdapter(JButton button1, JButton button2, JButton button3) {
			this.button1 = button1;
			this.button2 = button2;
			this.button3 = button3;
		}

		public void mouseEntered(MouseEvent e) {
			for (JButton b : btnArrayList)
				b.setVisible(false);
			button1.setVisible(true);
			button2.setVisible(true);
			button3.setVisible(true);
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
		ClientController cc = new ClientController();
		User user = new User("namn", "email", null);
		String[] show = { "Game of thrones", "Walking dead", "Game of luck season 4 episode 15" };
		cc.setUser(user);
		cc.addShow(show[0]);
		cc.addShow(show[1]);
		cc.addShow(show[2]);

		ShowList shoList = new ShowList(cc);
		shoList.draw();
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
