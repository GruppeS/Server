package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientThread implements Runnable {

	private Socket clientSocket = null;
	private GiantSwitch GS = new GiantSwitch();
	private Encryption cryp = new Encryption();

	public ClientThread(Socket clientSocket) {
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
			String userInfo = cryp.xorDecrypt(incommingJson);
			String reply = GS.GiantSwitchMethod(userInfo);			
			
			System.out.println("Sending client reply");
			byte[] message = cryp.xorEncrypt(reply);
			DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
			dos.writeInt(message.length);
			dos.write(message);
			dos.flush();
			
			if(!reply.equals("0")){
			
			String calendar = GS.GiantSwitchMethod(userInfo);
				
			} else {
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