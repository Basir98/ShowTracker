package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login  {
	ClientController cc;
	JFrame frame = new JFrame();

	private JButton signIn = new JButton(" Sign in ");
	private JButton signUp = new JButton(" Sign up ");

	private JTextField username = new JTextField();
	private JTextField userPassword = new JTextField();

	public Login() 
	{	

		//		frame.add(textFieldPanel(),BorderLayout.NORTH);
		frame.add(buttonPanel(),BorderLayout.SOUTH);

		frame.setPreferredSize(new Dimension(500,250));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);


	}
	//	public JPanel textFieldPanel() {
	//		return null;
	//	}

	public JPanel buttonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.setPreferredSize(new Dimension(500,50));

		buttonPanel.add(signIn);
		buttonPanel.add(signUp);

		return  buttonPanel;
	}


	
	public void signIn() {
		signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cc.signIn(Inp);
			}
		});
	}
	public void signUp() {
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cc.signUp();
			}
		});
	}
	
	public static void main(String[]args) {
		new Login();
	}
}
