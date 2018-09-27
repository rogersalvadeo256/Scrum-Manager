package db.util;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;
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
			Query q = this.em.createQuery("FROM USER_REGISTRATION WHERE USER_NAME=:USER_NAME AND USER_PASSWORD=:USER_PASSWORD");
			q.setParameter("USER_NAME", userNameOrEmail.getText());
			q.setParameter("USER_PASSWORD", password.getText());

			if (!q.getResultList().isEmpty()) {
				SESSION.START_SESSION((USER_REGISTRATION) q.getResultList().get(0));
				if (btnStayConnected.isSelected())
					
					
					SERIALIZATION.serialization(SESSION.getUserLogged(), FileType.SESSION);
					
					USER_REGISTRATION u = (USER_REGISTRATION) SERIALIZATION.undoSerialization(FileType.SESSION);
//				
					
						
					
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




















