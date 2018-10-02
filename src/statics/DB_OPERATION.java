package statics;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;

public class DB_OPERATION {


	public static void PERSIST(EntityManager em, Object o) {
		if (em == null)	Database.createEntityManager();
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}
	public static void MERGE(EntityManager em, Object o) {
		if (em == null) em =	Database.createEntityManager();
		
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}


	@SuppressWarnings("unchecked")
	public static List<Object> QUERY(EntityManager em, String query) {

		if (em == null)
			Database.createEntityManager();

		Query q = em.createQuery(query);

		return q.getResultList();

	}
	@SuppressWarnings("unchecked")
	public static List<Object> QUERY(EntityManager em, String query, String param, Object paramArgs) {
		if (em == null)
			Database.createEntityManager();

		Query q = em.createQuery(query);
		q.setParameter(param, paramArgs);

		return q.getResultList();

	}

	@SuppressWarnings("unchecked")
	public static List<Object> QUERY(EntityManager em, String query, List<String>param,List<Object> paramArgs) {
		if (em == null)	Database.createEntityManager();

		Query q = em.createQuery(query);

		for(int i=0; i < param.size(); i++) { 
			q.setParameter(param.get(i),paramArgs.get(i) ) ;
		}
		return q.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> QUERY(EntityManager em, String query, String[] strings, int[] paramArgs) {

		if(em == null)em = Database.createEntityManager();
		
		Query q = em.createQuery(query);
		
		for(int i = 0; i < strings.length; i++ ) { 
			q.setParameter(strings[i], paramArgs[i]);
		}
		return q.getResultList();
		
		
	}
































}















































