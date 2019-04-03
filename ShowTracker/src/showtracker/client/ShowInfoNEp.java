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
	JLabel episodezz;
	private Show show;
	private ImageIcon image;
	private final JLayeredPane layeredPane = new JLayeredPane();
	private Timer t1;
	private int nbrOfSeasons=3 , NbrOfEpisodes=4;

	
	private final JCheckBox checkBox_1 = new JCheckBox("");
	private final JCheckBox checkBox_2 = new JCheckBox("");
	private final JCheckBox checkBox_3 = new JCheckBox("");

	
	

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
		
//		JPanel panel = new JPanel();
//		panel.setBounds(10, 62, 190, 35);
//		mainPanel.add(panel);
//		panel.setLayout(null);
		
		
		int x=0, y=5, w=150, h=30, xx=10, yy=60, ww=190, hh=40;
		
		for(int i=0 ; i<nbrOfSeasons ; i++) {
			
			JPanel panel = new JPanel();
			panel.setBounds(xx, yy, ww, hh);
			mainPanel.add(panel);
			panel.setLayout(null);
			
			
			JButton test = new JButton("Season " + i);
			test.setBounds(x, y, w, h);
			test.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {	
					int test = 5;
//					if(panel.getHeight()>hh+5){
//						panel.setSize(190, 40);
//					
//					}else {
//						panel.setSize(190,3000);
					for(int z=0; z<NbrOfEpisodes ; z++) {
							
							JCheckBox checkBox = new JCheckBox();
							episodezz = new JLabel("Episode " + z);
							panel.add(episodezz);
//							panel.add(checkBox);
							episodezz.setBounds(x+25, test, 140, 30);
							checkBox.setBounds(x+140, test, w, h);
							test+=20;
							
							
								episodezz.enable(isEnabled());
//								episodezz.setVisible(episodezz.isVisible());
							
							
					}
					mainPanel.repaint();
					
					
	
				}
			});
			y+=5;
			
//			for(int z=0; z<NbrOfEpisodes ; z++) {
//				JCheckBox checkBox = new JCheckBox();
//				JLabel episodezz = new JLabel("Episode " + z);
//				panel.add(episodezz);
////				panel.add(checkBox);
//				episodezz.setBounds(x+25, y, 140, 30);
//				checkBox.setBounds(x+140, y, w, h);
//				y+=20;
//				episodezz.hide();
//			}
			panel.add(test);		
			yy+=35;
		}
		

		
//		checkBox.setBounds(151, 160, 60, 40);
		
//		panel_1.add(checkBox);
//		checkBox_1.setBounds(151, 115, 60, 40);
//		
//		panel_1.add(checkBox_1);
//		checkBox_2.setBounds(151, 70, 60, 40);
//		
//		panel_1.add(checkBox_2);
//		checkBox_3.setBounds(151, 25, 60, 40);
		
//		panel_1.add(checkBox_3);
//		label.setBounds(35, 29, 139, 29);
//		
//		panel_1.add(label);
//		label_1.setBounds(35, 74, 139, 29);
//		
//		panel_1.add(label_1);
//		label_2.setBounds(35, 119, 139, 29);
//		
//		panel_1.add(label_2);
//		label_3.setBounds(35, 164, 139, 29);
//		
//		panel_1.add(label_3);
		
		
		pack();
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2);
		setVisible(true);
	}
	public static void main(String[] args) {
		ShowInfoNEp s = new ShowInfoNEp(new Show("Tets"));
	}
}
