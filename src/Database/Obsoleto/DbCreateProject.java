package Database.Obsoleto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class DbCreateProject extends DatabaseConnection {

	private Statement commands;

	public static int projectID;
	public static int memberID;

	ArrayList<Integer> idM;
	int idP;

	public DbCreateProject() throws ClassNotFoundException, SQLException {
		this.commands = (Statement) this.getConnection().createStatement();
		this.idM = new ArrayList<Integer>();
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
//		insertMhasP(projectName);
		DbCreateProject.projectID = getProjectID(projectName);

	}

	

	
	public int getProjectID(String projectName) throws SQLException {

		String query = "select * from projects where project_name='" + projectName + "';";

		ResultSet result = commands.executeQuery(query);
		int id = 0;
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
			insert = "insert into members (nome) value ('" + DbLoadProfileHome.User.getName() + "');";
			
//			insert = "insert into members (nome) value ('" + members.get(i) + "');";
			commands.execute(insert);
		}
	}

	/*
	 * below the operations with the table members_has_project
	 */

	private void insertMhasP(String projectName) throws SQLException {

		String queryM = "select * from members where id='" + memberID + "';";

		String queryP = "select * from projects where project_name='" + projectName + "';";

		ResultSet resultM = commands.executeQuery(queryM);
		while (resultM.next()) {
		
			this.idM.add(Integer.parseInt(resultM.getString("id")));
	
		}
		
		
		ResultSet resultP = commands.executeQuery(queryP);
		while (resultP.next()) {
			idP = Integer.parseInt(resultP.getString("project_id"));
		}
		
		String insert = new String();
		for (int i = 0; i < this.idM.size(); i++) {
			insert = "insert into members_has_project (codM,CodP) values('" + DbLoadProfileHome.User.getIdUser() + "', '"
					+  idP  + "');";
		}
		commands.execute(insert);

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}