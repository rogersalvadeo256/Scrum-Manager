package DB.Functions;

import DB.database.functions.definition.LoadHomePageDefinition;
import hibernatebook.annotations.Profile;
import hibernatebook.annotations.UserRegistration;

public class LoadHomePage extends LoadHomePageDefinition{
	
	private static UserRegistration userLogged; 
	private static Profile userLoggedProfile;
	public LoadHomePage() {
		this.userLogged=new UserRegistration();
		this.userLoggedProfile=new Profile();
	}

	public void loadUserAndProfile() { 
		setUserLogged(getUserLogged());
		this.userLoggedProfile= getProfile();
	}



	
	
	public Profile getUserLoggedProfile() {
		return userLoggedProfile;
	}
	public void setUserLogged(UserRegistration userLogged) {
		LoadHomePage.userLogged = userLogged;
	}
	public UserRegistration getUserLogged() {
		return userLogged;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
