package model.database;

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

import exceptions.UnknownUserException;
import exceptions.UserAlreadyRegisteredException;
import util.Constants;

public class Database {

	private final static String FILENAME_DATABASE = "NullMessenger.db";
	private final static String FILENAME_SQL = "NullMessenger.sql";

	private String filenameDatabase;

	private static Connection conn;

	public Database() {
		this(FILENAME_DATABASE);
	}

	public Database(String filenameDatabase) {
		this.filenameDatabase = filenameDatabase;

		try {
			conn = initConnection();
			initStructure();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection initConnection() {
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

	public boolean closeConnection() {
		boolean isClosed = false;

		if (conn != null)
			try {
				conn.close();
				isClosed = conn.isClosed();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return isClosed;
	}

	private void initConnectionIfClosed() {
		if (conn == null)
			initConnection();
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
		initConnectionIfClosed();

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

	/**
	 * Retourne le nombre d'éléments dans une table
	 * 
	 * @param tableName
	 * @return
	 */
	public int count(String tableName) {
		initConnectionIfClosed();

		String query = "SELECT * FROM " + tableName;

		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet res;
			res = stmt.executeQuery(query);

			int i = 0;
			while (res.next()) {
				i++;
			}

			return i;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * Vide une table
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean emptyTable(String tableName) {
		initConnectionIfClosed();

		String query = "DELETE FROM " + tableName;
		int count = -1;

		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);

			count = count("connectedUser");
			return count == 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Retourne l'id du dernier élément ajouté dans une table
	 * 
	 * @return
	 */
	public int getLastInsertId() {
		initConnectionIfClosed();

		String query = "SELECT last_insert_rowid();";

		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			return res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Récupèrer tous les utilisateurs
	 * 
	 * @return
	 */
	public ResultSet getUsers() {
		initConnectionIfClosed();

		String query = "SELECT * FROM user";
		try {
			Statement stmt = conn.createStatement();

			ResultSet res = stmt.executeQuery(query);
			if (res.isClosed()) {
				return null;
			}

			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Récupère un utilisateur en fonction de son id
	 * 
	 * @param idUser
	 * @return
	 * @throws UnknownUserException
	 */
	public ResultSet getUser(int idUser) throws UnknownUserException {
		initConnectionIfClosed();

		String query = "SELECT * FROM user WHERE idUser = ?";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idUser);
			ResultSet res = stmt.executeQuery();
			if (res.isClosed()) {
				throw new UnknownUserException();
			}

			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Récupère un utilisateur en fonction de son pseudo
	 * 
	 * @param username
	 * @return
	 * @throws UnknownUserException
	 */
	public ResultSet getUser(String username) throws UnknownUserException {
		initConnectionIfClosed();

		String query = "SELECT * FROM user WHERE username LIKE ?";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);

			ResultSet res = stmt.executeQuery();

			if (res.isClosed()) {
				throw new UnknownUserException();
			}

			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Ajoute un utilisateur
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UserAlreadyRegisteredException
	 */
	public int addUser(String username, String password) throws UserAlreadyRegisteredException {
		initConnectionIfClosed();

		String query = "INSERT INTO user (username, password) VALUES (?,?);";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.execute();
			return getLastInsertId();
		} catch (SQLException e) {
			if (e.getErrorCode() == 19)
				throw new UserAlreadyRegisteredException();
			return -1;
		}
	}

	/**
	 * Modifie le pseudo de l'utilisateur
	 * 
	 * @param username
	 * @param password
	 */
	public void modifyUserUsername(int idUser, String newUsername) {
		initConnectionIfClosed();

		String query = "UPDATE user SET username = ? WHERE idUser = ?;";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, newUsername);
			stmt.setInt(2, idUser);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Supprime un utilisateur
	 * 
	 * @param username
	 */
	public void deleteUser(String username) {
		initConnectionIfClosed();

		String query = "DELETE FROM user WHERE username LIKE ?;";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute l'utilisateur dans la table des utilisateurs connectés
	 * 
	 * @param idUser
	 * @return
	 */
	public boolean addConnectedUser(int idUser) {
		initConnectionIfClosed();

		String query = "INSERT INTO connectedUser VALUES (?);";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idUser);
			stmt.execute();

			return isUserConnected(idUser);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Renvoie vrai si l'utilisateur est connecté
	 * 
	 * @param idUser
	 * @return
	 */
	public boolean isUserConnected(int idUser) {
		int count = count("(SELECT * FROM connectedUser WHERE idUser = " + idUser + ")");
		return count == 1;
	}

	/**
	 * Supprime l'utilisateur de la table des utilisateurs connectés
	 * 
	 * @param idUser
	 * @return
	 */
	public boolean deleteConnectedUser(int idUser) {
		initConnectionIfClosed();

		String query = "DELETE FROM connectedUser WHERE idUser = ?;";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idUser);
			stmt.execute();

			return !isUserConnected(idUser);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Crée une nouvelle conversation
	 * 
	 * @return
	 */
	public int addConversation() {
		initConnectionIfClosed();

		String query = "INSERT INTO conversation VALUES (NULL);";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.execute(query);
			return getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	public int getPrivateConversationId(int idUser1, int idUser2) {
		initConnectionIfClosed();

		String query = "SELECT (SELECT DISTINCT(idconversation) FROM message WHERE idSender = ?) AND (SELECT DISTINCT(idconversation) FROM message WHERE idSender = ?)";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idUser1);
			stmt.setInt(2, idUser1);
			ResultSet res = stmt.executeQuery();

			if (res.isClosed()) {
				return -1;
			}

			return res.getInt(Constants.KEY_ID_CONVERSATION);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * Ajoute un message
	 * 
	 * @param idConversation
	 * @param idSender
	 * @param content
	 * @return
	 */
	public int addMessage(int idConversation, int idSender, String content) {
		initConnectionIfClosed();

		String query = "INSERT INTO message (content, idSender, idConversation, date) VALUES (?,?,?,?);";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, content);
			stmt.setInt(2, idSender);
			stmt.setInt(3, idConversation);

			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());

			stmt.setString(4, date);

			stmt.execute();

			return getLastInsertId();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * Récupère le message par son id
	 * 
	 * @param idMessage
	 * @return
	 */
	public ResultSet getMessage(int idMessage) {
		initConnectionIfClosed();

		String query = "SELECT * FROM message WHERE idMessage = ?;";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idMessage);

			ResultSet res = stmt.executeQuery();

			if (res.isClosed()) {
				return null;
			}

			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Récupère tous les messages de la conversation
	 * 
	 * @param idConversation
	 * @return
	 */
	public ResultSet getConversationMessages(int idConversation) {
		initConnectionIfClosed();

		String query = "SELECT * FROM message WHERE idConversation = ? ORDER BY date ASC;";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idConversation);

			ResultSet res = stmt.executeQuery();

			if (res.isClosed()) {
				return null;
			}

			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Met à jour le contenu du message à "Message supprimé"
	 * 
	 * @param idMessage
	 */
	public void deleteMessage(int idMessage) {
		initConnectionIfClosed();

		String query = "UPDATE message SET content = ? WHERE idMessage = ?;";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, Constants.VALUE_DELETED_MESSAGE);
			stmt.setInt(2, idMessage);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Met à jour le contenu du message à "Message supprimé"
	 * 
	 * @param idConversation
	 * @param idSender
	 */
	public void deleteMessage(int idConversation, int idSender) {
		initConnectionIfClosed();

		String query = "UPDATE message SET content = ? WHERE idSender = ? AND idConversation = ?;";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, Constants.VALUE_DELETED_MESSAGE);
			stmt.setInt(2, idSender);
			stmt.setInt(3, idConversation);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
