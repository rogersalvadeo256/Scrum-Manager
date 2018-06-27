package Validations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Consumer;

import Database.ValidateRegistrationData;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ValidationMethods {

	private ValidateRegistrationData data;
	private Alert formWarnings;
	private ArrayList<String> message;
	private ArrayList<String> alertMessage;
	private ArrayList<String> emptyAlert;
	private ArrayList<String> errorAlert;

	public ValidationMethods() throws ClassNotFoundException, SQLException {

		this.formWarnings = new Alert(null);

		this.data = new ValidateRegistrationData();

		this.message = new ArrayList<String>();

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

		 this.addMessage(name, email, userName, password, passwordConfirmation);

		if (!this.message.isEmpty()) {
			for (int i = 0; i < this.message.size(); i++) {
				this.alertMessage.add(this.message.get(i));
			}

			this.formWarnings.setAlertType(AlertType.ERROR);
			this.formWarnings.setTitle("Error");
			this.formWarnings.setHeaderText("aaaaa");

			StringBuilder mensagem = new StringBuilder();
			
			for (String msg : alertMessage) {
				mensagem.append( msg );
			}
			
			this.formWarnings.setContentText(mensagem.toString());
			this.formWarnings.show();
		}

		if (this.message.isEmpty()){
			data.insert(name.getText(), email.getText(), userName.getText(), password.getText());
			this.formWarnings.setAlertType(AlertType.CONFIRMATION);
			this.formWarnings.setTitle("Cadastrado");
			this.formWarnings.setContentText("Cadastro realizado com sucesso");
			this.formWarnings.show();
		}

	}

	public void addMessage(TextField name, TextField email, TextField userName, PasswordField password,
			PasswordField passwordConfirmation) throws SQLException {


		if (this.checkName(name).equals(emptyAlert.get(0))) {
			this.message.add(emptyAlert.get(0));
		}
		if (this.checkEmail(email).equals(emptyAlert.get(1))) {
			this.message.add(emptyAlert.get(1));
		}
		if (this.checkEmail(email).equals(errorAlert.get(0))) {
			this.message.add(errorAlert.get(0));
		}
		if (this.checkUserName(userName).equals(emptyAlert.get(2))) {
			this.message.add(emptyAlert.get(2));
		}
		if (this.checkUserName(userName).equals(errorAlert.get(1))) {
			this.message.add(errorAlert.get(1));
		}
		if (this.wrongPassword(password, passwordConfirmation).equals(emptyAlert.get(2))) {
			this.message.add(emptyAlert.get(3));
		}
		if (this.wrongPassword(password, passwordConfirmation).equals(errorAlert.get(1))) {
			this.message.add(errorAlert.get(1));
		}

	}

	public String checkName(TextField name) {
		if (name.getText().trim().isEmpty()) {
			return this.emptyAlert.get(0);
		}
		return null;
	}

	public String checkEmail(TextField email) throws SQLException {
		if (data.queryForExistentEmail(email.getText())) {
			return this.errorAlert.get(0);
		}
		if (email.getText().equals(null)) {
			return this.emptyAlert.get(1);
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
