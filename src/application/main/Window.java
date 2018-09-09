package application.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import db.hibernate.factory.Database;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scenes.popoups.ForgotPasswordPOPOUP;
import scenes.popoups.FriendshipRequestPOPOUP;
import scenes.popoups.ProfileEditPOPOUP;
import scenes.scenes.LoginScene;

public class Window extends Stage {
	
	public static Stage mainStage;
	private FileInputStream fis;
	
	public Window() throws ClassNotFoundException, SQLException, FileNotFoundException {
		Window.mainStage = this;
		
		this.fis = new FileInputStream(new File("resources/images/icons/scrum_icon.png"));
		
		Window.mainStage.getIcons().add(new Image(fis));
		Window.mainStage.setResizable(false);
		//
		
		// mainStage.setScene(new HomePageScene());
		// mainStage.setScene(new NewProjectScene());
		mainStage.setScene(new LoginScene());
		
//		ForgotPasswordPOPOUP test = new ForgotPasswordPOPOUP(this);
//		test.show();
		
		// ProfileEditPOPOUP tests = new ProfileEditPOPOUP(this);
		// tests.show();
		// FriendshipRequestPOPOUP test = new FriendshipRequestPOPOUP(this);
		// test.show();
		
		// mainStage.setScene(new ProjectScene());
		
		this.show();
	}
	
}
