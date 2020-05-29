package model.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

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

		String username = client.getUsername();
		String password = client.getPassword();

		client.setUserName(username);

		JSONObject jsonClientMessage = null;

		while (!client.login()) {
		}

		do {
			if (client.getJson() != null) {
				jsonClientMessage = client.getJson();
				send(jsonClientMessage);

				client.setJson(null);
			}

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

	void send(JSONObject jsonMessage) {
		writer.println(jsonMessage.toString());
	}

	void login() {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("username", client.getUsername());
		jsonMessage.put("password", client.getPassword());
		jsonMessage.put("action", "login");

		send(jsonMessage);
	}

	void register() {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("username", client.getUsername());
		jsonMessage.put("password", client.getPassword());
		jsonMessage.put("action", "register");

		send(jsonMessage);
	}

	void disconnect() {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("username", client.getUsername());
		jsonMessage.put("action", "disconnect");

		send(jsonMessage);
	}

}
