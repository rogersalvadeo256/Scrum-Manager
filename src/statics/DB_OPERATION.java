package statics;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;

/**
 * 
 * 
 * Static functions for hibernate operations
 * 
 * The Object parameters had to be mapped objects (pojos) to the methods can work
 * 
 * @author jefter66
 *
 */
public class DB_OPERATION {
	/**
	 * 
	 * The parameter EntityManager can be null, in the function he will be instantiated
	 * 
	 * The Object parameter had to be an mapped object to this work
	 * 
	 * 
	 * @param EntityManager
	 * @param Object
	 * 
	 */
	public static void PERSIST(Object o) {
		EntityManager em = Database.createEntityManager();
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}
	public static void PERSIST(List<?> o) {
		EntityManager em = Database.createEntityManager();
		em.getTransaction().begin();
		for (Object object : o) {
			em.persist(object);
		}
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}
	public static void MERGE(Object o) {

		EntityManager em = Database.createEntityManager();
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}


	@SuppressWarnings("unchecked")
	public static List<Object> QUERY(String query) {

		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery(query);

		return q.getResultList();

	}
	@SuppressWarnings("unchecked")  
	public static List<Object> QUERY(String query, String param, Object paramArgs) {
		EntityManager em = Database.createEntityManager();
		
		Query q = em.createQuery(query);
		q.setParameter(param, paramArgs);

		 return q.getResultList();
	}
	/**
	 * For querys with more than one parameter the parameters has to be int numbers
	 * 
	 * 
	 * * @example List<Object> = (Object) QUERY(em, "FROM MY_TABLE WHERE MY_COLUMN =
	 * :PARAM1 AND MY_COLUMN2 = :PARAM2", new String[]{'PARAM1','PARAM2'}, new int[] {
	 * 1,2});
	 * 
	 * @param query
	 * @param strings
	 * @param paramArgs
	 * @return List
	 * @author jefter66
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> QUERY(String query, String[] strings, int[] paramArgs) {
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery(query);

		for (int i = 0; i < strings.length; i++) {
			q.setParameter(strings[i], paramArgs[i]);
		}
		return q.getResultList();
	}

	/**
	 * @example List<Object> = (Object) QUERY(em, "FROM MY_TABLE WHERE MY_COLUMN = :PARAM1
	 *          AND MY_COLUMN2 = :PARAM2", new String[]{'PARAM1','PARAM2'}, new String[] {
	 *          'NAME 1','NAME 2'});
	 * @param query
	 * @param strings
	 * @param paramArgs
	 * @return
	 * @author jefter66
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> QUERY(String query, String[] strings, String[] paramArgs) {
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery(query);

		for (int i = 0; i < strings.length; i++) {
			q.setParameter(strings[i], paramArgs[i]);
		}
		return q.getResultList();
	}
	/** 
	 * Any parameters
	 */
	@SuppressWarnings("unchecked")	
	public static List<Object> QUERY(String query, String[] strings, Object[] paramArgs) {
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery(query);

		for (int i = 0; i < strings.length; i++) {
			q.setParameter(strings[i], paramArgs[i]);
		}
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")	
	public static List<Object> QUERY(String query, List<String> strings, List<Integer> paramArgs) {
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery(query);

		for (int i = 0; i < strings.size(); i++) {
			q.setParameter(strings.get(i), paramArgs.get(i));
		}
		return q.getResultList();
	}
	@SuppressWarnings("unchecked")	
	public static List<Object> QUERY(String query, String string, List<Integer> paramArgs) {
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery(query);

		for (int i = 0; i < paramArgs.size(); i++) {
			q.setParameter(string, paramArgs.get(i));
		}
		return q.getResultList();
	}

}

