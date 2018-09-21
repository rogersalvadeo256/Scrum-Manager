package db.util;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.pojos.UserRegistration;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import statics.SERIALIZATION;
import statics.SERIALIZATION.FileType;
import statics.SESSION;
import validation.CheckEmptyFields;

public class Login {
	private EntityManager em;
	private CheckEmptyFields checkFields;

	public Login() {
		this.checkFields = new CheckEmptyFields();
	}

	/**
	 * The function to check if the data for login informed are right, return true =
	 * data is right
	 * 
	 * @param TextField     userName
	 * @param PasswordField password
	 * @return boolean
	 * @author jefter66
	 * @throws IOException
	 */
	public boolean valideLogin(TextField userNameOrEmail, PasswordField password, RadioButton btnStayConnected)
																					throws IOException {
		if (!isFieldEmpty(userNameOrEmail, password)) {
			if (em == null)
				this.em = Database.createEntityManager();
			Query q = this.em.createQuery("from UserRegistration where userName=:userName  and password=:password");
			q.setParameter("userName", userNameOrEmail.getText());
			q.setParameter("password", password.getText());

			if (!q.getResultList().isEmpty()) {
				SESSION.START_SESSION((UserRegistration) q.getResultList().get(0));
				if (btnStayConnected.isSelected())
					SERIALIZATION.doSerialization(SESSION.getUserLogged(), FileType.SESSION);
				
					UserRegistration u = (UserRegistration) SERIALIZATION.undoSerialization(FileType.SESSION);
				
					System.out.println(u.getCodUser() +  u.getEmail() +  u.getUserName());
					
						
					
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




















