package view; 

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel // LoginPanel klasse der extender JPanel
{ 
	private static final long serialVersionUID = 1L; // Genereret ID
	
	// instansvariable til at holde p� JLabels, JTextFields og JButtons
	private JLabel lblNewLabel;
	private JLabel lblPleaseLoginBelow;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblBackground;
	private JLabel lblIncorrect;
	private JTextField userName_Login;
	private JPasswordField password_Login;
	private JButton btnLogin;


	/**
	 * Constructer that adds the necessary layout and buttons for the Loginpanel
	 */
	public LoginPanel() // konstrukt�r der s�tter panelets udseende
	{
		setLayout(null); // absolut layout

		// JLabels
		lblNewLabel = new JLabel("Welcome ADMIN");
		lblNewLabel.setBounds(60, 98, 194, 24); // St�rrelse og placering
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 19)); // Tekst formattering
		add(lblNewLabel); // Tilf�j til panel

		lblPleaseLoginBelow = new JLabel("Please login below:");
		lblPleaseLoginBelow.setBounds(99, 145, 108, 16);
		lblPleaseLoginBelow.setFont(new Font("Calibri", Font.PLAIN, 12));
		add(lblPleaseLoginBelow);

		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(83, 197, 52, 14);
		lblUsername.setFont(new Font("Calibri", Font.PLAIN, 11));
		add(lblUsername);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(86, 252, 49, 14);
		lblPassword.setFont(new Font("Calibri", Font.PLAIN, 11));
		add(lblPassword);
		
		lblIncorrect = new JLabel("Username or password is incorrect");
		lblIncorrect.setBounds(77, 320, 165, 14);
		lblIncorrect.setFont(new Font("Calibri", Font.ITALIC, 11));
		lblIncorrect.setForeground(Color.red);
		lblIncorrect.setVisible(false);
		add(lblIncorrect);

		// JTextField
		userName_Login = new JTextField(); // Opret textfield
		userName_Login.setBounds(86, 222, 143, 20); // St�rrelse og placering
		userName_Login.setColumns(10); // St�rrelse
		add(userName_Login); // Tilf�j til panel
		
		//JPasswordField
		password_Login = new JPasswordField(); // Opret passwordfield
		password_Login.setBounds(86, 276, 143, 20); // St�rrelse og placering
		password_Login.setColumns(10); // St�rrelse
		add(password_Login); // Tilf�j til panel

		//JButton
		btnLogin = new JButton("Login"); // Lav knap og s�t tekst
		btnLogin.setBounds(118, 446, 89, 23); // St�rrelse og placering
		add(btnLogin); // Tilf�j til panel

	} // Konstrukt�r slutter


	// metode til at vise label
	public void incorrect()
	{
		lblIncorrect.setVisible(true);
	}
	/**
	 * @return UserName_Login.getText()
	 */
	// getters
	public String getUserName_Login()
	{
		return userName_Login.getText();
	}

	/**
	 * @return Password_Login.getText()
	 */
	@SuppressWarnings("deprecation")
	public String getPassword_Login()
	{
		return password_Login.getText();
	}
	/**
	 * Resets textfields
	 */
	public void reset() // metode der nulstiller panelet
	{
		// skjuler labels
		lblIncorrect.setVisible(false);
		// fjerner tekst i textfields
		password_Login.setText("");
	}
	/**
	 * adds actionlisteners and actioncommands for button
	 * @param l
	 */
	public void addActionListener(ActionListener l) // metode til at tilf�je actionlisteners og actioncommands til knapper
	{
		btnLogin.addActionListener(l); // tilf�jer actionlistener
		btnLogin.setActionCommand("LoginBtn"); // tilf�jer actioncommand
	} // metode slutter

	
}