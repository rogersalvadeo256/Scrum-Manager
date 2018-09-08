package db.hibernate.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Database {

	private static EntityManagerFactory FACTORY_JEFTER = Persistence.createEntityManagerFactory("HibernatePU");

	private static EntityManagerFactory FACTORY_ROGER = Persistence.createEntityManagerFactory("POE SUA UNIDADE DE PERSISTENCIA ");
	
	
	public static EntityManager createEntityManager_ROGER() { 
		return FACTORY_ROGER.createEntityManager();
	}
	public static void close_ROGER() {
		Database.FACTORY_JEFTER.close();
	}
	
	
	public static EntityManager createEntityManager() {
		return FACTORY_JEFTER.createEntityManager();
	}

	public static void close() {
		Database.FACTORY_JEFTER.close();
	}
}
