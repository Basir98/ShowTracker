package showtracker.client;

import showtracker.Episode;
import showtracker.Show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Home extends JPanel {
    private ClientController cc;
    private DecimalFormat df = new DecimalFormat("0.#");

    public Home(ClientController cc) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.cc = cc;
        draw();
    }

    void draw() {
        removeAll();
        for (Show sh : cc.getUser().getShows()) {
            Episode currentEpisode = null;
            for (int i = 0; i < sh.getEpisodes().size() && currentEpisode == null; i++)
                if (!sh.getEpisodes().get(i).isWatched() && sh.getEpisodes().get(i).getSeasonNumber() != 0)
                    currentEpisode = sh.getEpisodes().get(i);
            if (currentEpisode != null) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panel.setBorder(BorderFactory.createBevelBorder(1));
                JButton button = new JButton("<html>Set<br>watched</html>");
                button.addActionListener(new EpisodeListener(currentEpisode));
                panel.add(button);
                JLabel label = new JLabel(String.format("<html>%s<br>Season %s, episode %s%s</html>",
                        sh.getName(),
                        df.format(currentEpisode.getSeasonNumber()),
                        df.format(currentEpisode.getEpisodeNumber()),
                        currentEpisode.getName() != null && !currentEpisode.getName().equals("") ? ":<br>" + currentEpisode.getName() : ""));
                panel.add(label);
                add(panel);
            }
            revalidate();
        }
    }

    private class EpisodeListener implements ActionListener {
        private Episode ep;

        public EpisodeListener(Episode ep) {
            this.ep = ep;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ep.setWatched(true);
            removeAll();
            draw();
        }
    }
}