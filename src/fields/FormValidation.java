package fields;

import java.util.ArrayList;

import alert.message.CustomAlert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FormValidation {

	/*
	 * create a class to check stuffs in the db and make the functions here
	 */
	private static enum empty {
		YES, NO
	};
	private static enum data_error{
		YES,NO
	}
	
	private ArrayList<empty> field;
	private ArrayList<String> outMessage;
	private ArrayList<String> errorDataMessage;
	private ArrayList<String> errorFieldMessage;
	private ArrayList<String> confirmationMessage;
	private ArrayList<TextField> txtField;
	private ArrayList<String> fieldName;
	private ArrayList<PasswordField> passwordField;
	private FieldValidation checkFields;
	
	public FormValidation(ArrayList<TextField> field, ArrayList<String> fieldName, ArrayList<PasswordField> passwordField) {
		this.field = new ArrayList<empty>();
		this.outMessage = new ArrayList<String>();
		this.checkFields = new FieldValidation();
		this.errorFieldMessage = new ArrayList<String>();
		this.errorDataMessage = new ArrayList<String>();
		this.confirmationMessage = new ArrayList<String>();
		this.txtField = new ArrayList<TextField>();
		this.txtField =field;
		this.fieldName = new ArrayList<String>();
		this.fieldName=fieldName;
		this.passwordField =new ArrayList<PasswordField>();
		this.passwordField=passwordField;
		
	}

	private void setFieldForValidation() {
		this.errorFieldMessage.clear();
		for (int i = 0; i < field.size(); i++) {
			if (checkFields.isTextFieldEmpty(this.txtField.get(i))) {
				this.field.add(empty.YES);
				this.errorFieldMessage.add("O campo " + this.fieldName.get(i) + " não foi preenchido");
			} else {
				this.field.add(empty.NO);
			}
		}
	}

	private void setPasswordFieldsForValidation() {
		for (int i = 0; i < this.passwordField.size(); i++) {
			if (checkFields.isPasswordFieldEmpty(passwordField.get(i))) {
				this.errorFieldMessage.add("O campo senha não foi preenchido");
			}
		}
	}

	public CustomAlert message(AlertType alert, String title, String header, String contentText) {
		return new CustomAlert(alert, title, header, contentText);
	}

	private void valide(boolean formContainPassword) {
		if(formContainPassword) {
			setPasswordFieldsForValidation();
		}
		setFieldForValidation();
		/*
		 * add the query for checking the data
		 */
		for (int i = 0; i < field.size(); i++) {
			for (int j = 0; j < this.field.size(); j++) {
				this.outMessage.add(this.errorFieldMessage.get(j));
				this.outMessage.add(this.errorDataMessage.get(j));
			}
		}
		if (!this.errorFieldMessage.isEmpty()) {
			StringBuilder message = new StringBuilder();
			for (String msg : outMessage) {
				message.append(msg + "\n");
			}
			this.outMessage.clear();
			this.errorFieldMessage.clear();
			this.message(AlertType.ERROR, "Erro", "Algo está errado", message.toString());
			return;
		}
		if(!!this.errorDataMessage.isEmpty()) {
			
			StringBuilder message=new StringBuilder();
			for(String msg: outMessage) {
				message.append(msg + "\n");
			}
			
		}
		
	}

	/*
	 * for setting the message in the dialog alert
	 */
	public void setConfirmationMessage(ArrayList<String> message) {
		this.confirmationMessage.clear();
		for (int i = 0; i < message.size(); i++) {
			/*
			 * the index 0 - title 1 = header 2 = content
			 */
			this.confirmationMessage.add(message.get(i));
		}
	}

}
