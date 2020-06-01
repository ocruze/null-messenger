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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import exceptions.RegisterWithoutPasswordException;
import exceptions.UnknownUserException;
import exceptions.UserAlreadyLoggedInException;
import exceptions.UserAlreadyRegisteredException;
import util.Constants;

public class UserThread extends Thread {
	private Socket socket;
	private Server server;
	private PrintWriter writer;
	private BufferedReader reader;
	private String username;
	private int idUser;
	JSONObject jsonClientMessage;
	JSONObject jsonServerMessage;
	private Optional<Integer> idConvOptional;

	// private User user;

	public Optional<Integer> getIdConvOptional() {
		return idConvOptional;
	}

	public void setIdConvOptional(Optional<Integer> idConvOptional) {
		this.idConvOptional = idConvOptional;
	}

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

			String action;

			do {
				jsonServerMessage = new JSONObject();

				jsonClientMessage = new JSONObject(reader.readLine());
				System.out.println(jsonClientMessage.toString());

				action = jsonClientMessage.getString(Constants.KEY_CLIENT_ACTION);
				System.out.println(action);

				switch (action) {
				case Constants.VALUE_ACTION_LOGIN: {
					String username = jsonClientMessage.getString(Constants.KEY_USERNAME);
					String password = jsonClientMessage.getString(Constants.KEY_PASSWORD);

					int idUser = -1;

					try {
						idUser = server.loginUser(username, password);

					} catch (UnknownUserException e) {
						idUser = -1;
					} catch (UserAlreadyLoggedInException e) {
						idUser = -2;
					}

					if (idUser == -1) { // user non trouvé
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_USER_NOT_FOUND);
					} else if (idUser == -2) { // user déjà connecté
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_USER_ALREADY_LOGGED_IN);
					} else {
						try {
							this.username = server.getDatabase().getUser(idUser).getString(Constants.KEY_USERNAME);
							this.idUser = idUser;
							jsonServerMessage.put(Constants.KEY_ID_USER, idUser + "");
							jsonServerMessage.put(Constants.KEY_USERNAME, username);
							jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);
						} catch (SQLException | UnknownUserException e) {
							e.printStackTrace();
						}
					}

				}
					break;

				case Constants.VALUE_ACTION_REGISTER: {
					String username = jsonClientMessage.getString(Constants.KEY_USERNAME);
					String password = jsonClientMessage.getString(Constants.KEY_PASSWORD);

					int idUser = -1;
					try {
						idUser = server.registerUser(username, password);
					} catch (UserAlreadyRegisteredException e) {
						idUser = -2;
					} catch (RegisterWithoutPasswordException e) {
						idUser = -3;
					}

					if (idUser > 0) {
						jsonServerMessage.put(Constants.KEY_ID_USER, idUser);
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);
					} else if (idUser == -2) {
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_USER_ALREADY_REGISTERED);
					} else if (idUser == -3) {
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_NO_PASSWORD_PROVIDED);
					} else {
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_INTERNAL);
					}
				}
					break;

				case "updateUser":

					break;

				case "deleteUser":

					break;

				case Constants.VALUE_ACTION_GET_CONNECTED_USERS:

					break;

				case Constants.VALUE_ACTION_GET_USERS: {
					ResultSet res = server.getDatabase().getUsers();

//					JSONObject json = new JSONObject();
					JSONArray users = new JSONArray();
					try {
						while (res.next()) {
							JSONObject user = new JSONObject();
							user.put(Constants.KEY_ID_USER, res.getString(1));
							user.put(Constants.KEY_USERNAME, res.getString(2));

							users.put(user);
						}
						jsonServerMessage.put(Constants.KEY_USERS, users);
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);
					} catch (SQLException e) {
						e.printStackTrace();
						jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_INTERNAL);
					}
				}
					break;

				case Constants.VALUE_ACTION_SEND_MESSAGE: {

//					String usernameSender = jsonClientMessage.getString(Constants.KEY_USERNAME_SENDER);
//					String usernameRecipient = jsonClientMessage.getString(Constants.KEY_USERNAME_RECIPIENT);
					String content = jsonClientMessage.getString(Constants.KEY_MESSAGE_CONTENT);
//					int idConversation = jsonClientMessage.getInt(Constants.KEY_ID_CONVERSATION);
//					int idSender = jsonClientMessage.getInt(Constants.KEY_ID_SENDER);
//					JSONArray jarrayRecipients = jsonClientMessage.getJSONArray(Constants.KEY_RECIPIENTS);
//
//					jarrayRecipients.forEach(x -> {
//						server.getDatabase().addMessage(idConversation, idSender, content);
//
//						UserThread recipientThread = server.getUserThread(idSender);
//						if (recipientThread != null) {
//							recipientThread.getConversations();
//						}
//					});

					JSONArray listRecipients = jsonClientMessage.getJSONArray(Constants.KEY_RECIPIENTS);
					Optional<Integer> idconv = Optional.of(jsonClientMessage.getInt(Constants.KEY_ID_CONVERSATION));
					//setIdConvOptional(Optional.of(1));
					try {
//						listRecipients.forEach(item -> {
//
//							JSONObject obj = (JSONObject) item;
//							String sender = obj.get(Constants.KEY_ID_USER).toString();
//							// String recipient = obj.get(Constants.KEY_USERNAME_RECIPIENT).toString();
//							// String recipient = Constants.VALUE_SYSTEM;
//							sendMessage(sender, content, idconv);
////							UserThread recipientThread = server.getUserThread(recipient);
////							if (recipientThread != null) {
////								recipientThread.receiveMessage();
////							}
//
//						});
						sendMessage(jsonClientMessage.get(Constants.KEY_USERNAME_SENDER).toString(), content, idconv);

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
					break;

				case Constants.VALUE_ACTION_GET_CONVERSATIONS:
					getConversations();

					break;

				case Constants.VALUE_ACTION_CREATE_CONVERSATION:
					System.out.println("create conv second step !");

//					String content = "You have been added to this conversation by " + username + ".";
//					int idConversation = server.getDatabase().addConversation();
//					int idSender = this.idUser;
//					JSONArray jarrayParticipants = jsonClientMessage.getJSONArray(Constants.KEY_PARTICIPANTS);
//
//					jarrayParticipants.forEach(x -> {
//						server.getDatabase().addMessage(idConversation, idSender, content);
//
//						UserThread recipientThread = server.getUserThread(idSender);
//						if (recipientThread != null) {
//							recipientThread.getConversations();
//						}
//					});

					JSONArray listParticipant = jsonClientMessage.getJSONArray(Constants.KEY_PARTICIPANTS);
					setIdConvOptional(Optional.of(-1));
					try {
						listParticipant.forEach(item -> {

							JSONObject obj = (JSONObject) item;
							String sender = obj.get(Constants.KEY_USERNAME).toString();
							if (this.getIdConvOptional().get() == -1) {
								this.setIdConvOptional(Optional.of(sendMessage(sender,
										"you have been add to the conversation" + sender, Optional.of(-1))));
							} else {
								sendMessage(sender, "you have been add to the conversation " + sender,
										this.getIdConvOptional());
							}

						});

					} catch (Exception e) {
						e.printStackTrace();
					}
					jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);

					break;

				case "updateMessage":

					break;

				case "deleteMessage":

					break;

				case Constants.VALUE_ACTION_DISCONNECT:
					jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);
					break;

				default:
					break;
				}

//				serverMessage = "[" + username + "]: " + clientMessage;
				jsonServerMessage.put(Constants.KEY_CLIENT_ACTION, action);
				send(jsonServerMessage);

			} while (!action.equals(Constants.VALUE_ACTION_DISCONNECT));

			socket.close();

//			serverMessage = username + " has left.";
//			server.broadcast(serverMessage, this);
			server.disconnectUser(this);

		} catch (Exception ex) {
			server.disconnectUser(this);
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public JSONObject getConversations() {
		int idUser = this.idUser;// jsonClientMessage.getInt(Constants.KEY_ID_USER);
		ResultSet res = server.getDatabase().getUserConversations(idUser);

		jsonServerMessage = new JSONObject();

		JSONArray jArrayConv = new JSONArray();
		try {
			if (res == null) {
				jsonServerMessage.put(Constants.KEY_INFO, Constants.VALUE_NONE_MESSAGE);
			} else {
				while (res.next()) {
					int idConversation = res.getInt(1);
					ResultSet resMessages = server.getDatabase().getConversationMessages(idConversation);

					JSONArray jArrayMessages = new JSONArray();

					while (resMessages.next()) {
						JSONObject message = new JSONObject();

						message.put(Constants.KEY_ID_MESSAGE, resMessages.getInt(1));
						message.put(Constants.KEY_MESSAGE_CONTENT, resMessages.getString(2));
						message.put(Constants.KEY_ID_SENDER, resMessages.getInt(3));
						message.put(Constants.KEY_ID_CONVERSATION, resMessages.getInt(4));
						message.put(Constants.KEY_MESSANGE_DATE, resMessages.getString(5));

						jArrayMessages.put(message);
					}
					jArrayConv.put(jArrayMessages);
				}

				jsonServerMessage.put(Constants.KEY_CONVERSATIONS, jArrayConv);
				jsonServerMessage.put(Constants.KEY_INFO, Constants.VALUE_INCOMING_MESSAGE);
			}

			jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);

		} catch (SQLException e) {
			e.printStackTrace();
			jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_ERROR_INTERNAL);
		}

		return jsonServerMessage;
	}

	void send(JSONObject jsonMessage) {
		System.out.println("Server says : " + jsonMessage.toString() + "\n\n");
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

	public int getIdUser() {
		return idUser;
	}

	private void receiveMessage() {
		JSONObject json = new JSONObject();
		json.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_RECEIVE_MESSAGE);
		json.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);
		send(json);
	}

	private int sendMessage(String sender, String message) {
		return this.sendMessage(sender, message, Optional.empty());
	}

	private int sendMessage(String sender, String message, Optional<Integer> idConvOptional) {
		ResultSet resSender = server.getUser(sender);
		UserThread threadRecipient = null;
		String idConversation = "";
		int idConv = -1, idSender = -1, idRecipient = -1;

		// sauvegarde du message dans la BD
		try {
			if (resSender.next()) {
				idSender = resSender.getInt(Constants.KEY_ID_USER);
				System.out.println(resSender.getInt(Constants.KEY_ID_USER));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			/**
			 * if(jsonClientMessage.get(Constants.KEY_ID_CONVERSATION).equals(null)) {
			 * idConv = server.getDatabase().addConversation(); } else { }
			 **/
			if (idConvOptional.isEmpty()) {
				idConversation = jsonClientMessage.getString(Constants.KEY_ID_CONVERSATION);
				idConv = Integer.parseInt(idConversation);
			} else {
				idConv = idConvOptional.get();
			}

		} catch (JSONException e) {
			idConv = server.getPrivateConversationId(idSender, idRecipient);
		}
		if (idConv == -1)
			idConv = server.getDatabase().addConversation();

		server.getDatabase().addMessage(idConv, idSender, message);

		// si user connecté, on l'envoie le msg, sinon rien
		// fait crasher pour l'instant
		try {

			List<Integer> allUsers = server.getDatabase().getConversationParticipants(idConv);
			int idLocal = idSender;
			allUsers.stream().filter(u -> u != idLocal).forEach(u -> {
				sendMessageToUser(u);
			});

		} catch (NoSuchElementException e) {
			System.out.println("user not connected yet, the message will be deliver later");
		}

		jsonServerMessage = new JSONObject();
		jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);

		return idConv;
	}

	private void sendMessageToUser(int targetId) {
		UserThread threadRecipient = server.getUserThread(targetId);
		if (threadRecipient != null) {
			// jsonServerMessage.put(Constants.KEY_USERNAME_SENDER, sender);
			// jsonServerMessage.put(Constants.KEY_USERNAME_RECIPIENT, recipient);
			// jsonServerMessage.put(Constants.KEY_MESSAGE_CONTENT, message);
			jsonServerMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_GET_CONVERSATIONS);
			jsonServerMessage.put(Constants.KEY_MESSAGE, Constants.VALUE_MESSAGE_OK);

			threadRecipient.send(threadRecipient.getConversations());
		}
	}

}