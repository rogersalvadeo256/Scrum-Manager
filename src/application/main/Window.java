package application.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import listeners.Close;
import view.scenes.LoginScene;
import view.scenes.ProjectScene;
import widgets.designComponents.projectContents.CreateStoryComponent;

public class Window extends Stage {
	
	public static Stage mainStage;
	private FileInputStream fis;
	
	public Window() throws ClassNotFoundException, SQLException, IOException {
		Window.mainStage = this;
		
		this.fis = new FileInputStream(new File("resources/images/icons/scrum_icon.png"));
		
		Window.mainStage.getIcons().add(new Image(fis));
		Window.mainStage.setResizable(true);
		
//		Window.mainStage.setScene(new ProjectScene());

		new CreateStoryComponent();
		
//		if (SERIALIZATION.fileExists(FileType.SESSION)) {
//			EntityManager em = Database.createEntityManager();
//			
//			Query q = em.createQuery("FROM USER_REGISTRATION");
//			if (!q.getResultList().isEmpty()) {
//				
//				USER_REGISTRATION u = (USER_REGISTRATION) SERIALIZATION.undoSerialization(FileType.SESSION);
//				
//				for (int i = 0; i < q.getResultList().size(); i++) {
//					USER_REGISTRATION r = (USER_REGISTRATION) q.getResultList().get(i);
//					if (u.getCodUser() == r.getCodUser()) {
//						SESSION.START_SESSION(u);
//					}
//				}
//				setScene(new HomePageScene());
//				this.show();
//				return;
//			}
//		}
		Window.mainStage.setOnCloseRequest(e -> {
			new Close(Window.mainStage);
		});
//		mainStage.setScene(new LoginScene());
		this.show();
	}
}
















