package db.querys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.FRIENDSHIP;
import db.pojos.USER_PROFILE;
import statics.SESSION;

public class QUERYs_FRIENDSHIP {

	/**
	 * Do a query and add to a arraylist the friendship requests of the user that
	 * are on hold
	 * 
	 * @author jefter66
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<USER_PROFILE> friendshipRequestsList() {
		List<USER_PROFILE> listReturn = new ArrayList<USER_PROFILE>();
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery("FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER =: COD AND FRQ_REQUEST_STATUS = 'ON_HOLD'");
		q.setParameter("COD", SESSION.getProfileLogged().getCod());

		for (FRIENDSHIP request : (List<FRIENDSHIP>) q.getResultList()) {
			Query q1 = em.createQuery("FROM USER_PROFILE WHERE PROF_COD =:COD");
			FRIENDSHIP fr = request;
			q1.setParameter("COD", fr.getRequestedBy());
			for (USER_PROFILE profile : (List<USER_PROFILE>) q1.getResultList()) {
				listReturn.add(profile);
			}
		}
		return listReturn;
	}

	/*
	 * SOO FUCKING UGLY
	 */
	@SuppressWarnings("unchecked")
	public static List<USER_PROFILE> friendsList() {
		EntityManager em = Database.createEntityManager();
		List<USER_PROFILE> listReturn = new ArrayList<USER_PROFILE>();
		Query q = em.createQuery("FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER = :COD AND FRQ_REQUEST_STATUS= 'ACCEPTED' OR FRQ_COD_PROF_REQUESTED_BY = :COD AND FRQ_REQUEST_STATUS = 'ACCEPTED'");
		q.setParameter("COD", SESSION.getProfileLogged().getCod());

		for (FRIENDSHIP rq : (List<FRIENDSHIP>) q.getResultList()) {

			Query q1 = em.createQuery("FROM USER_PROFILE WHERE PROF_COD = :COD OR PROF_COD = :COD2 AND PROF_COD <> :USER_ONLINE");
			q1.setParameter("COD", rq.getRequestedBy());
			q1.setParameter("COD2", rq.getReceiver());
			q1.setParameter("USER_ONLINE", SESSION.getProfileLogged().getCod());

			for (USER_PROFILE up : (List<USER_PROFILE>) q1.getResultList()) {
				listReturn.add(up);
			}
		}
		return listReturn;
	}

	@SuppressWarnings("unchecked")
	public static List<FRIENDSHIP> friendshipList() {

		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery("FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER = :COD OR FRQ_COD_PROF_REQUESTED_BY = :COD AND FRQ_REQUEST_STATUS = 'ACCEPTED' ");
		q.setParameter("COD", SESSION.getProfileLogged().getCod());

		return q.getResultList();

	}

}
