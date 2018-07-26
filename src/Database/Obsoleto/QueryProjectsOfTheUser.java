package Database.Obsoleto;

import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class QueryProjectsOfTheUser extends DatabaseConnection{

	
	private Statement commands;
	private String tableProject = "projects";
	
	
	public QueryProjectsOfTheUser() throws ClassNotFoundException, SQLException {

	
		this.commands = (Statement) this.getConnection().createStatement();
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
