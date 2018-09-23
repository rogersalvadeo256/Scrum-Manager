package friendship;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.FRIENDSHIP_REQUEST;
import db.pojos.USER_PROFILE;
import statics.SESSION;

public class FriendshipRequest {

	private EntityManager em;
	private USER_PROFILE p;

	public static enum REQUEST_STATUS { 
		ACCEPTED, REFUSED, ON_HOLD
	}
	
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

		FRIENDSHIP_REQUEST friendshipRequest = new FRIENDSHIP_REQUEST();
		
		friendshipRequest.setRequestedBy(SESSION.getProfileLogged().getCod());
		friendshipRequest.setReceiver(this.p.getCod());
		friendshipRequest.setSendDate();
		friendshipRequest.setStatus(REQUEST_STATUS.ON_HOLD);
		
		
		em.getTransaction().begin();
		em.persist(friendshipRequest);
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
