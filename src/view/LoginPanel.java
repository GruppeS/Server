package view; 

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JLabel lblWelcome;
	private JLabel lblPleaseLoginBelow;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField userName_Login;
	private JPasswordField password_Login;
	private JButton btnLogin;

	public LoginPanel()
	{
		setLayout(null);

		lblWelcome = new JLabel("Welcome ADMIN");
		lblWelcome.setBounds(60, 98, 194, 24);
		add(lblWelcome);

		lblPleaseLoginBelow = new JLabel("Please login below:");
		lblPleaseLoginBelow.setBounds(99, 145, 108, 16);
		add(lblPleaseLoginBelow);

		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(83, 197, 52, 14);
		add(lblUsername);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(86, 252, 49, 14);
		add(lblPassword);
		
		userName_Login = new JTextField();
		userName_Login.setBounds(86, 222, 143, 20);
		userName_Login.setColumns(10);
		add(userName_Login);
		
		password_Login = new JPasswordField();
		password_Login.setBounds(86, 276, 143, 20);
		password_Login.setColumns(10);
		add(password_Login);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(118, 446, 89, 23);
		add(btnLogin);
	}

	public void addActionListener(ActionListener l)
	{
		btnLogin.addActionListener(l);
		btnLogin.setActionCommand("LoginBtn");
	}
}
