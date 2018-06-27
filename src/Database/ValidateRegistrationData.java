package Database;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ValidateRegistrationData {

	private DatabaseConnection data;
	private Statement commands;
	private String table = "users_register";

	public ValidateRegistrationData() throws ClassNotFoundException, SQLException {
		this.data = new DatabaseConnection();
		this.commands = (Statement) data.getConnection().createStatement();
	}

	public boolean queryForExistentEmail(String email) throws SQLException {

		String databaseEmail = null;

		String query = "select * from " + table + " where email='" + email.toString() + "';";

		ResultSet queryResults = commands.executeQuery(query);

		while (queryResults.next()) {

			databaseEmail = queryResults.getString("email");

			if (databaseEmail.equals(email)) {
				return true;
			}
		}
		return false;
	}

	public boolean queryForExistentUserName(String userName) throws SQLException {


		
		String query = "select * from " + table + " where user_name='" + userName.toString() + "';";

		ResultSet queryResults = commands.executeQuery(query);

	while(queryResults.next()) {
			if (queryResults.getString("user_name").equals(userName.toString())){
				return true;
			}
		}
		return false;
	}

	public void insert(String name, String userName, String email, String password) throws SQLException {

		String insert = "insert into " + table + "(name, user_name, email, password) value ('" + name + "','" + userName
				+ "', '" + email + "', '" + password + "');";

		try {
			commands.execute(insert);
		} catch (SQLException a) {
			a.printStackTrace();

		}

	}

}
