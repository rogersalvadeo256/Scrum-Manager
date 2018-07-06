package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class DbCreateProject extends DatabaseConnection {

	private Statement commands;

	public static int projectID;
	public String projectName;
	
	public DbCreateProject()
			throws ClassNotFoundException, SQLException {
		this.commands = (Statement) this.getConnection().createStatement();

	
		
	}

	/* 
	 * dont touch this shit
	 */
	public boolean createProject(String projectName, String projectDescription, ArrayList<String> members) throws SQLException { 
		this.projectName = projectName;
		String insert = "insert into projects (project_name, project_description, users_register_user_id) value ('" + projectName + "', '"  + 
		projectDescription + "', '" + DbLoadProfileHome.User.getIdUser() + "');";
		
		if(commands.execute(insert)) {
			
			DbCreateProject.projectID =  getProjectID();
			return true;
		}
		else return false;
	}
	
	public int getProjectID () throws SQLException { 
		
		String query = "select * from projects where project_name='" + this.projectName + "';";
		
		ResultSet result = commands.executeQuery(query);
		int id = 0;
		while(result.next()) {
			id = Integer.parseInt(result.getString("project_id"));
		}
		return id;
	}
	
	
//		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}