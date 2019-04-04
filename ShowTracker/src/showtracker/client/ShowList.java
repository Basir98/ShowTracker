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
	private ArrayList<ClientController> show = new ArrayList<>();
	private JPanel jpShowList = new JPanel(new GridLayout(10, 2));
	private JPanel searchBarJP = new JPanel();
	ArrayList<JButton> btnList = new ArrayList<>();

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
			btnList.add(button);
			button.setVisible(false);

			jpShowList.add(infoLabel1 = new JLabel(s.getName()));
			jpShowList.add(button);

			infoLabel1.setBorder(new LineBorder(Color.GRAY, 1));

			button.addMouseListener(new ButtonAdapter());
			infoLabel1.addMouseListener(new LabelAdapter(button));

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
