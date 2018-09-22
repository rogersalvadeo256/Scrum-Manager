package friendship;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import statics.SESSION;

public class FriendshipRequest {

	private EntityManager em;
	private USER_PROFILE p;

	/**
	 * The parameter is the profile of who send the request, this profile is removed
	 * of the list of friendship requests and moved to the friends list in the two
	 * profiles ( sender and receiver )
	 * 
	 * @author jefter66
	 * @param USER_PROFILE pRequest
	 */
	public FriendshipRequest(USER_PROFILE p) {
		this.em = null;
		this.p = p;
	}

	/**
	 * The parameter are the user that going to receive the friendship request
	 * 
	 * @param USER_PROFILE
	 */
	public void sendFriendshipRequest() {// Profile p) {
		if (this.em == null)
			em = Database.createEntityManager();
		/*
		 * the list will be atached to the object profile
		 */
//		List<USER_PROFILE> sendToUser = this.p.getFriendshipRequests();
//		sendToUser.add(SESSION.getProfileLogged());

		// List<Profile> logged = SESSION.getProfileLogged().getFriendshipRequests();
		// logged.add(this.p);

		em.getTransaction().begin();
		em.merge(this.p);
//		em.merge(SESSION.getProfileLogged());
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}

	public void acceptRequest() { // Profile pRequest) {

//		this.p.getFriendsList().add(SESSION.getProfileLogged());
//		this.p.getFriendshipRequests().remove(SESSION.getProfileLogged());

//		SESSION.getProfileLogged().getFriendshipRequests().remove(this.p);
//		SESSION.getProfileLogged().getFriendsList().add(this.p);

		
		if (this.em == null)this.em = Database.createEntityManager();

		this.em.getTransaction().begin();
		this.em.merge(this.p);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
		SESSION.UPDATE_SESSION();
	}

	public void refuseRequest() { // Profile p) {

//		List<USER_PROFILE> loggedUser = SESSION.getProfileLogged().getFriendshipRequests();

//		List<USER_PROFILE> senderUser = this.p.getFriendshipRequests();

//		senderUser.remove(SESSION.getProfileLogged());
//		loggedUser.remove(this.p);

		USER_PROFILE update = SESSION.getProfileLogged();

		if (this.em == null)
			this.em = Database.createEntityManager();

		this.em.getTransaction().begin();
		this.em.merge(this.p);
		this.em.merge(update);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;

		SESSION.UPDATE_SESSION();

	}
}
