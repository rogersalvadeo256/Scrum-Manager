import java.sql.SQLException;

import javafx.stage.Stage;

public class Window extends Stage {

 	private LoginScreen login;
 	public  Window() throws ClassNotFoundException, SQLException {
		
		// depois colocar um preloader screen
 		
		this.login = new LoginScreen(this);
		this.setScene(login.loginScene());
		this.setTitle("Login");
		this.show();
		
	}
}