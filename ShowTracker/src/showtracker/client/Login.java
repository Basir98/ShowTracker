package showtracker.client;

//<<<<<<< HEAD
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.GridLayout;
//=======
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import showtracker.Helper;
import showtracker.User;

import static showtracker.Helper.*;

/**
 * @author
 *
 */
public class Login extends JPanel {

	private ClientController cc;
	private JButton btLogIn = new JButton(" Log In ");
	private JButton btSignUp = new JButton("New here? Sign up!");;

	private JTextField tfUsernameSignUp;
	private JPasswordField pfPasswordSignUp;
	private JTextField tfEmailSignup;

	private JTextField tfUsername = new JTextField();
	private JPasswordField pfPassword = new JPasswordField();

	public Login(ClientController cc) {
		this.cc = cc;
		setLayout(null);

		ImageIcon ii = new ImageIcon("images/logo.jpg");
		Image image = ii.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		JLabel lbLogo = new JLabel(new ImageIcon(image));

		btLogIn.addActionListener(e -> checkUserLogin());
		btSignUp.addActionListener(e -> signUp());

		lbLogo.setBounds(75, 25, 150, 150);
		tfUsername.setBounds(60, 200, 200, 30);
		pfPassword.setBounds(60, 240, 200, 30);
		btLogIn.setBounds(100, 290, 120, 30);
		btSignUp.setBounds(60, 340, 200, 30);

		add(lbLogo);
		add(tfUsername);
		add(pfPassword);
		add(btLogIn);
		add(btSignUp);
	}

	public void draw() {
		tfUsername.setText("Username");
		pfPassword.setText("password");
		tfUsername.selectAll();
		pfPassword.selectAll();
	}

//<<<<<<< HEAD
//	public JPanel textFieldPanel() {
//		
//		JPanel topPanel = new JPanel();
//		topPanel.setLayout(new GridLayout(5, 1, 50, 50));
//		topPanel.setPreferredSize(new Dimension(250, 100));
//
//		textFieldUsernameLogin.setText("Username");
//		textFieldUsernameLogin.selectAll();
//
//		textFieldUserPasswordLogin.setText("Password");
//		textFieldUserPasswordLogin.selectAll();
//
//		topPanel.add(textFieldUsernameLogin);
//		topPanel.add(textFieldUserPasswordLogin);
//
//		return topPanel;
//
//	}
//
//	public JPanel buttonPanel() {
//		JPanel buttonPanel = new JPanel();
////		buttonPanel.setLayout(new GridLayout(2, 10));
//		buttonPanel.add(signInBtn);
//
//		signInBtn.addActionListener(e -> checkUserLogin());
//		singnUpPanel(buttonPanel);
//		buttonPanel.revalidate();
//
//		return buttonPanel;
//	}
//
//	public void singnUpPanel(JPanel panel) {
//
////		JPanel panel = new JPanel();
//		signUplbl = new JButton("New here? Sign up!");
//		panel.setLayout(new GridLayout(2, 1, 0, 5));
//
//		panel.add(new JLabel());
//		panel.add(signUplbl);
//
//		signUplbl.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int res = JOptionPane.showConfirmDialog(null, createAccount(), "Sign Up!", JOptionPane.OK_CANCEL_OPTION,
//						JOptionPane.PLAIN_MESSAGE);
//
//				while (!(checkPasswordValidity(passwordField.getText())
//						&& checkUsernameValidity(textFieldUserName.getText())
//						&& checkEmailValidity(textFieldEmail.getText())) && res == JOptionPane.OK_OPTION) {
//=======
	
	private void signUp() {
		int res = JOptionPane.showConfirmDialog(null, createAccount(), "Sign Up!", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		while (!(checkPasswordValidity(new String(pfPasswordSignUp.getPassword()))
				&& checkUsernameValidity(tfUsernameSignUp.getText())
				&& checkEmailValidity(tfEmailSignup.getText())) && res == JOptionPane.OK_OPTION) {

			if (!checkUsernameValidity(tfUsernameSignUp.getText()))
				JOptionPane.showMessageDialog(null, "No username");

			if (!checkEmailValidity(tfEmailSignup.getText()))
				JOptionPane.showMessageDialog(null, "Enter a email");

			if (!checkPasswordValidity(new String(pfPasswordSignUp.getPassword())))
				JOptionPane.showMessageDialog(null, "Your password must contain at least 8 charachters, "
						+ "one capital letter, one small letter and one digit!");

			res = JOptionPane.showConfirmDialog(null, createAccount(), "Sign Up!", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		}

		if (res == JOptionPane.OK_OPTION)
			cc.signUp(tfUsernameSignUp.getText(), new String(pfPasswordSignUp.getPassword()), tfEmailSignup.getText());
	}

	public JPanel createAccount() {
		JPanel panel = new JPanel(new GridLayout(7, 1));
		JLabel usernameLabel = new JLabel("Username : ");
		JLabel userPasswordLabel = new JLabel("Password : ");
		JLabel userEmailLabel = new JLabel("Email : ");

		tfUsernameSignUp = new JTextField(20);
		tfEmailSignup = new JTextField(20);
		pfPasswordSignUp = new JPasswordField(20);
		JCheckBox check = new JCheckBox("Show password");

		panel.add(usernameLabel);
		panel.add(tfUsernameSignUp);
		panel.add(userEmailLabel);
		panel.add(tfEmailSignup);
		panel.add(userPasswordLabel);
		panel.add(pfPasswordSignUp);
		panel.add(check);

		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (check.isSelected()) {
					pfPasswordSignUp.setEchoChar((char) 0);
				} else {
					pfPasswordSignUp.setEchoChar('*');
				}
			}
		});

		return panel;

	}

	public void checkUserLogin() {
		String username = tfUsername.getText();
		String password = new String(pfPassword.getPassword());
		System.out.println(username + ", " + password);
		User user = cc.logIn(username, password);
		if (user != null) {
			cc.setUser(user);
			cc.setButtonsEnabled(true);
			cc.setPanel("Home", null);
			System.out.println("Welcome back!");

		} else { // ny ide på UI, kan förbättras ^_^
			Helper.message("Login failed!");
			Border compound = null;
			Border redline = BorderFactory.createLineBorder(Color.red);
			compound = BorderFactory.createCompoundBorder(redline, compound);
			btSignUp.setBorder(BorderFactory.createCompoundBorder(redline, compound));
			revalidate();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
	
		Login log = new Login(	new ClientController ());
		log.draw();
		frame.setTitle("Login");
		frame.setPreferredSize(new Dimension(400, 500));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(log);
		frame.pack();
		frame.setVisible(true);
	}
}