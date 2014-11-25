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
	}

	public void run() {
		mainScreen.show(MainFrame.MAIN);
		mainScreen.setVisible(true);
	}

	private class MainActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String cmd = e.getActionCommand();

			if(cmd.equals("AdminBtn")) {
				screen.show(Screen.LOGIN);
				screen.setVisible(true);
				
			}
			if(cmd.equals("TerminateBtn")) {
				System.exit(0);
			}
		}
	}
}