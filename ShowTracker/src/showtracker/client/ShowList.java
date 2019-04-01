package showtracker.client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ShowList extends JFrame {
	ClientController clientController = new ClientController();
	JFrame frame = new JFrame("Show List");

	private JLabel infoLabel1 = new JLabel("Game Of Thrones");

	private JLabel numberLabel1 = new JLabel();

	private JButton btn1 = new JButton("Info");

	public ShowList() {
		frame.add(filedPanel(), BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.pack();

	}

	public JPanel filedPanel() {
//		JPanel panel = new JPanel();
//		panel.setLayout(new GridLayout(2, 2, 1, 1));

		btn1.setVisible(false);

		infoLabel1.setLayout(new BorderLayout());
		infoLabel1.setBorder(new LineBorder(Color.GRAY, 1));
		infoLabel1.setPreferredSize(new Dimension(200, 30));
		infoLabel1.add(btn1, BorderLayout.EAST);

		btn1.addMouseListener(new MouseAdapter() {
			public void mouse(MouseEvent e) {
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

		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "The button was pushed!", "    Info", JOptionPane.PLAIN_MESSAGE);
				Point mousePosition = MouseInfo.getPointerInfo().getLocation();
				infoLabel1.dispatchEvent(new MouseEvent(infoLabel1, MouseEvent.MOUSE_EXITED, System.currentTimeMillis(),
						0, mousePosition.x, mousePosition.y, 0, false));
			}
		});

		infoLabel1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				JLabel label = (JLabel) e.getSource();
//				label.setText("");
				btn1.setVisible(true);

			}

			public void mouseExited(MouseEvent e) {
				Point point = e.getPoint();
				point.setLocation(point.x - btn1.getX(), point.y - btn1.getY());

				if (!btn1.contains(point)) {
					JLabel label = (JLabel) e.getSource();
					btn1.setVisible(false);
				}
			}

		});

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		panel.add(infoLabel1);

		setContentPane(panel);
		return panel;

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ShowList();
			}

		});

	}

}
