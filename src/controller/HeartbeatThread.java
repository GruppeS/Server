package controller;

import java.net.Socket;

public class HeartbeatThread implements Runnable {

	private Socket clientSocket;
	private boolean active;
	
	public HeartbeatThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		clientSocket = null;
		active = true;
	}
	
	public void run(){
		
	}
}