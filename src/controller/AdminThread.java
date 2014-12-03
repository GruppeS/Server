package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Frame;
import view.MainFrame;

public class AdminThread implements Runnable {

	private MainFrame mainScreen;
	private Frame frame;

	private SwitchMethods switchMethods;
	private AdminMethods adminMethods;

	private String email;
	private String password;
	private String authenticated;

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

	public void run() {
		mainScreen.show(MainFrame.MAIN);
		mainScreen.setResizable(false);
		mainScreen.setVisible(true);
	}

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
				}
				else if(!authenticated.equals("0")){
					frame.getLogin().incorrect();
				}
			}
		}
	}
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
				frame.show(Frame.NOTELIST);
			}
			if(cmd.equals("btnUserList")) {
				frame.getUserList().createTable(adminMethods.userTable());
				frame.show(Frame.USERLIST);
			}
		}	
	}	
	private class CalendarListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("btnDelete")) {

				String calendarid = frame.getCalendarList().getSelectedCalendar();

				if(calendarid!=null){
					adminMethods.deleteCalendar(calendarid);
					frame.getCalendarList().createTable(adminMethods.calendarTable());
				}

			}

			if(cmd.equals("btnBackToMain")) {
				frame.show(Frame.MENU);
			}
		}
	}
	private class NoteListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("btnBackToMain")) {
				frame.show(Frame.MENU);
			}
		}
	}
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
	private class EventListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String cmd  = e.getActionCommand();

			if(cmd.equals("btnDelete")) {
				String eventid = frame.getEventList().getSelectedEvent();
				
				if(eventid!=null) {
					adminMethods.deleteEvent(eventid);
					frame.getEventList().createTable(adminMethods.eventsTable());
				}
			}

			if(cmd.equals("btnBackToMain")) {
				frame.show(Frame.MENU);
			}
		}
	}
}