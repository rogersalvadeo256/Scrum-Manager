package scenes;
import java.sql.SQLException;
import java.util.ArrayList;

import POJOs.Profile;
import POJOs.UserRegistration;
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
	public Button btnRegister;
	private ArrayList<TextField> field;
	private ArrayList<PasswordField>passwordField;
	private ArrayList<String> fieldName;
	private ArrayList<String> confirmationMessage;
	private FormsValidation validation;
	private UserRegistration registration;
	private Profile profile;
	public RegistrationFormScene() throws ClassNotFoundException, SQLException {	
		
		this.fieldName = new ArrayList<String>();
		this.field = new ArrayList<TextField>();
		this.passwordField = new ArrayList<PasswordField>();
		this.lblName = new Label("Nome");
		this.lblUserName = new Label("UserName");
		this.lblEmail = new Label("Email");
		this.lblPassword = new Label("Senha");
		this.lblConfirmPassword = new Label("Confirmação de senha");
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
		
		this.registration=new UserRegistration();
		this.profile=new Profile();
		
		this.field.add(txtName);
		this.field.add(txtEmail);
		this.field.add(txtUserName);
		this.passwordField.add(txtPasswordField);
		this.passwordField.add(txtPasswordConfirmation);

		this.fieldName.add("nome");
		this.fieldName.add("email");
		this.fieldName.add("nome de usuario");
		
		this.confirmationMessage = new ArrayList<String>();
		
		this.confirmationMessage.add("Cadastro realizado com sucesso");
		this.confirmationMessage.add("Voce está cadastrado no Scrum Manager");
		this.confirmationMessage.add("boa");
		
		this.validation=new FormsValidation(field, fieldName, passwordField);
		this.validation.setConfirmationMessage(confirmationMessage);

		this.btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					/*
					 * validação para campos vazios
					 */
					validation.setField(field);
					validation.setPasswordField(passwordField);
					validation.setFieldName(fieldName);

					profile.setName(RegistrationFormScene.this.txtName.getText());
					registration.setEmail(RegistrationFormScene.this.txtEmail.getText());
					registration.setUserName(RegistrationFormScene.this.txtUserName.getText());
					registration.setPassword(RegistrationFormScene.this.txtPasswordConfirmation.getText());
					registration.setProfile(profile);
					validation.setUserRegistration(RegistrationFormScene.this.registration);
					validation.registrationOfNewUser();
			}
		});
		this.getChildren().addAll(lblName,txtName,lblUserName,txtUserName,lblEmail,txtEmail);
		this.getChildren().addAll(lblPassword,txtPasswordField,lblConfirmPassword,txtPasswordConfirmation, btnRegister);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(5);
	}
	
	
}
