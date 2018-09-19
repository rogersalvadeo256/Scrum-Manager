package validation;

import java.util.ArrayList;

import db.pojos.UserRegistration;
import db.util.RegistrationDB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import widgets.alertMessage.CustomAlert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Make the validation of forms, functions for check if the fields are empty,
 * password not equal, data informed already registered and return of alert for
 * the data.
 * 
 * @author jefter66
 */
public class FormsValidation {

	/*
	 * create a class to check stuffs in the db and make the functions here
	 */
	private ArrayList<String> passwordFieldValidationMessage;
	private ArrayList<String> errorFieldMessage;
	private ArrayList<String> confirmationMessage;
	private ArrayList<String> fieldName;
	private ArrayList<TextField> txtField;
	private ArrayList<PasswordField> passwordField;
	private CheckEmptyFields checkFields;
	private UserRegistration userRegistration;
	private RegistrationDB registration;

	public FormsValidation(ArrayList<TextField> field, ArrayList<String> fieldName,
			ArrayList<PasswordField> passwordField) {

		this.errorFieldMessage = new ArrayList<String>();
		this.passwordFieldValidationMessage = new ArrayList<String>();
		this.confirmationMessage = new ArrayList<String>();

		this.fieldName = new ArrayList<String>();

		this.txtField = new ArrayList<TextField>();
		this.passwordField = new ArrayList<PasswordField>();
		this.txtField = field;
		this.passwordField = passwordField;
		this.fieldName = new ArrayList<String>();
		this.fieldName = fieldName;

		this.checkFields = new CheckEmptyFields();

		this.userRegistration = new UserRegistration();

		this.registration = new RegistrationDB();
	}

	/**
	 * Use to check if the fields are empty going to pupulate a arraylist with a
	 * message if the fields checked are empty
	 * 
	 * @author jefter66
	 */
	public String checkingForEmptyField() {

		ArrayList<String> outputMessageContent = new ArrayList<String>();

		if (!this.getFieldName().isEmpty()) {
			for (int i = 0; i < this.getFieldName().size(); i++) {
				if (checkFields.isTextFieldEmpty(this.getTextField().get(i))) {
					outputMessageContent.add("O campo " + this.fieldName.get(i) + " não foi preenchido");
				}
			}
		}
		if (!this.getPasswordField().isEmpty()) {
			for (int i = 1; i < this.getPasswordField().size(); i++) {
				if (this.checkFields.isPasswordFieldEmpty(this.getPasswordField().get(i))) {
					outputMessageContent.add("O campo senha não foi preenchido");
				}
			}
		}
		StringBuilder buildContent = new StringBuilder();

		for (String msg : outputMessageContent) {
			buildContent.append(msg + "\n");
		}
		outputMessageContent.clear();
		return buildContent.toString();
	}

	/**
	 * check if the passwordfields on the form is empty and also if the passwords
	 * are equal
	 * 
	 * @author jefter66
	 */
	private String checkingForEmptyPasswordField() {
		ArrayList<String> outputMessageContent = new ArrayList<String>();
		for (int i = 1; i < this.passwordField.size(); i++) {
			if (checkFields.isPasswordFieldEmpty(passwordField.get(i))) {
				this.errorFieldMessage.add("O campo senha não foi preenchido");
			}
		}
		StringBuilder buildContent = new StringBuilder();
		for (String msg : outputMessageContent) {
			buildContent.append(msg + "\n");
		}
		outputMessageContent.clear();
		return buildContent.toString();
	}

	/**
	 * Check all the PasswordFields on the form and add a error message for the
	 * fields that are empty, if the passwordfield contents are different, going to
	 * add a message for fixed this
	 * 
	 * @return String buildContent
	 * @author jefter66
	 */
	public String validationForPassword(boolean passwordToShort) {
		if (passwordToShort) {
			if (this.passwordField.get(0).getText().length() < 1) // 8)
				this.passwordFieldValidationMessage.add("Senha muito curta");
			StringBuilder builContent = new StringBuilder();
			for (String msg : this.passwordFieldValidationMessage) {
				builContent.append(msg);
			}
			this.passwordFieldValidationMessage.clear();
			return builContent.toString();
		}
		if (!this.passwordField.get(0).getText().equals(this.passwordField.get(1).getText())) {
			this.passwordFieldValidationMessage.add("Senhas diferentes");
			StringBuilder buildContent = new StringBuilder();
			for (String msg : this.passwordFieldValidationMessage) {
				buildContent.append(msg);
			}
			this.passwordFieldValidationMessage.clear();
			return buildContent.toString();
		}
		return new String();
	}

	/**
	 * For registration of new user. If the registration go's ok, then, the return
	 * will be true, otherwise false
	 * 
	 * @author jefter66
	 * @param UserRegistration
	 * @return boolean
	 */
	public boolean validationForExistentUserName(UserRegistration registration) {
		if (this.registration.userExist(registration))
			return false;
		return true;
	}
	/**
	 * For registration of new user. If the registration go's ok, then, the return
	 * will be true, otherwise false
	 * 
	 * @author jefter66
	 * @param UserRegistration
	 * @return boolean
	 */
	public boolean validationForExistentEmail(UserRegistration registration) {
		if (this.registration.emailExist(registration))return false;
		return true;
	}

	public CustomAlert message(AlertType alert, String title, String header, String contentText) {
		return new CustomAlert(alert, title, header, contentText);
	}

	/**
	 * Make the registration of new user after do all the validations of empty
	 * fields and data existent
	 * 
	 * @return Alert
	 */
	public Alert registrationOfNewUser() {
		String fieldEmptyMessage = checkingForEmptyField();
		String passwordFieldEmptyMessage = checkingForEmptyPasswordField();
		String returnMessage;
		if (!fieldEmptyMessage.isEmpty() || !passwordFieldEmptyMessage.isEmpty()) {
			returnMessage = new String();
			returnMessage = fieldEmptyMessage + "\n" + passwordFieldEmptyMessage;
			fieldEmptyMessage = new String();
			passwordFieldEmptyMessage = new String();
			return message(AlertType.ERROR, "Algo está errado", "Erro ao tentar cadastrar", returnMessage);
		}
		String passwordErrorMessage = new String();
		if (!validationForPassword(true).isEmpty()) {
			passwordErrorMessage = new String();
			passwordErrorMessage = validationForPassword(true);
			return message(AlertType.ERROR, "Algo está errado", "Erro ao tentar cadastrar", passwordErrorMessage);
		}
		String passwordFieldErrorMessage = new String();
		if (!validationForPassword(false).isEmpty()) {
			passwordFieldEmptyMessage = new String();
			passwordFieldEmptyMessage = validationForPassword(false);
			return message(AlertType.ERROR, "Algo está errado", "Erro ao tentar cadastrar", passwordFieldErrorMessage);
		}
		if (!validationForExistentUserName(this.getUserRegistration())) {
			return message(AlertType.ERROR, "Algo está errado", "Erro ao tentar cadastrar",
					"Nome de usuario já está cadastrado");
		}
		if (!validationForExistentEmail(this.getUserRegistration())) {
			return message(AlertType.ERROR, "Algo está errado", "Erro ao tentar cadastrar",
					"Email já está cadastrado");
		}
		registration.insertUser(getUserRegistration());
		return message(AlertType.CONFIRMATION, "Cadastrado", "Cadastro realizado com sucesso", "Voce está cadastrado");
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

	public void setUserRegistration(UserRegistration registration) {
		this.userRegistration = registration;
	}

	private UserRegistration getUserRegistration() {
		return this.userRegistration;
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
}
