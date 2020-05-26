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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {

	private final static String FILENAME_DATABASE = "NullMessenger.db";
	private final static String FILENAME_SQL = "NullMessenger.sql";

	private String filenameDatabase;

	private static Connection conn;

	public Database() {
		this(FILENAME_DATABASE);
	}

	protected Database(String filenameDatabase) {
		this.filenameDatabase = filenameDatabase;

		try {
			conn = initConnection();
			initStructure();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection initConnection() throws SQLException {
		String url = "jdbc:sqlite:" + filenameDatabase;
		System.out.println(url);
		File tmp = new File(filenameDatabase);

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
			query = new String(Files.readAllBytes(Paths.get(FILENAME_SQL)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] queries = query.split(";");
		for (String q : queries) {
			Statement stmt = conn.createStatement();
			stmt.execute(q);
		}
	}

	int count(String tableName) throws SQLException {
		String query = "SELECT * FROM " + tableName;

		Statement stmt = conn.createStatement();
		// stmt.setString(1, tableName);

		ResultSet res;
		res = stmt.executeQuery(query);

		int i = 0;
		while (res.next()) {
			i++;
		}

		return i;

	}

	/**
	 * Récupèrer tous les utilisateurs
	 * 
	 * @return
	 * @throws SQLException
	 */
	ResultSet getUsers() throws SQLException {
		String query = "SELECT * FROM user";
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(query);
	}

	/**
	 * Récupère un utilisateur en fonction de son id
	 * 
	 * @param idUser
	 * @return
	 * @throws SQLException
	 */
	ResultSet getUser(int idUser) throws SQLException {
		String query = "SELECT * FROM user WHERE idUser = ?";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, idUser);
		return stmt.executeQuery(query);
	}

	/**
	 * Récupère un utilisateur en fonction de son pseudo
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	ResultSet getUser(String username) throws SQLException {
		String query = "SELECT * FROM user WHERE username = ?";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, username);
		return stmt.executeQuery(query);
	}

	/**
	 * Ajoute un utilisateur
	 * 
	 * @param username
	 * @param password
	 * @throws SQLException
	 */
	void addUser(String username, String password) throws SQLException {
		String query = "INSERT INTO user (username, password) VALUES (?,?);";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.execute();
	}

	/**
	 * Modifie le pseudo de l'utilisateur
	 * 
	 * @param username
	 * @param password
	 * @throws SQLException
	 */
	void modifyUserUsername(int idUser, String newUsername) throws SQLException {
		String query = "UPDATE user SET username = ? WHERE idUser = ?;";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, newUsername);
		stmt.setInt(2, idUser);
		stmt.execute();

	}

	/**
	 * Supprime un utilisateur
	 * 
	 * @param username
	 * @throws SQLException
	 */
	void deleteUser(String username) throws SQLException {
		String query = "DELETE FROM user WHERE username LIKE ?;";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, username);
		stmt.execute();
	}

	/**
	 * Crée une nouvelle conversation
	 * 
	 * @throws SQLException
	 */
	void addConversation() throws SQLException {
		String query = "INSERT INTO conversation VALUES (NULL);";
		Statement stmt = conn.createStatement();
		stmt.execute(query);
	}

	/**
	 * Ajoute un message
	 * 
	 * @param idConversation
	 * @param idSender
	 * @param content
	 * @throws SQLException
	 */
	void addMessage(int idConversation, int idSender, String content) throws SQLException {
		String query = "INSERT INTO message (content, idSender, idConversation, date) VALUES (?,?,?,?);";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, content);
		stmt.setInt(2, idSender);
		stmt.setInt(3, idConversation);

		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		stmt.setString(4, date);

		stmt.execute();
	}

	/**
	 * Récupère le message par son id
	 * 
	 * @param idMessage
	 * @return
	 * @throws SQLException
	 */
	ResultSet getMessage(int idMessage) throws SQLException {
		String query = "SELECT * FROM message WHERE idUser = ?;";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, idMessage);
		return stmt.executeQuery(query);
	}

	/**
	 * Récupère tous les messages de la conversation
	 * 
	 * @param idConversation
	 * @return
	 * @throws SQLException
	 */
	ResultSet getConversationMessages(int idConversation) throws SQLException {
		String query = "SELECT * FROM message WHERE idConversation = ? ORDER BY date ASC;";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, idConversation);
		return stmt.executeQuery(query);
	}

	/**
	 * Met à jour le contenu du message à "Message supprimé"
	 * 
	 * @param idMessage
	 * @throws SQLException
	 */
	void deleteMessage(int idMessage) throws SQLException {
		String query = "UPDATE message SET contenu = ? WHERE idMessage = ?;";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, "Message supprimé");
		stmt.setInt(2, idMessage);
		stmt.execute();
	}

	/**
	 * Met à jour le contenu du message à "Message supprimé"
	 * 
	 * @param idConversation
	 * @param idSender
	 * @throws SQLException
	 */
	void deleteMessage(int idConversation, int idSender) throws SQLException {
		String query = "UPDATE message SET contenu = ? WHERE idSender = ? AND idConversation = ?;";

		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, "Message supprimé");
		stmt.setInt(2, idSender);
		stmt.setInt(3, idConversation);
		stmt.execute();
	}
}