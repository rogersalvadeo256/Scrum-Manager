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
			up.setName("Andre");
			up.setAvailability("AVAILABLE");
			
			em.clear();
			
			
			USER_REGISTRATION ur = new USER_REGISTRATION();
			ur.setStatus("ACTIVE");
			ur.setuDateRegistrated();
			ur.setEmail("a");
			ur.setUserName("andre");
			ur.setPassword("a");
			ur.setSecurityAnswer("a");
			ur.setSecurityQuestion("a");
			ur.setProfile(up);
			DB_OPERATION.PERSIST(ur);
			
			USER_PROFILE up2 = new USER_PROFILE();
			up2.setName("Roger");
			up2.setAvailability("AVAILABLE");
			
			em.clear();
			
			
			USER_REGISTRATION ur2 = new USER_REGISTRATION();
			ur2.setStatus("ACTIVE");
			ur2.setuDateRegistrated();
			ur2.setEmail("a");
			ur2.setUserName("roger");
			ur2.setPassword("a");
			ur2.setSecurityAnswer("a");
			ur2.setSecurityQuestion("a");
			ur2.setProfile(up2);
			DB_OPERATION.PERSIST(ur2);
			
			em.close();
		}
		
		
	}
	
	
	
	
}
