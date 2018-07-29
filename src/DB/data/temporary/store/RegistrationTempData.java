package DB.data.temporary.store;


import hibernatebook.annotations.UserRegistration;

public class RegistrationTempData {

	private static UserRegistration registration;
	
	public static void addUser(UserRegistration user) {
		if(registration==null) registration=new UserRegistration();
		registration=user;
	}
}
