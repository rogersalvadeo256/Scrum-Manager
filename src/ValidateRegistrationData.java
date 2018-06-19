import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ValidateRegistrationData {

	private DatabaseConnection data;
	private java.sql.Statement commands;
	private ValidationMethods message;
	private String table = "users_register";

	public ValidateRegistrationData() throws ClassNotFoundException, SQLException {
		this.data = new DatabaseConnection();
		this.commands = data.getConnection().createStatement();
	}

	public boolean queryForExistentEmail(String email) throws SQLException {

		String databaseEmail = null;

		String query = "select * from" + table + "where email='" + email.toString() + "';";

		ResultSet queryResults = commands.executeQuery(query);

		while (queryResults.next()) {

			databaseEmail = queryResults.getString("email");

			if (databaseEmail.equals(email)) {
				message.emailAlreadyRegistered();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean queryForExistentUserName(String userName) throws SQLException {

		String databaseUserName = null;

		String query = "select from " + table + " where user_name='" + userName.toString() + "';";

		ResultSet queryResults = commands.executeQuery(query);

		while (queryResults.next()) {

			databaseUserName = queryResults.getString("user_name");
			if (databaseUserName.equals(userName)) {
				message.userNameAlearyTaken();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	
	
	public void insert(String name, String userName, String email, String password) throws SQLException { 
		
		String insert = "insert into "+ table + "(name,user_name, email, password) values "+ name+ ", "+ userName +", "
				+ email+", "+ password+"';";
		
		try {
		commands.execute(insert);
		} catch (SQLException a) { 
			a.printStackTrace();
			
		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
















