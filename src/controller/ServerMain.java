package controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import model.Forecast.ForecastModel;
import model.QOTD.QOTDModel;
import model.database.DatabaseInit;
import config.Configurations;

public class ServerMain {

	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	private static Configurations cf = new Configurations();
	private static int port = cf.getServerport();

	public static void main(String args[]) throws SQLException, IOException {

		new DatabaseInit().go();
		
		new QOTDModel().saveQuote();
		new ForecastModel().saveForecast();

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
