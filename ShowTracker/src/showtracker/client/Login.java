package showtracker.client;

import java.awt.BorderLayout;
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

import com.sun.glass.events.MouseEvent;

import showtracker.User;

public class Login extends JPanel {

	private Controller cc = new Controller();
	private Connection connection = new Connection("127.0.0.1", 5555);
//	private Connection connection;
	private JButton signInBtn = new JButton(" Log In ");
	private JButton signUplbl;

	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	private JTextField textFieldEmail;

	private JTextField textFieldUsernameLogin = new JTextField();
	private JTextField textFieldUserPasswordLogin = new JTextField();

	public Login() {

		this.setLayout(new BorderLayout());
		add(textFieldPanel(), BorderLayout.CENTER);
		add(buttonPanel(), BorderLayout.SOUTH);
		add(singnUpPanel(), BorderLayout.NORTH);

	}
	
	public JPanel textFieldPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));
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
		buttonPanel.setLayout(new GridLayout(1, 1));

		buttonPanel.add(signInBtn);

		signInBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				checkUserLogin();
			}
		});

		return buttonPanel;
	}

	public JPanel singnUpPanel() {

		JPanel panel = new JPanel();
		signUplbl = new JButton("New here? Sign up!");
		panel.setLayout(new GridLayout(1, 2));

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


						res = JOptionPane.showConfirmDialog(null, createAccount(), "Sign Up!",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					}

				if (res == JOptionPane.OK_OPTION) {
					String res1 = connection.signUp(textFieldUserName.getText(), passwordField.getText(),
							textFieldEmail.getText());

					System.out.println(res1);
					
				} else if (res == JOptionPane.CANCEL_OPTION) 
					System.exit(0);
				
			}
		});

//		signUplbl.addMouseListener(new MouseAdapter() {
//			public void mouseReleased(MouseEvent e) {
//			JOptionPane.showMessageDialog(null, createAccount(), "Sign Up!", JOptionPane.PLAIN_MESSAGE);
//			}
//		});

		return panel;

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
		User user = connection.login(username, password);
		cc.setUser(user); 
		System.out.println(cc.getUser().getUserName());
	}

	private boolean checkUsernameValidity(String username) {
		
		String pattern = "[\\\\/:*?\"<>|%]";
		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(textFieldUserName.getText());
		if (match.find() && (textFieldUserName.getText().equals(""))) {
			
			return false;
			
		} else {
			
			return true;
		}

	}
	
	private boolean checkEmailValidity(String email) {

		String pattern = "[a-z0-9]+@[a-z0-9]+\\.[a-z]{1,3}";
		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(textFieldEmail.getText().toLowerCase());

		if (!(textFieldEmail.getText().equals("")) && match.find()) {
			
			return true;
			
		} else {
			
			return false;
		}
	}

	private boolean checkPasswordValidity(String pass) {
		String pattern1 = "[a-z]";
		String pattern2 = "[A-Z]";
		String pattern3 = "[0-9]";

		Pattern p1 = Pattern.compile(pattern1);
		Pattern p2 = Pattern.compile(pattern2);
		Pattern p3 = Pattern.compile(pattern3);

		Matcher match1 = p1.matcher(passwordField.getText());
		Matcher match2 = p2.matcher(passwordField.getText());
		Matcher match3 = p3.matcher(passwordField.getText());

		if (passwordField.getText().length() >= 8 && match1.find() && match2.find() && match3.find()) {
			
			return true;
			
		} else {

			return false;
		}
	}


	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Login log = new Login();
		frame.setTitle("Login");
		frame.setPreferredSize(new Dimension(400, 250));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(log);
		frame.pack();
		frame.setVisible(true);
	}
}
