package application.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.Close;
import scenes.scenes.LoginScene;

public class Window extends Stage {

	public static Stage mainStage;
	private FileInputStream fis;

	public Window() throws ClassNotFoundException, SQLException, IOException {
		Window.mainStage = this;

		this.fis = new FileInputStream(new File("resources/images/icons/scrum_icon.png"));

		Window.mainStage.getIcons().add(new Image(fis));
		Window.mainStage.setResizable(true);

		// mainStage.setScene(new HomePageScene());
		mainStage.setScene(new LoginScene());
		// new NewProjectScene(this);
		this.show();
		//
		// InviteFriendProjectPOPOUP i = new InviteFriendProjectPOPOUP(this);
		// i.showAndWait();
		// Profile p = new Profile();
		// p.setName("jefter");
		// p.setBio("aaakakakkaakaka afçasfçalfafçasklçfls \n sllslslsls
		// asfafasçfafjsçakfçakf \n");
		// HBFriendContent tests = new HBFriendContent(p);
		// Scene sc = new Scene(tests);
		// this.setScene(sc);
		// this.show();
		//

		// ForgotPasswordPOPOUP test = new ForgotPasswordPOPOUP(this);
		// test.show();

		// ProfileEditPOPOUP tests = new ProfileEditPOPOUP(this);
		// tests.show();
		// FriendshipRequestPOPOUP test = new FriendshipRequestPOPOUP(this);
		// test.show();

		// mainStage.setScene(new NewProjectScene());

		// this.show();

		this.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {

				if (e.isControlDown() && e.getCode() == KeyCode.W) {
				}

			}

		});

	}

}
