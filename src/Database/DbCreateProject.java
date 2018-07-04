package Database;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class DbCreateProject extends DatabaseConnection {

	private Statement commands;
	private String tableMembers = "members";
	private String tableProject = "projects";

	public DbCreateProject() throws ClassNotFoundException, SQLException {

		this.commands = (Statement) this.getConnection().createStatement();


	}

	public void insertMembers(ArrayList<String> membersName) {

		for (int i = 0; i < membersName.size(); i++) {
			String insert = "insert into " + tableMembers + " (nome) value ('" + membersName.get(i) + "');";
			try {
				commands.execute(insert);
			} catch (SQLException a) {
				a.printStackTrace();
			}
		}
	}

	public boolean insertProject(String projectName, String projectDescription, ArrayList<String> membersName) {

		int numberMembers = membersName.size();
		
		String insert = "insert into " + tableProject + " (project_name, project_description, number_members ,users_register_user_id) value ('" + projectName
				+ "', '" + projectDescription + "','" + numberMembers + "', '" + DbLoadProfileHome.User.getIdUser()
				+ "');";
		
		try {
			if(commands.execute(insert))return true;;
			
		} catch (SQLException a) {
			a.printStackTrace();
		}
		return false;
	}

}
