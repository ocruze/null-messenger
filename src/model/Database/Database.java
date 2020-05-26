package model.Database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	final static String DATABASE_FILENAME = "NullMessenger.db";
	final static String SQL_FILENAME = "NullMessenger.sql";

	private static Connection conn;
	private static boolean hasData = false;

	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILENAME);
		init();
	}

	private void init() throws SQLException {
		if (!hasData) {
			hasData = true;

			String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='user'";
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);

			if (!res.next()) {
				System.out.println("Building the user table");
				createSchema();
			}
		}
	}

	private void createSchema() throws SQLException {
		String query = "";

		try {
			query = new String(Files.readAllBytes(Paths.get(SQL_FILENAME)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] queries = query.split(";");
		for (String q : queries) {
			Statement stmt = conn.createStatement();
			stmt.execute(q);
		}
	}

	ResultSet getUsers() throws SQLException, ClassNotFoundException {
		if (conn == null)
			getConnection();

		String query = "SELECT * FROM user";
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(query);
	}

	/**
	 * Ajoute un utilisateur
	 * 
	 * @param username
	 * @param password
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	void addUser(String username, String password) throws SQLException, ClassNotFoundException {
		if (conn == null)
			getConnection();

		String query = "INSERT INTO user (username, password) VALUES (?,?);";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.execute();
	}

	/**
	 * Supprime un utilisateur
	 * 
	 * @param username
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	void deleteUser(String username) throws SQLException, ClassNotFoundException {
		if (conn == null)
			getConnection();

		// TODO
	}

	/**
	 * Ajoute un message
	 * 
	 * @param idConversation
	 * @param idSender
	 * @param content
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	void addMessage(int idConversation, int idSender, String content) throws SQLException, ClassNotFoundException {
		if (conn == null)
			getConnection();

		// TODO
	}

	/**
	 * Met à jour le contenu du message à "Message supprimé"
	 * 
	 * @param idConversation
	 * @param idSender
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	void deleteMessage(int idConversation, int idSender) throws SQLException, ClassNotFoundException {
		if (conn == null)
			getConnection();

		// TODO
	}
}
