package db.util;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import application.main.Window;
import db.pojos.USER_REGISTRATION;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SERIALIZATION;
import statics.SERIALIZATION.FileType;
import statics.SESSION;
import validation.CheckEmptyFields;
import view.popoups.ActivateAccount;
import widgets.alertMessage.CustomAlert;

public class Login {
	private CheckEmptyFields checkFields;

	public Login() {
		this.checkFields = new CheckEmptyFields();
	}

	/**
	 * The function to check if the data for login informed are right, return true = data
	 * is right
	 * 
	 * @param TextField
	 *            userName
	 * @param PasswordField
	 *            password
	 * @return boolean
	 * @author jefter66
	 * @throws IOException
	 */
	public boolean valideLogin(TextField userNameOrEmail, PasswordField password, RadioButton btnStayConnected) throws IOException {
		if (!isFieldEmpty(userNameOrEmail, password)) {
			
			@SuppressWarnings("rawtypes")
			List q = DB_OPERATION.QUERY("FROM USER_REGISTRATION WHERE USER_NAME=:USER_NAME AND USER_PASSWORD=:USER_PASSWORD",new String[] {"USER_NAME", "USER_PASSWORD"}, new String[] {userNameOrEmail.getText(), password.getText()});
					
			if(q.isEmpty()) { 
				q.clear();
				return false;
			}
			if (((USER_REGISTRATION) q.get(0)).getStatus().toString().equals(ENUMS.ACCOUNT_STATUS.INACTIVE.getValue().toString())) {

				Optional<ButtonType> result = new CustomAlert(AlertType.ERROR, "Usuario Inativo", "O usuario informado esta inativo", "Clique em OK para reativar a sua conta").showAndWait();
				if (result.get() == ButtonType.OK) {
					new ActivateAccount(Window.mainStage).showAndWait();
				}
			}
			if (!q.isEmpty()) {
				SESSION.START_SESSION((USER_REGISTRATION) q.get(0));
				if (btnStayConnected.isSelected())
					SERIALIZATION.serialization(SESSION.getUserLogged(), FileType.SESSION);
				q.clear();
				return true;
			}
			q.clear();
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

