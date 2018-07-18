package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private Connection connection;

	public DatabaseConnection() throws ClassNotFoundException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:mysql://localhost/Scrum?user=teste&password=1234");
		return connection;
	}

}
