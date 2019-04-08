package showtracker.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.LineBorder;

import showtracker.Show;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

public class ShowInfoNEp extends JFrame{
	private JPanel mainPanel;
	private JPanel headerBar;
	private JPanel innerPanel;
	private JButton button1 = new JButton("Profile");
	private JButton button2 = new JButton();
	private JButton button3 = new JButton("");
	private JButton button4 = new JButton("Exit");
	private JButton infoBtn;
	private JLabel showName;
	private Show show;
	private ImageIcon image;
	private final JLayeredPane layeredPane = new JLayeredPane();
	private Timer t1;
	private int nbrOfSeasons=3 , nbrOfEpisodes=4;

	
	private final JCheckBox checkBox_1 = new JCheckBox("");
	private final JCheckBox checkBox_2 = new JCheckBox("");
	private final JCheckBox checkBox_3 = new JCheckBox("");
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel_2 = new JPanel();
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JPanel panel_8 = new JPanel();
	private final JPanel panel_5 = new JPanel();
	private final JPanel panel_6 = new JPanel();
	private final JPanel panel_7 = new JPanel();
	private final JPanel panel_9 = new JPanel();
	private final JButton btnSeason = new JButton("Season 1");
	private final JButton btnSeason_1 = new JButton("Season 2");
	private final JButton btnSeason_2 = new JButton("Season 3");
	private final JButton btnSeason_4 = new JButton("Season 5");
	private final JButton btnSeason_3 = new JButton("Season 4");
	private final JButton btnSeason_5 = new JButton("Season 6");
	private final JButton btnSeason_6 = new JButton("Season 7");
	private final JButton btnSeason_7 = new JButton("Season 8");
	private final JButton btnSeason_8 = new JButton("Season 9");
	private final JButton btnSeason_9 = new JButton("Season 10");
	private JButton[] jBtns = new JButton[nbrOfSeasons];
	private JPanel[] jPnls = new JPanel[nbrOfSeasons];
	private JLabel[] jLbls = new JLabel[nbrOfEpisodes];
	private int xPnl=10, yPnl=60, wPnl=460, hPnl=30;
	private int xBtn=5, yBtn=0, wBtn=150, hBtn=30;
	private int xLbl=15, yLbl=30, wLbl=150, hLbl=25;
	private int dropdownEpCounter = 0;


	public ShowInfoNEp(Show show) {
		this.show=show;
		draw();
	}

	private void draw() {
		// TODO Auto-generated method stub
		
		mainPanel = new JPanel ();
		mainPanel.setPreferredSize(new Dimension(500,400));
		
		headerBar = new JPanel();
		headerBar.setBounds(0, 0, 500, 50);
		headerBar.setLayout(new BorderLayout());
		headerBar.setPreferredSize(new Dimension(620, 50));
		
		infoBtn = new JButton("i");
		infoBtn.setPreferredSize(new Dimension(30, 50));
		
		showName = new JLabel(show.getName());
		
		headerBar.add(showName);
		headerBar.add(infoBtn, BorderLayout.EAST);
		headerBar.setBorder(new LineBorder(Color.black));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 371, 500, 29);
		bottomPanel.setLayout(new GridLayout(1, 4, 1, 1));
		image = new ImageIcon("images/home-screen.png");
		Image img = image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon imgIcon = new ImageIcon(img);
		button2.setIcon(imgIcon);
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
//		button1.setIcon(new ImageIcon("images/home-screen.png"));

		bottomPanel.add(button1);
		bottomPanel.add(button2);
		bottomPanel.add(button3);
		bottomPanel.add(button4);
		mainPanel.setLayout(null);
		
		
		mainPanel.add(headerBar);
		mainPanel.add(bottomPanel);
		getContentPane().add(mainPanel, BorderLayout.WEST);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(479, 62, 15, 297);
		mainPanel.add(scrollBar);
		layeredPane.setBounds(161, 131, 1, 1);
		mainPanel.add(layeredPane);
		
		for(int i=0 ; i<nbrOfSeasons ; i++) {
			jPnls[i]=new JPanel();
			jBtns[i]=new JButton("Season " + (i+1));
			jPnls[i].setBounds(xPnl, yPnl, wPnl, hPnl);
			mainPanel.add(jPnls[i]);
			jPnls[i].setLayout(null);
			jBtns[i].addMouseListener(new MouseAdapter() {
				private int counter = dropdownEpCounter;
				@Override
				public void mouseClicked(MouseEvent e) {	
					epDropDown(counter);
				}
			});
			jBtns[i].setBounds(xBtn, yBtn, wBtn, hBtn);
			jPnls[i].add(jBtns[i]);
			
			dropdownEpCounter++;
			yPnl+=30;
		}

		
		
		
		
//		panel.setBounds(10, 60, 460, 30);
//		
//		mainPanel.add(panel);
//		panel.setLayout(null);
//		btnSeason.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason.setBounds(5, 0, 150, 30);
//		
//		panel.add(btnSeason);
//		panel_1.setBounds(10, 90, 460, 30);
//		
//		mainPanel.add(panel_1);
//		panel_1.setLayout(null);
//		btnSeason_1.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_1.setBounds(5, 0, 150, 30);
//		
//		panel_1.add(btnSeason_1);
//		panel_2.setBounds(10, 120, 460, 30);
//		
//		mainPanel.add(panel_2);
//		panel_2.setLayout(null);
//		btnSeason_2.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_2.setBounds(5, 0, 150, 30);
//		
//		panel_2.add(btnSeason_2);
//		panel_3.setBounds(10, 150, 460, 30);
//		
//		mainPanel.add(panel_3);
//		panel_3.setLayout(null);
//		btnSeason_3.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_3.setBounds(5, 0, 150, 30);
//		panel_3.add(btnSeason_3);
//		panel_4.setBounds(10, 180, 460, 30);
//		
//		mainPanel.add(panel_4);
//		panel_4.setLayout(null);
//		btnSeason_4.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_4.setBounds(6, -1, 150, 30);
//		
//		panel_4.add(btnSeason_4);
//		panel_5.setBounds(10, 210, 460, 30);
//		
//		mainPanel.add(panel_5);
//		panel_5.setLayout(null);
//		btnSeason_5.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_5.setBounds(5, 0, 150, 30);
//		
//		panel_5.add(btnSeason_5);
//		panel_6.setBounds(10, 240, 460, 30);
//		
//		mainPanel.add(panel_6);
//		panel_6.setLayout(null);
//		btnSeason_6.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_6.setBounds(5, 0, 150, 30);
//		
//		panel_6.add(btnSeason_6);
//		panel_7.setBounds(10, 270, 460, 30);
//		
//		mainPanel.add(panel_7);
//		panel_7.setLayout(null);
//		btnSeason_7.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_7.setBounds(5, 0, 150, 30);
//		
//		panel_7.add(btnSeason_7);
//		panel_8.setBounds(10, 300, 460, 30);
//		
//		mainPanel.add(panel_8);
//		panel_8.setLayout(null);
//		btnSeason_8.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_8.setBounds(5, 0, 150, 30);
//		
//		panel_8.add(btnSeason_8);
//		panel_9.setBounds(10, 330, 460, 30);
//		
//		mainPanel.add(panel_9);
//		panel_9.setLayout(null);
//		btnSeason_9.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnSeason_9.setBounds(5, 0, 150, 30);
//		
//		panel_9.add(btnSeason_9);
		
		
		
		pack();
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2);
		setVisible(true);
	}
	
	
	
//
//	lblEpisode.setHorizontalAlignment(SwingConstants.CENTER);
//	lblEpisode.setBounds(15, 30, 150, 25);
//	
//	panel_2.add(lblEpisode);
//	lblEpisode_1.setHorizontalAlignment(SwingConstants.CENTER);
//	lblEpisode_1.setBounds(15, 60, 150, 25);
//	
//	panel_2.add(lblEpisode_1);
//	lblEpisode_2.setHorizontalAlignment(SwingConstants.CENTER);
//	lblEpisode_2.setBounds(15, 90, 150, 25);
//	
//	panel_2.add(lblEpisode_2);
//	label.setHorizontalAlignment(SwingConstants.CENTER);
//	label.setBounds(15, 120, 150, 25);
//	
	
	protected void epDropDown(int counter) {
		int count=0;
		System.out.println(counter);
		for(int i=counter ; i<nbrOfEpisodes ; i++) {
			
			if(i==counter && jPnls[i].getY()<35) {
				jPnls[i].setBounds(jPnls[i].getX(), jPnls[i].getY()+(jPnls[i].getY()*nbrOfEpisodes), jPnls[i].getWidth(), jPnls[i].getHeight());
				for(int z=0 ; z<nbrOfEpisodes ; z++) {
					jLbls[z] = new JLabel("Episode " + (z+1));
					jLbls[z].setHorizontalAlignment(SwingConstants.CENTER);
					jLbls[z].setBounds(xLbl, yLbl, wLbl, hLbl);
					jPnls[i].add(jLbls[z]);
					yLbl+=30;
				}
			}else if(i==counter && jPnls[i].getY()>35) {
				jPnls[i].setBounds(jPnls[i].getX(), jPnls[i].getY()/(nbrOfEpisodes+1), jPnls[i].getWidth(), jPnls[i].getHeight());
			}
			count++;
		}
	}

	public static void main(String[] args) {
		ShowInfoNEp s = new ShowInfoNEp(new Show("Tets"));
	}
}
