package Validations;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ValidationLogin {

	private TextField emailUser;
	private PasswordField password;

	public ValidationLogin(TextField emailUser, PasswordField password) {

		this.emailUser = emailUser;
		this.password = password;

	}

	public boolean checkForEmptyField() {
		if (emailUser.getText().trim().isEmpty() && password.getText().isEmpty()) {
			return true;
		}
		return false;
	}
}
