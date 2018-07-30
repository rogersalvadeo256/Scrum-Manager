package DB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import main.Window;

public class Factory {
	private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("HibernatePU");

	public static EntityManager createEntityManager() {
		return FACTORY.createEntityManager();
	}
	public static void closeFactory() {
		Factory.FACTORY.close();
	}
	public static boolean insert(Object obj) {
		Window.DB.persist(obj);
		Window.DB.getTransaction().commit();
		return true;
	}
}


























