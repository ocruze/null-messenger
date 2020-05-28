package model.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private String hostname;
	private int port;
	private static String userName;

	public Client(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		Client.userName = null;
	}

	public void execute() {
		ReadThread readThread;
		WriteThread writeThread;

		try {
			Socket socket = new Socket(hostname, port);

			System.out.println("Connected to the chat server");

			readThread = new ReadThread(socket, this);
			writeThread = new WriteThread(socket, this);

			readThread.start();
			writeThread.start();

		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}

	}

	void setUserName(String userName) {
		Client.userName = userName;
	}

	static String getUserName() {
		return Client.userName;
	}

}