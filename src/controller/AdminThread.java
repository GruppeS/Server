package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;
import view.Screen;

public class AdminThread implements Runnable {

	private MainFrame mainScreen;
	private Screen screen;
	private String email;
	private String password;
	private String authenticated;

	private SwitchMethods switchMethods;

	public AdminThread(){

		switchMethods = new SwitchMethods();
		mainScreen = new MainFrame();
		mainScreen.getMain().addActionListener(new MainActionListener());
		screen = new Screen();
		screen.getLogin().addActionListener(new LoginActionListener());
		screen.getMenu().addActionListener(new MenuActionListener());
		screen.getCalendarList().addActionListener(new CalendarListActionListener());
		screen.getNoteList().addActionListener(new NoteListActionListener());
		screen.getUserList().addActionListener(new UserListActionListener());
		screen.getEventList().addActionListener(new EventListActionListener());
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
				screen.show(Screen.LOGIN);
				screen.setResizable(false);
				screen.setVisible(true);
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
				email = screen.getLogin().getUserName_Login(); 
				password = screen.getLogin().getPassword_Login();
				try {
					authenticated = switchMethods.authenticate(email, password, true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (authenticated.equals("0")){
				screen.show(Screen.MENU);
				}
				else if(!authenticated.equals("0")){
				screen.getLogin().incorrect();
				}
			}
		}
	}
	private class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd  = e.getActionCommand();
			
			if(cmd.equals("btnCalendarList")) {
				screen.show(Screen.CALENDARLIST);
			}
			if(cmd.equals("btnEventList")) {
				screen.show(Screen.EVENTLIST);
			}
			if(cmd.equals("btnNoteList")) {
				screen.show(Screen.NOTELIST);
			}
			if(cmd.equals("btnUserList")) {
				screen.show(Screen.USERLIST);
			}
		}	
	}	
	private class CalendarListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd  = e.getActionCommand();
			
			if(cmd.equals("btnBackToMain")) {
				screen.show(Screen.MENU);
			}
		}
	}
	private class NoteListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd  = e.getActionCommand();
			
			if(cmd.equals("btnBackToMain")) {
				screen.show(Screen.MENU);
			}
		}
	}
	private class UserListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd  = e.getActionCommand();
			
			if(cmd.equals("btnBackToMain")) {
				screen.show(Screen.MENU);
			}
		}
	}
	private class EventListActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd  = e.getActionCommand();
			
			if(cmd.equals("btnBackToMain")) {
				screen.show(Screen.MENU);
			}
		}
	}
}