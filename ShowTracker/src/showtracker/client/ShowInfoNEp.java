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
import java.util.ArrayList;
import java.awt.FlowLayout;

public class ShowInfoNEp extends JFrame{
	
	private JPanel m = new JPanel();
	private JPanel panel = new JPanel();
	private JScrollPane mm = new JScrollPane();
	private ArrayList <JPanel> panels = new ArrayList <JPanel>();
	private int nbrS = 15,  nbrE =30, x = 1;
	
	private JPanel headerBar;
	private JButton button1 = new JButton("Profile");
	private JButton button2 = new JButton();
	private JButton button3 = new JButton("");
	private JButton button4 = new JButton("Exit");
	private JButton infoBtn;
	private JLabel showName;
	private Show show;
	private ImageIcon image;

	public ShowInfoNEp(Show show) {
		this.show=show;
		draw();
	}

	private void draw() {
		// TODO Auto-generated method stub
		m.setLayout(new BoxLayout(m, BoxLayout.Y_AXIS));
		ritaPaneler(); // ritar alla baneler o sätter buttonlyssnare
		mm.setViewportView(m);
		mm.setLayout(new ScrollPaneLayout());
		
		
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

		bottomPanel.add(button1);
		bottomPanel.add(button2);
		bottomPanel.add(button3);
		bottomPanel.add(button4);
		
		
		add(headerBar, BorderLayout.NORTH);
		add(mm, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		pack();
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-this.getSize().width)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-this.getSize().height)/2);
		setVisible(true);
	}
	private void ritaPaneler() {
		for(int i = 1 ; i <= nbrS ; i++) {
			panel = new JPanel();
			JButton button = new JButton("button" + i);
			button.addActionListener(new ActionListener() {
				private int counter = x;
				@Override
				public void actionPerformed(ActionEvent e) {
					openPanel(counter);
				}
			});
			panel.add(button);
			panels.add(panel);
			m.add(panel);
			x++;
		}
	}
	
	protected void openPanel(int i) {
		int x = i-1;
//		panels.get(x).setPreferredSize(new Dimension(100,nbrE*35));
//		panels.get(i-1).setBackground(Color.CYAN);
		for (int y = 0 ; y < nbrE; y++ ) {
			panels.get(x).add(new JLabel("episode:" +(y+1)));
		}
		panels.get(x).setLayout(new GridLayout(nbrE,1));
		System.out.print(i);
		m.revalidate();

	}

	

	public static void main(String[] args) {
		ShowInfoNEp s = new ShowInfoNEp(new Show("Tets"));
	}
}
