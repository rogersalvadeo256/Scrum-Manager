package DB.queries;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import DB.Database;
import DB.database.functions.definition.FunctionsRegistrationDatabase;
import hibernatebook.annotations.UserRegistration;

public class RegistrationDB extends FunctionsRegistrationDatabase {

	private EntityManager manager;
	private Query queryForExistentData;
	private List<UserRegistration> result;
	public RegistrationDB() {
		this.manager = Database.createEntityManager();
		this.result=new ArrayList<UserRegistration>();
	}

	@Override
	public boolean insertUser(UserRegistration user) {
		if (queryValidation(user)) {
			this.manager.getTransaction().begin();
			this.manager.persist(user);
			this.manager.getTransaction().commit();
			this.manager.clear();
		}
		return false;
	}
	@Override
	public boolean queryValidation(UserRegistration user) {
		this.queryForExistentData = this.manager.createQuery("from UserRegistration");
		this.result = queryForExistentData.getResultList();
		
		for(UserRegistration listOfUsers: this.result) {
			if(listOfUsers.getUserName().equals(user.getUserName()) || (listOfUsers.getEmail().equals(user.getEmail()))) {
				return false;
			}
		}
		/*
		 * if the return are true, the registration is okay
		 */
		return true;
	}
}




































