package application.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import db.pojos.Profile;
import friendship.FriendshipRequest;
import javafx.scene.layout.VBox;
import scenes.popoups.FriendshipRequestPOPOUP;
import statics.GENERAL_STORE;
import statics.SESSION;
import widgets.designComponents.HBFriendRequest;

public class FriendshipRequestsController {
	private ArrayList<Profile> requestsList;

	public FriendshipRequestsController() {
		this.requestsList = new ArrayList<>();
	}
	
	public void init(VBox layout, FriendshipRequestPOPOUP screen) throws FileNotFoundException {
		drawRequests(layout,screen);
		this.requestsList = new ArrayList<Profile>();
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
	public void drawRequests(VBox layout, FriendshipRequestPOPOUP screen) throws FileNotFoundException {
		loadRequests();
		layout.getChildren().clear();
		if(this.requestsList.isEmpty()) screen.close() ;
		for (int i = 0; i < requestsList.size(); i++) {

			HBFriendRequest component = new HBFriendRequest(this.requestsList.get(i));
			FriendshipRequest controllerRequest = new FriendshipRequest(this.requestsList.get(i));
		
			layout.getChildren().add(component);

			component.setEventAccept(e -> {
				controllerRequest.acceptRequest();
				SESSION.UPDATE_SESSION();
				this.loadRequests();
				try {
					drawRequests(layout,screen);
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
				this.loadRequests();
				try {
					drawRequests(layout,screen);
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
		
	private void loadRequests() {
		requestsList.clear();
		for (int i = 0; i < SESSION.getProfileLogged().getFriendshipRequests().size(); i++) {
			requestsList.add((Profile) SESSION.getProfileLogged().getFriendshipRequests().get(i));
		}
	}
}

