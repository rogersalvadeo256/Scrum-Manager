
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class ValidationMethods {

	private ValidateRegistrationData data;
	private Alert formWarnings;
	private ArrayList<String> message;
	private ArrayList<String> alertMessage;

	public ValidationMethods() throws ClassNotFoundException, SQLException {
		this.data = new ValidateRegistrationData();
		this.message = new ArrayList<String>();
		this.alertMessage = new ArrayList<String>();
		this.formWarnings = new Alert(null);
	}

	public void validation(TextField name, TextField email, TextField userName, TextField password,
			TextField passwordConfirmation) throws SQLException {
		this.message = null;

		if(!checkEmail(email) || email.getText() == null){
			this.alertMessage.add("there seems to have a problem with your email");
		}
		if (!(wrongPassword(password, passwordConfirmation))
				|| password.getText() == null && passwordConfirmation.getText() == null) {
			this.message.add("password is wrong");
		}
		if(!checkUserName(userName) || userName.getText() == null) {
			this.message.add("user name already in use");
		}
		
		if (!this.message.isEmpty()) {
			for (int i = 0; i < this.message.size(); i++) {
				this.alertMessage.add(this.message.get(i));
			}
			for (int j = 0; j < alertMessage.size(); j++) {
				this.formWarnings.setContentText(alertMessage.get(j) + " \n");
			}
			this.formWarnings.setAlertType(AlertType.ERROR);
			this.formWarnings.setTitle("Error");
			this.formWarnings.show();
		}
	}

	public boolean checkEmail(TextField email) throws SQLException {
		if (data.queryForExistentEmail(email.getText())) {
			return false;
		}
		return true;
	}

	public boolean checkUserName(TextField userName) throws SQLException {

		if (data.queryForExistentUserName(userName.getText())) {
			return false;
		}
		return true;
	}

	public boolean wrongPassword(TextField password, TextField passwordConfirmation) {

		if (!password.getText().equals(passwordConfirmation.getText())) {
			return false;
		}
		return true;
	}
}
