package scenes;
import java.sql.SQLException;
import java.util.ArrayList;

import design.objects.LabelWithIcon;
import design.objects.LabelWithIcon.LabelType.BackgroundColor;
import design.objects.LabelWithIcon.LabelType.BackgroundHoverColor;
import design.objects.LabelWithIcon.LabelType.Type;
import fields.FormValidation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegistrationForm extends VBox {

	private LabelWithIcon lblName, lblUserName, lblEmail, lblPassword, lblConfirmPassword;
	private TextField txtName, txtUserName, txtEmail;
	private PasswordField txtPasswordField, txtPasswordConfirmation;
	private Button btnRegister;
	private ArrayList<TextField> field;
	private ArrayList<PasswordField>passwordField;
	private ArrayList<String> fieldName;
	private ArrayList<String> message;
	private FormValidation validation;
	
	public RegistrationForm() throws ClassNotFoundException, SQLException {	
		String css = this.getClass().getResource("/cssStyles/loginScene.css").toExternalForm();
		this.getStylesheets().add(css);
		
		this.fieldName = new ArrayList<String>();
		this.field = new ArrayList<TextField>();
		this.passwordField = new ArrayList<PasswordField>();
		this.lblName = new LabelWithIcon("NAME", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblUserName = new LabelWithIcon("USERNAME", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblEmail = new LabelWithIcon("EMAIL", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblPassword = new LabelWithIcon("PASSWORD", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblConfirmPassword = new LabelWithIcon("CONFIRM PASSWORD", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
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
		
		this.validation=new FormValidation(field, fieldName, passwordField);
		this.validation.setConfirmationMessage(message);
		
		this.getChildren().addAll(lblName,txtName,lblUserName,txtUserName,lblEmail,txtEmail,lblPassword,txtPasswordField,lblConfirmPassword,txtPasswordConfirmation, btnRegister);
		this.btnRegister.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				/*
				 * make the function valide() had a boolean return and do a conditional for insert the data in the db
				 */
				if(RegistrationForm.this.validation.isDataValid()) {
//					 insert  into db
					
				} else { 
//					treat the error
				}
			}
		});
		this.setAlignment(Pos.CENTER);
		this.setSpacing(5);
	}

}


















































