package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.RegisterWithoutPasswordException;
import exceptions.UnknownUserException;
import exceptions.UserAlreadyRegisteredException;
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
			database.addUser("arnest", "test");
			res = database.getUser(1);
			assertEquals("arnest", res.getString(Constants.KEY_USERNAME));
		} catch (SQLException | UnknownUserException | UserAlreadyRegisteredException
				| RegisterWithoutPasswordException e) {
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
			database.addUser("leo", "test");
			count = database.count("user");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(2, count);

	}

	@Test
	void testInsertUserAlreadyRegistered() {
		try {
			database.addUser("arnest", "test");
		} catch (UserAlreadyRegisteredException | RegisterWithoutPasswordException e) {
			e.printStackTrace();
		}
		assertThrows(UserAlreadyRegisteredException.class, () -> {
			database.addUser("arnest", "test");
		});
	}

	@Test
	void testRegisterWithoutPassword() {
		assertThrows(RegisterWithoutPasswordException.class, () -> {
			database.addUser("arnest", "");
		});

	}

//	@Test
//	void testAddConnectedUser() {
//		try {
//			database.addUser("arnest", "test");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		assertTrue(database.addConnectedUser(1));
//	}

//	@Test
//	void testDeletedConnectedUser() {
//		try {
//			database.addUser("arnest", "test");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		database.addConnectedUser(1);
//		assertTrue(database.deleteConnectedUser(1));
//	}

	@Test
	void testGetUsers() {
		ResultSet res = null;
		String username = "";
		try {
			database.addUser("arnest", "test");
			database.addUser("leo", "test");
			database.addUser("max", "test");

			res = database.getUsers();
			res.next();
			username = res.getString(Constants.KEY_USERNAME);
		} catch (SQLException | UserAlreadyRegisteredException | RegisterWithoutPasswordException e) {
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
			database.addUser("arnest", "test");
			database.addUser("max", "test");

			res = database.getUser("max");
			username = res.getString(Constants.KEY_USERNAME);
			id = res.getInt(Constants.KEY_ID_USER);

		} catch (SQLException | UnknownUserException | UserAlreadyRegisteredException
				| RegisterWithoutPasswordException e) {
			e.printStackTrace();
		}

		assertEquals("max", username);
		assertEquals(2, id);
	}

//	@Test
//	void testModifyUser() {
//		ResultSet res = null;
//		String username = "";
//
//		try {
//			database.addUser("arnest", "test");
//			res = database.getUser(1);
//			username = res.getString(Constants.KEY_USERNAME);
//		} catch (SQLException | UnknownUserException | UserAlreadyRegisteredException
//				| RegisterWithoutPasswordException e) {
//		}
//		assertEquals("arnest", username);
//
//		try {
//			database.modifyUserUsername(1, "Arnest");
//			res = database.getUser(1);
//
//			username = res.getString(Constants.KEY_USERNAME);
//		} catch (SQLException | UnknownUserException e) {
//			e.printStackTrace();
//		}
//
//		assertEquals("Arnest", username);
//	}

	@Test
	void testDeleteUser() {
		int count = -1;

		try {
			database.addUser("arnest", "test");
			database.addUser("leo", "test");
		} catch (UserAlreadyRegisteredException | RegisterWithoutPasswordException e) {
			e.printStackTrace();
		}
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
			database.addUser("arnest", "test");
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
			database.addUser("arnest", "test");
			database.addUser("leo", "test");

			database.addConversation();

			database.addMessage(1, 1, "heyy leo");
			database.addMessage(1, 2, "heyy arnest");

			res = database.getConversationMessages(1);

			res.next();
			message = res.getString("content");
		} catch (SQLException | UserAlreadyRegisteredException | RegisterWithoutPasswordException e) {
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
			database.addUser("arnest", "test");
			database.addUser("leo", "test");

			database.addConversation();

			database.addMessage(1, 1, "heyy leo");
			database.addMessage(1, 2, "heyy arnest");

			database.deleteMessage(1, 2);

			ResultSet res = database.getConversationMessages(1);

			res.next();
			res.next();

			message = res.getString("content");
		} catch (SQLException | UserAlreadyRegisteredException | RegisterWithoutPasswordException e) {
			e.printStackTrace();
		}
		assertEquals("Message supprimé", message);
	}
}
