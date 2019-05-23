package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import showtracker.Helper;
import showtracker.User;

import static showtracker.Helper.*;

/**
 * 
 * @author Basir Ramazani, Filip Sp�nberg
 * Represents the login panel
 *
 */
public class Login extends JPanel {

	private ClientController cc;
	private JButton btLogIn = new JButton(" Log In ");
	private JButton btSignUp = new JButton("New here? Sign up for free!");
	private JTextField tfUsernameSignUp;
	private JPasswordField pfPasswordSignUp;
	private JTextField tfEmailSignup;

	private JTextField tfUsername = new JTextField();
	private JPasswordField pfPassword = new JPasswordField();
	private String strImagePath;

	public Login(ClientController cc) {
		this.cc = cc;
		setLayout(null);

		ImageIcon ii = new ImageIcon("images/logo.png");
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

	private void signUp() {
		int res = JOptionPane.showConfirmDialog(null, createAccount(), "Sign up!", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		while (!(checkPasswordValidity(new String(pfPasswordSignUp.getPassword()))
				&& checkUsernameValidity(tfUsernameSignUp.getText()) && checkEmailValidity(tfEmailSignup.getText()))
				&& res == JOptionPane.OK_OPTION) {

			if (!checkUsernameValidity(tfUsernameSignUp.getText()))
				JOptionPane.showMessageDialog(null, "Username not valid!", "No Username", JOptionPane.WARNING_MESSAGE);

			if (!checkEmailValidity(tfEmailSignup.getText()))
				JOptionPane.showMessageDialog(null, "Email not valid!", "No Email", JOptionPane.WARNING_MESSAGE);

			if (!checkPasswordValidity(new String(pfPasswordSignUp.getPassword())))
				JOptionPane.showMessageDialog(null,
						"Your password must contain at least 8 charachters, "
								+ "\none capital letter, one small letter and one digit!",
						"No Password!", JOptionPane.WARNING_MESSAGE);

			res = JOptionPane.showConfirmDialog(null, createAccount(), "Sign Up!", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		}

		if (res == JOptionPane.OK_OPTION)
			cc.signUp(tfUsernameSignUp.getText(), new String(pfPasswordSignUp.getPassword()), tfEmailSignup.getText(),
					strImagePath);
	}

	public JPanel createAccount() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel pnlSouth = new JPanel(new GridLayout(7, 1));
		JPanel pnlProfilebtn = new JPanel(new BorderLayout());
		JLabel usernameLabel = new JLabel("Username : ");
		JLabel userPasswordLabel = new JLabel("Password : ");
		JLabel userEmailLabel = new JLabel("Email : ");
		
		ImageIcon imiProfile = new ImageIcon("images/add-profile.png");
		Image imgPro = imiProfile.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imiProfile = new ImageIcon(imgPro);

		tfUsernameSignUp = new JTextField(20);
		tfEmailSignup = new JTextField(20);
		pfPasswordSignUp = new JPasswordField(20);
		JCheckBox check = new JCheckBox("Show password");
		JButton btnAddProfile = new JButton(imiProfile);

		btnAddProfile.addActionListener(e -> {

			int res =JOptionPane.showConfirmDialog(null, profilePnl(), "User profile", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			
			if(JOptionPane.OK_OPTION != res) {
				strImagePath = null;
				
			}
		});
		pnlProfilebtn.add(btnAddProfile, BorderLayout.EAST);

//		panel.add(btnPickProfileImage);

		pnlSouth.add(usernameLabel);
		pnlSouth.add(tfUsernameSignUp);
		pnlSouth.add(userEmailLabel);
		pnlSouth.add(tfEmailSignup);
		pnlSouth.add(userPasswordLabel);
		pnlSouth.add(pfPasswordSignUp);
		pnlSouth.add(check);

		panel.add(pnlProfilebtn, BorderLayout.CENTER);
		panel.add(pnlSouth, BorderLayout.SOUTH);

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

	public JPanel profilePnl() {
		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		
		ImageIcon imiFile = new ImageIcon("images/choose-file.png");
		Image imgfile = imiFile.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		imiFile = new ImageIcon(imgfile);
		
		JButton btnPickProfileImage = new JButton(imiFile);
		JLabel lblProfilePicture = new JLabel();
		JFileChooser jfc = new JFileChooser();

		FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		jfc.setFileFilter(imageFilter);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		btnPickProfileImage.addActionListener(e -> {
			int f = jfc.showOpenDialog(null);
			if (f == JFileChooser.APPROVE_OPTION) {
				strImagePath = jfc.getSelectedFile().getPath();
				ImageIcon profilePicture = new ImageIcon(strImagePath);
				Image image = profilePicture.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				profilePicture = new ImageIcon(image);
				lblProfilePicture.setIcon(profilePicture);
			}
		});
		pnl.setPreferredSize(new Dimension(300,300));

//		setBounds(x,y,width,height)
		lblProfilePicture.setBounds(50, 25, 200, 200);
		btnPickProfileImage.setBounds(105, 240, 90, 35);
		lblProfilePicture.setBorder(new LineBorder(Color.BLACK));
		
		pnl.add(lblProfilePicture);
		pnl.add(btnPickProfileImage);

		return pnl;
	}

	public void checkUserLogin() {
		String username = tfUsername.getText();
		String password = new String(pfPassword.getPassword());
		System.out.println(username + ", " + password);
		User user = cc.logIn(username, password);
		if (user != null) {
			cc.finalizeUser(user);
		} else { // ny ide på UI, kan förbättras ^_^
			Helper.errorMessage("Login failed!");
			Border compound = null;
			Border redline = BorderFactory.createLineBorder(Color.red);
			compound = BorderFactory.createCompoundBorder(redline, compound);
			btSignUp.setBorder(BorderFactory.createCompoundBorder(redline, compound));
			revalidate();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Login log = new Login(new ClientController());
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