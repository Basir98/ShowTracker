package showtracker.client;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import showtracker.Show;

public class ShowList extends JFrame {
	ClientController clientController = new ClientController();
	private JLabel infoLabel1;
	private JLabel label;

	private ArrayList<ClientController> show = new ArrayList<>();
	private JPanel jpShowList = new JPanel(new GridLayout(10, 2));
	private JPanel searchBarJP = new JPanel();

	public ShowList() throws FileNotFoundException {
		clientController.fyllShows();
		updateShowList();
		add(jpShowList, BorderLayout.CENTER);

		setTitle("Show List");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();

//		setSize(new Dimension(350, 500));

	}

	/*
	 * public JPanel filedPanel() { // JPanel panel = new JPanel(); //
	 * panel.setLayout(new GridLayout(2, 2, 1, 1));
	 * 
	 * btn1.setVisible(false);
	 * 
	 * infoLabel1.setLayout(new BorderLayout()); infoLabel1.setBorder(new
	 * LineBorder(Color.GRAY, 1)); infoLabel1.setPreferredSize(new Dimension(200,
	 * 30)); infoLabel1.add(btn1, BorderLayout.EAST);
	 * 
	 * btn1.addMouseListener(new MouseAdapter() { public void mouse(MouseEvent e) {
	 * Point mousePosition = MouseInfo.getPointerInfo().getLocation(); if
	 * (infoLabel1.contains(mousePosition)) { infoLabel1.dispatchEvent(new
	 * MouseEvent(infoLabel1, MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(),
	 * 0, mousePosition.x, mousePosition.y, 0, false));
	 * 
	 * } else { infoLabel1.dispatchEvent(new MouseEvent(infoLabel1,
	 * MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, mousePosition.x,
	 * mousePosition.y, 0, false)); } }
	 * 
	 * });
	 * 
	 * btn1.addActionListener(new ActionListener() { public void
	 * actionPerformed(ActionEvent e) { JOptionPane.showMessageDialog(null,
	 * "The button was pushed!", "    Info", JOptionPane.PLAIN_MESSAGE); Point
	 * mousePosition = MouseInfo.getPointerInfo().getLocation();
	 * infoLabel1.dispatchEvent(new MouseEvent(infoLabel1, MouseEvent.MOUSE_EXITED,
	 * System.currentTimeMillis(), 0, mousePosition.x, mousePosition.y, 0, false));
	 * } });
	 * 
	 * infoLabel1.addMouseListener(new MouseAdapter() { public void
	 * mouseEntered(MouseEvent e) { JLabel label = (JLabel) e.getSource(); //
	 * label.setText(""); btn1.setVisible(true);
	 * 
	 * }
	 * 
	 * public void mouseExited(MouseEvent e) { Point point = e.getPoint();
	 * point.setLocation(point.x - btn1.getX(), point.y - btn1.getY());
	 * 
	 * if (!btn1.contains(point)) { JLabel label = (JLabel) e.getSource();
	 * btn1.setVisible(false); } }
	 * 
	 * });
	 * 
	 * JPanel panel = new JPanel(); panel.setLayout(new FlowLayout());
	 * 
	 * panel.add(infoLabel1);
	 * 
	 * setContentPane(panel); return panel;
	 * 
	 * }
	 */

	private JPanel drawSearchBarPanel() {
		searchBarJP.setBackground(Color.GREEN);
		searchBarJP.setSize(350, 100);
		JTextField searchBarTF = new JTextField("Enter name of the show here");
		JButton searchBarBtn = new JButton("search");
		searchBarBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				search(searchBarTF.getText());
			}
		});

		searchBarJP.add(searchBarTF);
		searchBarJP.add(searchBarBtn);

		return searchBarJP;
	}

	protected void search(String text) {

	}

	private void updateShowList() {
		ArrayList<Show> myShows = clientController.getUser(0).getShows();
		jpShowList.setLayout(new GridLayout(myShows.size(), 2));
		jpShowList.setPreferredSize(new Dimension(300, 80));
		int i = 1;
		for (Show s : myShows) {
			JButton button = new JButton("Info" + i);
			button.setVisible(false);

			jpShowList.add(infoLabel1 = new JLabel(s.getName()));
			jpShowList.add(button);

			infoLabel1.setBorder(new LineBorder(Color.GRAY, 1));

			button.addMouseListener(new MouseAdapter() {

				public void mouseExited(MouseEvent e) {
					Point mousePosition = MouseInfo.getPointerInfo().getLocation();
					if (infoLabel1.contains(mousePosition)) {
						infoLabel1.dispatchEvent(new MouseEvent(infoLabel1, MouseEvent.MOUSE_ENTERED,
								System.currentTimeMillis(), 0, mousePosition.x, mousePosition.y, 0, false));
					} else {
						infoLabel1.dispatchEvent(new MouseEvent(infoLabel1, MouseEvent.MOUSE_EXITED,
								System.currentTimeMillis(), 0, mousePosition.x, mousePosition.y, 0, false));
					}
				}

			});

			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "The button was pressed!");
					Point mousePosition = MouseInfo.getPointerInfo().getLocation();
					infoLabel1.dispatchEvent(new MouseEvent(infoLabel1, MouseEvent.MOUSE_EXITED,
							System.currentTimeMillis(), 0, mousePosition.x, mousePosition.y, 0, false));
				}
			});

			infoLabel1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
//					JLabel label = (JLabel) e.getSource();
//					label =(JLabel)e.getSource();

					infoLabel1 = (JLabel) e.getSource();
//					infoLabel1.setText("Here is the Button!");
					button.setVisible(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					Point point = e.getPoint();
					point.setLocation(point.x - button.getX(), point.y - button.getY()); // make the point relative to
																							// the // button's location
					if (!button.contains(point)) {
//						JLabel label = (JLabel) e.getSource();
						label = (JLabel) e.getSource();
//						label.setText("The button is gone!");
						button.setVisible(false);
					}
				}
			});
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

}
