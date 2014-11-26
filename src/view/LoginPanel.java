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
		lblWelcome.setBounds(163, 110, 101, 24);
		add(lblWelcome);

		lblPleaseLoginBelow = new JLabel("Please login below:");
		lblPleaseLoginBelow.setBounds(156, 145, 108, 16);
		add(lblPleaseLoginBelow);

		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(150, 172, 52, 14);
		add(lblUsername);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(150, 248, 74, 14);
		add(lblPassword);
		
		userName_Login = new JTextField();
		userName_Login.setBounds(145, 194, 143, 20);
		userName_Login.setColumns(10);
		add(userName_Login);
		
		password_Login = new JPasswordField();
		password_Login.setBounds(145, 273, 143, 20);
		password_Login.setColumns(10);
		add(password_Login);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(175, 304, 89, 23);
		add(btnLogin);
	}

	public void addActionListener(ActionListener l)
	{
		btnLogin.addActionListener(l);
		btnLogin.setActionCommand("LoginBtn");
	}
}
