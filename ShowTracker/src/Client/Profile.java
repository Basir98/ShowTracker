package Client;

import java.awt.*;

import javax.swing.*;

public class Profile {
	ClientController clientConttroller = new ClientController();
	JFrame frame = new JFrame();
	
	
	JLabel namn = new JLabel("Name  ");
	JLabel mail = new JLabel("Email  ");
	JLabel changeMail = new JLabel ("Change Email  ");
	JLabel changePass = new JLabel ("Chnage Password  ");
	
	JLabel inputName = new JLabel();
	JLabel inputMail = new JLabel();
	
	JTextField changeMa = new JTextField();
	JTextField changeP = new JTextField();
	
	JButton changeBtn1 = new JButton();
	JButton changeBtn2= new JButton();
	private int x =2;
	
	public Profile() {
	}
	
	public JPanel textFieldPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		panel.setPreferredSize(new Dimension(150,50));
		
		panel.add(changeMa);
		panel.add(changeMail);
		panel.add(changeBtn1);

		
		panel.add(changeP);
		panel.add(changePass);
		panel.add(changeBtn2);
		
		return panel;
	}
//	public JPanel labelPanel() {
//		JPanel panel = new JPanel();
//		panel.setLayout(new GridLayout());
//	}
	
	
	
	
	
	
	
}
