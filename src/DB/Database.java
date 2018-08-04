package DB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Database {
	
	private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("HibernatePU");

	public static EntityManager createEntityManager() {
		return FACTORY.createEntityManager();
	}
	public static void close() {
		Database.FACTORY.close();
	}
}


























