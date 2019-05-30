package showtracker.client;

import showtracker.Episode;
import showtracker.Helper;
import showtracker.Show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * @author Filip Spånberg
 * Changes made by Moustafa
 * 
 * Panel showing the next episode to watch in a show
 */
public class Home extends JPanel {
    private ClientController cc;
    private DecimalFormat df = new DecimalFormat("0.#");
    private JScrollPane scrollPane = new JScrollPane();

    public Home(ClientController cc) {
        this.cc = cc;
        add(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(335, 400));
    }

    /**
     * Refereshing the view
     */
    void draw() {
        scrollPane.getViewport().removeAll();
        Box box = Box.createVerticalBox();
        cc.getUser().getShows().sort(new Helper.LastWatchedComparator());
        int episodeCounter = 0;
        for (Show sh : cc.getUser().getShows()) {
            Episode currentEpisode = sh.getFirstUnwatched();

            if (currentEpisode != null) {
                episodeCounter++;
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createBevelBorder(1));
                JButton button = new JButton("<html>Set<br>watched</html>");
                button.addActionListener(new EpisodeListener(currentEpisode));
                panel.add(button, BorderLayout.WEST);
                JLabel label = new JLabel(String.format("<html><div style=\"width:150px;\">%s<br>Season %s, episode %s%s</div></html>",
                        sh.getName(),
                        df.format(currentEpisode.getSeasonNumber()),
                        df.format(currentEpisode.getEpisodeNumber()),
                        currentEpisode.getName() != null && !currentEpisode.getName().equals("") ? ":<br>" + currentEpisode.getName() : ""));
                panel.add(label, BorderLayout.CENTER);
                JLabel lbWidth = new JLabel();
                lbWidth.setPreferredSize(new Dimension(300, 1));
                panel.add(lbWidth, BorderLayout.SOUTH);
                panel.setMaximumSize(new Dimension(300, 100));
                box.add(panel);
            }
        }
        if (episodeCounter == 0)
            box.add(new JLabel("<html><p style=\"width:200px; align:center;\">\nNo new episodes to display. Either search for new shows, or go to your list and set some episodes to \"not watched\".</p></html>"));
        scrollPane.setViewportView(box);
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    /**
     * Inner class to handle the episode buttons (setting an episode to "watched"
     */
    private class EpisodeListener implements ActionListener {
        private Episode ep;

        EpisodeListener(Episode ep) {
            this.ep = ep;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(ep.getName() + ", " + ep);
            ep.setWatched(true);
            draw();
        }
    }
}