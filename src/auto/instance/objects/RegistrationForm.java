package auto.instance.objects;

import java.sql.SQLException;

import design.objects.LabelWithIcon;
import design.objects.LabelWithIcon.LabelType.BackgroundColor;
import design.objects.LabelWithIcon.LabelType.BackgroundHoverColor;
import design.objects.LabelWithIcon.LabelType.Type;
import fields.validation.ValidationOfRegistration;
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

	private LabelWithIcon lblName, lblUserName, lblEmail, lblPassword, lblConfirmPassword;
	private TextField txtName, txtUserName, txtEmail;
	private PasswordField passwordField, confirmPasswordField;
	private Button btnRegister;

	private ValidationOfRegistration validation;

	public RegistrationForm() throws ClassNotFoundException, SQLException {	
		String css = this.getClass().getResource("/cssStyles/loginScene.css").toExternalForm();
		this.getStylesheets().add(css);
		
		this.lblName = new LabelWithIcon("NAME", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblUserName = new LabelWithIcon("USERNAME", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblEmail = new LabelWithIcon("EMAIL", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblPassword = new LabelWithIcon("PASSWORD", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblConfirmPassword = new LabelWithIcon("CONFIRM PASSWORD", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.validation = new ValidationOfRegistration();
		this.txtName = new TextField();
		this.txtUserName = new TextField();
		this.txtEmail = new TextField();
		this.passwordField = new PasswordField();
		this.confirmPasswordField = new PasswordField();
		this.txtName.setMaxWidth(300);
		this.txtUserName.setMaxWidth(300);
		this.txtEmail.setMaxWidth(300);
		this.passwordField.setMaxWidth(300);
		this.confirmPasswordField.setMaxWidth(300);
		this.txtName.setAlignment(Pos.CENTER);
		this.txtUserName.	setAlignment(Pos.CENTER);
		this.txtEmail.setAlignment(Pos.CENTER);
		this.passwordField.setAlignment(Pos.CENTER);
		this.confirmPasswordField.setAlignment(Pos.CENTER);
		this.btnRegister = new Button("OK");
		this.getChildren().addAll(lblName,txtName,lblUserName,txtUserName,lblEmail,txtEmail,lblPassword,passwordField,lblConfirmPassword,confirmPasswordField, btnRegister);
		this.btnRegister.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					validation.validation(RegistrationForm.this.txtName, RegistrationForm.this.txtEmail, RegistrationForm.this.txtUserName, RegistrationForm.this.passwordField,
							RegistrationForm.this.confirmPasswordField);

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		this.setAlignment(Pos.CENTER);
		this.setSpacing(5);
	}

}
