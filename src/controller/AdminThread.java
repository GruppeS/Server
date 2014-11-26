package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;
import view.Screen;

public class AdminThread implements Runnable {

	private MainFrame mainScreen;
	private Screen screen;

	public AdminThread(){

		mainScreen = new MainFrame();
		mainScreen.getMain().addActionListener(new MainActionListener());
		screen = new Screen();
		screen.getLogin().addActionListener(new LoginActionListener());
		screen.getMenu().addActionListener(new MenuActionListener());
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
				screen.show(Screen.MENU);
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
}