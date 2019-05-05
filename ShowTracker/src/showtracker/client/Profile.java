
package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import showtracker.User;


public class Profile extends JPanel {

	private ClientController cc;
	
	private ArrayList<User> list = new ArrayList<>();
	private ImageIcon image;
	private JPanel panel;

	private JLabel inputMail;
	private JLabel inputPass;

	private JTextField tfChangeMail = new JTextField();
	private JTextField tfChangePass = new JTextField();
	private JTextField tfConfirmPassword = new JTextField();
	
	private JButton btnChangePassword = new JButton("Submit");

	private JPasswordField password;

	public Profile(ClientController cc) {
		this.cc = cc;
		this.setLayout(new BorderLayout());
		add(profilePanel(), BorderLayout.NORTH);
		add(textFieldPanel(), BorderLayout.CENTER);
//		add(bottomPanel(), BorderLayout.SOUTH);

	}

	public JPanel textFieldPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 3));
		JLabel inputName = new JLabel(getUserName());
		inputMail = new JLabel(getUserEmail());
		inputPass = new JLabel(getUserPass());
		
		JLabel namn = new JLabel("   Name:  ");
		JLabel mail = new JLabel("   Email:  ");
		JLabel pass = new JLabel("  Password:  ");
		JLabel changeMail = new JLabel("  Change Email  ");
		
		JButton btnChangeEmail = new JButton("Submit");
		JButton btnChangePass = new JButton("Change Password");
		
		try {
			inputPass = new JLabel(maskString(getUserPass(), 4, 8, '*'));
		} catch (Exception e) {
			e.printStackTrace();
		}

		panel.add(namn);
		panel.add(inputName);
		panel.add(new JLabel());

		panel.add(pass);
		panel.add(inputPass);
		panel.add(btnChangePass);

		panel.add(mail);
		panel.add(inputMail);
		panel.add(new JLabel());

		panel.add(changeMail);
		panel.add(tfChangeMail);
		panel.add(btnChangeEmail);

		btnChangeEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				submitChangeEmail(tfChangeMail.getText());
			}
		});

		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				submitChangePass(tfChangePass.getText());
				inputPass.setText(getUserPass());
			}
		});

		btnChangePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, changePasswordPanel(), "Change password!",
						JOptionPane.PLAIN_MESSAGE);

			}
		});

		return panel;

	}

	private String getUserEmail() {
		return cc.getUserEmail();
	}

	private String getUserName() {
		return cc.getUserName();
	}

	private String getUserPass() {
		return cc.getUser().getUserPass();
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
			cc.setEmail(mail);
			inputMail.setText(getUserEmail());
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
				&& match3.find() && tfConfirmPassword.getText().equals(getUserPass())) {
			cc.getUser().setUserPassword(pass);
			inputPass.setText(getUserPass());

//			if (match1.find()) {
//				
//			}
//			if (match2.find()) {
//
//			}
//			if (match3.find()) {
//					
//			}

		} else if (password.getText().equals("") || password.getText().length() < 8 || !match1.find() || !match2.find()
				|| !match3.find()) {

			JOptionPane.showMessageDialog(null, "Your password must contain at least 8 charachters, one capital letter,"
					+ " one small letter and one digit!", "Weak password", JOptionPane.WARNING_MESSAGE);

		}
	}

	public ImageIcon getUserProfilePicture() {
		return image = cc.getProfilePicture();
	}

	/*
	 * DocumentListener documentListener = new DocumentListener() {
	 * 
	 * public void changedUpdate(DocumentEvent e) { confirm(e); }
	 * 
	 * public void insertUpdate(DocumentEvent e) { confirm(e); }
	 * 
	 * public void removeUpdate(DocumentEvent e) { confirm(e); }
	 * 
	 * private void confirm(DocumentEvent e) { Document source = e.getDocument();
	 * int length = source.getLength(); ArrayList<User> pass = new ArrayList<>();
	 * for(User user : list) { try {
	 * if(user.getUserPass().contains(source.getText(0, length))) pass.add(user); }
	 * catch (BadLocationException arg) { arg.printStackTrace(); } }
	 * changePasswordPanel(pass); } };
	 */

	public JPanel changePasswordPanel() {
//        conforimPassword.getDocument().addDocumentListener(documentListener);

		panel = new JPanel();
		panel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.setLayout(new GridLayout(3, 2, 2, 2));

		password = new JPasswordField();

		JCheckBox check = new JCheckBox("Show password");

		JLabel label1 = new JLabel("Current password");
		JLabel label2 = new JLabel("New password");
				

//		confirmPassword.setText("Enter your current password!");
//		confirmPassword.selectAll();
//		confirmPassword.requestFocus();

		panel.add(label1);
		panel.add(tfConfirmPassword);

		panel.add(label2);
		panel.add(password);

		panel.add(check);
		panel.add(btnChangePassword);

		btnChangePassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				submitChangePass(password.getText());

				try {
					inputPass.setText(maskString(getUserPass(), 4, 8, '*'));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

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

		Profile profile = new Profile(new ClientController());
		JFrame frame = new JFrame();
		frame.setTitle("Profile");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(profile);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
		frame.pack();
		frame.setSize(500, 400);

	}
}