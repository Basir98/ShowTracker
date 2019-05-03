package showtracker.client;

import showtracker.Episode;
import showtracker.Show;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * @author Filip Spånberg
 * En panel som visar nästa avsnitt tittare ska se
 */
public class Home extends JPanel {
    private ClientController cc;
    private DecimalFormat df = new DecimalFormat("0.#");
    private JScrollPane scrollPane = new JScrollPane();
    private JViewport jvp;

    public Home(ClientController cc) {
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new FlowLayout(FlowLayout.LEFT));
        setLayout(new BorderLayout());
        this.cc = cc;
        jvp = scrollPane.getViewport();
        jvp.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(scrollPane, BorderLayout.CENTER);
        draw();
    }

    void draw() {
        jvp.removeAll();
        for (Show sh : cc.getUser().getShows()) {
            Episode currentEpisode = null;
            for (int i = 0; i < sh.getEpisodes().size() && currentEpisode == null; i++)
                if (!sh.getEpisodes().get(i).isWatched() && sh.getEpisodes().get(i).getSeasonNumber() != 0)
                    currentEpisode = sh.getEpisodes().get(i);
            if (currentEpisode != null) {
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
                //lbWidth.setPreferredSize(new Dimension(300, 1));
                panel.add(lbWidth, BorderLayout.SOUTH);
                //panel.setMaximumSize(new Dimension(300, 100));
                //panel.setPreferredSize(new Dimension(320, 50));
                jvp.add(panel);
            }
        }
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private class EpisodeListener implements ActionListener {
        private Episode ep;

        public EpisodeListener(Episode ep) {
            this.ep = ep;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ep.setWatched(true);
            draw();
        }
    }
}