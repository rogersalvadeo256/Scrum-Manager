package DB.Functions;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import DB.Database;
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

	
	
	
	public boolean doLogin(TextField userName, PasswordField password) {
		if(!isFieldEmpty(userName, password)) {
			
			Query queryForLogin = this.manager.createQuery("from UserRegistration");
			queryForLogin.setParameter("userName", userName.getText());
			queryForLogin.setParameter("password", password.getText());
			
			if(queryForLogin.getResultList().size() == 2 ) return true; 
		
		
		}
		return true;
	}
	private boolean isFieldEmpty(TextField userName, PasswordField password) {
		if (checkFields.isTextFieldEmpty(userName) || checkFields.isPasswordFieldEmpty(password)) {
			return true;
		}
		return false;
	}
}