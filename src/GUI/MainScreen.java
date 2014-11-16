package GUI;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final String MAIN = "1";
	private JPanel contentPane;
	private Main main;
	
	CardLayout c;

	public MainScreen() {
		setTitle("Calendar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 217, 118);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		main = new Main();
		contentPane.add(main, MAIN);

		c = (CardLayout) getContentPane().getLayout();
	}
	public Main getMain() {
		return main;
	}

	public void show(String card)
	{
		c.show(getContentPane(), card);
	}
}
