package controller;

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

//		new Thread( new adminThread() ).start();

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
	private Encryption cryp = new Encryption();

	public clientThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		try {
			System.out.println("Client connected");

			byte[] incommingJson = null;

			DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
			int length = dis.readInt();

			if(length>0) {
				incommingJson = new byte[length];
				dis.readFully(incommingJson, 0, incommingJson.length);
			}

			String reply = GS.GiantSwitchMethod(cryp.xorDecrypt(incommingJson));			
			
			System.out.println("Sending client reply");
			byte[] message = cryp.xorEncrypt(reply);
			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
			dos.writeInt(message.length);
			dos.write(message);  
			dos.flush();
			
			if(!reply.equals("0")){
				
			}
			
			else{
				
			clientSocket.close();
			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class adminThread implements Runnable {

	public void run() {
		new GUILogic().run();
	}
}