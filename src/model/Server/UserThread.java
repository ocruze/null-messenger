package model.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import org.json.JSONObject;

import model.Exception.UserInexistantException;

public class UserThread extends Thread {
	private Socket socket;
	private ChatServer server;
	private PrintWriter writer;
	private String username;
	// private User user;

	public UserThread(Socket socket, ChatServer server) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);

			printUsers();

//			String username = reader.readLine();
//			user = server.addUser(username);

//			String serverMessage = "New user connected: " + username;
//			server.broadcast(serverMessage, this);
			String serverMessage = "";

			String clientMessage;
			JSONObject jsonClientMessage;
			JSONObject jsonServerMessage;

			String action;

			do {
				jsonServerMessage = new JSONObject();

				clientMessage = "";
//				for (int i = 0; i < 10; i++) {
//					System.out.println(reader.readLine());
//					
//				}
				
//				System.out.println("clientMessage : " + clientMessage);

				jsonClientMessage = new JSONObject(reader.readLine());
				System.out.println(jsonClientMessage.toString());
				
				action = jsonClientMessage.getString("action");
				System.out.println(action);

				switch (action) {
				case "login": {
					String username = jsonClientMessage.getString("username");
					String password = jsonClientMessage.getString("password");

					try {
						int idUser = server.loginUser(username, password);

						if (idUser == -1)
							jsonServerMessage.put("message", "user not found");
						else {
							this.username = server.database.getUser(idUser).getString("username");
						}

					} catch (UserInexistantException e) {
						e.printStackTrace();
						jsonServerMessage.put("message", "user not found");
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
					break;

				case "register": {
					String username = jsonClientMessage.getString("username");
					String password = jsonClientMessage.getString("password");

					try {
						int idUser = server.database.addUser(username, password);

						jsonServerMessage.put("idUser", idUser);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
					break;

				case "updateUser":

					break;

				case "deleteUser":

					break;

				case "send":

					break;

				case "updateMessage":

					break;

				case "deleteMessage":

					break;

				default:
					break;
				}

				serverMessage = "[" + username + "]: " + clientMessage;
				server.broadcast(serverMessage, this);

			} while (!action.equals("disconnect"));

			// server.disconnectUser(username);
			socket.close();

			serverMessage = username + " has left.";
			server.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Sends a list of online users to the newly connected user.
	 */
	void printUsers() {
		if (server.hasUsers()) {
			writer.println("Connected users: " + server.getUsers());
		} else {
			writer.println("No other users connected");
		}
	}

	/**
	 * Sends a message to the client.
	 */
	void sendMessage(String message) {
		writer.println(message);
	}

}