package model.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DatabaseMainTest {
	public static void main(String[] args) {
		Database database;

		try {
			database = new Database();

//			showResultSet(database.getUsers());
//
//			database.addUser("arnest", "456464");
//			database.addUser("leo", "5465464");

			showResultSet(database.getUsers());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	static void showResultSet(ResultSet resultSet) throws SQLException {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (resultSet.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnValue = resultSet.getString(i);
				System.out.print(columnValue + " " + rsmd.getColumnName(i));
			}
			System.out.println("");
		}
	}
}
