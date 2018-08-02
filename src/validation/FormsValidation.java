package validation;

import java.util.ArrayList;

import DB.queries.RegistrationDB;
import alert.message.CustomAlert;
import hibernatebook.annotations.Profile;
import hibernatebook.annotations.UserRegistration;
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
	private ArrayList<String> fieldName;
	private ArrayList<String> createUserRegistration;
	private ArrayList<TextField> txtField;
	private ArrayList<PasswordField> passwordField;
	private CheckEmptyFields checkFields;
	private UserRegistration user;
	private Profile profile;
	private RegistrationDB registration;

	public FormsValidation(ArrayList<TextField> field, ArrayList<String> fieldName,
			ArrayList<PasswordField> passwordField) {

		this.outMessage = new ArrayList<String>();
		this.errorFieldMessage = new ArrayList<String>();
		this.errorDataMessage = new ArrayList<String>();
		this.confirmationMessage = new ArrayList<String>();

		this.createUserRegistration = new ArrayList<String>();

		this.fieldName = new ArrayList<String>();

		this.txtField = new ArrayList<TextField>();
		this.passwordField = new ArrayList<PasswordField>();
		this.txtField = field;
		this.passwordField = passwordField;
		this.fieldName = new ArrayList<String>();
		this.fieldName = fieldName;

		this.checkFields = new CheckEmptyFields();

		this.user = new UserRegistration();
		this.profile = new Profile();

		this.registration = new RegistrationDB();
	}

	public void setFieldName(ArrayList<String> name) {
		this.fieldName = name;
	}

	public void setField(ArrayList<TextField> field) {
		this.txtField = field;
	}

	public void setPasswordField(ArrayList<PasswordField> password) {
		this.passwordField = password;
	}

	private ArrayList<PasswordField> getPasswordField() {
		return this.passwordField;
	}

	private ArrayList<TextField> getTextField() {
		return this.txtField;
	}

	private ArrayList<String> getFieldName() {
		return this.fieldName;
	}

	public void validationForEmptyField() {

		ArrayList<String> outputMessageContent = new ArrayList<String>();

		if (!this.getFieldName().isEmpty()) {
			for (int i = 0; i < this.getFieldName().size(); i++) {
				if (checkFields.isTextFieldEmpty(this.getTextField().get(i))) {
					outputMessageContent.add("O campo " + this.fieldName.get(i) + " não foi preenchido");
				}
			}
		}
		if (!this.getPasswordField().isEmpty()) {
			for (int i = 0; i < this.getPasswordField().size(); i++) {
				if(this.checkFields.isPasswordFieldEmpty(this.getPasswordField().get(i))) {
					outputMessageContent.add("O campo senha não foi preenchido");
				}
			}
		}
		
		StringBuilder buildContent = new StringBuilder();
		
		for (String msg : outputMessageContent) {
			buildContent.append(msg + "\n");
		}
}
	public void validationForExistentData(UserRegistration registration) { 
		
		
		
		
	}
	
	/*
	 * 
	 */
	private void checkFieldAreEmpty() {
		this.errorFieldMessage.clear();
		for (int i = 0; i < fieldName.size(); i++) {
			if (checkFields.isTextFieldEmpty(this.txtField.get(i))) {
				this.errorFieldMessage.add("O campo " + this.fieldName.get(i) + " não foi preenchido");
			}
		}
	}

	private void checkPasswordField() {
		for (int i = 0; i < this.passwordField.size(); i++) {
			if (checkFields.isPasswordFieldEmpty(passwordField.get(i))) {
				this.errorFieldMessage.add("O campo senha não foi preenchido");
			}
		}
		if (!this.passwordField.get(0).getText().equals(this.passwordField.get(1).getText()))
			this.errorDataMessage.add("Senhas diferentes");

		if (this.passwordField.get(0).getText().length() < 8)
			this.errorDataMessage.add("Senha muito curta");
	}

	public CustomAlert message(AlertType alert, String title, String header, String contentText) {
		return new CustomAlert(alert, title, header, contentText);
	}

	public boolean registration(boolean formContainPassword) {
		if (formContainPassword) {
			checkPasswordField();
		}
		checkFieldAreEmpty();
		/*
		 * add the query for checking the data
		 */
		if (!this.errorDataMessage.isEmpty() || !this.errorFieldMessage.isEmpty()) {
			StringBuilder fieldEmptymessage = new StringBuilder();
			StringBuilder dataErrorMessage = new StringBuilder();
			for (String msg : this.errorFieldMessage) {
				fieldEmptymessage.append(msg + "\n");
			}
			for (String msg : this.errorDataMessage) {
				dataErrorMessage.append(msg + "\n");
			}
			String outMessage = fieldEmptymessage.toString() + dataErrorMessage.toString();

			this.message(AlertType.ERROR, "ERRO", "Algo errado", outMessage.toString());

			/*
			 * the message and the arrays had to be cleaned after ever loop, otherwise the
			 * informations on the message dialog going to be huge
			 */

			outMessage = new String();
			this.outMessage.clear();
			this.errorFieldMessage.clear();
			this.errorDataMessage.clear();
			return false;
		}
		if (this.outMessage.isEmpty() && !this.confirmationMessage.isEmpty()) {
			/*
			 * if the data informed bring the user to this part, it means that everthing
			 * until this point is ok, then will be checked if some of the data is already
			 * in the database, if the return are true, the object was persist successfully
			 */
			this.createUserRegistration.add(txtField.get(2).getText().toString());
			this.createUserRegistration.add(txtField.get(1).getText().toString());
			this.createUserRegistration.add(txtField.get(0).getText().toString());
			/*
			 * this is the order of the parameter that had pass
			 */
			this.profile.setName(this.createUserRegistration.get(0));
			this.user.setEmail(this.createUserRegistration.get(1));
			this.user.setUserName(this.createUserRegistration.get(2));
			this.user.setProfile(profile);
			if (FormsValidation.this.registration.insertUser(user)) {
				this.message(AlertType.CONFIRMATION, this.confirmationMessage.get(0), this.confirmationMessage.get(1),
						this.confirmationMessage.get(2));
				this.outMessage.clear();
				this.errorFieldMessage.clear();
				this.errorDataMessage.clear();
				return true;
			}
		}
		this.outMessage.clear();
		this.errorFieldMessage.clear();
		this.errorDataMessage.clear();
		return false;
	}

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
