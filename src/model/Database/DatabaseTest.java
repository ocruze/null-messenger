package model.Database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseTest {

	private Database database;

	@BeforeEach
	void setUp() throws Exception {
		database = new Database("NullMessengerTest.db");
	}

	@AfterEach
	void tearDown() throws Exception {
		File file = new File("NullMessengerTest.db");
		file.delete();
	}

	@Test
	void testInsertUser() {
		try {
			// empty user table
			assertEquals(0, database.count("user"));

			// addding one user
			database.addUser("arnest", "");
			ResultSet res = database.getUser(1);
			assertEquals("arnest", res.getString("username"));
			assertEquals(1, database.count("user"));

			// addding another user
			database.addUser("leo", "");
			assertEquals(2, database.count("user"));
		} catch (SQLException e) {
		}
	}

	@Test
	void testGetUsers() {
		try {
			database.addUser("arnest", "");
			database.addUser("leo", "");
			database.addUser("max", "");

			ResultSet res = database.getUsers();

			res.next();
			assertEquals("arnest", res.getString("username"));
			res.next();
			assertEquals("leo", res.getString("username"));
			res.next();
			assertEquals("max", res.getString("username"));

		} catch (SQLException e) {
		}
	}

	@Test
	void testGetUserByUsername() {
		try {
			database.addUser("arnest", "");
			database.addUser("max", "");

			ResultSet res = database.getUser("max");
			assertEquals("max", res.getInt("username"));
			assertEquals(2, res.getInt("idUser"));

		} catch (SQLException e) {
		}
	}

	@Test
	void testModifyUser() {
		try {
			database.addUser("arnest", "");
			ResultSet res = database.getUser(1);
			assertEquals("arnest", res.getString("username"));

			database.modifyUserUsername(1, "Arnest");

			res = database.getUser(1);
			assertEquals("Arnest", res.getString("username"));
		} catch (SQLException e) {
		}
	}

	@Test
	void testDeleteUser() {
		try {
			database.addUser("arnest", "");
			database.addUser("leo", "");
			assertEquals(2, database.count("user"));

			database.deleteUser("arnest");
			assertEquals(1, database.count("user"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddConversation() {
		try {
			database.addConversation();

		} catch (SQLException e) {
		}
	}

	@Test
	void testAddGetMessage() {
		try {
			database.addUser("arnest", "");
			database.addConversation();

			database.addMessage(1, 1, "heyy");

			ResultSet res = database.getMessage(1);
			assertEquals("heyy", res.getString("content"));
		} catch (Exception e) {

		}
	}

	@Test
	void testGetConversationMessages() {
		try {
			database.addUser("arnest", "");
			database.addUser("leo", "");

			database.addConversation();

			database.addMessage(1, 1, "heyy leo");
			database.addMessage(1, 2, "heyy arnest");

			ResultSet res = database.getConversationMessages(1);

			res.next();
			assertEquals("heyy leo", res.getString("contenu"));
			res.next();
			assertEquals("heyy arnest", res.getString("contenu"));
		} catch (SQLException e) {
		}
	}

	@Test
	void testDeleteMessage() {
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
			assertEquals("Message supprimé", res.getString("contenu"));
		} catch (SQLException e) {
		}

	}
}
