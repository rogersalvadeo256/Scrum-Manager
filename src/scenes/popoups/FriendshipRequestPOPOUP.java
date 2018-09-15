package scenes.popoups;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.util.SESSION;
import friendship.FriendshipRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	int count = 0;
	/**
	 * This scene will contain the friendship requests of the user the scene have
	 * he's own stage, this because are a popoup window
	 * 
	 * @author jefter66
	 */
	public FriendshipRequestPOPOUP(Window parent) throws FileNotFoundException {
		this.layout = new VBox();
		
		
		this.parent= parent;
		
		this.hbFriendRequest = new  ArrayList<HBFriendRequest>();
		
		 this.drawHbox();
		 
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
		
		ArrayList<Profile> r = new ArrayList<Profile>();
		r.clear();
		SESSION.UPDATE_SESSION();
		for (int i = 0; i < SESSION.getProfileLogged().getFriendshipRequests().size(); i++) {
			
			Profile p = (Profile) SESSION.getProfileLogged().getFriendshipRequests().get(i);
			r.add((Profile) p);
		}
		if (!r.isEmpty()) {
			for (int i = 0; i < r.size(); i++) {
				count = i;
				HBFriendRequest f = new HBFriendRequest(r.get(i));
				FriendshipRequest fr = new FriendshipRequest();
				this.hbFriendRequest.add(f);
				this.hbFriendRequest.get(i).setEventAccept(e ->{ 
					fr.acceptRequest(r.get(FriendshipRequestPOPOUP.this.count));
				});
				this.hbFriendRequest.get(i).setEventRefuse(e ->{ 
					fr.refuseRequest(r.get(FriendshipRequestPOPOUP.this.count));
				});
				this.layout.getChildren().add(this.hbFriendRequest.get(i));
			}
		}
		return;
	}
}
class Componente extends VBox {
	
	private Button btn;

	public Componente() {
		this.btn = new Button("ewewe");
		this.getChildren().add(btn);
	}
	
	public void setEventHandler(EventHandler<ActionEvent> e) {
		this.btn.setOnAction(e);
	}
}
















