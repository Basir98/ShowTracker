
package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import showtracker.User;

public class Profile extends JPanel {

	private ClientController cc;
	private User user;

	private ImageIcon image;
	private JPanel southPanel;

	private JLabel inputMail;
	private JLabel inputPass;

	private JTextField tfChangeMail;
	private JTextField tfChangePass = new JTextField();
	private JTextField tfConfirmPassword;

	private JButton btnChangePassword = new JButton("Submit");

	private JPasswordField password;

	public Profile(ClientController cc) {
		this.cc = cc;
		user = cc.getUser();
		this.setLayout(new BorderLayout());
//		draw();

//		add(bottomPanel(), BorderLayout.SOUTH);

	}

	public void draw() {
		add(profilePanel(), BorderLayout.NORTH);
		add(textFieldPanel(), BorderLayout.CENTER);
		changePanel();
		add(southPanel, BorderLayout.SOUTH);
	}

	public JPanel textFieldPanel() {
		JPanel panel = new JPanel();
	

		panel.setLayout(new GridLayout(2, 2, 6,1));
		JLabel inputName = new JLabel(user.getUserName());
		inputPass = new JLabel(user.getUserPass());
		inputMail = new JLabel(user.getEmail());


		JLabel namn = new JLabel("   Name:  ");
		JLabel mail = new JLabel("   Email:  ");
//		JLabel pass = new JLabel("  Password:  ");

		
		tfChangeMail = new JTextField();
		

		try {
			inputPass = new JLabel(maskString(user.getUserPass(), 4, 8, '*'));
		} catch (Exception e) {
			e.printStackTrace();
		}

		panel.add(namn);
		panel.add(inputName);


		panel.add(mail);
		panel.add(inputMail);

		 		


		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				submitChangePass(tfChangePass.getText());
				inputPass.setText(user.getUserPass());
			}
		});

	

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
			public void actionPerformed(ActionEvent evt) {
				int res = JOptionPane.showConfirmDialog(null, changeEmail(), "Email", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
					
				
				if(res == JOptionPane.OK_OPTION)
					submitChangeEmail(tfChangeMail.getText());

				
			}
		});
		
		btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				int res = JOptionPane.showConfirmDialog(null, changePasswordPanel(), "Change password!", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				
				
				if(res == JOptionPane.OK_OPTION) {
					submitChangePass(password.getText());
					try {
						inputPass.setText(maskString(user.getUserPass(), 4, 8, '*'));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				
			}
		});

		
	}
	
	private JPanel changeEmail() {
		JPanel panel = new JPanel();
		JLabel changeMail = new JLabel("Change Email ");

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

	private String maskString(String strText, int start, int end, char maskChar) throws Exception {

		if (strText == null || strText.equals(""))
			return "";

		if (start < 0)
			start = 0;

		if (end > strText.length())
			end = strText.length();

		if (start > end)
			throw new Exception();

		int maskLenght = end - start;

		if (maskLenght == 0)
			return strText;

		StringBuilder sbMaskString = new StringBuilder(maskLenght);

		for (int i = 0; i < maskLenght; i++) {
			sbMaskString.append(maskChar);
		}
		return strText.substring(0, start) + sbMaskString.toString() + strText.substring(start + maskLenght);

	}

	public void submitChangeEmail(String mail) {

		String pattern = "[a-z0-9]+@[a-z0-9]+\\.[a-z]{1,3}";

		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(tfChangeMail.getText());

		if (!(tfChangeMail.getText().equals("")) && match.find()) {
			user.setEmail(mail);
			inputMail.setText(user.getEmail());
		} else if (tfChangeMail.getText().equals("")) {
			tfChangeMail.setText("Enter a mail!");
			tfChangeMail.selectAll();
			tfChangeMail.requestFocus();
		}
	}

	public void submitChangePass(String pass) {

		String pattern1 = "[a-z]";
		String pattern2 = "[A-Z]";
		String pattern3 = "[0-9]";

		Pattern p1 = Pattern.compile(pattern1);
		Pattern p2 = Pattern.compile(pattern2);
		Pattern p3 = Pattern.compile(pattern3);

		Matcher match1 = p1.matcher(password.getText());
		Matcher match2 = p2.matcher(password.getText());
		Matcher match3 = p3.matcher(password.getText());

		if (!(password.getText().equals("")) && password.getText().length() >= 8 && match1.find() && match2.find()
				&& match3.find() && tfConfirmPassword.getText().equals(user.getUserPass())) {
			
			cc.getUser().setUserPassword(pass);
			inputPass.setText(user.getUserPass());


		} else if (password.getText().equals("") || password.getText().length() < 8 || !match1.find() || !match2.find()
				|| !match3.find()) {

			JOptionPane.showMessageDialog(null, "Your password must contain at least 8 charachters, one capital letter,"
					+ " one small letter and one digit!", "Weak password", JOptionPane.WARNING_MESSAGE);

		}
	}

	public ImageIcon getUserProfilePicture() {
		return image = user.getProfilePicture();
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
		
//		panel.add(btnChangePassword);
//
//		btnChangePassword.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				
//				
//				submitChangePass(password.getText());
//
//				try {
//					inputPass.setText(maskString(getUserPass(), 4, 8, '*'));
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				
//			}
//
//		});

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
		User user = new User ("namn" , "email" , null);
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