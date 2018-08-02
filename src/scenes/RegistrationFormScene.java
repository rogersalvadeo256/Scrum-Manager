package scenes;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import validation.FormsValidation;

public class RegistrationFormScene extends VBox {

	private Label lblName, lblUserName, lblEmail, lblPassword, lblConfirmPassword;
	private TextField txtName, txtUserName, txtEmail;
	private PasswordField txtPasswordField, txtPasswordConfirmation;
	private Button btnRegister;
	private ArrayList<TextField> field;
	private ArrayList<PasswordField>passwordField;
	private ArrayList<String> fieldName;
	private ArrayList<String> message;
	private FormsValidation validation;
	private ArrayList<String> data;
	
	public RegistrationFormScene() throws ClassNotFoundException, SQLException {	
		String css = this.getClass().getResource("/cssStyles/loginScene.css").toExternalForm();
		this.getStylesheets().add(css);
		
		this.fieldName = new ArrayList<String>();
		this.field = new ArrayList<TextField>();
		this.passwordField = new ArrayList<PasswordField>();
		this.lblName = new Label("NAME");
		this.lblUserName = new Label("USERNAME");
		this.lblEmail = new Label("EMAIL");
		this.lblPassword = new Label("PASSWORD");
		this.lblConfirmPassword = new Label("CONFIRM PASSWORD");
		this.txtName = new TextField();
		this.txtUserName = new TextField();
		this.txtEmail = new TextField();
		this.txtPasswordField = new PasswordField();
		this.txtPasswordConfirmation = new PasswordField();

		this.txtName.setMaxWidth(300);
		this.txtUserName.setMaxWidth(300);
		this.txtEmail.setMaxWidth(300);
		this.txtPasswordField.setMaxWidth(300);
		this.txtPasswordConfirmation.setMaxWidth(300);
		this.txtName.setAlignment(Pos.CENTER);
		this.txtUserName.setAlignment(Pos.CENTER);
		this.txtEmail.setAlignment(Pos.CENTER);
		this.txtPasswordField.setAlignment(Pos.CENTER);
		this.txtPasswordConfirmation.setAlignment(Pos.CENTER);
		this.btnRegister = new Button("OK");
		this.data=new ArrayList<String>();
		
		this.field.add(txtName);
		this.field.add(txtEmail);
		this.field.add(txtUserName);
		this.passwordField.add(txtPasswordField);
		this.passwordField.add(txtPasswordConfirmation);

		this.fieldName.add("nome");
		this.fieldName.add("email");
		this.fieldName.add("nome de usuario");
		
		this.message = new ArrayList<String>();
		
		this.message.add("Cadastro realizado com sucesso");
		this.message.add("Voce está cadastrado no Scrum Manager");
		this.message.add("boa");
		
		this.validation=new FormsValidation(field, fieldName, passwordField);
		this.validation.setConfirmationMessage(message);

		this.btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(RegistrationFormScene.this.validation.registration(true)){
					RegistrationFormScene.this.data.add(RegistrationFormScene.this.txtName.getText());
					RegistrationFormScene.this.data.add(RegistrationFormScene.this.txtEmail.getText());
					RegistrationFormScene.this.data.add(RegistrationFormScene.this.txtUserName.getText());
					RegistrationFormScene.this.data.add(RegistrationFormScene.this.txtPasswordConfirmation.getText());
					RegistrationFormScene.this.data.clear();
				}
			}
		});
		this.getChildren().addAll(lblName,txtName,lblUserName,txtUserName,lblEmail,txtEmail);
		this.getChildren().addAll(lblPassword,txtPasswordField,lblConfirmPassword,txtPasswordConfirmation, btnRegister);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(5);
	}
}
