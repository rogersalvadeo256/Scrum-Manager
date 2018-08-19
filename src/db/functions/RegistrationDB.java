package db.functions;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.UserRegistration;
import db.hibernate.factory.Database;

public class RegistrationDB{
	private EntityManager manager;
	public RegistrationDB() {
		this.manager = Database.createEntityManager();
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
	public void insertUser(UserRegistration user) {
		this.manager.getTransaction().begin();
		this.manager.persist(user);
		this.manager.getTransaction().commit();
		this.manager.clear();
	}
	/**
	 *  if the return value are false, so the user doesn't exist
	 * @author jefter66
	 * @example boolean value = queryValidation(UserRegistration user);
	 * @param UserRegistration user
	 */
	public boolean userExist(UserRegistration user) {
		Query queryForExistentUserName = this.manager.createQuery("from UserRegistration where userName=:userName");
		queryForExistentUserName.setParameter("userName", user.getUserName());
		if (!queryForExistentUserName.getResultList().isEmpty()) return true;
		return false;
	}
}
























