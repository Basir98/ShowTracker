package showtracker.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import showtracker.Helper;
import showtracker.Show;


/**
 * A panel for user show list
 * @author Basir Ramazani
 *
 */


class ShowList extends JPanel {
    private ClientController cc;
    private JPanel pnlShowList = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();

    ShowList(ClientController cc) {
        this.cc = cc;
        MyDocumentListener myDocumentListener = new MyDocumentListener();
        setLayout(new BorderLayout());
        add(myDocumentListener, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    void draw() {
        draw(cc.getUser().getShows());
    }

    private void draw(ArrayList<Show> shows) {
        shows.sort(new Helper.NameComparator());
        GridBagConstraints gbc = new GridBagConstraints();
        pnlShowList.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;

        pnlShowList.removeAll();
        if (shows.size() > 0) {
            for (Show show : shows) {
                JButton btnInfo = new JButton("Info");
                JButton btnUpdate = new JButton("Update");
                JButton btnRemove = new JButton("Remove");

                JPanel pnlMiddle = new JPanel(new FlowLayout());
                pnlMiddle.add(new JLabel("<html><body><p style=\"width: 200px; text-align: center;\">" + show.getName() + "</p></body></html>"));

                JPanel pnlSouth = new JPanel(new FlowLayout());
                pnlSouth.add(btnInfo);
                pnlSouth.add(btnUpdate);
                pnlSouth.add(btnRemove);

                JPanel pnlMain = new JPanel(new BorderLayout());
                pnlMain.setBorder(new LineBorder(Color.DARK_GRAY));
                pnlMain.add(pnlMiddle, BorderLayout.CENTER);
                pnlMain.add(pnlSouth, BorderLayout.SOUTH);

                btnInfo.addActionListener(e -> cc.setPanel("Info", show));

                btnUpdate.addActionListener(e -> cc.getUser().updateShow(cc.updateShow(show)));

                btnRemove.addActionListener(e -> {
                    cc.getUser().removeShow(show);
                    draw();
                });

                gbc.gridx = 0;
                gbc.weightx = 1;

                pnlShowList.add(pnlMain, gbc);

            }
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.weighty = 1;
            pnlShowList.add(new JPanel(), gbc);

        } else {
            pnlShowList.add(new JLabel("   Kunde inte hitta show med angivet namn !!"));

        }
        scrollPane.setViewportView(pnlShowList);
        scrollPane.setLayout(new ScrollPaneLayout());
        pnlShowList.revalidate();
    }

    private class MyDocumentListener extends JTextField implements DocumentListener {

        MyDocumentListener() {
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

        private void searchShow() {
            ArrayList<Show> searchShows = new ArrayList<>();
            for (Show show : cc.getUser().getShows()) {
                if (show.getName().toLowerCase().contains(getText().toLowerCase()))
                    searchShows.add(show);
            }
            pnlShowList.removeAll();
            draw(searchShows);
        }
    }
}