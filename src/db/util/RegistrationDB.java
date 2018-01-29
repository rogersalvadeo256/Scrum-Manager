package db.util;

import db.pojos.USER_REGISTRATION;
import statics.DB_OPERATION;
import statics.ENUMS;

public class RegistrationDB {

	public RegistrationDB() {
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
		u.setStatus(ENUMS.ACCOUNT_STATUS.ACTIVE.getValue());
		DB_OPERATION.PERSIST(u);
	}
	/**
	 * if the return value are false, the user doesn't exist
	 * 
	 * @author jefter66
	 * @example boolean value = queryValidation(UserRegistration user);
	 * @param USER_REGISTRATION user
	 */
	public boolean userExist(USER_REGISTRATION user) {
		return (DB_OPERATION.QUERY("FROM USER_REGISTRATION WHERE USER_NAME=:USER_NAME" , "USER_NAME", user.getUserName()).isEmpty()) ? false :true ;
	}
	/**
	 * if the return are false, the email are not registered
	 * 
	 * @author jefter66
	 * @param USER_REGISTRATION user
	 */
	public boolean emailExist(USER_REGISTRATION user) {
		return (DB_OPERATION.QUERY("FROM USER_REGISTRATION WHERE USER_EMAIL =:USER_EMAIL", "USER_EMAIL", user.getEmail()).isEmpty()) ? false : true;  
	}
}




























