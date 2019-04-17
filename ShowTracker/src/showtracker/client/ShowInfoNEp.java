package showtracker.client;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import showtracker.Show;


public class ShowInfoNEp extends JPanel	{
    private static ClientController cc  = new ClientController();

	private JPanel mainPanel  = new JPanel();	//mainPanel
	private JPanel panel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane();		//sp
	private ArrayList <JPanel> panels = new ArrayList <JPanel>();
	private ArrayList <JLabel> nbrEp = new ArrayList <JLabel>();
	private JPanel headerBar;
	private JButton infoBtn;
	private ImageIcon image;
	private JButton button1 = new JButton("Profile");
	private JButton button2 = new JButton();
	private JButton button3 = new JButton("");
	private JButton button4 = new JButton("Exit");
	private JLabel showName;
	private Show show;

	private int nbrOfSeasons = 15,  nbrOfEpisodes =8, x = 1;


	public ShowInfoNEp(Show show, ClientController cc ) {
		this.cc = cc;
		this.show=show;
		draw();
		
	}

	private void draw() {

		ritaPaneler(); 
		scrollPane.setViewportView(mainPanel);
		scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.setBackground(Color.CYAN);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(Box.createHorizontalGlue());


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

		add(headerBar,BorderLayout.NORTH);


//		JPanel bottomPanel = new JPanel();
//		bottomPanel.setLayout(new GridLayout(1, 4, 1, 1));
//		image = new ImageIcon("images/home-screen.png");
//		Image img = image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//		ImageIcon imgIcon = new ImageIcon(img);
//		button2.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//
//			}
//		});
//
//		button2.setIcon(imgIcon);
//		button1.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				Profile p;
//				try {
//
////					dispose();
//					p = new Profile(cc);
//					p.setVisible(true);
//				} catch (FileNotFoundException e1) {}
//			}
//		});
//
//		bottomPanel.add(button1);
//		bottomPanel.add(button2);
//		bottomPanel.add(button3);
//		button4.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				System.exit(0);
//			}
//		});
//		bottomPanel.add(button4);
		setLayout(new BorderLayout());
		add(headerBar,BorderLayout.NORTH);
//		add(bottomPanel, BorderLayout.SOUTH);
		add(scrollPane);
		
	}

	private void ritaPaneler() {
		for(int i = 1 ; i <= nbrOfSeasons ; i++) {
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			panel.add(Box.createHorizontalGlue());
			JButton button = new JButton("button" + i);
			button.setMinimumSize(new Dimension(100,30));
			button.setMaximumSize(new Dimension(100,30));
			button.addActionListener(new ActionListener() {
				private int counter = x;
				private boolean opened = false;
				@Override
				public void actionPerformed(ActionEvent e) {

					openPanel(counter, opened);
					if(!(opened)) {
						opened =true;
					}
					else { opened = false;}
				}
			});
			panel.add(button);
			panels.add(panel);
			mainPanel.add(panel);
			x++;
		}
	}

	protected void openPanel(int i, boolean opened) {
		if(!(opened)) {
			panels.get(i-1).setLayout(new BoxLayout(panels.get(i-1),BoxLayout.PAGE_AXIS));
			for (int y = 0 ; y<nbrOfEpisodes; y++) {
				JLabel lbl = new JLabel("Episode " +(y+1));
				panels.get(i-1).add(lbl);	
				nbrEp.add(lbl);
			}
			System.out.print(i +":");
			mainPanel.revalidate();
		}
		else {	
			for(int q=0 ; q<nbrEp.size() ; q++) {
					nbrEp.get(q).hide();
			}
		}
	}

	public static void main (String [] args) {
//		ShowInfoNEp ss = new ShowInfoNEp(new Show("Test"),cc);
//		JFrame frame = new JFrame();
//		frame.add(ss);
//		frame.setSize(400,500);
//		frame.setVisible(true);
//		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
	}
}
