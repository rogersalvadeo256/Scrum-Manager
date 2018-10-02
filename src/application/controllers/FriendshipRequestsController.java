package application.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import application.main.Window;
import db.hibernate.factory.Database;
import db.pojos.FRIENDSHIP;
import db.pojos.USER_PROFILE;
import db.querys.QUERYs_FRIENDSHIP;
import friendship.FriendshipActions;
import javafx.scene.layout.VBox;
import statics.GENERAL_STORE;
import statics.SESSION;
import view.popoups.FriendshipRequestPOPOUP;
import widgets.designComponents.HBFriendRequest;
import widgets.toaster.Toast;

public class FriendshipRequestsController {
	private ArrayList<USER_PROFILE> requestsList;

	public FriendshipRequestsController() {
		this.requestsList = new ArrayList<>();
	}

	public void init(VBox layout, FriendshipRequestPOPOUP screen) throws FileNotFoundException {
		drawRequests(layout, screen);
		this.requestsList = new ArrayList<USER_PROFILE>();
	}

	/**
	 * This method are for bring the friendship requests of the database and put on
	 * a component with the user that send the request informations
	 * HBFriendshipRequest and show for the user, according to the amount of
	 * registers in the database the hbox's will be drawn on the scene
	 * 
	 * @param VBox                    layout (where the profile component will be
	 *                                loaded)
	 * @param FriendshipRequestPOPOUP screen ( the stage where the layout are, will
	 *                                be use to close the stage automatically when
	 *                                the user answer all the requests)
	 * @throws FileNotFoundException
	 * @author jefter66
	 */
	public void drawRequests(VBox layout, FriendshipRequestPOPOUP screen) throws FileNotFoundException {
		loadRequests();
		layout.getChildren().clear();

		if (requestsList.isEmpty())
			screen.close();

		if (!this.requestsList.isEmpty()) {
			for (int i = 0; i < this.requestsList.size(); i++) {
				HBFriendRequest component = new HBFriendRequest(this.requestsList.get(i));
				FriendshipActions controllerRequest = new FriendshipActions(this.requestsList.get(i));

				layout.getChildren().add(component);
				component.setEventAccept(e -> {
					controllerRequest.acceptRequest();
					SESSION.UPDATE_SESSION();
					this.loadRequests();
					try {
						drawRequests(layout, screen);
						GENERAL_STORE.updateComponentsHOME();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				component.setEventRefuse(e -> {
					controllerRequest.refuseRequest();
					SESSION.UPDATE_SESSION();
					loadRequests();
					try {
						drawRequests(layout, screen);
						GENERAL_STORE.updateComponentsHOME();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
			}
			return;
		}
	}

	private void loadRequests() {
		this.requestsList.clear();
		this.requestsList = (ArrayList<USER_PROFILE>) QUERYs_FRIENDSHIP.friendshipRequestsList();

	}

}
