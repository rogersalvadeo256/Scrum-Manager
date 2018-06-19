import javafx.stage.Stage;

public class Window extends Stage {

	private LoginScreen login;
	
	
	public Window() throws ClassNotFoundException {
		
		this.login = new LoginScreen();
		this.setScene(login.loginScene());
		this.setTitle("Login");
		this.show();
		
	}
}