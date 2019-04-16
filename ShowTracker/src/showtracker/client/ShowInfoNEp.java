package showtracker.client;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import showtracker.Show;



public class ShowInfoNEp extends JFrame{

	private JPanel mainPanel  = new JPanel();	//mainPanel
	private JPanel panel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane();		//sp
	private ArrayList <JPanel> panels = new ArrayList <JPanel>();
	private ArrayList <JLabel> nbrEp = new ArrayList <JLabel>();
	//>>>>>>> refs/remotes/origin/Adam_branch
	private JPanel headerBar;
	private JButton infoBtn;
	private ImageIcon image;
	//>>>>>>> refs/remotes/origin/Adam_branch
	private JButton button1 = new JButton("Profile");
	private JButton button2 = new JButton();
	private JButton button3 = new JButton("");
	private JButton button4 = new JButton("Exit");
	private JLabel showName;
	private Show show;
	private int nbrOfSeasons = 15,  nbrOfEpisodes =8, x = 1;


	//>>>>>>> refs/remotes/origin/Adam_branch
	public ShowInfoNEp(Show show) {
		this.show=show;
		draw();
		add(scrollPane);
		setSize(400,400);
		setVisible(true);
	}

	private void draw() {

		ritaPaneler(); 
		scrollPane.setViewportView(mainPanel);
		scrollPane.setLayout(new ScrollPaneLayout());
		scrollPane.setBackground(Color.CYAN);
		//>>>>>>> refs/remotes/origin/Adam_branch

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(Box.createHorizontalGlue());

		//>>>>>>> refs/remotes/origin/Adam_branch

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

		JPanel bottomPanel = new JPanel();
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
				} catch (FileNotFoundException e1) {}
			}
		});
		//>>>>>>> refs/remotes/origin/Adam_branch

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
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private void ritaPaneler() {
		for(int i = 1 ; i <= nbrOfSeasons ; i++) {
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			panel.add(Box.createHorizontalGlue());
			JButton button = new JButton("button" + i);
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
		//>>>>>>> refs/remotes/origin/Adam_branch
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
				//				if(q==(i-1)) {
				nbrEp.get(q).hide();
				//				}
			}

			//>>>>>>> refs/remotes/origin/Adam_branch
		}
	}

	public static void main (String [] args) {
		ShowInfoNEp ss = new ShowInfoNEp(new Show("Test"));
	}
}
