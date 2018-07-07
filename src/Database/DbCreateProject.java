package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class DbCreateProject extends DatabaseConnection {

	private Statement commands;

	public static int projectID;
	public static int memberID;


	public DbCreateProject() throws ClassNotFoundException, SQLException {
		this.commands = (Statement) this.getConnection().createStatement();

	}

	/*
	 * dont touch this shit
	 */
	public void createProject(String projectName, String projectDescription, ArrayList<String> members)
			throws SQLException {
		String insert = "insert into projects (project_name, project_description, users_register_user_id) value ('"
				+ projectName + "', '" + projectDescription + "', '" + DbLoadProfileHome.User.getIdUser() + "');";

		commands.execute(insert);
		insertMembers(members);
		insertMhasP(projectName);
		DbCreateProject.projectID = getProjectID( projectName);


	}

	public int getProjectID(String projectName) throws SQLException {

		String query = "select * from projects where project_name='" + projectName + "';";

		ResultSet result = commands.executeQuery(query);
		int id = (Integer) null;
		while (result.next()) {
			id = Integer.parseInt(result.getString("project_id"));
		}
		return id;
	}

	/*
	 * the table members
	 */

	private void insertMembers(ArrayList<String> members) throws SQLException {

		String insert;
		for (int i = 0; i < members.size(); i++) {
			insert = "insert into members (nome) value ('" + members.get(i) + "');";
			commands.execute(insert);
		}
	}

	/*
	 * below the operations with the table members_has_project
	 */

	private void insertMhasP( String projectName) throws SQLException {
		
		String  queryM = "select * from members where id is not null;";

		ResultSet resultM = commands.executeQuery(queryM);
		
		String queryP = "select * from projects where project_name='" + projectName + "' where project_id is not null;";


		while (resultM.next()) {

			int idM = resultM.getInt("id");

			String insert = "insert into members_has_project (codM) values ('" + idM + "');";
			commands.execute(insert);
		}

		ResultSet resultP = commands.executeQuery(queryP);
		while (resultP.next()) {
			
			int idP = resultP.getInt("project_id");
			String insert = "insert into members_has_project (CodP) value ('"  + idP + "');" ;
			commands.execute(insert);
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}