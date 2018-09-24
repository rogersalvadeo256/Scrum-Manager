package statics;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;

/*
 * this class contain all the functions for load the informations about the user
 * logged
 */
public class SESSION {
	private static USER_REGISTRATION u;
	private static USER_PROFILE p;
	private static EntityManager em;

	/**
	 * Use when the user do the login on the system, this data will be used to load
	 * all the stuff in the home page
	 * 
	 * @return UserRegistration
	 * @author jefter66
	 */
	public static USER_REGISTRATION getUserLogged() {
		return u;
	}

	public static USER_PROFILE getProfileLogged() {
		return p;
	}

	private static void setProfile() {
		if (em == null)
			em = Database.createEntityManager();
		Query q1 = em.createQuery("FROM USER_PROFILE WHERE PROF_COD =: PROF_COD");
		q1.setParameter("PROF_COD", SESSION.getUserLogged().getProfile().getCod());
		USER_PROFILE p = (USER_PROFILE) q1.getResultList().get(0);
		SESSION.p = p;
	}

	private static void setUser(USER_REGISTRATION u) {
		SESSION.u = u;
	}

	public static void START_SESSION(USER_REGISTRATION u) {
		SESSION.setUser(u);
		SESSION.setProfile();
	}

	public static void UPDATE_SESSION(USER_PROFILE p, USER_REGISTRATION u) {
		SESSION.p = p;
		SESSION.u = u;
	}

	public static void UPDATE_SESSION(USER_REGISTRATION u) {
		SESSION.u = u;
	}

	public static void UPDATE_SESSION() {
		if (em == null)
			em = Database.createEntityManager();
		Query q = em.createQuery("FROM USER_REGISTRATION WHERE USER_COD=:USER_COD");
		q.setParameter("USER_COD", SESSION.getUserLogged().getCodUser());
		USER_REGISTRATION u = (USER_REGISTRATION) q.getResultList().get(0);

		Query q1 = em.createQuery("FROM USER_PROFILE WHERE PROF_COD =: PROF_COD");
		q1.setParameter("PROF_COD", SESSION.getUserLogged().getProfile().getCod());
		USER_PROFILE p = (USER_PROFILE) q1.getResultList().get(0);

		SESSION.UPDATE_SESSION(p, u);
		em.clear();
		em.close();
		em = null;
	}

	public static void RESET() {
		SESSION.u = null;
		SESSION.p = null;
	}

}
