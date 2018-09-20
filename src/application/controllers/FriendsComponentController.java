package application.controllers;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.util.SESSION;

public class FriendsComponentController {
	private EntityManager em;
	public FriendsComponentController() {
	}

	
	/*
	 * this was not tested yet
	 */
	public void deleteFriend(Profile p) { 
		if(this.em == null) this.em = Database.createEntityManager();
		
		SESSION.getProfileLogged().getFriendsList().remove(p);
		SESSION.UPDATE_SESSION();
		p.getFriendsList().remove(SESSION.getProfileLogged());
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
		em.clear();
		em.close();
		em=null;
	}
}