package scenes.popoups;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import db.pojos.Profile;
import db.util.GENERAL_STORE;
import db.util.SESSION;
import friendship.FriendshipRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import widgets.designComponents.HBFriendRequest;

public class FriendshipRequestPOPOUP extends StandartLayoutPOPOUP {

	private final ScrollBar sc;
	private ArrayList<HBFriendRequest> requestList;

	/**
	 * This scene will contain the friendship requests of the user the scene have
	 * he's own stage, this because are a popoup window
	 * 
	 * @author jefter66
	 * @throws FileNotFoundException 
	 */


	public FriendshipRequestPOPOUP(Window owner) throws FileNotFoundException {
		super(owner);

		this.layout = new VBox();
		this.requestList = new ArrayList<HBFriendRequest>();

		this.scene = new Scene(layout);
		 this.scene.getStylesheets().add(this.getClass().getResource("/css/FRIEND_REQUEST.css").toExternalForm());

		this.setScene(scene);

		drawRequests();

		/* 
		 * make this work
		 */
		this.sc = new ScrollBar();
		this.sc.setLayoutX(scene.getWidth() - sc.getWidth());
		this.sc.setMin(0);
		this.sc.setOrientation(Orientation.VERTICAL);
		this.sc.prefHeight(this.getHeight());

	}

	/**
	 * This method are for bring the friendship requests of the database and put on
	 * a component with the sender informations HBFriendshipRequest and show for the
	 * user, according to the amount of registers in the database the hbox's will be
	 * drawn on the scene
	 * 
	 * @throws FileNotFoundException
	 * @author jefter66
	 */
	public void drawRequests() throws FileNotFoundException {

		ArrayList<Profile> r = new ArrayList<Profile>();
		r.clear();
		for (int i = 0; i < SESSION.getProfileLogged().getFriendshipRequests().size(); i++) {
			r.add((Profile) SESSION.getProfileLogged().getFriendshipRequests().get(i));
		}
		if (!r.isEmpty()) {
			for (int i = 0; i < r.size(); i++) {

				/*
				 * how to remove the component????????????????????
				 */
				FriendshipRequest answerRequest = new FriendshipRequest(r.get(i));
				HBFriendRequest request = new HBFriendRequest(r.get(i));
				request.setEventAccept(e -> {
					answerRequest.acceptRequest();
					SESSION.UPDATE_SESSION();
					this.layout.getChildren().clear();
					try {
						drawRequests();
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				
					try {
						GENERAL_STORE.updateComponentsHOME();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				request.setEventRefuse(e -> {
					answerRequest.refuseRequest();
					SESSION.UPDATE_SESSION();
					this.layout.getChildren().clear();
					try {
						drawRequests();
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
					}
					try {
						GENERAL_STORE.updateComponentsHOME();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				requestList.add(request);
				this.layout.getChildren().add(requestList.get(i));
			}
		}
		r.clear();
		return;
	}
}

/**
 * 
 * @author ensismoebius
 *
 */
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
