package controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import model.QOTD.QOTDModel;
import model.database.DatabaseInit;

public class ServerMain {

	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;

	public static void main(String args[]) throws SQLException, IOException {

		new DatabaseInit().go();
		
		new QOTDModel().saveQuote();

		new Thread( new AdminThread() ).start();

		try {
			serverSocket = new ServerSocket(8888);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				clientSocket = serverSocket.accept();
				new Thread( new ClientThread(clientSocket) ).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
