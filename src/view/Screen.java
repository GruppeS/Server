package view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Screen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final String CALENDARLIST = "1";
	public static final String EVENTLIST = "2";
	public static final String LOGIN = "3";
	public static final String MENU = "4";
	public static final String NOTELIST = "5";
	public static final String USERLIST = "6";
	
	private JPanel contentPane;
	private CalendarListPanel calendarList;
	private EventListPanel eventList;
	private LoginPanel login;
	private MenuPanel menu;
	private NoteListPanel noteList;
	private UserListPanel userList;

	CardLayout c;
	
	public Screen() {
		setTitle("Server Control");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(0, 0, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new CardLayout(0, 0));
		setContentPane(contentPane);
		
		calendarList = new CalendarListPanel();
		contentPane.add(calendarList, CALENDARLIST);
		eventList = new EventListPanel();
		contentPane.add(eventList, EVENTLIST);
		login = new LoginPanel();
		contentPane.add(login, LOGIN);
		menu = new MenuPanel();
		contentPane.add(menu, MENU);
		noteList = new NoteListPanel();
		contentPane.add(noteList, NOTELIST);
		userList = new UserListPanel();
		contentPane.add(userList, USERLIST);
		
		c = (CardLayout) getContentPane().getLayout();
	}
	
	public CalendarListPanel getCalendarList() {
		return calendarList;
	}
	
	public EventListPanel getEventList() {
		return eventList;
	}
	
	public LoginPanel getLogin() {
		return login;
	}
	
	public MenuPanel getMenu() {
		return menu;
	}
	
	public NoteListPanel getNoteList() {
		return noteList;
	}
	
	public UserListPanel getUserList() {
		return userList;
	}
	
	public void show(String card)
	{
		c.show(getContentPane(), card);
	}
}
