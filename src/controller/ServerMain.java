package controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.database.DatabaseInit;
import config.Configurations;

public class ServerMain {

	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static Configurations cf = new Configurations();
	private static int port = cf.getServerport();

	/**
	 * Main calls database initialization and creates a serversocket.
	 * Contains a while loop that creates a socket everytime a connection is made, and creates a new client thread.
	 * @param args
	 */
	public static void main(String args[]) {

		new DatabaseInit().go();

		new Thread( new AdminThread() ).start();

		try {
			serverSocket = new ServerSocket(port);
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
