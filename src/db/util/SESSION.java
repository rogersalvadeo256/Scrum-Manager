package db.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.pojos.UserRegistration;

/*
 *  this class contain all the functions for load the informations about the user logged
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
		if(em == null ) em = Database.createEntityManager();
		
		Query q = em.createQuery("from UserRegistration where codUser=:codUser");
		q.setParameter("codUser", SESSION.u.getCodUser());
		
		if(q.getResultList().size() > 0) {
			em.clear();
			em = null;
			UserRegistration uReturn = (UserRegistration) q.getResultList().get(0);
			return uReturn; 
		}
		return null;
	}
	public static Profile getProfileLogged() {
		if (em == null)
			em = Database.createEntityManager();

		Query q = em.createQuery("from Profile where CODPROFILE=:codProfile");
		q.setParameter("codProfile", SESSION.p.getCod());
			em.clear();
			em = null;
			Profile pReturn = (Profile) q.getResultList().get(0); 
			return pReturn;
	}
	public static void START_SESSION(UserRegistration u) {
		SESSION.u = u;
		SESSION.p = u.getProfile();
	}
	public static void UPDATE_SESSION(Profile p) { 
		SESSION.p = p;
	}
	
	
}
















