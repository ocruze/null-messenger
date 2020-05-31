package model.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import exceptions.RegisterWithoutPasswordException;
import exceptions.UnknownUserException;
import exceptions.UserAlreadyLoggedInException;
import exceptions.UserAlreadyRegisteredException;
import model.database.Database;
import model.entity.User;
import util.Constants;

public class Server {
	private int port;
	private Database database;

	private Map<Integer, String> connectedUsers;
	private Set<UserThread> userThreads;

	public Server(int port) {
		this.port = port;
		this.database = new Database();

		connectedUsers = new HashMap<>();
		userThreads = new HashSet<>();
	}

	public void execute() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Chat Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");

				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				newUser.start();

			}

		} catch (IOException ex) {
			System.out.println("Error in the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Delivers a message from one user to others (broadcasting)
	 */
//	void broadcast(String message, UserThread excludeUser) {
//		for (UserThread aUser : userThreads) {
//			if (aUser != excludeUser) {
//				aUser.sendMessage(message);
//			}
//		}
//	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UnknownUserException
	 * @throws UserAlreadyLoggedInException
	 */
	int loginUser(String username, String password) throws UnknownUserException, UserAlreadyLoggedInException {
		ResultSet res = null;
		try {
			res = database.getUser(username);

			if (res == null)
				return -1;

			int idUser = res.getInt(Constants.KEY_ID_USER);

			if (isUserConnected(idUser)) {
				throw new UserAlreadyLoggedInException();
			}

			String pwd = res.getString(Constants.KEY_PASSWORD);

			if (pwd.equals(password)) {
				connectedUsers.put(idUser, username);
				return idUser;
			}

			throw new UnknownUserException();

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	int registerUser(String username, String password)
			throws UserAlreadyRegisteredException, RegisterWithoutPasswordException {
		return database.addUser(username, password);
	}

	ResultSet getUser(int idUser) {
		try {
			return database.getUser(idUser);
		} catch (UnknownUserException e) {
			e.printStackTrace();
			return null;
		}
	}

	ResultSet getUser(String username) {
		try {
			return database.getUser(username);
		} catch (UnknownUserException e) {
			e.printStackTrace();
			return null;
		}
	}

	int getPrivateConversationId(int idUser1, int idUser2) {
		return database.getPrivateConversationId(idUser1, idUser2);
	}

	UserThread getUserThread(String username) {
		return userThreads.stream().filter(x -> x.getUsername().equals(username)).findFirst().get();
	}

	/**
	 * When a client is disconneted, removes the associated username and UserThread
	 */
	boolean disconnectUser(UserThread userThread) {
		connectedUsers.remove(userThread.getIdUser());
		return userThreads.remove(userThread);
	}

	Database getDatabase() {
		return database;
	}

	boolean isUserConnected(String username) {
		return connectedUsers.values().contains(username);
	}

	boolean isUserConnected(int idUser) {
		return connectedUsers.keySet().contains(idUser);
	}

}