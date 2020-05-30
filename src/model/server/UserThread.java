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
import util.Constants;

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

				action = jsonClientMessage.getString(Constants.KEY_CLIENT_ACTION);
				System.out.println(action);

				switch (action) {
				case Constants.VALUE_ACTION_LOGIN: {
					String username = jsonClientMessage.getString(Constants.KEY_USERNAME);
					String password = jsonClientMessage.getString(Constants.KEY_PASSWORD);

					try {
						int idUser = server.loginUser(username, password);

						if (idUser == -1) { // user non trouvé
							jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_USER_NOT_FOUND);
						} else {
							this.username = server.getDatabase().getUser(idUser).getString(Constants.KEY_USERNAME);
							jsonServerMessage.put(Constants.KEY_ID_USER, idUser + "");
							jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);
						}

					} catch (UnknownUserException | SQLException e) {
						e.printStackTrace();
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_USER_NOT_FOUND);
					}
				}
					break;

				case Constants.VALUE_ACTION_REGISTER: {
					String username = jsonClientMessage.getString(Constants.KEY_ID_USER);
					String password = jsonClientMessage.getString(Constants.KEY_PASSWORD);

					int idUser = server.registerUser(username, password);

					if (idUser != -1) {
						jsonServerMessage.put(Constants.KEY_ID_USER, idUser);
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);
					} else {
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_INTERNAL);
					}
				}
					break;

				case "updateUser":

					break;

				case "deleteUser":

					break;

				case Constants.VALUE_ACTION_SEND_MESSAGE: {
					String sender = jsonClientMessage.getString(Constants.KEY_USERNAME_SENDER);
					String recipient = jsonClientMessage.getString(Constants.KEY_USERNAME_RECIPIENT);
					String message = jsonClientMessage.getString(Constants.KEY_MESSAGE_CONTENT);

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
							idSender = resSender.getInt(Constants.KEY_ID_USER);
							idRecipient = resRecipient.getInt(Constants.KEY_ID_USER);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						try {
							idConversation = jsonClientMessage.getString(Constants.KEY_ID_CONVERSATION);
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

							jsonServerMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_RECEIVE_MESSAGE);
							jsonServerMessage.put(Constants.KEY_USERNAME_SENDER, sender);
							jsonServerMessage.put(Constants.KEY_USERNAME_RECIPIENT, recipient);
							jsonServerMessage.put(Constants.KEY_MESSAGE_CONTENT, message);
							jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);

							threadRecipient.send(jsonServerMessage);
						}

						jsonServerMessage = new JSONObject();
						jsonServerMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_SEND_MESSAGE);
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);

					} else {
						jsonServerMessage = new JSONObject();
						jsonServerMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_SEND_MESSAGE);
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_USER_NOT_FOUND);
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

			} while (!action.equals(Constants.VALUE_ACTION_DISCONNECT));

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