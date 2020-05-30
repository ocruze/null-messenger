package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.UnknownUserException;
import model.database.Database;
import util.Constants;

public class DatabaseTest {

	private Database database;

	@BeforeEach
	void setUp() throws Exception {
		database = new Database("NullMessengerTest.db");
	}

	@AfterEach
	void tearDown() throws Exception {
		database.closeConnection();

		File file = new File("NullMessengerTest.db");
		file.delete();
	}

	@Test
	void testInsertUser() {
		int count = -1;
		try {
			count = database.count("user");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// empty user table
		assertEquals(0, count);

		ResultSet res;
		try {
			// addding one user
			database.addUser("arnest", "");
			res = database.getUser(1);
			assertEquals("arnest", res.getString(Constants.KEY_USERNAME));
		} catch (SQLException | UnknownUserException e) {
			e.printStackTrace();
		}

		try {
			count = database.count("user");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(1, count);

		// addding another user
		try {
			database.addUser("leo", "");
			count = database.count("user");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(2, count);

	}

	@Test
	void testGetUsers() {
		ResultSet res = null;
		String username = "";
		try {
			database.addUser("arnest", "");
			database.addUser("leo", "");
			database.addUser("max", "");

			res = database.getUsers();
			res.next();
			username = res.getString(Constants.KEY_USERNAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals("arnest", username);

		try {
			res.next();
			username = res.getString(Constants.KEY_USERNAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals("leo", username);

		try {
			res.next();
			username = res.getString(Constants.KEY_USERNAME);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals("max", username);

	}

	@Test
	void testGetUserByUsername() {
		ResultSet res = null;
		String username = "";
		int id = -1;

		try {
			database.addUser("arnest", "");
			database.addUser("max", "");

			res = database.getUser("max");
			username = res.getString(Constants.KEY_USERNAME);
			id = res.getInt(Constants.KEY_ID_USER);

		} catch (SQLException | UnknownUserException e) {
			e.printStackTrace();
		}

		assertEquals("max", username);
		assertEquals(2, id);
	}

	@Test
	void testModifyUser() {
		ResultSet res = null;
		String username = "";

		try {
			database.addUser("arnest", "");
			res = database.getUser(1);
			username = res.getString(Constants.KEY_USERNAME);
		} catch (SQLException | UnknownUserException e) {
		}
		assertEquals("arnest", username);

		try {
			database.modifyUserUsername(1, "Arnest");
			res = database.getUser(1);

			username = res.getString(Constants.KEY_USERNAME);
		} catch (SQLException | UnknownUserException e) {
			e.printStackTrace();
		}

		assertEquals("Arnest", username);
	}

	@Test
	void testDeleteUser() {
		int count = -1;

		database.addUser("arnest", "");
		database.addUser("leo", "");
		count = database.count("user");
		assertEquals(2, count);

		database.deleteUser("arnest");
		count = database.count("user");
		assertEquals(1, count);
	}

	@Test
	void testAddConversation() throws SQLException {
		int count = -1;
		int idConv = -1;

		count = database.count("conversation");
		assertEquals(0, count);

		idConv = (int) database.addConversation();
		count = database.count("conversation");

		assertEquals(1, idConv);
		assertEquals(1, count);

	}

	@Test
	void testAddGetMessage() {
		String message = "";
		try {
			database.addUser("arnest", "");
			database.addConversation();

			database.addMessage(1, 1, "heyy");

			ResultSet res = database.getMessage(1);
			message = res.getString("content");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("heyy", message);
	}

	@Test
	void testGetConversationMessages() {
		String message = "";
		ResultSet res = null;

		try {
			database.addUser("arnest", "");
			database.addUser("leo", "");

			database.addConversation();

			database.addMessage(1, 1, "heyy leo");
			database.addMessage(1, 2, "heyy arnest");

			res = database.getConversationMessages(1);

			res.next();
			message = res.getString("content");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals("heyy leo", message);

		try {
			res.next();
			message = res.getString("content");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals("heyy arnest", message);
	}

	@Test
	void testDeleteMessage() {
		String message = "";
		try {
			database.addUser("arnest", "");
			database.addUser("leo", "");

			database.addConversation();

			database.addMessage(1, 1, "heyy leo");
			database.addMessage(1, 2, "heyy arnest");

			database.deleteMessage(1, 2);

			ResultSet res = database.getConversationMessages(1);

			res.next();
			res.next();

			message = res.getString("content");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals("Message supprimé", message);
	}
}
