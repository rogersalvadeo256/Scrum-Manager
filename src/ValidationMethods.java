
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	}

	public void validation(String name, String email, String userName, String password, String passwordConfirmation)
			throws SQLException {

		this.message.add(checkUserName(userName));
		this.message.add(checkEmail(email));
		this.message.add(wrongPassword(password, passwordConfirmation));

		if(!this.message.isEmpty()) {
			for (int i =0;i<this.message.size();i++) {
				this.alertMessage.add(this.message.get(i));
			}
			for(int j =0; j < alertMessage.size(); j++) {
				this.formWarnings.setContentText(alertMessage.get(j) + " \n");
			}
			this.formWarnings.setAlertType(AlertType.ERROR);
			this.formWarnings.setTitle("Error");
			this.formWarnings.show();
		}
		else { 
			data.insert(name, email, userName, password);
			this.formWarnings.setAlertType(AlertType.CONFIRMATION);
			this.formWarnings.setTitle("Cadastrado");
			this.formWarnings.setContentText("Cadastro realizado com sucesso");
			this.formWarnings.show();
		}

	}

	public String checkEmail(String email) throws SQLException {
		if (data.queryForExistentEmail(email)) {
			return "Email já cadastrado";
		}
		return null;
	}

	public String checkUserName(String userName) throws SQLException {

		if (data.queryForExistentUserName(userName)) {
			return "Nome de usuario já cadastrado";
		}
		return null;
	}

	public String wrongPassword(String password, String passwordConfirmation) {

		if (!password.equals(passwordConfirmation)) {
			return "As senhas digitadas não correspodem.";
		}
		return null;
	}
}
