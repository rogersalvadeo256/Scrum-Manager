package friendship;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	public static enum FRIENDSHIP_STATE{ 
		
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

	public void acceptRequest() { 
		if (this.em == null)this.em = Database.createEntityManager();

		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER");
		q.setParameter("COD_PROF_RECEIVER",SESSION.getProfileLogged().getCod());
		q.setParameter("COD_PROF_SENDER", this.p.getCod());
		
		
		FRIENDSHIP_REQUEST fr = (FRIENDSHIP_REQUEST) q.getResultList().get(0);
		
		fr.setStatus(REQUEST_STATUS.ACCEPTED);
		
		this.em.getTransaction().begin();
		this.em.merge(fr);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
		SESSION.UPDATE_SESSION();
	}

	public void refuseRequest() { 
		if (this.em == null)this.em = Database.createEntityManager();

		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER");
		q.setParameter("COD_PROF_RECEIVER",SESSION.getProfileLogged().getCod());
		q.setParameter("COD_PROF_SENDER", this.p.getCod());
		
		
		FRIENDSHIP_REQUEST fr = (FRIENDSHIP_REQUEST) q.getResultList().get(0);
		
		fr.setStatus(REQUEST_STATUS.REFUSED);
		
		this.em.getTransaction().begin();
		this.em.merge(fr);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
	
		SESSION.UPDATE_SESSION();

	}
}









