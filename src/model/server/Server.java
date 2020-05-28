package model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.Database.Database;
import model.Entity.User;
import model.Exception.UserInexistantException;

public class ChatServer {
	private int port;
	Database database;
	private Set<User> users = new HashSet<>();
	private Set<UserThread> userThreads = new HashSet<>();

	public ChatServer(int port) {
		this.port = port;
		this.database = new Database();
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
	void broadcast(String message, UserThread excludeUser) {
		for (UserThread aUser : userThreads) {
			if (aUser != excludeUser) {
				aUser.sendMessage(message);
			}
		}
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UserInexistantException
	 */
	int loginUser(String username, String password) throws UserInexistantException {
		ResultSet res = null;
		try {
			res = database.getUser(username);

			String pwd = res.getString("password");

			if (pwd.equals(password)) {
				return res.getInt("idUser");
			}

			throw new UserInexistantException();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	int registerUser(String username, String password) {
		try {
			return database.addUser(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	User getUser(String username) {
		return users.stream().filter(x -> x.getUsername().equals(username)).findFirst().get();
	}

	User addUser(String username) {
		User newUser = new User(username);
		boolean added = users.add(newUser);
		return added ? newUser : null;
	}

	UserThread getUserThread(User user) {
		// TODO
		// return userThreads.stream().filter(x ->
		// x.getUser().equals(user)).findFirst().get();
		return null;
	}

	UserThread getUserThread(String username) {
		return getUserThread(getUser(username));
	}

	/**
	 * When a client is disconneted, removes the associated username and UserThread
	 */
	boolean disconnectUser(User user) {
		return userThreads.remove(getUserThread(user));
	}

	/**
	 * Returns true if there are other users connected (not count the currently
	 * connected user)
	 */
	boolean hasUsers() {
		return !this.users.isEmpty();
	}

	public Set<User> getUsers() {
		return users;
	}
}