package db.util;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.pojos.UserRegistration;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import validation.CheckEmptyFields;

public class Login {
	private EntityManager manager;
	private CheckEmptyFields checkFields;

	public Login() {
		this.manager = Database.createEntityManager();
		this.checkFields = new CheckEmptyFields();
	}

	/**
	 * The function to check if the data for login informed are right, return true =
	 * data is right
	 * 
	 * @param TextField
	 *            userName
	 * @param PasswordField
	 *            password
	 * @return boolean
	 * @author jefter66
	 */
	public boolean doLogin(TextField userNameOrEmail, PasswordField password) {
		if (!isFieldEmpty(userNameOrEmail, password)) {
			Query q = this.manager.createQuery("from UserRegistration where userName=:userName or email=:email and password=:password");
			q.setParameter("userName", userNameOrEmail.getText());
			q.setParameter("email", userNameOrEmail.getText());
			q.setParameter("password", password.getText());
			
			if (!q.getResultList().isEmpty()) {
				SESSION.START_SESSION((UserRegistration) q.getResultList().get(0));
				return true;
			}
		}
		return false;
	}
	private boolean isFieldEmpty(TextField userName, PasswordField password) {
		if (checkFields.isTextFieldEmpty(userName) || checkFields.isPasswordFieldEmpty(password)) {
			return true;
		}
		return false;
	}
}





















