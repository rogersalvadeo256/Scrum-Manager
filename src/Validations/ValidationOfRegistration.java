package Validations;

import java.sql.SQLException;
import java.util.ArrayList;
import Database.ValidateRegistrationData;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ValidationOfRegistration {

	private ValidateRegistrationData data;
	private Alert formWarnings;
	private ArrayList<String> messageEmptyField;
	private ArrayList<String> messageDataError;
	private ArrayList<String> alertMessage;

	private final ArrayList<String> emptyAlert;
	private final ArrayList<String> errorAlert;

	public ValidationOfRegistration() throws ClassNotFoundException, SQLException {

		this.formWarnings = new Alert(null);

		this.formWarnings.setWidth(60);
		this.formWarnings.setHeight(40);

		this.data = new ValidateRegistrationData();

		this.messageDataError = new ArrayList<String>();
		this.messageEmptyField = new ArrayList<String>();

		this.alertMessage = new ArrayList<String>();

		this.emptyAlert = new ArrayList<String>();
		this.errorAlert = new ArrayList<String>();

		this.emptyAlert.add("Campo nome não foi preenchido");
		this.emptyAlert.add("Email não informado");
		this.emptyAlert.add("Nome de usuario não informado");
		this.emptyAlert.add("Senha não informada");

		this.errorAlert.add("Email já cadastrado");
		this.errorAlert.add("Nome de usuario já cadastrado");
		this.errorAlert.add("As senhas digitadas não correspodem");

	}

	public void validation(TextField name, TextField email, TextField userName, PasswordField password,
			PasswordField passwordConfirmation) throws SQLException {

		this.emptyField(name, email, userName, password, passwordConfirmation);
		
		if (!this.messageEmptyField.isEmpty()) {
			fieldsEmptyAlert();
		}
		
		
		this.checkData(email, userName, password, passwordConfirmation);
		
		if(!this.messageDataError.isEmpty()) {
			errorData();
		}
		
		
		
		if (this.messageEmptyField.isEmpty()) {
			data.insert(name.getText(), email.getText(), userName.getText(), password.getText());
			this.formWarnings.setAlertType(AlertType.CONFIRMATION);
			this.formWarnings.setTitle("Cadastrado");
			this.formWarnings.setContentText("Cadastro realizado com sucesso");
			this.formWarnings.show();
		}

	}
	
	public void errorData() throws SQLException {
		for (int i = 0; i < this.messageEmptyField.size(); i++) {
			this.alertMessage.add(this.messageDataError.get(i));
		}
		StringBuilder message = new StringBuilder();
		for (String msg : alertMessage) {
			message.append(msg + "\n");
		}
		this.formWarnings.setAlertType(AlertType.ERROR);
		this.formWarnings.setTitle("Error");
		this.formWarnings.setHeaderText("Algo está errado com os dados informados");
		this.formWarnings.setContentText(message.toString());
		this.formWarnings.show();
	}

	public void fieldsEmptyAlert() {
		
		for (int i = 0; i < this.messageEmptyField.size(); i++) {
			this.alertMessage.add(this.messageEmptyField.get(i));
		}

		StringBuilder message = new StringBuilder();

		for (String msg : alertMessage) {
			message.append(msg + "\n");
		}
		this.formWarnings.setAlertType(AlertType.ERROR);
		this.formWarnings.setTitle("Error");
		this.formWarnings.setHeaderText("Algo está errado com os dados informados");
		this.formWarnings.setContentText(message.toString());
		this.formWarnings.show();

	}
	public void emptyField(TextField name, TextField email, TextField userName, PasswordField password,
			PasswordField passwordConfirmation) throws SQLException {

		if (this.checkName(name).equals(emptyAlert.get(0))) {
			this.messageEmptyField.add(emptyAlert.get(0));
		}
		if (this.checkUserName(userName).equals(emptyAlert.get(2))) {
			this.messageEmptyField.add(emptyAlert.get(2));
		}
		if (this.checkEmail(email).equals(emptyAlert.get(1))) {
			this.messageEmptyField.add(emptyAlert.get(1));
		}
		if (this.wrongPassword(password, passwordConfirmation).equals(emptyAlert.get(2))) {
			this.messageEmptyField.add(emptyAlert.get(3));
		}
	}

	public void checkData(TextField email, TextField userName, PasswordField password,
			PasswordField passwordConfirmation) throws SQLException {

		if (this.checkEmail(email).equals(errorAlert.get(0))) {
			this.messageDataError.add(errorAlert.get(0));
		}
		if (this.checkUserName(userName).equals(errorAlert.get(1))) {
			this.messageDataError.add(errorAlert.get(1));
		}
		if (this.wrongPassword(password, passwordConfirmation).equals(errorAlert.get(1))) {
			this.messageDataError.add(errorAlert.get(1));
		}
	}

	public void resetAlert() {
		this.formWarnings.setTitle(null);
		this.formWarnings.setHeaderText(null);
		this.formWarnings.setContentText(null);
	}
	
	public String checkName(TextField name) {
		if (name.getText().trim().isEmpty()) {
			return this.emptyAlert.get(0);
		}
		return null;
	}

	public String checkEmail(TextField email) throws SQLException {
		if (email.getText().trim().isEmpty()) {
			return this.emptyAlert.get(1);
		}
		if (data.queryForExistentEmail(email.getText())) {
			return this.errorAlert.get(1);
		}
		return null;
	}

	public String checkUserName(TextField userName) throws SQLException {
		if (userName.getText().trim().isEmpty()) {
			return this.emptyAlert.get(2);
		}
		if (data.queryForExistentUserName(userName.getText())) {
			return this.errorAlert.get(1);
		}
		return null;
	}

	public String wrongPassword(PasswordField password, PasswordField passwordConfirmation) {

		if (password.getText().isEmpty() || passwordConfirmation.getText().isEmpty()) {
			return this.emptyAlert.get(3);
		}

		if (!password.getText().equals(passwordConfirmation.getText())) {
			return this.errorAlert.get(2);
		}
		return null;
	}
}