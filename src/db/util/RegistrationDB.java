package db.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.UserRegistration;

public class RegistrationDB{
	private EntityManager em;
	public RegistrationDB() {
		this.em = Database.createEntityManager();
	}
	/**
	 * make the persistense for registration
	 * use after do the query for existent user and validations
	 * 
	 * @author jefter66
	 * @example boolean value = insertUser(user);
	 * @param UserRegistration
	 *            user
	 */
	public void insertUser(UserRegistration u) {
		if (em == null) {
			em = Database.createEntityManager();
		}
		this.em.getTransaction().begin();
		this.em.persist(u);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
	}
	/**
	 *  if the return value are false, the user doesn't exist
	 * @author jefter66
	 * @example boolean value = queryValidation(UserRegistration user);
	 * @param UserRegistration user
	 */
	public boolean userExist(UserRegistration user) {
		if (em == null) em = Database.createEntityManager();
		
		Query q = this.em.createQuery("from UserRegistration where userName=:userName");
		q.setParameter("userName", user.getUserName());
		if (!q.getResultList().isEmpty()) return true;
		return false;
	}
	/** 
	 * if the return are false, the email are not registered
	 * @author jefter66
	 * @param UserRegistration user
	 */
	public boolean emailExist(UserRegistration user) { 
		if(em == null) em  = Database.createEntityManager();
		
		Query q = this.em.createQuery("from UserRegistration where email =:email");
		q.setParameter("email",user.getEmail());

		if(!q.getResultList().isEmpty()) return true;
		return false;
	}
}
























