package scenes.popoups;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.util.SESSION;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import widgets.designComponents.HBFriendRequest;

public class FriendshipRequestPOPOUP extends Stage {
	
	private ArrayList<HBFriendRequest> hbFriendRequest;
	private VBox layout;
	private final ScrollBar sc;
	private Scene scene;
	private Window parent;
	
	/**
	 * This scene will contain the friendship requests of the user the scene have
	 * he's own stage, this because are a popoup window
	 * 
	 * @author jefter66
	 */
	public FriendshipRequestPOPOUP(Window parent) throws FileNotFoundException {
		this.layout = new VBox();
		
		this.hbFriendRequest = new ArrayList<HBFriendRequest>();
		
		this.parent= parent;
		
		 drawHbox();
		this.scene = new Scene(layout);
//		this.scene.getStylesheets().add(this.getClass().getResource("/css/FRIEND_REQUEST.css").toExternalForm());
		
		this.setScene(scene);
		
		this.sc = new ScrollBar();
		this.sc.setLayoutX(scene.getWidth() - sc.getWidth());
		this.sc.setMin(0);
		this.sc.setOrientation(Orientation.VERTICAL);
		this.sc.prefHeight(this.getHeight());
		
		this.initOwner(parent);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(600);
		this.setHeight(500);
		this.setResizable(false);
	}
	
	/**
	 * This method are for bring the friendship requests of the database and put on
	 * a component with the sender informations HBFriendshipRequest and show for
	 * the user, according to the amount of registers in the database the hbox's
	 * will be drawn on the scene
	 * 
	 * @throws FileNotFoundException
	 * @author                       jefter66
	 */
	private void drawHbox() throws FileNotFoundException {
		this.hbFriendRequest.clear();
//		for(int i=0;i<10;i++) {
//			Profile p = new Profile();
//			p.setName("GENERIC");
//			p.setBiography("JAAJAAJAJ");
//			
//			HBFriendRequest f = new HBFriendRequest(p);
//			this.hbFriendRequest.add(f);
//			this.layout.getChildren().add(this.hbFriendRequest.get(i));
//		}
		
		EntityManager em = Database.createEntityManager();
		
		ArrayList<Profile> r = new ArrayList<Profile>();
		
		
		r.clear();
		for (int i = 0; i < SESSION.getProfileLogged().getFriendshipRequest().size(); i++) {
			
			Profile p = (Profile) SESSION.getProfileLogged().getFriendshipRequest().get(i);
				
			r.add((Profile) p);
//			Query q1 = em.createQuery("from Profile where codProfile =:cod");
//			q1.setParameter("cod", p.getCod());
//			
//			if (!q1.getResultList().isEmpty()) {
//				for (int j = 0; j < q1.getResultList().size(); j++) {
//					r.add((Profile) q1.getResultList().get(j));
//				}
//				q1.getResultList().clear();
//			}
		}
		if (!r.isEmpty()) {
			for (int i = 0; i < r.size(); i++) {
				HBFriendRequest f = new HBFriendRequest(r.get(i));
				this.hbFriendRequest.add(f);
				this.layout.getChildren().add(this.hbFriendRequest.get(i));
			}
		}
		return;
	}
}














