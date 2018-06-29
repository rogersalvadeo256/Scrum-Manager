package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DatabaseConnection {

	private Connection connection;
	private java.sql.Statement commands;

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

	public boolean enterLogin(TextField userName, PasswordField password) throws SQLException {
		this.commands = getConnection().createStatement();

		String user_name = null;
		String passWord = null;

		String query = "select * from users_register where user_name='" + userName.getText().toString() + "' && password='"
				+ password.getText().toString() + "'";

		ResultSet confirmation = commands.executeQuery(query);

		while (confirmation.next()) {

			user_name = confirmation.getString("user_name");
			passWord = confirmation.getString("password");

			if (userName.getText().equals(user_name.toString()) && password.getText().equals(passWord.toString())) {
				return true;
			}
		}
		return false;
	}
}
