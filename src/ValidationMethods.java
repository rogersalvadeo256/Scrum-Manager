
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class ValidationMethods {

	private ValidateRegistrationData data;
	private Alert formWarnings;
	private Alert emailWarning;
	private Alert userNameWarning;
	private Alert wrongPassword;
	private Alert sucessufullRegistered;
	private Alert unknowError;

	public ValidationMethods() throws ClassNotFoundException, SQLException {
		this.data = new ValidateRegistrationData();

		this.formWarnings = new Alert(AlertType.WARNING, "O  digitado é muito grande");
		this.emailWarning = new Alert(AlertType.WARNING, "O email informado já está cadastrado");
		this.userNameWarning = new Alert(AlertType.WARNING, "Este nome de usuario já está em uso");
		this.wrongPassword = new Alert(AlertType.WARNING, "As senhas não correspondem, digite novamente");
		this.unknowError = new Alert(AlertType.ERROR, "Algo no seu cadastro está errado");
		this.sucessufullRegistered = new Alert(AlertType.CONFIRMATION, "Cadastro com sucesso");
	
	}

	public void textTooBig(String texto, int lengthAllowed) {
		if (texto.toString().length() > lengthAllowed) {
			formWarnings.setTitle("Erro");
			formWarnings.show();
		}
	}
	public void emailAlreadyRegistered() {
		emailWarning.setTitle("Erro com o email");
		emailWarning.show();
	}

	public void userNameAlearyTaken() {
		userNameWarning.setTitle("Erro com o nome de usuario");
		userNameWarning.show();
	}

	public void wrongPassword(String password, String confirmPassword, TextField txtPassword,
			TextField txtConfirmPassword) {

		if (!password.equals(confirmPassword)) {
			wrongPassword.setTitle("Senhas diferentes");
			wrongPassword.show();
			txtPassword.clear();
			txtConfirmPassword.clear();
		}
	}

	public boolean wrongPassword(String password, String confirmPassword) {

		if (!password.equals(confirmPassword)) {
			wrongPassword.setTitle("Senhas diferentes");
			wrongPassword.show();
			return true;
		}
		return false;
	}

	public void sucessufullRegistered( boolean email,boolean userName, boolean password)
			throws SQLException {

		if(userName && email && password) { 
			
			unknowError.setTitle("usuario de bosta");
			unknowError.show();
				
		} else {

			sucessufullRegistered.setTitle("CADASTRADO");
			sucessufullRegistered.show();
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
