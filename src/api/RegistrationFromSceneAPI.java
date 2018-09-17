package api;

import java.util.ArrayList;

import db.pojos.Profile;
import db.pojos.UserRegistration;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import validation.FormsValidation;

public class RegistrationFromSceneAPI {
	private FormsValidation validation;

	public void setEventBtnLogin(ActionEvent e, ArrayList<TextField> field, ArrayList<String> fieldName,
																					ArrayList<PasswordField> passwordField,
																					TextField txtName,
																					ArrayList<String> confirmationMessage,
																					TextField txtUserName,
																					TextField txtEmail,
																					TextField txtQuestion,
																					TextField txtAnswer,
																					PasswordField txtPasswordField,
																					PasswordField txtPasswordConfirmation) {

		Profile p = new Profile();
		UserRegistration u = new UserRegistration();

		this.validation = new FormsValidation(field, fieldName, passwordField);
		this.validation.setConfirmationMessage(confirmationMessage);

		validation.setField(field);
		validation.setPasswordField(passwordField);
		validation.setFieldName(fieldName);
		p.setName(txtName.getText());
		u.setEmail(txtEmail.getText());
		u.setUserName(txtUserName.getText());
		u.setPassword(txtPasswordConfirmation.getText());
		u.setSecurityQuestion(txtQuestion.getText());
		u.setSecurityQuestionAnswer(txtAnswer.getText());
		u.setProfile(p);
		validation.setUserRegistration(u);
		validation.registrationOfNewUser();
	}

	public void setEventPasswordField(KeyEvent e, ArrayList<TextField> field, ArrayList<String> fieldName,
																					ArrayList<PasswordField> passwordField,
																					TextField txtName,
																					ArrayList<String> confirmationMessage,
																					TextField txtUserName,
																					TextField txtEmail,
																					TextField txtQuestion,
																					TextField txtAnswer,
																					PasswordField txtPasswordField,
																					PasswordField txtPasswordConfirmation) {

			Profile p = new Profile();
			UserRegistration u = new UserRegistration();

			this.validation = new FormsValidation(field, fieldName, passwordField);
			this.validation.setConfirmationMessage(confirmationMessage);

			validation.setField(field);
			validation.setPasswordField(passwordField);
			validation.setFieldName(fieldName);
			p.setName(txtName.getText());
			u.setEmail(txtEmail.getText());
			u.setUserName(txtUserName.getText());
			u.setPassword(txtPasswordConfirmation.getText());
			u.setSecurityQuestion(txtQuestion.getText());
			u.setSecurityQuestionAnswer(txtAnswer.getText());
			u.setProfile(p);
			validation.setUserRegistration(u);
			validation.registrationOfNewUser();
	}
}
