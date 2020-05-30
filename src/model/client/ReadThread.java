package model.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONObject;

import util.Constants;

public class ReadThread extends Thread {
	private BufferedReader reader;
	private Socket socket;
	private Client client;
	private JSONObject json;

	public ReadThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;

		try {
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
		JSONObject jsonServerMessage = null;

		while (true) {
			try {
				jsonServerMessage = receive();

				if (!jsonServerMessage.getString(Constants.KEY_MESSAGE).equals(Constants.VALUE_MESSAGE_OK)) {
					client.onRequestFailed(jsonServerMessage);
				}
				if (client.getUsername() != null) {
					client.onRequestSuccess(jsonServerMessage);
				}

			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}

	JSONObject receive() throws IOException {
		return new JSONObject(reader.readLine());
	}

	boolean login() {
		try {
			JSONObject jsonMessage = receive();
			System.out.println(jsonMessage.toString());
			return (jsonMessage.getString(Constants.KEY_MESSAGE).equals(Constants.VALUE_MESSAGE_OK));

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
