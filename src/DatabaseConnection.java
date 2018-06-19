
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
	
		String name = null;
		String pass = null;
		
		
		String query = "select * from users_register where user_name='" + userName + "' && password='" + password + "'";
			
		ResultSet a = commands.executeQuery(query);
		
		while(a.next()) { 
			
			name = a.getString("user_name");
			pass = a.getString("password");
		}
	
		if(userName.equals(name) && password.equals(pass)) { 
			
			return true;
		} else { 
			return false;
		}
		
		
	}

}
