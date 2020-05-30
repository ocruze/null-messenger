package model.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import org.json.JSONObject;

import util.Constants;

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

		if(client.isWantRegister()) {
			while (!client.register() || !client.isWantRegister()) {
			}
		}
		
		while (!client.login()) {
		}

		do {
			if (client.getJson() != null) {
				jsonClientMessage = client.getJson();
				send(jsonClientMessage);

				client.setJson(null);
			}

		} while (!jsonClientMessage.getString(Constants.KEY_CLIENT_ACTION).equals(Constants.VALUE_ACTION_DISCONNECT));

		disconnect();

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
		jsonMessage.put(Constants.KEY_USERNAME, client.getUsername());
		jsonMessage.put(Constants.KEY_PASSWORD, client.getPassword());
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_LOGIN);

		send(jsonMessage);
	}

	void register() {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put(Constants.KEY_USERNAME, client.getUsername());
		jsonMessage.put(Constants.KEY_PASSWORD, client.getPassword());
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_REGISTER);

		send(jsonMessage);
	}

	void disconnect() {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put(Constants.KEY_USERNAME, client.getUsername());
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_DISCONNECT);

		send(jsonMessage);
	}

}
