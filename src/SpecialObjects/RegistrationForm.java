package SpecialObjects;

import java.sql.SQLException;

import Validations.ValidationOfRegistration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrationForm extends VBox {

	private Label lblName, lblUserName, lblEmail, lblPassword, lblConfirmPassword;
	private TextField txtName, txtUserName, txtEmail;
	private PasswordField passwordField, confirmPasswordField;
	private Button btnRegister;

	private ValidationOfRegistration validation;


	
	public RegistrationForm() throws ClassNotFoundException, SQLException {

		this.lblName = new Label("NAME");
		this.lblUserName = new Label("USERNAME");
		this.lblEmail = new Label("EMAIL");
		this.lblPassword = new Label("PASSWORD");
		this.lblConfirmPassword = new Label("CONFIRM PASSWORD");

		this.validation = new ValidationOfRegistration();
		
		this.txtName = new TextField();
		this.txtUserName = new TextField();
		this.txtEmail = new TextField();

		this.txtName.setAlignment(Pos.CENTER);
		this.txtUserName.setAlignment(Pos.CENTER);
		this.txtEmail.setAlignment(Pos.CENTER);
		
		this.passwordField = new PasswordField();
		this.confirmPasswordField = new PasswordField();
		
		this.passwordField.setAlignment(Pos.CENTER);
		this.confirmPasswordField.setAlignment(Pos.CENTER);

		this.btnRegister = new Button("OK");

		
		this.getChildren().addAll(lblName,txtName,lblUserName,txtUserName,lblEmail,txtEmail,lblPassword,passwordField,lblConfirmPassword,confirmPasswordField, btnRegister);
		
		this.btnRegister.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					/*
					 * before the stuff typed from the registration, will be calling a validation,
					 * using a class named ValidationOfRegistration, go to check if are some field
					 * are empty and if the informed data are acceptable (if already exist in the
					 * database and etc)
					 */
					validation.validation(RegistrationForm.this.txtName, RegistrationForm.this.txtEmail, RegistrationForm.this.txtUserName, RegistrationForm.this.passwordField,
							RegistrationForm.this.confirmPasswordField);

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		this.setAlignment(Pos.CENTER);
		this.setSpacing(2);

	}

}
