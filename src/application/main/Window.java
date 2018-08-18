package application.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import DB.Database;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scenes.HomePageScene;
import scenes.LoginScene;
import scenes.NewProjectScene;

public class Window extends Stage {

	public static Stage mainStage;
	private FileInputStream fis;
	public  Window() throws ClassNotFoundException, SQLException, FileNotFoundException {
 		Window.mainStage = this;
 		
 		
		this.fis = new FileInputStream(new File("resources/images/icons/scrum_icon.png"));
 		Window.mainStage.getIcons().add(new Image(fis)); 		
 		Window.mainStage.setResizable(false);

 		
// 		Window.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//			@Override
//			public void handle(WindowEvent event) {
//				Database.close();
//			}
//		});
 		
 		mainStage.setScene(new HomePageScene());
// 		mainStage.setScene(new NewProjectScene());
// 		mainStage.setScene(new LoginScene());
 		this.show();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	