package application.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
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

	private void loadFriendsList() {
		this.friendsList.clear();

		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery("FROM PROF_COD FROM USER_PROFILE AS PR INNER JOIN FRIENDSHIP AS FR ON FR.FR_PROF_1 AND FR.FR_PROF_2 WHERE FR.FR_PROF_1 OR FR.FR_PROF_2 =: PROF_COD AND FR.FR_PROF_1 AND FR.FR_PROF_2 <> =:PROF_COD");
		q.setParameter("PROF_COD", SESSION.getProfileLogged().getCod());

		if (!q.getResultList().isEmpty()) {
			for (int i = 0; i < q.getResultList().size(); i++) {
				this.friendsList.add((USER_PROFILE) q.getResultList().get(i));
			}
		}
	}
}

















