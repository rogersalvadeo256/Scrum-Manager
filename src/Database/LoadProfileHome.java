package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class LoadProfileHome extends DatabaseConnection {

	private String strUsername;
	DatabaseConnection connection;
	private Statement commands;

	public LoadProfileHome(String strUser) throws ClassNotFoundException, SQLException {

		this.strUsername = new String();
		this.strUsername = strUser.toString();
		this.connection = new DatabaseConnection();

		this.commands = (Statement) connection.getConnection().createStatement();
	}

	public String getStrUsername() {
		return strUsername;
	}

	public String bringData() throws SQLException {

		String query = "select * from users_register where user_name='" + getStrUsername().toString() + "';";

		ResultSet user = this.commands.executeQuery(query);

		while (user.next()) {

			String userName = user.getString("user_name");

			if (userName.equals(LoadProfileHome.this.strUsername.toString())) {
				return userName;
			}
		}
		return new String();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
