package showtracker.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.border.LineBorder;

import showtracker.Show;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.FlowLayout;

public class ShowInfoNEp extends JFrame{
	private JPanel mainPanel = new JPanel ();
	private JScrollPane sp = new JScrollPane();
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

	private JButton[] jBtns = new JButton[nbrOfSeasons];
	private JPanel[] jPnls = new JPanel[nbrOfSeasons];
	private JLabel[] jLbls = new JLabel[nbrOfEpisodes];
	private int xPnl=10, yPnl=60, wPnl=460, hPnl=30;
	private int xBtn=5, yBtn=0, wBtn=150, hBtn=30;
	private int xLbl=15, yLbl=30, wLbl=150, hLbl=25;
	private int dropdownEpCounter = 0;
	

	
	
	private JPanel m = new JPanel();
	private JPanel panel = new JPanel();
	private JScrollPane mm = new JScrollPane();
	private ArrayList <JPanel> panels = new ArrayList <JPanel>();
	private int nbrS = 15,  nbrE =30, x = 1;
	
	
	
	public ShowInfoNEp(Show show) {
		this.show=show;
		add(sp);
		draw();
		setSize(400,400);
//		pack();
		setVisible(true);
	}

	private void draw() {
		// TODO Auto-generated method stub
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//		mainPanel.setPreferredSize(new Dimension(200,200));
//		mainPanel.setLayout(new FlowLayout());
		drawSeasonButtons();
		sp.setViewportView(mainPanel);
		sp.setLayout(new ScrollPaneLayout());
		
		
		
		
		
		headerBar = new JPanel();
		headerBar.setBounds(0, 0, 500, 50);
		headerBar.setLayout(new BorderLayout());
		headerBar.setPreferredSize(new Dimension(500, 50));
		
		infoBtn = new JButton("i");
		infoBtn.setPreferredSize(new Dimension(30, 50));
		
		showName = new JLabel(show.getName());
		
		headerBar.add(showName);
		headerBar.add(infoBtn, BorderLayout.EAST);
		headerBar.setBorder(new LineBorder(Color.black));
		
		JPanel bottomPanel = new JPanel();
//		bottomPanel.setBounds(0, 371, 500, 29);
		bottomPanel.setLayout(new GridLayout(1, 4, 1, 1));
		image = new ImageIcon("images/home-screen.png");
		Image img = image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon imgIcon = new ImageIcon(img);
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		button2.setIcon(imgIcon);
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Profile p;
				try {
					dispose();
					
					p = new Profile();
					p.setVisible(true);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

//		button1.setIcon(new ImageIcon("images/home-screen.png"));

		bottomPanel.add(button1);
		bottomPanel.add(button2);
		bottomPanel.add(button3);
		button4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		bottomPanel.add(button4);
		mainPanel.setLayout(null);
		
//		mainPanel.add(headerBar);
//		mainPanel.add(bottomPanel);
		getContentPane().add(headerBar, BorderLayout.NORTH);
		getContentPane().add(bottomPanel,BorderLayout.SOUTH);
//		getContentPane().add(sp, BorderLayout.CENTER);

		
//		JScrollBar scrollBar = new JScrollBar();
//		scrollBar.setBounds(479, 62, 15, 297);
//		mainPanel.add(scrollBar);
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
		
//		for(int i=0 ; i<nbrOfEpisodes ;i++) {
//			jLbls[i] = new JLabel("Episode " + (i+1));
//		}

		
		

		
//		sp.setViewportView(mainPanel);
//		sp.add(mainPanel);
//		sp.setPreferredSize(new Dimension(200,700));
//		setSize(300,400);
		pack();
//		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2);
		setVisible(true);
	}
	
	
	

	
	private void drawSeasonButtons() {
		// TODO Auto-generated method stub
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
	}

	protected void epDropDown(int counter) {
		int count=counter;
		System.out.println(counter);
		for(int i=counter ; i<nbrOfSeasons ; i++) {
			
			if(i>counter && jPnls[i].getY()<35) {
				jPnls[i].setBounds(jPnls[i].getX(), jPnls[i].getY()+(jPnls[i].getY()*nbrOfEpisodes), jPnls[i].getWidth(), jPnls[i].getHeight());
//				yPnl+=30;
//				jBtns[i].setBounds(jBtns[i].getX(), jBtns[i].getY()+(jBtns[i].getY()*nbrOfEpisodes), jBtns[i].getWidth(), jBtns[i].getHeight());
				mainPanel.revalidate();
			}else if(i>counter && jPnls[i].getY()>35) {
				jPnls[i].setBounds(jPnls[i].getX(), jPnls[i].getY()/(nbrOfEpisodes+1), jPnls[i].getWidth(), jPnls[i].getHeight());
				mainPanel.revalidate();
			}
			
			if(i==counter && jPnls[i].getHeight()<35) {
				jPnls[i].setBounds(jPnls[i].getX(), jPnls[i].getY(), jPnls[i].getWidth(), (jPnls[i].getHeight()+(jPnls[i].getHeight()*nbrOfEpisodes)));
				for(int z=0 ; z<nbrOfEpisodes ; z++) {
					jLbls[z] = new JLabel("Episode " + (z+1));
					jLbls[z].setBounds(xLbl, yLbl, wLbl, hLbl);
					jLbls[z].setHorizontalAlignment(SwingConstants.CENTER);
					jPnls[i].add(jLbls[z]);
					yLbl+=25;
					mainPanel.revalidate();
				}
			}else if(i==counter && jPnls[i].getHeight()>35) {
				jPnls[i].setBounds(jPnls[i].getX(), jPnls[i].getY(), jPnls[i].getWidth(), jPnls[i].getHeight()/(nbrOfEpisodes+1));
//				for(int n=counter+1 ; n<nbrOfSeasons ; n++) {
//					jPnls[n].setBounds(jPnls[n].getX(), jPnls[n].getY()/(nbrOfEpisodes+1), jPnls[n].getWidth(), jPnls[n].getHeight());
				
//				}
				mainPanel.revalidate();
			}
			
			
			count++;
			
		}
		xPnl=10; yPnl=60; wPnl=460;	hPnl=30; xBtn=5; yBtn=0; wBtn=150; hBtn=30;	xLbl=15; yLbl=30; wLbl=150; hLbl=25;
	}

	public static void main(String[] args) {
		ShowInfoNEp s = new ShowInfoNEp(new Show("Tets"));
	}
}
