
package showtracker.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import showtracker.Helper;
import showtracker.User;
/**
 * 
 * @author Basir Ramazani
 * Changes made by Moustafa & Filip
 * 
 * A profile panel for user
 * 
 */

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
    	removeAll();
        user = cc.getUser();
        add(profilePanel(), BorderLayout.NORTH);
        add(textFieldPanel(), BorderLayout.CENTER);
        changePanel();
        add(southPanel, BorderLayout.SOUTH);
    }

    public JPanel textFieldPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3, 2, 6, 1));
        JLabel inputName = new JLabel(user.getUserName());
        inputMail = new JLabel(user.getEmail());
        
        JLabel lbl = new JLabel(" My profile");
        
        JLabel namn = new JLabel("    Username:  ");
        JLabel mail = new JLabel("    Email:  ");
        tfChangeMail = new JTextField();
        panel.add(lbl);
        panel.add(new JLabel());
        
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
                        JOptionPane.showMessageDialog(null, "Email not valid!", "No Email",
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
                boolean passwordChanged = false;
                int res;
                do {
                    res = JOptionPane.showConfirmDialog(null, changePasswordPanel(), "Change password!",
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
                        String reply = cc.updatePassword(user.getUserName(), tfConfirmPassword.getText(), new String(password.getText()));
                        if (reply.equals("Password changed")) {
                            JOptionPane.showMessageDialog(null, reply, "Request approved", JOptionPane.INFORMATION_MESSAGE);
                            passwordChanged = true;
                        } else {
                            JOptionPane.showMessageDialog(null, reply, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } while (!passwordChanged && res == JOptionPane.OK_OPTION);
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
        
        

        JPanel topPanel = new JPanel();

        topPanel.setLayout(new GridLayout(1, 1));
        if(image != null) {
        Image img = image.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon imgIcon = new ImageIcon(img);
        JLabel imageLabel = new JLabel(imgIcon);
        topPanel.add(imageLabel);
        }

        return topPanel;
    }

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
}