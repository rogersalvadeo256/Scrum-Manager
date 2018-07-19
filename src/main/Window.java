package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scenes.HomePageScene;
import scenes.LoginScene;

public class Window extends Stage {

	
	public static Stage mainStage;
	FileOutputStream fis;
	public  Window() throws ClassNotFoundException, SQLException, FileNotFoundException {
 		Window.mainStage = this;
 		
 		File f = new File("/home/jefter66/java-workspace/SCRUM/resources/images/icons/scrum_icon.png");
		FileInputStream fis = new FileInputStream(f);
 		Window.mainStage.getIcons().add(new Image(fis)); 		
 		
// 		mainStage.setScene(new LoginScene());
 		mainStage.setScene(new HomePageScene());
 		this.show();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	