package showtracker.client;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.*;
import javax.swing.border.LineBorder;

import showtracker.Episode;
import showtracker.Helper;
import showtracker.Show;

/**
 * 
 * @author Adam Joulak
 * Changes made by Filip
 * 
 * Show info panel
 *
 */
class ShowInfo extends JPanel {
	private JPanel mainPanel = new JPanel();
	private ArrayList<SeasonListener> listeners = new ArrayList<>();
	private Show show;

	ShowInfo(Show show) {
		this.show = show;
		for (double d: show.getSeasons())
			listeners.add(new SeasonListener(d));

		initiatePanels();
		draw();
	}

	private void initiatePanels() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(mainPanel);
		scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.setBackground(Color.CYAN);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(Box.createHorizontalGlue());
		
		ImageIcon imiInfo = new ImageIcon("images/info.png");
		Image imgInfo = imiInfo.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imiInfo = new ImageIcon(imgInfo);

		JButton btnInfo = new JButton(imiInfo);
		btnInfo.setPreferredSize(new Dimension(30, 50));
		
		btnInfo.addActionListener(e -> JOptionPane.showMessageDialog(null,
			"<html><body><p style=\"width: 200px;\">" +
					show.getDescription() +
					"</p></body></html>", "Show info", JOptionPane.PLAIN_MESSAGE));

		JPanel pnlHeader = new JPanel();
		pnlHeader.setBounds(0, 0, 500, 50);
		pnlHeader.setLayout(new BorderLayout());
		pnlHeader.setPreferredSize(new Dimension(500, 50));
		pnlHeader.add(new JLabel(show.getName()));
		pnlHeader.add(btnInfo, BorderLayout.EAST);
		pnlHeader.setBorder(new LineBorder(Color.black));

		setLayout(new BorderLayout());
		add(pnlHeader, BorderLayout.NORTH);
		add(scrollPane);
	}

	private void draw() {
		mainPanel.removeAll();
		for (SeasonListener listener : listeners) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			panel.add(Box.createHorizontalGlue());
			JButton button;
			if (listener.getSeason() == 0)
				button = new JButton("Specials");
			else
				button = new JButton("Season " + Helper.df.format(listener.getSeason()));
			button.setMinimumSize(new Dimension(100, 30));
			button.setMaximumSize(new Dimension(100, 30));
			button.addActionListener(listener);
			panel.add(button);
			if (listener.getOpen())
				for (Episode episode : show.getEpisodes())
					if (episode.getSeasonNumber() == listener.getSeason()) {
						JButton infoButton = new JButton("Info - Episode " + Helper.df.format(episode.getEpisodeNumber()) + " - " + episode.getName());
						infoButton.addActionListener(e -> {
								JOptionPane.showMessageDialog(null,
										"<html><body><p style=\"width: 200px;\">" +
												show.getEpisode(episode.getSeasonNumber(),
														episode.getEpisodeNumber()).getDescription() +
												"</p></body></html>", episode.getName(), JOptionPane.INFORMATION_MESSAGE);
						});
						panel.add(infoButton);
						JCheckBox checkBox = new JCheckBox();
						checkBox.setSelected(episode.isWatched());
						checkBox.addActionListener(new EpisodeListener(episode));
						panel.add(checkBox);
			}
			mainPanel.add(panel);
		}
		revalidate();
		repaint();
	}

	private class SeasonListener implements ActionListener {
		private double season;
		private boolean open = false;

		SeasonListener(double season) {
			this.season = season;
		}

		double getSeason() {
			return season;
		}

		boolean getOpen() {
			return open;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			open = !open;
			draw();
		}
	}

	private class EpisodeListener implements ActionListener {
		private Episode episode;

		EpisodeListener(Episode episode) {
			this.episode = episode;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isWatched = ((JCheckBox) e.getSource()).isSelected();
			episode.setWatched(isWatched);
		}
	}
}