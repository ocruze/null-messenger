package model.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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

		String username = client.getUserName();
		String password = client.getPassword();

		client.setUserName(username);

		JSONObject jsonClientMessage = null;

		do {
			jsonClientMessage = new JSONObject();
			jsonClientMessage.put("username", username);
			jsonClientMessage.put("password", password);
			jsonClientMessage.put("action", "login");

			writer.println(jsonClientMessage.toString());

		} while (!jsonClientMessage.getString("action").equals("disconnect"));

		jsonClientMessage = new JSONObject();
		jsonClientMessage.put("username", username);
		jsonClientMessage.put("action", "disconnect");

		try {
			socket.close();
		} catch (IOException ex) {
			System.out.println("Error writing to server: " + ex.getMessage());
		}
	}
}
