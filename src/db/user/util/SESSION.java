package db.user.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import POJOs.UserRegistration;
import db.hibernate.factory.Database;

/*
 *  this class contain all the functions for load the informations about the user logged
 */
public class SESSION {
	private static UserRegistration u;
	private static Profile p;
	
	/**
	 * Use when the user do the login on the system, this data will be used to load
	 * all the stuff in the home page
	 * 
	 * @return UserRegistration
	 * @author jefter66
	 */
	public static UserRegistration getUserLogged() {
		return SESSION.u;
	}
	public static Profile getProfileLogged() { 
		return SESSION.p;
	}
	public static void START_SESSION(UserRegistration u) { 
		SESSION.u = u;
		SESSION.p = u.getProfile();
	}	
}






















