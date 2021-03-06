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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.RegisterWithoutPasswordException;
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

	public void addSomeUsers() {
		initConnectionIfClosed();

		try {
			addUser("arnest", "123456");
			addUser("leo", "123456");
			addUser("max", "123456");
			addUser("arnaud", "123456");
			addUser("hector", "123456");
			addUser("baptiste", "123456");
			addUser("guillaume", "123456");
			addUser("alex", "123456");
			addUser("arnold", "123456");
			addUser("cedric", "123456");
			addUser("ismael", "123456");
			addUser("naunau", "123456");
			addUser("leffy", "123456");
			addUser("einstein", "123456");
			addUser("albert", "123456");
			addUser("robin", "123456");
			addUser("jacques", "123456");
			addUser("jean", "123456");
			addUser("celine", "123456");
			addUser("marie", "123456");
			addUser("natasha", "123456");
			addUser("alice", "123456");
		} catch (UserAlreadyRegisteredException | RegisterWithoutPasswordException e) {
			e.printStackTrace();
		}
	}

	public void createSomeConversations() {
		initConnectionIfClosed();

		int idConv = addConversation();

		addMessage(idConv, 1, "hey max");
		addMessage(idConv, 3, "hey arnest ça va ?");
		addMessage(idConv, 1, "ouais et toi ?");
		addMessage(idConv, 3, "super merci :)");

		idConv = addConversation();
		addMessage(idConv, 3, "hey alice comment vas ?");
		addMessage(idConv, 20, "max quelle suprise!!!");
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

		String query = "SELECT * FROM user WHERE username LIKE ? ;";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);

			ResultSet res = stmt.executeQuery();

//			if (res.isClosed()) {
//				throw new UnknownUserException();
//			}

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
	 * @throws RegisterWithoutPasswordException
	 */
	public int addUser(String username, String password)
			throws UserAlreadyRegisteredException, RegisterWithoutPasswordException {
		initConnectionIfClosed();

		if (password.equals(""))
			throw new RegisterWithoutPasswordException();

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

		String query = "UPDATE user SET username = '?' WHERE idUser = ?;";

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
	 * Récupère les utilisateurs connectés
	 * 
	 * @return
	 */
	public ResultSet getConnectedUsers() {
		initConnectionIfClosed();

		String query = "SELECT * FROM connectedUser ;";
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
	 * Crée une nouvelle conversation
	 * 
	 * @return
	 */
	public int addConversation() {
		initConnectionIfClosed();

		String query = "INSERT INTO conversation VALUES (NULL) ;";
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

		String query = "SELECT (SELECT DISTINCT(idConversation) FROM message WHERE idSender = ?) AND (SELECT DISTINCT(idConversation) FROM message WHERE idSender = ?) ;";
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
			// e.printStackTrace();
			System.out.println("conversation not found, a new will be created");
			return -1;
		}
	}

	public ResultSet getUserConversations(int idUser) {
		initConnectionIfClosed();

		String query = "SELECT DISTINCT(idconversation) FROM message WHERE idSender = ? ;";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idUser);
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

		String query = "SELECT idMessage,content,idSender,idConversation,date FROM message WHERE idConversation = ?;"; // ORDER
																														// BY
																														// date
																														// ASC

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

	public List<Integer> getConversationParticipants(int idConversation) {
		initConnectionIfClosed();
		List<Integer> usersList = new ArrayList<Integer>();
		String query = "SELECT DISTINCT(idSender) FROM message WHERE idConversation = ? ;";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, idConversation);

			ResultSet res = stmt.executeQuery();
			
			while(res.next()) {
				System.out.println("voici l id conversation = "+res.getInt(1));
				usersList.add(res.getInt(1)); 
			}

			return usersList;
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
