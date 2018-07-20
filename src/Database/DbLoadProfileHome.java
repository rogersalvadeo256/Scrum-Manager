package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DbLoadProfileHome extends DatabaseConnection {

	private String strUsername;
	private Statement commands;
	
	public DbLoadProfileHome() throws ClassNotFoundException, SQLException {

		this.strUsername = new String();

		this.commands = (Statement) this.getConnection().createStatement();
			
		DbLoadProfileHome.User.setIdUser(1);
		DbLoadProfileHome.User.setName("aaaa");
		DbLoadProfileHome.User.setUser("akfalfkj");
		}

	public String getStrUsername() {
		return strUsername;
	}

	public void bringUser() throws SQLException {

		String query = "select * from users_register where user_name='" + User.getUser().toString() + "';";

		
		ResultSet user = this.commands.executeQuery(query);


		while (user.next()) {
			String name = user.getString("name");
			String id = user.getString("user_id");

			name = user.getString("name");
			id = user.getString("user_id");
//			DbLoadProfileHome.User.setName(name.toString());
//			DbLoadProfileHome.User.setIdUser(Integer.parseInt(id.toString()));				
		}
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
