package controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
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

			DataInputStream is = new DataInputStream(clientSocket.getInputStream());
			PrintStream os = new PrintStream(clientSocket.getOutputStream());

			String json = is.readLine();
			String userInfo = cryp.xorDecrypt(json);
			String message = GS.GiantSwitchMethod(userInfo);			
			String reply = cryp.xorEncrypt(message);
			os.println(reply);

			clientSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}