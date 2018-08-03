package DB.database.functions.definition;

import hibernatebook.annotations.UserRegistration;

public abstract class FunctionsRegistrationDatabase {
	public abstract void insertUser(UserRegistration user);
	public abstract boolean userExist(UserRegistration user);
}
