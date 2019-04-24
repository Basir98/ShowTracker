package showtracker.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class Login  {
	ClientController cc = new ClientController();
	JFrame frame = new JFrame();
	//    private JLabel nameLabel = new JLabel();

	private JButton signIn = new JButton(" Sign in ");
	private JButton signUp = new JButton(" Sign up ");


	private JLabel usernameLabel = new JLabel("Name : ");
	private JLabel userPasswordLabel = new JLabel("Password : ");
	private JLabel userEmailLabel = new JLabel("Email : ");

	private JTextField username = new JTextField();
//	private JLabel username = new JLabel();
	private JTextField userPassword = new JTextField();
	private JTextField userEmail = new JTextField();


	public Login() 
	{	

		frame.add(textFieldPanel(),BorderLayout.NORTH);
		frame.add(buttonPanel(),BorderLayout.SOUTH);

		frame.setPreferredSize(new Dimension(500,250));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);


	}
	public JPanel textFieldPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3,2));
		topPanel.setPreferredSize(new Dimension(250,100));

		topPanel.add(usernameLabel);
		topPanel.add(username);

		topPanel.add(userPasswordLabel);
		topPanel.add(userPassword);

		topPanel.add(userEmailLabel);
		topPanel.add(userEmail);


		return topPanel;
	}

	public JPanel buttonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.setPreferredSize(new Dimension(500,50));

		buttonPanel.add(signIn);
		buttonPanel.add(signUp);

		signIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					signIn();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				signUp();
			}
		});
		return buttonPanel;
	}

	public void signIn() throws FileNotFoundException {
		cc.signIn(username.getText(), userPassword.getText());
	}
	public void signUp() {
		String pattern1 = "[a-z]";
		String pattern2 = "[A-Z]";
		String pattern3 = "[0-9]";
		
		Pattern p1 = Pattern.compile(pattern1);
		Pattern p2 = Pattern.compile(pattern2);
		Pattern p3 = Pattern.compile(pattern3);
		
		Matcher match1 = p1.matcher(userPassword.getText());
		Matcher match2 = p2.matcher(userPassword.getText());
		Matcher match3 = p3.matcher(userPassword.getText());
		
		if (userPassword.getText().length() >= 8 && match1.find()&& match2.find() && match3.find()) {
			cc.signUp(username.getText(), userPassword.getText(),userEmail.getText());

		} else  {
			JOptionPane.showMessageDialog(null, "invalid password, please try again");

		}
		
	}

	public static void main(String[]args) {
		new Login();
	}
}
