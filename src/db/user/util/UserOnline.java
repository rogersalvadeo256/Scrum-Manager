package db.user.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import POJOs.UserRegistration;
import db.hibernate.factory.Database;

/*
 *  this class contain all the functions for load the informations about the user logged
 */
public class UserOnline {
	private static EntityManager manager;
	private static UserRegistration userLogged;
	/**
	 * Use when the user do the login on the system, this data will be used to load
	 * all the stuff in the home page
	 * 
	 * @return UserRegistration
	 * @author jefter66
	 */
	public static void setUserLogged(UserRegistration userLogged) {
		UserOnline.userLogged = new UserRegistration();
		UserOnline.userLogged = userLogged;
		
	}
	public static UserRegistration getUserLogged() {
		return UserOnline.userLogged;
	}
	/**
	 * The class that extends this one, doesn't go to access this function, this
	 * will make the code of her more clean
	 * 
	 * @return Profile
	 * @author jefter66
	 */
	private static Profile userProfile() {
		if(UserOnline.manager == null )UserOnline.manager = Database.createEntityManager();
		Profile userProfile = new Profile();
		Query getUser = manager.createQuery("from Profile where codProfile=:codProfile");
		getUser.setParameter("codProfile", getUserLogged().getProfile().getCod());
		
		if(!getUser.getResultList().isEmpty()) {
			userProfile = (Profile) getUser.getResultList().get(0);
			System.out.println(userProfile.getName());
			}
		return userProfile;
	}
	/**
	 * Function that the class going to use to access the profile of the user
	 * 
	 * @return Profile
	 */
	public static Profile getProfile() {
		return userProfile();
	}
}




