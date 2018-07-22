package fields;

/*
 * LoginScreen.java
 * 
 * Created on: 28 jun de 2018
 * 		Autor: jefter66
 */
import java.sql.SQLException;
import java.util.ArrayList;
import Database.QuerysDataValidation;
import alert.message.MessageDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ValidationOfRegistration {

	/*
	* switch this for the hibernate stuff
	*/
	private QuerysDataValidation data;

	private Alert formWarnings;
	/*
	 * this two arrays will be populated for constants strings, and is used to check
	 * if some fields are empty or error with the database
	 */
	private ArrayList<String> arrayEmptyFieldMessage;
	private ArrayList<String> arrayErrorDataMessage;
	private ArrayList<String> arrayEverthingOK;

	/*
	 * after the validation this array will contain the constants messages
	 */
	private ArrayList<String> finalMessage;

	private final ArrayList<String> constArrayFieldEmpty;
	private final ArrayList<String> constArrayDataWrong;
	private final ArrayList<String> constArrayEverthingOK;

	private FieldValidation fempty;

	public ValidationOfRegistration() throws ClassNotFoundException, SQLException {

		
		this.formWarnings = new Alert(null);
		this.formWarnings.setWidth(60);
		this.formWarnings.setHeight(40);		
		this.fempty = new FieldValidation();
		this.data = new QuerysDataValidation();
		this.arrayErrorDataMessage = new ArrayList<String>();
		this.arrayEmptyFieldMessage = new ArrayList<String>();
		this.arrayEverthingOK = new ArrayList<String>();
		this.finalMessage = new ArrayList<String>();
		this.constArrayFieldEmpty = new ArrayList<String>();
		this.constArrayDataWrong = new ArrayList<String>();
		this.constArrayEverthingOK = new ArrayList<String>();

		this.constArrayFieldEmpty.add("Campo nome não foi preenchido");
		this.constArrayFieldEmpty.add("Email não informado");
		this.constArrayFieldEmpty.add("Nome de usuario não informado");
		this.constArrayFieldEmpty.add("Senha não informada");

		this.constArrayDataWrong.add("Email já cadastrado");
		this.constArrayDataWrong.add("Nome de usuario já cadastrado");
		this.constArrayDataWrong.add("As senhas digitadas não correspodem");

		this.constArrayEverthingOK.add("Name ok");
		this.constArrayEverthingOK.add("Email ok");
		this.constArrayEverthingOK.add("Username ok");
		this.constArrayEverthingOK.add("Senhas ok");
		
		MessageDialog dialog = new MessageDialog();
		dialog.applyCss();
		this.formWarnings.setDialogPane(dialog);
		
	}
	public void validation(TextField name, TextField email, TextField userName, PasswordField password,
			PasswordField passwordConfirmation) throws SQLException {

		this.emptyField(name, email, userName, password, passwordConfirmation);
		if (!this.arrayEmptyFieldMessage.isEmpty()) {
			fieldsEmptyAlert();
			return;
		}
		this.checkData(email, userName, password, passwordConfirmation);
		if (!this.arrayErrorDataMessage.isEmpty()) {
			errorData();
			return;
		}
		this.dataOkay(name, email, userName, password, passwordConfirmation);
		if (!this.arrayEverthingOK.isEmpty()) {
			this.arrayEverthingOK.clear();
			data.insert(name.getText(), userName.getText(), email.getText(), password.getText());
			setTheAlert(AlertType.CONFIRMATION, "Successful Registration", "Successful Registration", "LALALALALA")
					.show();
			return;
		}
	}
	public void errorData() throws SQLException {
		for (int i = 0; i < this.arrayErrorDataMessage.size(); i++) {
			this.finalMessage.add(this.arrayErrorDataMessage.get(i));
		}
		StringBuilder alertMessage = new StringBuilder();
		for (String msg : finalMessage) {
			alertMessage.append(msg + "\n");
		}
		this.formWarnings.setAlertType(AlertType.ERROR);
		this.formWarnings.setTitle("Error");
		this.formWarnings.setHeaderText("Algo está errado com os dados informados");
		this.formWarnings.setContentText(alertMessage.toString());
		this.formWarnings.show();
		this.finalMessage.clear();
	}
	public Alert setTheAlert(AlertType alert, String title, String header, String contentText) {
		this.formWarnings.setAlertType(alert);
		this.formWarnings.setTitle(title.toString());
		this.formWarnings.setHeaderText(header);
		this.formWarnings.setContentText(contentText);
		this.formWarnings.getContentText();	
		return this.formWarnings;
	}
	
	public void fieldsEmptyAlert() {

		for (int i = 0; i < this.arrayEmptyFieldMessage.size(); i++) {
			this.finalMessage.add(this.arrayEmptyFieldMessage.get(i));
		}
		StringBuilder message = new StringBuilder();
		for (String msg : finalMessage) {
			message.append(msg + "\n");
		}
		this.finalMessage.clear();
		this.arrayEmptyFieldMessage.clear();
		this.setTheAlert(AlertType.ERROR, "ERROR", "There is something wrong with the data informed",
				message.toString()).showAndWait();
		return;		
	}
	
	public void emptyField(TextField name, TextField email, TextField userName, PasswordField password,
			PasswordField passwordConfirmation) throws SQLException {
		if(this.fempty.isTextFieldEmpty(name)){	this.arrayEmptyFieldMessage.add(constArrayFieldEmpty.get(0));}
		if(this.fempty.isTextFieldEmpty(email)){ this.arrayEmptyFieldMessage.add(constArrayFieldEmpty.get(1));}
		if(this.fempty.isTextFieldEmpty(userName)){ this.arrayEmptyFieldMessage.add(constArrayFieldEmpty.get(2));}
		if(this.fempty.isPasswordFieldEmpty(password) || this.fempty.isPasswordFieldEmpty(passwordConfirmation)){this.arrayEmptyFieldMessage.add(constArrayFieldEmpty.get(3));}
	}


	/*
		change it
	*/
	public void checkData(TextField email, TextField userName, PasswordField password,
			PasswordField passwordConfirmation) throws SQLException {

		if (this.checkEmail(email).equals(constArrayDataWrong.get(0))) {
			this.arrayErrorDataMessage.add(constArrayDataWrong.get(0));
		}
		if (this.checkUserName(userName).equals(constArrayDataWrong.get(1))) {
			this.arrayErrorDataMessage.add(constArrayDataWrong.get(1));
		}
		if (this.wrongPassword(password, passwordConfirmation).equals(constArrayDataWrong.get(2))) {
			this.arrayErrorDataMessage.add(constArrayDataWrong.get(2));
		}
	}

	public void dataOkay(TextField name, TextField email, TextField userName, PasswordField password,
			PasswordField passwordConfirmation) throws SQLException {

		if (this.checkName(name).equals(constArrayEverthingOK.get(0))) {
			this.arrayEverthingOK.add(constArrayEverthingOK.get(0));
		}
		if (this.checkEmail(email).equals(constArrayEverthingOK.get(1))) {
			this.arrayEverthingOK.add(constArrayEverthingOK.get(1));
		}
		if (this.checkUserName(userName).equals(constArrayEverthingOK.get(2))) {
			this.arrayEverthingOK.add(constArrayEverthingOK.get(2));
		}
		if (this.wrongPassword(password, passwordConfirmation).equals(constArrayEverthingOK.get(3))) {
			this.arrayEverthingOK.add(constArrayEverthingOK.get(3));
		}
	}

	public String checkName(TextField name) {
		if (name.getText().trim().isEmpty()) {
			return this.constArrayFieldEmpty.get(0);
		}
		return this.constArrayEverthingOK.get(0);
	}

	public String checkEmail(TextField email) throws SQLException {
		if (email.getText().trim().isEmpty()) {
			return this.constArrayFieldEmpty.get(1);
		}
		if (data.queryForExistentEmail(email.getText())) {
			return this.constArrayDataWrong.get(0);
		}
		return this.constArrayEverthingOK.get(1);
	}

	public String checkUserName(TextField userName) throws SQLException {
		if (userName.getText().trim().isEmpty()) {
			return this.constArrayFieldEmpty.get(2);
		}
		if (data.queryForExistentUserName(userName.getText())) {
			return this.constArrayDataWrong.get(1);
		}
		return this.constArrayEverthingOK.get(2);
	}

	public String wrongPassword(PasswordField password, PasswordField passwordConfirmation) {

		if (password.getText().isEmpty() || passwordConfirmation.getText().isEmpty()) {
			return this.constArrayFieldEmpty.get(3);
		}

		if (!password.getText().equals(passwordConfirmation.getText())) {
			return this.constArrayDataWrong.get(2);
		}
		return this.constArrayEverthingOK.get(3);
	}
}