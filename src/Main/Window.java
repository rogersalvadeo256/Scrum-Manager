package Main;
import java.sql.SQLException;

import Scenes.LoginScreen;
import javafx.stage.Stage;

public class Window extends Stage {

	
	public static Stage janela;
	
 	public  Window() throws ClassNotFoundException, SQLException {
 		
 		Window.janela = this;
 		janela.setScene(new LoginScreen());
		this.show();
	}
}