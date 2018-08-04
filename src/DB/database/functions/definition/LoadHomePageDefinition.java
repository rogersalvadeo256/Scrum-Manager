package DB.database.functions.definition;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import DB.Database;
import hibernatebook.annotations.Profile;
import hibernatebook.annotations.UserRegistration;

public abstract class LoadHomePageDefinition {

	public static EntityManager manager;
	public static UserRegistration userLogged;

	public LoadHomePageDefinition() {
		manager = Database.createEntityManager();
		userLogged = new UserRegistration();
	}
	/**
	 * Use when the user do the login on the system, this data will be used to load
	 * all the stuff in the home page
	 * @return UserRegistration
	 * @author jefter66
	 */
	public void setUserLogged(UserRegistration userLogged) {
		LoadHomePageDefinition.userLogged = userLogged;
	}
	private static UserRegistration getUserLogged() {
		return LoadHomePageDefinition.userLogged;
	}
	/**
	 * The class that extends this one, doesn't go to access this function, this
	 * will make the code of her more clean
	 * @return Profile
	 * @author jefter66
	 */
	private static Profile userProfile() {
		Query getUserProfile = manager.createNamedQuery("from UserRegistration where Profile=:Profile");
		getUserProfile.setParameter("Profile", getUserLogged().getProfile());
		Profile userProfile = (Profile) getUserProfile.getSingleResult();
		return userProfile;
	}
	/**
	 * Function that tha class going to use to access the profile of the user
	 * @return Profile
	 * @author jefter66
	 */
	public static Profile getProfile() {
		return userProfile();
	}
}












































