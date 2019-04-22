
package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class Profile extends JPanel {

    private static ClientController cc;

	private ImageIcon image;
	private JLabel imageLabel;

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

	private JButton changeBtnMail = new JButton("Submit");
	private JButton changeBtnPass = new JButton("Submit");

	public Profile(ClientController cc) {
		this.cc = cc;
		this.setLayout(new BorderLayout());
		add(profilePanel(), BorderLayout.NORTH);
		add(textFieldPanel1(), BorderLayout.CENTER);
//		add(bottomPanel(), BorderLayout.SOUTH);
		
	}

	public JPanel textFieldPanel1() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 3, 2, 2));
		inputName = new JLabel(getUserName());
		inputMail = new JLabel(getUserEmail());
		inputPass = new JLabel(getUserPass());

		panel.add(namn);
		panel.add(inputName);
		panel.add(new JLabel());

		panel.add(mail);
		panel.add(inputMail);
		panel.add(new JLabel());

		panel.add(changeMail);
		panel.add(changeMailTextField);
		panel.add(changeBtnMail);

		panel.add(changePass);
		panel.add(changePassTextField);
		panel.add(changeBtnPass);

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

		return panel;

	}

	private String getUserEmail() {
		return cc.getUserEmail();
	}

	private String getUserName() {
		return cc.getUserName();
	}

	private String getUserPass() {
		return cc.getUserPassword();
	}

	public JPanel profilePanel() {
		image = getUserProfilePicture();
		imageLabel = new JLabel(image);

		JPanel topPanel = new JPanel();

		topPanel.setLayout(new GridLayout(1, 1, 1, 1));
		topPanel.add(imageLabel);

		return topPanel;
	}

//	public JPanel bottomPanel() {
//
//		JPanel bottomPanel = new JPanel();
//		bottomPanel.setLayout(new GridLayout(1, 5, 1, 1));
//		ImageIcon image1 = new ImageIcon("images/home-screen.png");
//		ImageIcon image2 = new ImageIcon("images/list.png");
////		ImageIcon image3 = new ImageIcon("images/search-Icon1.png");
//
//		Image img = image1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//		Image img3 = image2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//		Image img4 = image3.getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH);

//		ImageIcon imgIcon1 = new ImageIcon(img);
//		ImageIcon imgIcon2 = new ImageIcon(img3);
//		ImageIcon imgIcon4 = new ImageIcon(img4);

//		button2.setIcon(imgIcon1);
//		button3.setIcon(imgIcon2);
////		button4.setIcon(imgIcon4);
//
//		bottomPanel.add(button1);
//		bottomPanel.add(button2);
//		bottomPanel.add(button3);
//		button4.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
//			}
//		});
//		bottomPanel.add(button4);
//		bottomPanel.add(button5);

		/*
		 * 
		 * bottomPanel.setLayout(new GridLayout(1, 5, 1, 1)); image1 = new
		 * ImageIcon("ShowTracker/images/home1.png"); // image1 = new
		 * ImageIcon("ShowTracker/images/search.png"); Image img =
		 * image1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); ImageIcon
		 * imgIcon = new ImageIcon(img);
		 * 
		 * // image2 = new ImageIcon("ShowTracker/images/Home.png"); //
		 * button2.setIcon(imgIcon); button1.setIcon(new
		 * ImageIcon("ShowTracker/images/home1.png")); button1.setIcon(new ImageIcon());
		 * // button2.setIcon(new ImageIcon("ShowTracker/images/Home.png")); //
		 * button3.setIcon(new ImageIcon("ShowTracker/images/search.png")); //
		 * button4.setIcon(new ImageIcon("ShowTracker/images/my lista.png")); //
		 * button5.setIcon(new ImageIcon("ShowTracker/images/exit.jpg"));
		 * 
		 */

//		return bottomPanel;
//
//	}

	public void submitChangeEmail(String mail) {

		String pattern = "[a-z0-9]+@[a-z0-9]+\\.[a-z]{1,3}";

		Pattern p = Pattern.compile(pattern);
		Matcher match = p.matcher(changeMailTextField.getText());

		if (!(changeMailTextField.getText().equals("")) && match.find()) {
			cc.setEmail(mail);
			inputMail.setText(getUserEmail());
		} else if (changeMailTextField.getText().equals("")) {
			changeMailTextField.setText("Enter a mail!");
			changeMailTextField.selectAll();
			changeMailTextField.requestFocus();
		}

	}

	public void submitChangePass(String pass) {

		if (!(changePassTextField.getText().equals("")) && changePassTextField.getText().length() > 6) {
			cc.setPassword(pass);
		} else if (changePassTextField.getText().equals("") || changePassTextField.getText().length() < 6) {
			changePassTextField.setText("At least 6 characters!");
			changePassTextField.selectAll();
			changePassTextField.requestFocus();

		}
	}

	public ImageIcon getUserProfilePicture() {
		return image = cc.getProfilePicture();
	}

	public static void main(String[] args) throws FileNotFoundException {

//		Profile profile = new Profile(cc);
//		JFrame frame = new JFrame();
//		frame.setTitle("Profile");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(profile);
//		frame.setLocationRelativeTo(null);
//
//		frame.setVisible(true);
//		frame.pack();
//		frame.setSize(500, 400);

	}

}