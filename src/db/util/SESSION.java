package db.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.pojos.UserRegistration;

/*
 * this class contain all the functions for load the informations about the user
 * logged
 */
public class SESSION {
	private static UserRegistration u;
	private static Profile p;
	private static EntityManager em;
	
	/**
	 * Use when the user do the login on the system, this data will be used to load
	 * all the stuff in the home page
	 * 
	 * @return UserRegistration
	 * @author jefter66
	 */
	public static UserRegistration getUserLogged() {
		return u;
	}
	
	public static Profile getProfileLogged() {
		return p;
	}
	private static void setProfile() {
		if (em == null)
			em = Database.createEntityManager();
		Query q1 = em.createQuery("from Profile where codProfile =: cod");
		q1.setParameter("cod", SESSION.getUserLogged().getProfile().getCod());
		Profile p = (Profile) q1.getResultList().get(0);
		SESSION.p = p;
	}
	private static void setUser(UserRegistration u) {
		SESSION.u = u;
	}
	public static void START_SESSION(UserRegistration u) {
		SESSION.setUser(u);
		SESSION.setProfile();
	}
	private static void UPDATE_SESSION(Profile p) {
		SESSION.p = p;
	}
	public static void UPDATE_SESSION(Profile p, UserRegistration u) {
		SESSION.p = p;
		SESSION.u = u;
	}
	public static void UPDATE_SESSION(UserRegistration u ) { 
		SESSION.u = u;
	}
	public static void UPDATE_SESSION() {
		if (em == null)
			em = Database.createEntityManager();
		Query q = em.createQuery("from UserRegistration where codUser=: cod");
		q.setParameter("cod", SESSION.getUserLogged().getCodUser());
		UserRegistration u = (UserRegistration) q.getResultList().get(0);
		
		Query q1 = em.createQuery("from Profile where codProfile =: cod");
		q1.setParameter("cod", SESSION.getUserLogged().getProfile().getCod());
		Profile p = (Profile) q1.getResultList().get(0);
		
		SESSION.UPDATE_SESSION(p, u);
		em.clear();
		em.close();
		em = null;
	}
}



































