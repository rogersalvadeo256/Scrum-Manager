package DB.database.functions.definition;

import hibernatebook.annotations.UserRegistration;

public abstract class FunctionsRegistrationDatabase {
	public abstract boolean insertUser(UserRegistration user);
	public abstract boolean queryValidation(UserRegistration user);
}
