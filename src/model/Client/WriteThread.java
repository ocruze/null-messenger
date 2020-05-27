package model.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
	private PrintWriter writer;
	private Socket socket;
	private ChatClient client;

	public WriteThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;

		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {

		// Console console = System.console();

		Scanner saisie = new Scanner(System.in);
		// System.out.print("Enter your name: ");
		// String userName = saisie.nextLine();
		String userName = model.Client.ChatClient.getUserName();
		client.setUserName(userName);
		writer.println(userName);

		String text;

		do {
			System.out.print("[" + userName + "] :");
			// TODO
			text = 
			// text = saisie.nextLine();
			writer.println(text);

		} while (!text.equals("bye"));


		try {
			socket.close();
			saisie.close();
		} catch (IOException ex) {

			System.out.println("Error writing to server: " + ex.getMessage());
		}
	}
}

