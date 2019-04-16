package showtracker.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class ShowInfo extends JFrame{
	private JPanel m = new JPanel(); //MainPanel
	private JPanel panel = new JPanel();
	private JScrollPane mm = new JScrollPane(); //sp
	private int nbrOfSeasons=15 , nbrOfEpisodes=4;
	private int dropdownEpCounter = 0;
	
	private JButton[] seasonBtnArray = new JButton[nbrOfSeasons];
	private JPanel[] seasonPnlArray = new JPanel[nbrOfSeasons];
	private JLabel[] episodeLblArray= new JLabel[nbrOfEpisodes];
	
	
	public ShowInfo() {
		draw();
		add(mm);
		setSize(400,400);
		setVisible(true);
	}
	
	private void draw() {
		m.setLayout(new BoxLayout(m, BoxLayout.Y_AXIS));
		drawPanels(); // ritar alla baneler o sätter buttonlyssnare
		mm.setViewportView(m);
		mm.setLayout(new ScrollPaneLayout());
	}
	
	private void drawPanels() {
		
		for(int i=1 ; i<=nbrOfSeasons ; i++) {
			seasonPnlArray[i-1]=new JPanel();
			seasonPnlArray[i-1].setPreferredSize(new Dimension(200,30));
			seasonPnlArray[i-1].setLayout(new BoxLayout(seasonPnlArray[i-1], BoxLayout.Y_AXIS));
			seasonBtnArray[i-1]=new JButton("Season " + (i));
			
			seasonBtnArray[i-1].addMouseListener(new MouseAdapter() {
				private int counter = dropdownEpCounter;
				@Override
				public void mouseClicked(MouseEvent e) {	
					openPanel(counter);
				}
			});
			seasonPnlArray[i-1].add(seasonBtnArray[i-1]);
			m.add(seasonPnlArray[i-1]);
			dropdownEpCounter++;
		}
	}

	protected void openPanel(int counter) {
		System.out.print(counter);
		
		
		for(int i=counter ; i<nbrOfSeasons ; i++) {
			
			if(i==counter && seasonPnlArray[i].getHeight()<35) {
				seasonPnlArray[counter].setPreferredSize(new Dimension(200,seasonPnlArray[counter].getHeight()+(seasonPnlArray[counter].getHeight()*nbrOfEpisodes)));
				for(int z=0 ; z<nbrOfEpisodes ; z++) {
					episodeLblArray[z] = new JLabel("Episode " + (z+1));
					episodeLblArray[z].setPreferredSize(new Dimension(150, 15));
					seasonPnlArray[counter].add(episodeLblArray[z]);
				}
			}else if(i==counter && seasonPnlArray[i].getHeight()>35) {
				
				seasonPnlArray[counter].setPreferredSize(new Dimension(200,seasonPnlArray[counter].getHeight()/(nbrOfEpisodes+1)));
				
				m.revalidate();
			}
			
			if(i>counter && seasonPnlArray[i].getY()<35) {
				
			}else if(i>counter && seasonPnlArray[i].getY()>35) {
				
			}
		}
		
		
		
		
		
		m.revalidate();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShowInfo ss = new ShowInfo();
	}

}
