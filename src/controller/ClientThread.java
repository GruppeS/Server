package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable {

	private Socket clientSocket;
	private GiantSwitch GS = new GiantSwitch();
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private boolean active = true;

	public ClientThread(Socket clientSocket) {
		this.clientSocket = clientSocket;

		try {
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(clientSocket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		while(active)
		{
			try {
				String message = (String) input.readObject();
				System.out.println("Incomming: " + message);
				String reply = GS.GiantSwitchMethod(message);
				System.out.println("Reply: " + reply);
				output.writeObject(reply);
				output.flush();

			} catch (IOException e) {
				terminate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void terminate() {
		try {
			output.close();
			input.close();
			clientSocket.close();
			active = false;
			System.out.println("Client disconnected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}