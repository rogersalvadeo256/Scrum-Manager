package db.functions;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.UserRegistration;
import db.hibernate.factory.Database;
import db.user.util.UserOnline;
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
	public boolean doLogin(TextField userName, PasswordField password) {
		if (!isFieldEmpty(userName, password)) {
			Query queryForLogin = this.manager.createQuery("from UserRegistration where userName=:userName and password=:password");
			queryForLogin.setParameter("userName", userName.getText());
			queryForLogin.setParameter("password", password.getText());

			if (!queryForLogin.getResultList().isEmpty()) {
				UserOnline.setUserLogged((UserRegistration) queryForLogin.getResultList().get(0));
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





















