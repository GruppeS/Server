package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ClientThread implements Runnable so that it can be run as a thread
 */
public class ClientThread implements Runnable {

	private Socket clientSocket;
	private ServerSwitch serverSwitch = new ServerSwitch();
	private Encryption encryption = new Encryption();
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean active = true;

	/**
	 * Constructor recieves clientSocket and opens input and output streams on it
	 * @param clientSocket
	 */
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

	/**
	 * While client has an open socket inputstream listens for input.
	 * Inputs gets handled by the server switch which returns an answer which gets written to the outputstream.
	 * Inputs and outputs gets encrypted/decrypted using XOR
	 * If client closes his socket the terminate method will be called
	 */
	public void run() {

		while(active)
		{
			try {
				byte[] requestEncrypted = (byte[]) input.readObject();
				String requestDecrypted = encryption.decrypt(requestEncrypted);
				System.out.println("Request: " + requestDecrypted);
				String reply = serverSwitch.giantSwitchMethod(requestDecrypted);
				System.out.println("Reply: " + reply);
				output.writeObject(encryption.encrypt(reply));
				output.flush();
			} catch (IOException e) {
				terminate();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * output- and inputstreams is closed along with the clientsocket
	 */
	public void terminate() {
		try {
			input.close();
			output.close();
			clientSocket.close();
			active = false;
			System.out.println("Client disconnected");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}