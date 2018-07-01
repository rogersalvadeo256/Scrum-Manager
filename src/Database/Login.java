package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login extends DatabaseConnection {

	private java.sql.Statement commands;

	public Login() throws ClassNotFoundException {
		
	}

	public boolean enterLogin(TextField userName, PasswordField password) throws SQLException {
		this.commands = getConnection().createStatement();

		String user_name = null;
		String passWord = null;

		String query = "select * from users_register where user_name='" + userName.getText().toString()
				+ "' && password='" + password.getText().toString() + "';";

		ResultSet confirmation = commands.executeQuery(query);

		while (confirmation.next()) {

			user_name = confirmation.getString("user_name");
			passWord = confirmation.getString("password");

			if (userName.getText().equals(user_name.toString()) && password.getText().equals(passWord.toString())) {
				return true;
			}
		}
		return false;
	}
}
