
package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
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

    private ClientController clientController;
	private ArrayList<User> list = new ArrayList<>();
	private ImageIcon image;
	private JPanel panel;
	private boolean enabled = true;

	private JLabel namn = new JLabel("   Name:  ");
	private JLabel mail = new JLabel("   Email:  ");
	private JLabel pass = new JLabel("  Lösenord:  ");

	private JLabel changeMail = new JLabel("  Change Email  ");
	private JLabel changePass = new JLabel("  Change Password  ");

	private JLabel inputName = new JLabel();
	private JLabel inputMail = new JLabel();
	private JLabel inputPass = new JLabel();

	private JTextField changeMailTextField = new JTextField();
	private JTextField changePassTextField = new JTextField();
	private JTextField confirmPassword = new JTextField();

	private JButton changeBtnMail = new JButton("Submit");
	private JButton changeBtnPass = new JButton("Submit");
	private JButton changeButtonPass = new JButton("Change Password");

	private JButton confirmChangePass = new JButton("Submit");

	private JPasswordField password;

	public Profile(ClientController cc) {
		this.clientController = cc;
		this.setLayout(new BorderLayout());
		add(profilePanel(), BorderLayout.NORTH);
		add(textFieldPanel1(), BorderLayout.CENTER);
//		add(bottomPanel(), BorderLayout.SOUTH);
		
	}

	public JPanel textFieldPanel1() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 3));
		inputName = new JLabel(getUserName());
		inputMail = new JLabel(getUserEmail());
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
		panel.add(changeButtonPass);
		
		panel.add(mail);
		panel.add(inputMail);
		panel.add(new JLabel());
		
		panel.add(changeMail);
		panel.add(changeMailTextField);
		panel.add(changeBtnMail);
	

		changeBtnMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				submitChangeEmail(changeMailTextField.getText());
			}
		});

		changeBtnPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				submitChangePass(changePassTextField.getText());
				inputPass.setText(getUserPass());
			}
		});

		changeButtonPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				JOptionPane.showMessageDialog(null, changePasswordPanel(), "Change password!", JOptionPane.PLAIN_MESSAGE);
				
				
				
			}
		});

		return panel;

	}

	private String getUserEmail() {
		return clientController.getUserEmail();
	}

	private String getUserName() {
		return clientController.getUserName();
	}

	private String getUserPass() {
		return clientController.getUser().getUserPass();
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
		
		if(maskLenght == 0) 
			return strText;
		
		StringBuilder sbMaskString = new StringBuilder(maskLenght);
		
		for(int i =0 ; i<maskLenght; i++) {
			sbMaskString.append(maskChar);
		}
		return strText.substring(0, start)
				+ sbMaskString.toString() + strText.substring(start + maskLenght);

	}


	public void submitChangeEmail(String mail) {

		String pattern = "[a-z0-9]+@[a-z0-9]+\\.[a-z]{1,3}";

		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(changeMailTextField.getText());

		if (!(changeMailTextField.getText().equals("")) && match.find()) {
			clientController.setEmail(mail);
			inputMail.setText(getUserEmail());
		} else if (changeMailTextField.getText().equals("")) {
			changeMailTextField.setText("Enter a mail!");
			changeMailTextField.selectAll();
			changeMailTextField.requestFocus();
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
				&& match3.find() && confirmPassword.getText().equals(getUserPass())) {
			clientController.getUser().setUserPassword(pass);
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
		return image = clientController.getProfilePicture();
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
		panel.add(confirmPassword);

		panel.add(label2);
		panel.add(password);

		panel.add(check);
		panel.add(confirmChangePass);

		confirmChangePass.addActionListener(new ActionListener() {

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