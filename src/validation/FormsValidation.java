package validation;

import java.util.ArrayList;

import alert.message.CustomAlert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FormsValidation {

	/*
	 * create a class to check stuffs in the db and make the functions here
	 */
	private ArrayList<String> outMessage;
	private ArrayList<String> errorDataMessage;
	private ArrayList<String> errorFieldMessage;
	private ArrayList<String> confirmationMessage;
	private ArrayList<TextField> txtField;
	private ArrayList<String> fieldName;
	private ArrayList<PasswordField> passwordField;
	private CheckEmptyFields checkFields;

	public FormsValidation(ArrayList<TextField> field, ArrayList<String> fieldName,
			ArrayList<PasswordField> passwordField) {
		this.outMessage = new ArrayList<String>();
		this.checkFields = new CheckEmptyFields();
		this.errorFieldMessage = new ArrayList<String>();
		this.errorDataMessage = new ArrayList<String>();
		this.confirmationMessage = new ArrayList<String>();
		this.txtField = new ArrayList<TextField>();
		this.txtField = field;
		this.fieldName = new ArrayList<String>();
		this.fieldName = fieldName;
		this.passwordField = new ArrayList<PasswordField>();
		this.passwordField = passwordField;

	}

	private void setFieldForValidation() {
		this.errorFieldMessage.clear();
		for (int i = 0; i < txtField.size(); i++) {
			if (checkFields.isTextFieldEmpty(this.txtField.get(i))) {
				this.errorFieldMessage.add("O campo " + this.fieldName.get(i) + " não foi preenchido");
			}
			if(this.errorDataMessage.isEmpty()) {
			 if (emailInUse()) {
			 this.errorDataMessage.add("Email informado já em uso");
			 }
			 if (usernameInUse()) {
			 this.errorDataMessage.add("Nome de usuario já em uso");
			 }
			}
		}
	}
	// private void setPasswordFieldsForValidation() {
	// for (int i = 0; i < this.passwordField.size(); i++) {
	// if (checkFields.isPasswordFieldEmpty(passwordField.get(i))) {
	// this.errorFieldMessage.add("O campo senha não foi preenchido");
	// }
	// }
	// }
	public CustomAlert message(AlertType alert, String title, String header, String contentText) {
		return new CustomAlert(alert, title, header, contentText);
	}

	public boolean isDataValid() { // (boolean formContainPassword) {
		// if (formContainPassword) {
		// setPasswordFieldsForValidation();
		// }
		setFieldForValidation();
		/*
		 * add the query for checking the data
		 */
		if(!this.errorDataMessage.isEmpty() || !this.errorFieldMessage.isEmpty()) {
			StringBuilder fieldEmptymessage = new StringBuilder();
			StringBuilder dataErrorMessage = new StringBuilder();
			for (String msg : this.errorFieldMessage) {
				fieldEmptymessage.append(msg + "\n");
			}
			for(String msg: this.errorDataMessage) {
				dataErrorMessage.append(msg + "\n");
			}
			String outMessage = fieldEmptymessage.toString() + dataErrorMessage.toString();
			
			this.message(AlertType.ERROR, "ERRO", "Algo errado", outMessage.toString());
			outMessage = new String();
			this.outMessage.clear();
			this.errorFieldMessage.clear();
			this.errorDataMessage.clear();
			return false;
		}
		if (this.outMessage.isEmpty() && !this.confirmationMessage.isEmpty()) {
			this.message(AlertType.CONFIRMATION, this.confirmationMessage.get(0), this.confirmationMessage.get(1),
					this.confirmationMessage.get(2));
			this.outMessage.clear();
			this.errorFieldMessage.clear();
			this.errorDataMessage.clear();
			return true;
		}
		this.outMessage.clear();
		this.errorFieldMessage.clear();
		this.errorDataMessage.clear();
		return false;
	}
	/*
	 * do query in hibernate where
	 */
	private boolean emailInUse() {
		return true;
	}

	private boolean usernameInUse() {
		return true;
	}
	/*
	 * for setting the message in the dialog alert
	 */
	public void setConfirmationMessage(ArrayList<String> message) {
		this.confirmationMessage.clear();
		for (int i = 0; i < message.size(); i++) {
			/*
			 * the index 0 = title -- 1 = header -- 2 = content
			 */
			this.confirmationMessage.add(message.get(i));
		}
	}
}
