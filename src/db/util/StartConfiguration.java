package db.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;
import statics.DB_OPERATION;

public class StartConfiguration {

	public StartConfiguration() {
		
		EntityManager em = Database.createEntityManager();
		
		Query q = em.createQuery("FROM USER_REGISTRATION");

		if(q.getResultList().isEmpty()) {
			USER_PROFILE up = new USER_PROFILE();
			up.setName("a");
			up.setAvailability("AVAILABLE");
			
			em.clear();
			
			
			USER_REGISTRATION ur = new USER_REGISTRATION();
			ur.setStatus("ACTIVE");
			ur.setuDateRegistrated();
			ur.setEmail("a");
			ur.setUserName("a");
			ur.setPassword("a");
			ur.setSecurityAnswer("a");
			ur.setSecurityQuestion("a");
			ur.setProfile(up);
			DB_OPERATION.PERSIST(ur);
			
			em.close();
		}
		
		
	}
	
	
	
	
}
