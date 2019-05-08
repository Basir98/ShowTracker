
package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import showtracker.Helper;
import showtracker.User;

public class Profile extends JPanel {

	private ClientController cc;
	private User user;
	private Helper helper = new Helper();
	private ImageIcon image;
	private JPanel southPanel;

	private JLabel inputMail;

	private JTextField tfChangeMail;
	private JTextField tfConfirmPassword;

	private JPasswordField password;

	public Profile(ClientController cc) {
		this.cc = cc;
		this.setLayout(new BorderLayout());
	}

	public void draw() {
		user = cc.getUser();
		add(profilePanel(), BorderLayout.NORTH);
		add(textFieldPanel(), BorderLayout.CENTER);
		changePanel();
		add(southPanel, BorderLayout.SOUTH);
	}

	public JPanel textFieldPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(2, 2, 6, 1));
		JLabel inputName = new JLabel(user.getUserName());
		inputMail = new JLabel(user.getEmail());

		JLabel namn = new JLabel("   Username:  ");
		JLabel mail = new JLabel("   Email:  ");
		tfChangeMail = new JTextField();

		panel.add(namn);
		panel.add(inputName);

		panel.add(mail);
		panel.add(inputMail);

		return panel;

	}

	private void changePanel() {
		southPanel = new JPanel(new BorderLayout());
		JPanel panel = new JPanel();

		JButton btnChangeEmail = new JButton("Change Email?");
		JButton btnChangePass = new JButton("Change Password?");

		panel.add(btnChangeEmail);
		panel.add(btnChangePass);

		southPanel.add(panel, BorderLayout.SOUTH);

		btnChangeEmail.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent evt) {
				int res = JOptionPane.showConfirmDialog(null, changeEmailPanel(), "Change Email",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

				while (!(helper.checkEmailValidity(tfChangeMail.getText())) && res == JOptionPane.OK_OPTION) {

					if (!helper.checkEmailValidity(tfChangeMail.getText()))
						JOptionPane.showMessageDialog(null, "Please enter a email!", "No Email",
								JOptionPane.WARNING_MESSAGE);

					res = JOptionPane.showConfirmDialog(null, changeEmailPanel(), "Email", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
				}

				if (res == JOptionPane.OK_OPTION) {
					user.setEmail(tfChangeMail.getText());
					inputMail.setText(user.getEmail());
				}

			}
		});

		btnChangePass.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {

				int res = JOptionPane.showConfirmDialog(null, changePasswordPanel(), "Change password!",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

				while (!(helper.checkPasswordValidity(password.getText())) && res == JOptionPane.OK_OPTION) {

					if (!helper.checkPasswordValidity(password.getText()))
						JOptionPane.showMessageDialog(null,
								"Your password must contain at least 8 charachters, \n one capital letter,"
										+ " one small letter and one digit!",
								"Weak password", JOptionPane.WARNING_MESSAGE);

					res = JOptionPane.showConfirmDialog(null, changePasswordPanel(), "Change password!",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				}

				if (res == JOptionPane.OK_OPTION) {
//				cc.updatePassword(cc.getUser().getUserName(), cc.getUser().getUserPass(), new String(password.getText()));
				cc.updatePassword(user.getUserName(), user.getUserPass(), new String(password.getText()));
				
				
				 cc.getUser().setUserPassword(password.getText());

				
				}
			}
		});

	}

	private JPanel changeEmailPanel() {
		JPanel panel = new JPanel();
		JLabel changeMail = new JLabel("Enter your email");

		panel.setLayout(new BorderLayout());

		panel.add(changeMail, BorderLayout.NORTH);
		panel.add(tfChangeMail, BorderLayout.CENTER);

		return panel;
	}

	public JPanel profilePanel() {
		image = getUserProfilePicture();
		JLabel imageLabel = new JLabel(image);

		JPanel topPanel = new JPanel();

		topPanel.setLayout(new GridLayout(1, 1, 1, 1));
		topPanel.add(imageLabel);

		return topPanel;
	}


	/*
	 * public void submitChangeEmail(String mail) {
	 * 
	 * String pattern = "[a-z0-9]+@[a-z0-9]+\\.[a-z]{1,3}";
	 * 
	 * Pattern p = Pattern.compile(pattern); Matcher match =
	 * p.matcher(tfChangeMail.getText());
	 * 
	 * if (!(tfChangeMail.getText().equals("")) && match.find()) {
	 * user.setEmail(mail); inputMail.setText(user.getEmail()); } else if
	 * (tfChangeMail.getText().equals("")) { tfChangeMail.setText("Enter a mail!");
	 * tfChangeMail.selectAll(); tfChangeMail.requestFocus(); } }
	 */

/*	
	 * public void submitChangePass(String pass) {
	 * 
	 * String pattern1 = "[a-z]"; String pattern2 = "[A-Z]"; String pattern3 =
	 * "[0-9]";
	 * 
	 * Pattern p1 = Pattern.compile(pattern1); Pattern p2 =
	 * Pattern.compile(pattern2); Pattern p3 = Pattern.compile(pattern3);
	 * 
	 * Matcher match1 = p1.matcher(password.getText()); Matcher match2 =
	 * p2.matcher(password.getText()); Matcher match3 =
	 * p3.matcher(password.getText());
	 * 
	 * if (!(password.getText().equals("")) && password.getText().length() >= 8 &&
	 * match1.find() && match2.find() && match3.find()) {
	 * 
	 * cc.getUser().setUserPassword(pass);
	 * 
	 * inputPass.setText(user.getUserPass());
	 * 
	 * } else if (password.getText().equals("") || password.getText().length() < 8
	 * || !match1.find() || !match2.find() || !match3.find()) {
	 * 
	 * JOptionPane.showMessageDialog(null,
	 * "Your password must contain at least 8 charachters, one capital letter," +
	 * " one small letter and one digit!", "Weak password",
	 * JOptionPane.WARNING_MESSAGE);
	 * 
	 * } }
	 */

	public ImageIcon getUserProfilePicture() {
		return user.getProfilePicture();
	}

	public JPanel changePasswordPanel() {

		JPanel panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.setLayout(new GridLayout(3, 2, 2, 2));

		password = new JPasswordField();
		tfConfirmPassword = new JTextField();

		JCheckBox check = new JCheckBox("Show password");

		JLabel label1 = new JLabel("Current password");
		JLabel label2 = new JLabel("New password");

		panel.add(label1);
		panel.add(tfConfirmPassword);

		panel.add(label2);
		panel.add(password);

		panel.add(check);

		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (check.isSelected()) {
					password.setEchoChar((char) 0);
				} else {
					password.setEchoChar('*');
				}
			}
		});

		return panel;
	}

	public static void main(String[] args) throws Exception {
		ClientController cc = new ClientController();
		User user = new User("namn", "email", null);
		cc.setUser(user);

		Profile profile = new Profile(cc);
		profile.draw();
		JFrame frame = new JFrame();
		frame.setTitle("Profile");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(profile);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
		frame.pack();
		frame.setSize(350, 500);

	}
}