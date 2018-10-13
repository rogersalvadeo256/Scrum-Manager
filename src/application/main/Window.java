package application.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_REGISTRATION;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import listeners.Close;
import statics.SERIALIZATION;
import statics.SERIALIZATION.FileType;
import statics.SESSION;
import view.scenes.HomePageScene;
import view.scenes.LoginScene;

public class Window extends Stage {
	
	public static Stage mainStage;
	private FileInputStream fis;
	
	public Window() throws ClassNotFoundException, SQLException, IOException {
		Window.mainStage = this;
		
		this.fis = new FileInputStream(new File("resources/images/icons/scrum_icon.png"));
		
		Window.mainStage.getIcons().add(new Image(fis));
		Window.mainStage.setResizable(true);
		
		if (SERIALIZATION.fileExists(FileType.SESSION)) {
			EntityManager em = Database.createEntityManager();
			
			Query q = em.createQuery("FROM USER_REGISTRATION");
			if (!q.getResultList().isEmpty()) {
				
				USER_REGISTRATION u = (USER_REGISTRATION) SERIALIZATION.undoSerialization(FileType.SESSION);
				
				for (int i = 0; i < q.getResultList().size(); i++) {
					USER_REGISTRATION r = (USER_REGISTRATION) q.getResultList().get(i);
					if (u.getCodUser() == r.getCodUser()) {
						SESSION.START_SESSION(u);
					}
				}
				setScene(new HomePageScene());
				this.show();
				return;
			}
		}
		this.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				
				if (e.isControlDown() && e.getCode() == KeyCode.W) {
					
				}
			}
		});
		Window.mainStage.setOnCloseRequest(e -> {
			new Close(Window.mainStage);
		});
		mainStage.setScene(new LoginScene());
		this.show();
	}
}
