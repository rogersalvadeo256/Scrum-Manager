package main;
import java.sql.SQLException;

import Database.DbLoadProfileHome;
import javafx.stage.Stage;
import scenes.HomePageScene;
import scenes.LoginScene;
import scenes.newProjectScene;

public class Window extends Stage {

	
	public static Stage mainStage;
	
 	public  Window() throws ClassNotFoundException, SQLException {
 		

 		
 		Window.mainStage = this;
// 		mainStage.setScene(new newProjectScene());
 		mainStage.setScene(new LoginScene());
// 		Window.mainStage.setScene(new HomePageScene(new DbLoadProfileHome("jefter66")));
 		this.show();
	}
}	