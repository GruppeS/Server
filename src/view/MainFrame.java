package view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final String MAIN = "1";
	
	private JPanel contentPane;
	private MainPanel main;
	
	CardLayout c;

	public MainFrame() {
		setTitle("Calendar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 230, 114);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new CardLayout(0, 0));
		setContentPane(contentPane);

		main = new MainPanel();
		contentPane.add(main, MAIN);

		c = (CardLayout) getContentPane().getLayout();
	}
	
	public MainPanel getMain() {
		return main;
	}

	public void show(String card)
	{
		c.show(getContentPane(), card);
	}
}