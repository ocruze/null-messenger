package model.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.entity.User;
import util.Constants;

public class RequestInvoker {
	private PrintWriter writer;

	public RequestInvoker(Socket socket) {
		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
		} catch (IOException ex) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	

	private void send(JSONObject jsonMessage) {
		writer.println(jsonMessage.toString());
	}

	public void login(String username, String password) {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put(Constants.KEY_USERNAME, username);
		jsonMessage.put(Constants.KEY_PASSWORD, password);
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_LOGIN);

		send(jsonMessage);
	}

	public void register(String username, String password) {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put(Constants.KEY_USERNAME, username);
		jsonMessage.put(Constants.KEY_PASSWORD, password);
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_REGISTER);

		send(jsonMessage);
	}

	public void loadUsers() {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_GET_USERS);
		send(jsonMessage);
	}
	
	public void loadConversations() {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put(Constants.KEY_ID_USER, UserSession.getConnectedUserId());
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_GET_CONVERSATIONS);
		send(jsonMessage);
	}
	public void createConversation(List<User> listUser) {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_CREATE_CONVERSATION);
		JSONArray participants = new JSONArray();
		for(User user : listUser) {
			JSONObject json = new JSONObject();
			json.put(Constants.KEY_ID_USER, user.getIdUser());
			json.put(Constants.KEY_USERNAME, user.getUsername());
			participants.put(json);
		}
		jsonMessage.put(Constants.VALUE_PARTICIPANT, participants);
		send(jsonMessage);
	}
	public void disconnect(String username) {
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put(Constants.KEY_USERNAME, username);
		jsonMessage.put(Constants.KEY_CLIENT_ACTION, Constants.VALUE_ACTION_DISCONNECT);
		send(jsonMessage);
	}
	
	void close() {
		this.writer.close();
	}

}
