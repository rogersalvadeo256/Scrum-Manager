package Main;
import java.sql.SQLException;

import Scenes.LoginScene;
import javafx.stage.Stage;

public class Window extends Stage {

	
	public static Stage mainStage;
	
 	public  Window() throws ClassNotFoundException, SQLException {
 		
 		Window.mainStage = this;
 		mainStage.setScene(new LoginScene());
		this.show();
	}
}