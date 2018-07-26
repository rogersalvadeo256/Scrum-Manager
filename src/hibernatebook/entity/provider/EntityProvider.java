package hibernatebook.entity.provider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityProvider {
	
	public static class Factory {
		public static  EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("HibernatePU");;
		public static EntityManager entityManager = FACTORY.createEntityManager();
	

	}
}
