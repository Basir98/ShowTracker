package showtracker.client;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import showtracker.Episode;
import showtracker.Helper;
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

	private JLabel showName;
	private Show show;

	private int x = 0;


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
		infoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "<html><body><p style='width: 200px;'>"+show.getDescription()+"</p></body></html>");
			}
		});
		showName = new JLabel(show.getName());
		headerBar.add(showName);
		headerBar.add(infoBtn, BorderLayout.EAST);
		headerBar.setBorder(new LineBorder(Color.black));

		add(headerBar,BorderLayout.NORTH);

		setLayout(new BorderLayout());
		add(headerBar,BorderLayout.NORTH);
		add(scrollPane);
		
	}

	private void ritaPaneler() {
		JButton button;
		for(double d : show.getSeasons()) {
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			panel.add(Box.createHorizontalGlue());
			if(d==0) {
				button = new JButton("Specials");
			}else {
				button = new JButton("Season " + Helper.df.format(d));
			}
//			JButton button = new JButton("Season " + Helper.df.format(d));
			button.setMinimumSize(new Dimension(100,30));
			button.setMaximumSize(new Dimension(100,30));
			button.addActionListener(new ActionListener() {
				private int counter = x;
				private double doubleCounter = (double)x;
				private boolean opened = false;
				@Override
				public void actionPerformed(ActionEvent e) {

					openPanel(counter, opened, doubleCounter, panel);
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

	protected void openPanel(int i, boolean opened, double y, JPanel jpanel) {
		if(!(opened)) {
			jpanel.setLayout(new BoxLayout(jpanel,BoxLayout.PAGE_AXIS));
//			for (int y = 0 ; y<show.getEpisodes().size(); y++) {
//				
//				Episode e = show.getEpisodes().get(y);
//				if(e.getSeasonNumber()==i) {
//					JLabel lbl = new JLabel("Episode " +e.getEpisodeNumber() + "\n" + e.getName() );
//					panels.get(i-1).add(lbl);	
//					nbrEp.add(lbl);
//				}
//			}
			for(Episode ep : show.getEpisodes()) {
				if(ep.getSeasonNumber()==y) {
					JLabel lbl = new JLabel("Episode " + Helper.df.format(ep.getEpisodeNumber()) + " - " + ep.getName() );
					lbl.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							JOptionPane.showMessageDialog(null, "<html><body><p style='width: 200px;'>"+show.getEpisode(ep.getSeasonNumber(), ep.getEpisodeNumber()).getDescription()+"</p></body></html>", ep.getName(), JOptionPane.INFORMATION_MESSAGE);
						}
					});
					panels.get(i).add(lbl);	
					nbrEp.add(lbl);
				}
			}
			
//			System.out.print(i +":");
//			mainPanel.revalidate();
		}
		else {
			for(int q=0 ; q<nbrEp.size() ; q++) {
					nbrEp.get(q).hide();
			}
		}
		
		mainPanel.revalidate();
	}

	public static void main (String [] args) {
//		Show s = (Show) Helper.readFromFile("files/venture_bros.obj");
//		ShowInfoNEp ss = new ShowInfoNEp(s,cc);
		
		
//		JFrame frame = new JFrame();
//		frame.add(ss);
//		frame.setSize(400,500);
//		frame.setVisible(true);
//		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
	}
}
