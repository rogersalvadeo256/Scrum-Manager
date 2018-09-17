package friendship;

import java.util.List;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.util.SESSION;

public class FriendshipRequest {

	private EntityManager em;
	private Profile p;
	
	public FriendshipRequest(Profile p ) {
		this.em = null;
		this.p = p;
	}

	/**
	 * The parameter are the user that going to receive the friendship request
	 * 
	 * @param Profile
	 */
	public void sendFriendshipRequest() {//Profile p) {
		if (this.em == null)
			em = Database.createEntityManager();
		this.p.getFriendshipRequests().add(SESSION.getProfileLogged());
//		SESSION.getProfileLogged().getFriendshipRequests().add(this.p);
		SESSION.UPDATE_SESSION();
		em.getTransaction().begin();
		em.merge(this.p);
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}

	/**
	 * The parameter is the profile of who send the request, this profile is
	 * removed of the list of friendship requests and moved to the friends list
	 * in the two profiles ( sender and receiver )
	 * 
	 * @author jefter66
	 * @param Profile
	 *            pRequest
	 */
	public void acceptRequest(){ //Profile pRequest) {

		this.p.getFriendsList().add(SESSION.getProfileLogged());
		this.p.getFriendshipRequests().remove(SESSION.getProfileLogged());

		// SESSION.getProfileLogged().getFriendshipRequests().remove(pRequest);
		SESSION.getProfileLogged().getFriendsList().add(this.p);

		if (this.em == null)
			this.em = Database.createEntityManager();

		this.em.getTransaction().begin();
		this.em.merge(this.p);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
		SESSION.UPDATE_SESSION();
	}

	public void refuseRequest(){ //Profile p) {
	
			
		List<Profile> loggedUser = SESSION.getProfileLogged().getFriendshipRequests();
		
		List<Profile> senderUser = this.p.getFriendshipRequests();
		
		
		senderUser.remove(SESSION.getProfileLogged());
		loggedUser.remove(this.p);
		
		Profile update = SESSION.getProfileLogged();
		
		if (this.em == null)this.em = Database.createEntityManager();

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





























