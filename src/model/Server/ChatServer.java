package model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.User;

public class ChatServer {
	private int port;
	private Connection database;
	private Set<User> users = new HashSet<>();
	private Set<UserThread> userThreads = new HashSet<>();

	public ChatServer(int port) {
		this.port = port;
		this.database = this.createDatabase();
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

	Connection createDatabase() {
		String url = "jdbc:sqlite:./db.sql";
		System.out.println(url);
		File tmp = new File(url);
		if (tmp.exists()) {
			System.out.println("File exists !");
			try {
				return DriverManager.getConnection(url);
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		System.out.println("File does not exists !");
		try {
			return DriverManager.getConnection(url);
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
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
		return userThreads.stream().filter(x -> x.getUser().equals(user)).findFirst().get();
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