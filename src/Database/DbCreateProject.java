package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DbCreateProject extends DatabaseConnection {

	private Statement commands;
	private String tableProject = "projects";

	public DbCreateProject() throws ClassNotFoundException, SQLException {
		this.commands = (Statement) this.getConnection().createStatement();
	}


	/*
	 * insert in the table members_has_project
	 */
	public void insertIDMemberHasProject() throws SQLException {

		String insert = "insert into members_has_project  (codM) value ('" + DbLoadProfileHome.User.getIdUser() + "');";

		commands.execute(insert);

	}

	public void insertIDProjectMemberHasProject() throws SQLException {

		String query = "select * from " + tableProject + " where project_id ";
		
		
		ResultSet teste  = commands.executeQuery(query);
	
		int id =  Integer.parseInt(teste.getString("project_id"));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	/*
	 * insert project
	 */
	public boolean insertProject(String projectName, String projectDescription) throws SQLException {

		String insert = "insert into " + tableProject
				+ " (project_name, project_description ,users_register_user_id) value ('" + projectName + "', '"
				+ projectDescription + "','" + "', '" + DbLoadProfileHome.User.getIdUser() + "');";

		if (commands.execute(insert))return true;

		else return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
