package db.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_REGISTRATION;
import statics.ENUMS;

public class RegistrationDB {
	private EntityManager em;

	public RegistrationDB() {
		this.em = Database.createEntityManager();
	}

	/**
	 * make the persistense for registration use after do the query for existent
	 * user and validations
	 * 
	 * @author jefter66
	 * @example boolean value = insertUser(user);
	 * @param USER_REGISTRATION user
	 */
	public void insertUser(USER_REGISTRATION u) {
		if (em == null)
			em = Database.createEntityManager();
		u.setuStatus(ENUMS.ACCOUNT_STATUS.ACTIVE.getValue());

		this.em.getTransaction().begin();
		this.em.persist(u);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
	}

	/**
	 * if the return value are false, the user doesn't exist
	 * 
	 * @author jefter66
	 * @example boolean value = queryValidation(UserRegistration user);
	 * @param USER_REGISTRATION user
	 */
	public boolean userExist(USER_REGISTRATION user) {
		if (em == null)
			em = Database.createEntityManager();

		Query q = this.em.createQuery("FROM USER_REGISTRATION WHERE USER_NAME=:USER_NAME");
		q.setParameter("USER_NAME", user.getUserName());
		if (!q.getResultList().isEmpty())
			return true;
		return false;
	}

	/**
	 * if the return are false, the email are not registered
	 * 
	 * @author jefter66
	 * @param USER_REGISTRATION user
	 */
	public boolean emailExist(USER_REGISTRATION user) {
		if (em == null)
			em = Database.createEntityManager();

		Query q = this.em.createQuery("FROM USER_REGISTRATION WHERE USER_EMAIL =:USER_EMAIL");
		q.setParameter("USER_EMAIL", user.getEmail());

		if (!q.getResultList().isEmpty())
			return true;
		return false;
	}
}
