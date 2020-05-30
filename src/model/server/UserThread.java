package model.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.UnknownUserException;

public class UserThread extends Thread {
	private Socket socket;
	private Server server;
	private PrintWriter writer;
	private BufferedReader reader;
	private String username;

	// private User user;

	public UserThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);

			// printUsers();

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

						if (idUser == -1) { // user non trouvé
							jsonServerMessage.put("code", "404");
							jsonServerMessage.put("message", "NOT FOUND");
						} else {
							this.username = server.getDatabase().getUser(idUser).getString("username");
							jsonServerMessage.put("idUser", idUser + "");
							jsonServerMessage.put("code", "200");
							jsonServerMessage.put("message", "OK");
						}

					} catch (UnknownUserException | SQLException e) {
						e.printStackTrace();
						jsonServerMessage.put("code", "404");
						jsonServerMessage.put("message", "NOT FOUND");
					}
				}
					break;

				case "register": {
					String username = jsonClientMessage.getString("username");
					String password = jsonClientMessage.getString("password");

					int idUser = server.registerUser(username, password);

					if (idUser != -1) {
						jsonServerMessage.put("idUser", idUser);
						jsonServerMessage.put("code", "200");
						jsonServerMessage.put("message", "OK");
					} else {
						jsonServerMessage.put("code", "500");
						jsonServerMessage.put("message", "INTERNAL SERVER ERROR");
					}
				}
					break;

				case "updateUser":

					break;

				case "deleteUser":

					break;

				case "send": {
					String sender = jsonClientMessage.getString("sender");
					String recipient = jsonClientMessage.getString("recipient");
					String message = jsonClientMessage.getString("message");

					ResultSet resSender = server.getUser(sender);

					ResultSet resRecipient = server.getUser(recipient);
					UserThread threadRecipient = null;

					String idConversation = "";
					int idConv = -1;
					int idSender = -1, idRecipient = -1;

					// si user recipient existe
					if (resRecipient != null) {
						// sauvegarde du message dans la BD
						try {
							idSender = resSender.getInt("idUser");
							idRecipient = resRecipient.getInt("idUser");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						try {
							idConversation = jsonClientMessage.getString("idConversation");
							idConv = Integer.parseInt(idConversation);

						} catch (JSONException e) {
							idConv = server.getPrivateConversationId(idSender, idRecipient);
						}
						if (idConv == -1)
							idConv = server.getDatabase().addConversation();

						server.getDatabase().addMessage(idConv, idSender, message);

						// si user connecté, on l'envoie le msg, sinon rien
						threadRecipient = server.getUserThread(recipient);
						if (threadRecipient != null) {

							jsonServerMessage.put("action", "receive");
							jsonServerMessage.put("sender", sender);
							jsonServerMessage.put("recipient", recipient);
							jsonServerMessage.put("content", message);
							jsonServerMessage.put("message", "OK");
							jsonServerMessage.put("code", 200);

							threadRecipient.send(jsonServerMessage);
						}

						jsonServerMessage = new JSONObject();
						jsonServerMessage.put("code", 201);
						jsonServerMessage.put("message", "OK");

					} else {
						jsonServerMessage.put("code", 404);
						jsonServerMessage.put("message", "RECIPIENT USER NOT FOUND");
					}
				}
					break;

				case "updateMessage":

					break;

				case "deleteMessage":

					break;

				default:
					break;
				}

//				serverMessage = "[" + username + "]: " + clientMessage;

				send(jsonServerMessage);

			} while (!action.equals("disconnect"));

			socket.close();

//			serverMessage = username + " has left.";
//			server.broadcast(serverMessage, this);
			server.disconnectUser(this);

		} catch (IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	void send(JSONObject jsonMessage) {
		System.out.println("Server says : " + jsonMessage.toString());
		writer.println(jsonMessage.toString());
	}

	JSONObject receive() throws IOException {
		return new JSONObject(reader.readLine());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserThread other = (UserThread) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public String getUsername() {
		return username;
	}

}