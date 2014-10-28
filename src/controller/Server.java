package controller;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import GUI.GUILogic;

public class Server {

	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;

	public static void main(String args[]) {

		new Thread( new adminThread() ).start();

		try {
			serverSocket = new ServerSocket(8888);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				clientSocket = serverSocket.accept();
				new Thread( new clientThread(clientSocket) ).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class clientThread implements Runnable {

	private Socket clientSocket = null;
	private GiantSwitch GS = new GiantSwitch();
	private encryption cryp = new encryption();

	public clientThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
			System.out.println("forbindelse Oprettet!");
			//BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			byte[] b = new byte[500000];
			int count = clientSocket.getInputStream().read(b);
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			DataInputStream inFromClient = new DataInputStream(clientSocket.getInputStream());		
			//Creates an object of the data which is to be send back to the client, via the connectionSocket
			DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println("Outtoclient oprettet!");
			//Sets client sentence equals input from client
			//incomingJson = inFromClient.readLine();			

			String ny = cryp.decrypt(b);

			//cryp.StringEncryption(inFromClient.readLine());
			System.out.println("Besked modtaget!");
			//Sysout recieved message
			System.out.println("Received: " + ny);
			String returnSvar = GS.GiantSwitchMethod(ny);		
			//Sends the capitalized message back to client!!
			outToClient.writeBytes(returnSvar + "\n");
			System.out.println("svar sendt");
			//BufferedWriter writer = new BufferedWriter(arg0)
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

class adminThread implements Runnable {

	public void run() {
		new GUILogic().run();
	}
}