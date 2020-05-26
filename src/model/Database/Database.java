package model.Database;

import java.io.File;
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

	public Database() {
		try {
			conn = initConnection();
			initStructure();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection initConnection() throws SQLException {
		String url = "jdbc:sqlite:" + DATABASE_FILENAME;
		System.out.println(url);
		File tmp = new File(DATABASE_FILENAME);

		if (tmp.exists()) {
			System.out.println("File exists!");
		} else {
			System.out.println("File does not exists!");
		}

		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private void initStructure() throws SQLException {
		String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='user'";
		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(query);

		if (!res.next()) {
			createSchema();
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
		String query = "DELETE FROM user WHERE username LIKE ?;";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, username);
		stmt.execute();
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
		String query = "INSERT INTO message (content, idSender, idConversation) VALUES (?,?,?);";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, content);
		stmt.setInt(2, idSender);
		stmt.setInt(3, idConversation);
		stmt.execute();
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
		String query = "DELETE FROM message WHERE idSender LIKE ? AND idConversation LIKE ?;";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, idSender);
		stmt.setInt(2, idConversation);
		stmt.execute();

	}
}
