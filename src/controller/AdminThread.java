package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Frame;
import view.MainFrame;

/**
 * AdminThread implements Runnable so that it can be run as a thread
 */
public class AdminThread implements Runnable {

	private MainFrame mainScreen;
	private Frame frame;

	private SwitchMethods switchMethods;
	private AdminMethods adminMethods;

	private String email;
	private String password;
	private String authenticated;

	/**
	 * Constructor initializes objects and adds actionlisteners to admin panels
	 */
	public AdminThread(){

		switchMethods = new SwitchMethods();
		adminMethods = new AdminMethods();

		mainScreen = new MainFrame();
		mainScreen.getMain().addActionListener(new MainActionListener());

		frame = new Frame();
		frame.getLogin().addActionListener(new LoginActionListener());
		frame.getMenu().addActionListener(new MenuActionListener());
		frame.getCalendarList().addActionListener(new CalendarListActionListener());
		frame.getNoteList().addActionListener(new NoteListActionListener());
		frame.getUserList().addActionListener(new UserListActionListener());
		frame.getEventList().addActionListener(new EventListActionListener());
	}

	/**
	 * Shows the main frame
	 */
	public void run() {
		mainScreen.show(MainFrame.MAIN);
		mainScreen.setResizable(false);
		mainScreen.setVisible(true);
	}

	/**
	 * Actionlistener for main panel
	 * AdminBtn opens up a new frame with login panel for admin
	 * TerminateBtn terminates the server
	 */
	private class MainActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd = e.getActionCommand();

			if(cmd.equals("AdminBtn")) {
				frame.show(Frame.LOGIN);
				frame.setResizable(false);
				frame.setVisible(true);
			}
			if(cmd.equals("TerminateBtn")) {
				System.exit(0);
			}
		}
	}
	/**
	 * Actionlistener for login panel
	 * LoginBtn calls authenticate method in switchmethods class
	 */
	private class LoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("LoginBtn")) {
				email = frame.getLogin().getUserName_Login(); 
				password = frame.getLogin().getPassword_Login();
				try {
					authenticated = switchMethods.authenticate(email, password, true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (authenticated.equals("0")){
					frame.show(Frame.MENU);
					frame.getLogin().reset();
				}
				else if(!authenticated.equals("0")){
					frame.getLogin().incorrect();
				}
			}
		}
	}
	/**
	 * Actionlistener for admin menu panel
	 * CalendarListBtn shows calendarlist panel and calls admin method calendarTable
	 * EventListBtn shows eventlist panel and calls admin method eventsTable
	 * NoteListBtn shows notelist panel and calls admin method notesTable
	 * UserListBtn shows userlist panel and calls admin method userTable
	 */
	private class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("btnCalendarList")) {
				frame.getCalendarList().createTable(adminMethods.calendarTable());
				frame.show(Frame.CALENDARLIST);
			}
			if(cmd.equals("btnEventList")) {
				frame.getEventList().createTable(adminMethods.eventsTable());
				frame.show(Frame.EVENTLIST);
			}
			if(cmd.equals("btnNoteList")) {
				frame.getNoteList().createTable(adminMethods.notesTable());
				frame.show(Frame.NOTELIST);
			}
			if(cmd.equals("btnUserList")) {
				frame.getUserList().createTable(adminMethods.userTable());
				frame.show(Frame.USERLIST);
			}
		}	
	}
	/**
	 * Actionlistener for calendarlist panel
	 * DeleteBtn calls admin method deleteCalendar with the selected calendar
	 * BackToMainBtn returns to menu panel
	 */
	private class CalendarListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("btnDelete")) {

				String calendarid = frame.getCalendarList().getSelectedCalendar();

				if(calendarid!=null){
					adminMethods.deleteCalendar(calendarid, "admin");
					frame.getCalendarList().createTable(adminMethods.calendarTable());
				}
			}

			if(cmd.equals("btnBackToMain")) {
				frame.show(Frame.MENU);
			}
		}
	}
	/**
	 * Actionlistener for notelist panel
	 * DeleteBtn calls admin method deleteNote with the selected note
	 * BackToMainBtn returns to menu panel
	 */
	private class NoteListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("btnDelete")) {
				String noteid = frame.getNoteList().getSelectedNote();

				if(noteid!=null) {
					adminMethods.deleteNote(noteid);
					frame.getNoteList().createTable(adminMethods.notesTable());
				}
			}

			if(cmd.equals("btnBackToMain")) {
				frame.show(Frame.MENU);
			}
		}
	}
	/**
	 * Actionlistener for userlist panel
	 * AddBtn calls admin method addUser with the typed username and password (these cannot be empty)
	 * DeleteBtn calls admin method deleteUser with the selected user
	 * BackToMainBtn returns to menu panel
	 */
	private class UserListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("btnAdd")) {
				String username = frame.getUserList().getUsername();
				String password = frame.getUserList().getPassword();

				frame.getUserList().reset();

				if(!username.equals("") && !password.equals("")) {
					if(adminMethods.addUser(username, password)){
					} else {
						frame.getUserList().userExists();
					}
				}
				frame.getUserList().createTable(adminMethods.userTable());
			}

			if(cmd.equals("btnDelete")) {
				String username = frame.getUserList().getSelectedUser();

				if(username!=null){
					adminMethods.deleteUser(username);
					frame.getUserList().createTable(adminMethods.userTable());
				}
			}

			if(cmd.equals("btnBackToMain")) {
				frame.getUserList().reset();
				frame.show(Frame.MENU);
			}
		}
	}
	/**
	 * Actionlistener for eventlist panel
	 * DeleteBtn calls admin method deleteEvent with the selected event and admin credential
	 * BackToMainBtn returns to menu panel
	 */
	private class EventListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("btnDelete")) {
				String eventid = frame.getEventList().getSelectedEvent();

				if(eventid!=null) {
					adminMethods.deleteEvent(eventid, "admin");
					frame.getEventList().createTable(adminMethods.eventsTable());
				}
			}

			if(cmd.equals("btnBackToMain")) {
				frame.show(Frame.MENU);
			}
		}
	}
}