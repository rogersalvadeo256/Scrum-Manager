package friendship;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.FRIENDSHIP;
import db.pojos.FRIENDSHIP_REQUEST;
import db.pojos.USER_PROFILE;
import statics.SESSION;
import statics.ENUMS;
import statics.ENUMS.FRIEND_STATE;
import statics.ENUMS.REQUEST_STATUS;

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
		if (this.em == null)
			this.em = Database.createEntityManager();

		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER");
		q.setParameter("COD_PROF_RECEIVER", SESSION.getProfileLogged().getCod());
		q.setParameter("COD_PROF_SENDER", this.p.getCod());

		FRIENDSHIP_REQUEST frq = (FRIENDSHIP_REQUEST) q.getResultList().get(0);

		frq.setStatus(REQUEST_STATUS.ACCEPTED);

		this.em.getTransaction().begin();
		this.em.merge(frq);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
		SESSION.UPDATE_SESSION();
		friendshipBegin();
	}

	public void refuseRequest() {
		if (this.em == null)
			this.em = Database.createEntityManager();

		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER");
		q.setParameter("COD_PROF_RECEIVER", SESSION.getProfileLogged().getCod());
		q.setParameter("COD_PROF_SENDER", this.p.getCod());

		FRIENDSHIP_REQUEST fr = (FRIENDSHIP_REQUEST) q.getResultList().get(0);

		fr.setStatus(REQUEST_STATUS.REFUSED);
		fr.setAnswredDate(Calendar.getInstance().getTime());

		this.em.getTransaction().begin();
		this.em.merge(fr);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;

		SESSION.UPDATE_SESSION();
	}
	
	
	/*
	 * create a register in the table friends 
	 */
	private void friendshipBegin() {
		if (this.em == null)
			this.em = Database.createEntityManager();

		Query q = em.createQuery("FROM FRIENDSHIP_REQUEST WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER AND FRQ_REQUEST_STATUS =: STATUS");
		q.setParameter("COD_PROF_RECEIVER", SESSION.getProfileLogged().getCod());
		q.setParameter("COD_PROF_SENDER", this.p.getCod());
		q.setParameter("STATUS", ENUMS.GET_REQUEST_STATUS(REQUEST_STATUS.ACCEPTED) );

		if (!q.getResultList().isEmpty()) {
			FRIENDSHIP fr = new FRIENDSHIP();
			fr.setDateBegin();
			fr.setCodProf1(SESSION.getProfileLogged().getCod());
			fr.setCodProf2(this.p.getCod());
			fr.setStatus(FRIEND_STATE.NORMAL);

			this.em.getTransaction().begin();
			this.em.persist(fr);
			this.em.getTransaction().commit();
			this.em.clear();
			this.em.close();
			this.em = null;
		}
	}

}














