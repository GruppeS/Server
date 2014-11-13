package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread implements Runnable {

	private Socket clientSocket;
	private GiantSwitch GS = new GiantSwitch();
	private DataInputStream is;
	private PrintStream os;

	public ClientThread(Socket clientSocket) {
		this.clientSocket = clientSocket;

		try {
			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		while(true)
		{
			try {
				String message = is.readLine();
				System.out.println("Incomming: " + message);
				String reply = GS.GiantSwitchMethod(message);
				System.out.println("Reply: " + reply);
				os.println(reply);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}