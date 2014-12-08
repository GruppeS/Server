package view; 

import java.awt.Color;
import java.awt.Font;
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
	private JLabel lblIncorrect;
	private JTextField userName_Login;
	private JPasswordField password_Login;
	private JButton btnLogin;

	/**
	 * Sets swing objects
	 */
	public LoginPanel()
	{
		setLayout(null);

		lblWelcome = new JLabel("Welcome ADMIN");
		lblWelcome.setBounds(135, 73, 135, 24);
		add(lblWelcome);

		lblPleaseLoginBelow = new JLabel("Please login below:");
		lblPleaseLoginBelow.setBounds(128, 108, 132, 16);
		add(lblPleaseLoginBelow);

		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(122, 148, 74, 14);
		add(lblUsername);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(122, 211, 89, 14);
		add(lblPassword);

		lblIncorrect = new JLabel("Username or password is incorrect");
		lblIncorrect.setFont(new Font("Calibri", Font.ITALIC, 11));
		lblIncorrect.setBounds(117, 320, 181, 14);
		lblIncorrect.setForeground(Color.red);
		lblIncorrect.setVisible(false);
		add(lblIncorrect);

		userName_Login = new JTextField();
		userName_Login.setBounds(117, 170, 143, 20);
		userName_Login.setColumns(10);
		add(userName_Login);

		password_Login = new JPasswordField();
		password_Login.setBounds(117, 236, 143, 20);
		password_Login.setColumns(10);
		add(password_Login);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(147, 286, 89, 23);
		add(btnLogin);
	}

	/**
	 * Sets lblIncorrect visible
	 */
	public void incorrect()
	{
		lblIncorrect.setVisible(true);
	}

	/**
	 * @return String username
	 */
	public String getUserName_Login()
	{
		return userName_Login.getText();
	}

	/**
	 * @return password
	 */
	@SuppressWarnings("deprecation")
	public String getPassword_Login()
	{
		return password_Login.getText();
	}

	/**
	 * Sets lblIncorrect not visible and removes text from password field
	 */
	public void reset()
	{
		lblIncorrect.setVisible(false);
		password_Login.setText("");
	}

	/**
	 * Adds actionlistener and actioncommand
	 * @param l
	 */
	public void addActionListener(ActionListener l)
	{
		btnLogin.addActionListener(l);
		btnLogin.setActionCommand("LoginBtn");
	}
}
