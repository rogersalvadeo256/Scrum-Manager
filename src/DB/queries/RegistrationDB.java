package DB.queries;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import DB.Database;
import DB.database.functions.definition.FunctionsRegistrationDatabase;
import hibernatebook.annotations.UserRegistration;

public class RegistrationDB extends FunctionsRegistrationDatabase {
	EntityManager manager;

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
	@Override
	public void insertUser(UserRegistration user) {
		this.manager.getTransaction().begin();
		this.manager.persist(user);
		this.manager.getTransaction().commit();
		this.manager.clear();
	}
	/**
	 *  if the return value are false, so the user doesn't already exist
	 * @author jefter66
	 * @example boolean value = queryValidation(UserRegistration user);
	 * @param UserRegistration user
	 */
	@Override
	public boolean userExist(UserRegistration user) {
		Query queryForExistentData = this.manager.createQuery("from UserRegistration where  userName=userName");
		queryForExistentData.setParameter("userName", user.getUserName());

		if (!queryForExistentData.getResultList().isEmpty()) {
			return true;
		}
		this.manager.clear();
		this.manager.close();
		return false;
	}
}
