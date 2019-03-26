package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Paper;
import java.io.FileNotFoundException;

import javax.swing.*;

public class Profile extends JFrame {

	ClientController clientController = new ClientController();

	JFrame frame = new JFrame("Profile");
	private ImageIcon image;
	private JLabel imageLabel;

	private JLabel namn = new JLabel("   Name:  ");
	private JLabel mail = new JLabel("   Email:  ");
	private JLabel changeMail = new JLabel("  Change Email  ");
	private JLabel changePass = new JLabel("  Change Password  ");

	private JLabel inputName = new JLabel();
	private JLabel inputMail = new JLabel();
	private JLabel label1 = new JLabel("");
	private JLabel label2 = new JLabel("");

	private JTextField changeMa = new JTextField();
	private JTextField changeP = new JTextField();

	private JButton changeBtn1 = new JButton("Submit");
	private JButton changeBtn2 = new JButton("Submit");

	JButton button1 = new JButton("Profile");
	JButton button2 = new JButton("Home");
	JButton button3 = new JButton("");
	JButton button4 = new JButton("Exit");

	public Profile() throws FileNotFoundException {
		frame.add(textFildPanel1(), BorderLayout.CENTER);
		frame.add(profilePanel(), BorderLayout.NORTH);
		frame.add(bottomPanel(), BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
		frame.pack();

	}

	public JPanel textFildPanel1() throws FileNotFoundException {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4, 3, 2, 2));
		inputName = new JLabel(getUserName());
		inputMail = new JLabel(getUserEmail());

		panel.add(namn);
		panel.add(inputName);
		panel.add(label1);

		panel.add(mail);
		panel.add(inputMail);
		panel.add(label2);

		panel.add(changeMail);
		panel.add(changeMa);
		panel.add(changeBtn1);

		panel.add(changePass);
		panel.add(changeP);
		panel.add(changeBtn2);

		changeBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				submitChangeEmail(changeMa.getText());
				inputMail = new JLabel(getUserEmail());

			}
		});

		changeBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				submitChangePass(changeP.getText());

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

	public JPanel profilePanel() {
		image = getUserProfilePicture();
		imageLabel = new JLabel(image);

		JPanel topPanel = new JPanel();

		topPanel.setLayout(new GridLayout(1, 1, 1, 1));
		topPanel.add(imageLabel);

		return topPanel;
	}

	public JPanel bottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 4, 1, 1));
		bottomPanel.add(button1);
		bottomPanel.add(button2);
		bottomPanel.add(button3);
		bottomPanel.add(button4);

		return bottomPanel;

	}

	public void submitChangeEmail(String mail) {

		clientController.setEmail(mail);
	}

	public void submitChangePass(String pass) {
		clientController.setPassword(pass);
	}

	public ImageIcon getUserProfilePicture() {
		return image = clientController.getProfilePicture();
	}

	public static void main(String[] args) throws FileNotFoundException {
		new Profile();
	}

}