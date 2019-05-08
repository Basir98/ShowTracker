package showtracker.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import showtracker.User;

/**
 * @author
 *
 */
public class Login extends JPanel {

	private ClientController cc;
	private JButton signInBtn = new JButton(" Log In ");
	private JButton signUplbl;

	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	private JTextField textFieldEmail;

	private JTextField textFieldUsernameLogin = new JTextField();
	private JTextField textFieldUserPasswordLogin = new JTextField();

	public Login(ClientController cc) {
		this.cc = cc;
		this.setLayout(new BorderLayout());
	}

	public void draw() {
		JPanel tomPanel = new JPanel();
		tomPanel.setPreferredSize(new Dimension(300, 100));
		add(tomPanel, BorderLayout.NORTH);
		add(textFieldPanel(), BorderLayout.CENTER);
		add(buttonPanel(), BorderLayout.SOUTH);
//	add(singnUpPanel(), BorderLayout.NORTH);
	}

	public JPanel textFieldPanel() {
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(5, 1, 50, 50));
		topPanel.setPreferredSize(new Dimension(250, 100));

		textFieldUsernameLogin.setText("Username");
		textFieldUsernameLogin.selectAll();

		textFieldUserPasswordLogin.setText("Password");
		textFieldUserPasswordLogin.selectAll();

		topPanel.add(textFieldUsernameLogin);
		topPanel.add(textFieldUserPasswordLogin);

		return topPanel;

	}

	public JPanel buttonPanel() {
		JPanel buttonPanel = new JPanel();
//		buttonPanel.setLayout(new GridLayout(2, 10));

		buttonPanel.add(signInBtn);

		signInBtn.addActionListener(e -> checkUserLogin());
		singnUpPanel(buttonPanel);
		return buttonPanel;
	}

	public void singnUpPanel(JPanel panel) {

//		JPanel panel = new JPanel();
		signUplbl = new JButton("New here? Sign up!");
		panel.setLayout(new GridLayout(2, 1, 0, 5));

		panel.add(new JLabel());
		panel.add(signUplbl);

		signUplbl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(null, createAccount(), "Sign Up!", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);

				while (!(checkPasswordValidity(passwordField.getText())
						&& checkUsernameValidity(textFieldUserName.getText())
						&& checkEmailValidity(textFieldEmail.getText())) && res == JOptionPane.OK_OPTION) {

					if (!checkUsernameValidity(textFieldUserName.getText()))
						JOptionPane.showMessageDialog(null, "No username");

					if (!checkEmailValidity(textFieldEmail.getText()))
						JOptionPane.showMessageDialog(null, "Enter a email");

					if (!checkPasswordValidity(passwordField.getText()))
						JOptionPane.showMessageDialog(null, "Your password must contain at least 8 charachters, "
								+ "one capital letter, one small letter and one digit!");

					res = JOptionPane.showConfirmDialog(null, createAccount(), "Sign Up!", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
				}

				if (res == JOptionPane.OK_OPTION)
					cc.signUp(textFieldUserName.getText(), passwordField.getPassword().toString(), textFieldEmail.getText());

				

			}
		});

//		return panel;

	}

	public JPanel createAccount() {

		JPanel panel = new JPanel(new GridLayout(7, 1));
		JLabel usernameLabel = new JLabel("Username : ");
		JLabel userPasswordLabel = new JLabel("Password : ");
		JLabel userEmailLabel = new JLabel("Email : ");

		textFieldUserName = new JTextField(20);
		textFieldEmail = new JTextField(20);
		passwordField = new JPasswordField(20);
		JCheckBox check = new JCheckBox("Show password");

		panel.add(usernameLabel);
		panel.add(textFieldUserName);
		panel.add(userEmailLabel);
		panel.add(textFieldEmail);
		panel.add(userPasswordLabel);
		panel.add(passwordField);
		panel.add(check);

		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (check.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('*');
				}
			}
		});

		return panel;

	}

	public void checkUserLogin() {

		String username = textFieldUsernameLogin.getText();
		String password = textFieldUserPasswordLogin.getText();
		System.out.println(username + ", " + password);
		User user = cc.logIn(username, password);
		if (user != null) {
			cc.setUser(user);
			cc.setButtonsEnabled(true);
			cc.setPanel("Home", null);
			System.out.println("Welcome back!");

		} else { // ny ide på UI, kan förbättras ^_^
			System.out.println("No user found!");
			Border compound = null;
			Border redline = BorderFactory.createLineBorder(Color.red);
			compound = BorderFactory.createCompoundBorder(redline, compound);
			signUplbl.setBorder(BorderFactory.createCompoundBorder(redline, compound));
			revalidate();
		}
	}

	private boolean checkUsernameValidity(String username) {
		String pattern = "[\\\\/:*?\"<>|%]";
		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(textFieldUserName.getText());
		return !(match.find() && (textFieldUserName.getText().equals("")));
	}

	private boolean checkEmailValidity(String email) {
		String pattern = "[a-z0-9]+@[a-z0-9]+\\.[a-z]{1,3}";
		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(textFieldEmail.getText().toLowerCase());
		return (!(textFieldEmail.getText().equals("")) && match.find());
	}

	private boolean checkPasswordValidity(String pass) {
		Pattern p1 = Pattern.compile("[a-z]");
		Pattern p2 = Pattern.compile("[A-Z]");
		Pattern p3 = Pattern.compile("[0-9]");

		Matcher match1 = p1.matcher(passwordField.getPassword().toString());
		Matcher match2 = p2.matcher(passwordField.getPassword().toString());
		Matcher match3 = p3.matcher(passwordField.getPassword().toString());

		return (passwordField.getText().length() >= 8 && match1.find() && match2.find() && match3.find());
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