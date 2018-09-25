package application.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.FRIENDSHIP_REQUEST;
import db.pojos.USER_PROFILE;
import friendship.FriendshipRequest;
import javafx.scene.layout.VBox;
import statics.GENERAL_STORE;
import statics.SESSION;
import view.popoups.FriendshipRequestPOPOUP;
import widgets.designComponents.HBFriendRequest;

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
	 *	@param VBox layout (where the profile component will be loaded)
	 * @param FriendshipRequestPOPOUP screen ( the stage where the layout are, will be use to close the stage automatically when the user answer all the requests)
	 * @throws FileNotFoundException
	 * @author jefter66
	 */
	public void drawRequests(VBox layout, FriendshipRequestPOPOUP screen) throws FileNotFoundException {
		loadRequests();
		layout.getChildren().clear();
		
		if(requestsList.isEmpty()) screen.close();
		
		if (!this.requestsList.isEmpty()) {
			for (int i = 0; i < this.requestsList.size(); i++) {
				HBFriendRequest component = new HBFriendRequest(this.requestsList.get(i));
				FriendshipRequest controllerRequest = new FriendshipRequest(this.requestsList.get(i));

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
			}
			return;
		}
	}

	/**
	 * Do a query and add to a arraylist the friendship requests of the user that
	 * are on hold
	 * 
	 * @author jefter66
	 */
	private void loadRequests() {
		this.requestsList.clear();
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =: COD AND FRQ_REQUEST_STATUS = 'ON_HOLD'");
		q.setParameter("COD", SESSION.getProfileLogged().getCod());
		for (int i = 0; i < q.getResultList().size(); i++) {
			Query q1 = em.createQuery("FROM USER_PROFILE WHERE PROF_COD =:COD");
			FRIENDSHIP_REQUEST fr = (FRIENDSHIP_REQUEST) q.getResultList().get(i);
			q1.setParameter("COD", fr.getRequestedBy());
			this.requestsList.add((USER_PROFILE) q1.getResultList().get(i));
		}
	}

}
















