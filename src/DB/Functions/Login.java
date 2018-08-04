package DB.Functions;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import DB.Database;
import hibernatebook.annotations.UserRegistration;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import validation.CheckEmptyFields;

public class Login {
	private EntityManager manager;
	private CheckEmptyFields checkFields;
	private List<UserRegistration> listOfUserForLogin;

	public Login() {
		this.manager = Database.createEntityManager();
		this.checkFields = new CheckEmptyFields();
		this.listOfUserForLogin = new ArrayList<UserRegistration>();
	}
	
	/**
	 * The function to check if the data for login informed are right,
	 * return true = data is right
	 * @param  TextField userName
	 * @param  PasswordField password 
	 * @return boolean
	 * @author jefter66
	 */
	@SuppressWarnings("unchecked")
	public boolean doLogin(TextField userName, PasswordField password) {
		if (!isFieldEmpty(userName, password)) {
			this.listOfUserForLogin.clear();
			Query queryForLogin = this.manager.createQuery("from UserRegistration");
			this.listOfUserForLogin = queryForLogin.getResultList();
			for (UserRegistration user : this.listOfUserForLogin) {
				if (user.getUserName().equals(userName.getText()) && user.getPassword().equals(password.getText()))
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
