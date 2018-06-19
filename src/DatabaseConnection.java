
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public boolean validateLogin(String userName, String password) throws SQLException {
		this.commands = getConnection().createStatement();

		String user_name = null;
		String passWord = null;

		String query = "select * from users_register where user_name='" + userName + "' && password='" + password + "'";

		ResultSet confirmation = commands.executeQuery(query);

		while (confirmation.next()) {

			user_name = confirmation.getString("user_name");
			passWord = confirmation.getString("password");

			if (userName.equals(user_name) && password.equals(passWord)) {
				return true;
			}
		}
		return false;
	}

}
