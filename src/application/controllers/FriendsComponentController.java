package application.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.FRIENDSHIP_REQUEST;
import db.pojos.USER_PROFILE;
import javafx.scene.layout.VBox;
import statics.SESSION;
import view.popoups.FriendListPOPOUP;
import widgets.designComponents.HBFriendContent;

public class FriendsComponentController {
	private ArrayList<USER_PROFILE> friendsList;

	public FriendsComponentController() throws IOException {
		this.friendsList = new ArrayList<>();
	}

	/**
	 * Draw the components with friends information on the scene
	 * 
	 * @param layout
	 * @param screen
	 * @throws IOException
	 */
	public void init(VBox layout, FriendListPOPOUP screen) throws IOException {

		layout.getChildren().clear();
		loadFriendsList();

		if (this.friendsList.isEmpty())
			screen.close();

		for (int i = 0; i < this.friendsList.size(); i++) {

			HBFriendContent fc = new HBFriendContent(this.friendsList.get(i));
			layout.getChildren().add(fc);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadFriendsList() {
		this.friendsList.clear();

		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER = :COD OR FRQ_COD_PROF_REQUESTED_BY = :COD AND FRQ_REQUEST_STATUS = 'ACCEPTED' ");
		q.setParameter("COD", SESSION.getProfileLogged().getCod());

		for (FRIENDSHIP_REQUEST rq : (List<FRIENDSHIP_REQUEST>) q.getResultList()) {

			Query q1 = em.createQuery("FROM USER_PROFILE WHERE PROF_COD = :COD OR PROF_COD = :COD2 AND PROF_COD <> :USER_ONLINE");
			q1.setParameter("COD", rq.getRequestedBy());
			q1.setParameter("COD2", rq.getReceiver());
			q1.setParameter("USER_ONLINE", SESSION.getProfileLogged().getCod());

			for (USER_PROFILE up : (List<USER_PROFILE>) q1.getResultList()) {
				this.friendsList.add(up);
			}
		}
	}
}
