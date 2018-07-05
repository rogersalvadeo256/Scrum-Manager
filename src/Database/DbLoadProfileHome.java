package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DbLoadProfileHome extends DatabaseConnection {

	private String strUsername;
	private Statement commands;
	private String table = "users_register" ;
	
	public DbLoadProfileHome(String strUser) throws ClassNotFoundException, SQLException {

		this.strUsername = new String();
		this.strUsername = strUser.toString();

		this.commands = (Statement) this.getConnection().createStatement();
	}

	public String getStrUsername() {
		return strUsername;
	}

	public String bringUser() throws SQLException {

		String query = "select from " + table + " where user_name='" + getStrUsername().toString() + "';";

		ResultSet user = this.commands.executeQuery(query);

		while (user.next()) {

			String userName = user.getString("user_name");
			String name = user.getString("name");
			/*
			 * for accessing the id of the user in another part of the project
			 */
			DbLoadProfileHome.User.setIdUser(Integer.parseInt(user.getString("user_id")));
			
			if (userName.equals(DbLoadProfileHome.this.strUsername.toString())) {
				
				/*
				 * setting the user name 
				 */
				DbLoadProfileHome.User.setUser(userName.toString());
				DbLoadProfileHome.User.setName(name.toString());
				return userName;
			}
		}
		return new String();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class User {
		
		private static int id_user;
	
		private static String user;
		private static String name;
		
		public static int getIdUser() { 
			return id_user;
		}
		public static void setIdUser(int id) {
			User.id_user = id;
		}
		public static String getUser() {
			return user;
		}
		public static void setUser(String user) {
			User.user = user;
		}
		public static String getName() {
			return name;
		}
		public static void setName(String name) {
			User.name = name;
		}
	
	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
