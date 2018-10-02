package friendship;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.FRIENDSHIP;
import db.pojos.USER_PROFILE;
import db.querys.QUERYs_FRIENDSHIP;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;

public class FriendshipActions {

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
	public FriendshipActions(USER_PROFILE p) {
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

		FRIENDSHIP friendshipRequest = new FRIENDSHIP();

		friendshipRequest.setRequestedBy(SESSION.getProfileLogged().getCod());
		friendshipRequest.setReceiver(this.p.getCod());
		friendshipRequest.setSendDate();
		friendshipRequest.setStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

		em.getTransaction().begin();
		em.persist(friendshipRequest);
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}

	public void acceptRequest() {
		if (this.em == null)
			this.em = Database.createEntityManager();

		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER");
		q.setParameter("COD_PROF_RECEIVER", SESSION.getProfileLogged().getCod());
		q.setParameter("COD_PROF_SENDER", this.p.getCod());

		FRIENDSHIP frq = (FRIENDSHIP) q.getResultList().get(0);

		
//		DB_OPERATION.QUERY(this.em,"FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER", new String[]{"COD_PROF_RECEIVER", "COD_PROF_SENDER"}, new Object[]{SESSION.getProfileLogged().getCod(), this.p.getCod()});
		
		
		
		frq.setStatus(ENUMS.REQUEST_STATUS.ACCEPTED.getValue());

		this.em.getTransaction().begin();
		this.em.merge(frq);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
		SESSION.UPDATE_SESSION();
	}

	public void refuseRequest() {
		if (this.em == null)
			this.em = Database.createEntityManager();

		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER");
		q.setParameter("COD_PROF_RECEIVER", SESSION.getProfileLogged().getCod());
		q.setParameter("COD_PROF_SENDER", this.p.getCod());

		FRIENDSHIP fr = (FRIENDSHIP) q.getResultList().get(0);

		fr.setStatus(ENUMS.REQUEST_STATUS.REFUSED.getValue());

		this.em.getTransaction().begin();
		this.em.merge(fr);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;

		SESSION.UPDATE_SESSION();
	}

	public void removeFriend() {

		if (this.em == null)
			this.em = Database.createEntityManager();

		for (FRIENDSHIP r : (List<FRIENDSHIP>) QUERYs_FRIENDSHIP.friendshipList()) {

			if (r.getReceiver() == this.p.getCod() || r.getRequestedBy() == this.p.getCod()) {
				r.setStatus(ENUMS.REQUEST_STATUS.REMOVED.getValue());
				FRIENDSHIP fr = r;
				this.em.getTransaction().begin();
				this.em.merge(fr);
				this.em.getTransaction().commit();
				this.em.clear();
				this.em.close();
				this.em = null;
				SESSION.UPDATE_SESSION();
				return;
			}

		}

	}

}
