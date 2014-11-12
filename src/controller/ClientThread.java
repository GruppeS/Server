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

			while(true)
			{
				String inFromClientC = is.readLine();

				if(!inFromClientC.equals(null))
				{
					String inFromClientD = cryp.xorDecrypt(inFromClientC);
					String replyD = GS.GiantSwitchMethod(inFromClientD);			
					String replyC = cryp.xorEncrypt(replyD);
					os.println("0");
//					os.println(replyC);
				}
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