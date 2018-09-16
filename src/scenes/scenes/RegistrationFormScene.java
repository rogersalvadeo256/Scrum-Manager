package scenes.scenes;
import java.sql.SQLException;
import java.util.ArrayList;

import db.pojos.Profile;
import db.pojos.UserRegistration;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import validation.FormsValidation;

public class RegistrationFormScene extends VBox {

	private Label lblName, lblUserName, lblEmail, lblPassword, lblConfirmPassword, lblQuestion,lblAnswer;
	private TextField txtName, txtUserName, txtEmail,txtQuestion,txtAnswer;
	private PasswordField txtPasswordField, txtPasswordConfirmation;
	public Button btnRegister,btnCancel;
	private ArrayList<TextField> field;
	private ArrayList<PasswordField>passwordField;
	private ArrayList<String> fieldName;
	private ArrayList<String> confirmationMessage;
	private FormsValidation validation;
	private UserRegistration registration;
	private Profile profile;
	private HBox hbButtons;
	public RegistrationFormScene() throws ClassNotFoundException, SQLException {	
		
		this.fieldName = new ArrayList<String>();
		this.field = new ArrayList<TextField>();
		this.passwordField = new ArrayList<PasswordField>();
		
		this.lblName = new Label("Nome");
		this.lblUserName = new Label("UserName");
		this.lblEmail = new Label("Email");
		this.lblQuestion = new Label("Escreva uma pergunta de segurança");
		this.lblAnswer = new Label("Resposta da pergunta");
		this.lblPassword = new Label("Senha");
		this.lblConfirmPassword = new Label("Confirmação de senha");

		this.txtName = new TextField();
		this.txtUserName = new TextField();
		this.txtEmail = new TextField();
		this.txtQuestion=new TextField();
		this.txtAnswer = new TextField();
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
		
		this.hbButtons = new HBox(10);
		this.btnRegister = new Button("Cadastrar");
		this.btnCancel = new Button();
		
		this.hbButtons.getChildren().addAll(btnCancel,btnRegister);
		this.hbButtons.setAlignment(Pos.CENTER);
		
		this.field.add(txtName);
		this.field.add(txtEmail);
		this.field.add(txtUserName);
		this.field.add(txtQuestion);
		this.field.add(txtAnswer);
		this.passwordField.add(txtPasswordField);
		this.passwordField.add(txtPasswordConfirmation);
	
		
		
		this.fieldName.add("nome");
		this.fieldName.add("email");
		this.fieldName.add("nome de usuario");
		this.fieldName.add(" pergunta de segurança");
		this.fieldName.add(" resposta de segurança");
		
		this.confirmationMessage = new ArrayList<String>();
		
		this.confirmationMessage.add("Cadastro realizado com sucesso");
		this.confirmationMessage.add("Voce está cadastrado no Scrum Manager");
		this.confirmationMessage.add("boa");
		
		this.validation=new FormsValidation(field, fieldName, passwordField);
		this.validation.setConfirmationMessage(confirmationMessage);
		
		this.txtPasswordConfirmation.setOnKeyPressed(e ->{ 
			if(e.getCode() == KeyCode.ENTER) validationAndRegistration();
		});
		this.btnRegister.setOnAction(e ->{	validationAndRegistration();});
		this.getChildren().addAll(lblName,txtName,lblUserName,txtUserName,lblEmail,txtEmail, lblQuestion, txtQuestion,lblAnswer,txtAnswer);
		this.getChildren().addAll(lblPassword,txtPasswordField,lblConfirmPassword,txtPasswordConfirmation, hbButtons);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(5);
	}
	private void validationAndRegistration() { 
		RegistrationFormScene.this.registration=new UserRegistration();
		RegistrationFormScene.this.profile=new Profile();
		
		validation.setField(field);
		validation.setPasswordField(passwordField);
		validation.setFieldName(fieldName);

		profile.setName(this.txtName.getText());
		registration.setEmail(this.txtEmail.getText());
		registration.setUserName(this.txtUserName.getText());
		registration.setPassword(this.txtPasswordConfirmation.getText());
		registration.setSecurityQuestion(this.txtQuestion.getText());
		registration.setSecurityQuestionAnswer(this.txtAnswer.getText());
		registration.setProfile(profile);
		validation.setUserRegistration(this.registration);
		validation.registrationOfNewUser();
	}
	
}
