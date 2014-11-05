import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.ClientThread;
import controller.AdminThread;

public class Test {
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
				new Thread( new ClientThread(clientSocket) ).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
