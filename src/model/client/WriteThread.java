package model.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

public class WriteThread extends Thread {
	private PrintWriter writer;
	private Socket socket;
	private Client client;

	public WriteThread(Socket socket, Client client) {
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
		String userName = client.getUserName();
		String password = client.getPassword();
		
		client.setUserName(userName);
//		writer.println(userName);

		JSONObject jsonClientMessage = null;

		do {
			// System.out.print("[" + userName + "] :");
			jsonClientMessage = new JSONObject();
			jsonClientMessage.put("username", userName);
			jsonClientMessage.put("password", password);
			jsonClientMessage.put("action", "login");
			// text = saisie.nextLine();

			writer.println(jsonClientMessage.toString());

		} while (!jsonClientMessage.getString("action").equals("disconnect"));

		try {
			socket.close();
			saisie.close();
		} catch (IOException ex) {

			System.out.println("Error writing to server: " + ex.getMessage());
		}
	}
}
