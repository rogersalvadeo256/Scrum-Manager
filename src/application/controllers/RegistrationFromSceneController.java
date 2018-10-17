package application.controllers;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import statics.ENUMS;
import statics.ENUMS.DISPONIBILITY_FOR_PROJECT;
import validation.FormsValidation;

public class RegistrationFromSceneController {
	private FormsValidation validation;
	private String email;
	
	public void setEventBtnLogin(ActionEvent e, ArrayList<TextField> field, ArrayList<String> fieldName,
																					ArrayList<PasswordField> passwordField,
																					TextField txtName,
																					ArrayList<String> confirmationMessage,
																					TextField txtUserName,
																					String email,
																					TextField txtQuestion,
																					TextField txtAnswer,
																					PasswordField txtPasswordField,
																					PasswordField txtPasswordConfirmation) throws ClassNotFoundException, FileNotFoundException, SQLException {

		USER_PROFILE p = new USER_PROFILE();
		USER_REGISTRATION u = new USER_REGISTRATION();

		this.validation = new FormsValidation(field, fieldName, passwordField);
		this.validation.setConfirmationMessage(confirmationMessage);

		validation.setField(field);
		validation.setPasswordField(passwordField);
		validation.setFieldName(fieldName);
		p.setName(txtName.getText());
		p.setAvailability(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue());
		u.setuDateRegistrated();
		u.setEmail(email);
		u.setUserName(txtUserName.getText());
		u.setPassword(txtPasswordConfirmation.getText());
		u.setSecurityQuestion(txtQuestion.getText());
		u.setSecurityAnswer(txtAnswer.getText());
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
																					PasswordField txtPasswordConfirmation) throws ClassNotFoundException, FileNotFoundException, SQLException {

			USER_PROFILE p = new USER_PROFILE();
			USER_REGISTRATION u = new USER_REGISTRATION();

			this.validation = new FormsValidation(field, fieldName, passwordField);
			this.validation.setConfirmationMessage(confirmationMessage);

			
			
			
			validation.setField(field);
			validation.setPasswordField(passwordField);
			validation.setFieldName(fieldName);
			p.setName(txtName.getText());
			p.setAvailability(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue());
			u.setEmail(txtEmail.getText());
			u.setuDateRegistrated();
			u.setUserName(txtUserName.getText());
			u.setPassword(txtPasswordConfirmation.getText());
			u.setSecurityQuestion(txtQuestion.getText());
			u.setSecurityAnswer(txtAnswer.getText());
			u.setProfile(p);
			validation.setUserRegistration(u);
			validation.registrationOfNewUser();
	}
	
	
	
}
