package scenes.popoups;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.query.criteria.internal.expression.ConcatExpression;
import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.pojos.UserRegistration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import listeners.Close;
import validation.CheckEmptyFields;
import widgets.designComponents.HBProfileContentForgotPassword;

public class ForgotPasswordPOPOUP extends Stage {

	private Label lblQuestion, lblEmailOrUsername, lblNewPasswrd, lblConfirmPassword;
	private TextField txtEmailUserName, txtAnswer;
	private PasswordField newPassword, passwordConfirmation;
	private EntityManager em;
	private Query q;
	private VBox layout;
	private Scene scene;
	private Button btnOk, btnCancel, btnEmail, btnAnswer, btnPasswords;
	private HBox hbButtons;
	private ToggleButton tbYes, tbNot;
	private HBProfileContentForgotPassword hbp;
	private ToggleGroup tbGroup;
	private CheckEmptyFields checkFields;
	private Profile p;
	private UserRegistration u;

	public ForgotPasswordPOPOUP(Window owner) throws IOException {
		init(owner);
	}

	public void init(Window owner) {

		this.checkFields = new CheckEmptyFields();

		this.lblQuestion = new Label("Este(ª) é voce?");

		this.lblEmailOrUsername = new Label("Digite seu email ou nome de usuario ");
		this.txtEmailUserName = new TextField();
		this.txtAnswer = new TextField();

		this.newPassword = new PasswordField();
		this.lblNewPasswrd = new Label("Digite uma nova senha: ");

		this.lblConfirmPassword = new Label("Confirmação de senha: ");
		this.passwordConfirmation = new PasswordField();

		this.hbButtons = new HBox();

		this.tbNot = new ToggleButton("Não");
		this.tbYes = new ToggleButton("Sim");

		this.hbButtons.setAlignment(Pos.CENTER);
		this.hbButtons.getChildren().addAll(tbNot, tbYes);

		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setOnAction(e -> {
			this.close();
		});
		this.btnOk = new Button("Ok");
		this.btnOk.setOnAction(e -> {
		});

		this.tbGroup = new ToggleGroup();
		this.tbNot.setToggleGroup(tbGroup);
		this.tbYes.setToggleGroup(tbGroup);

		this.layout = new VBox(10);
		this.scene = new Scene(layout);

		this.btnPasswords = new Button("Enviar");
		this.btnAnswer = new Button("Enviar");
		this.btnEmail = new Button("Enviar");
		valideEmail();

		btnEmail.setOnAction(e -> {
			findUser();
		});

		this.layout.setAlignment(Pos.CENTER);

		this.setScene(scene);

		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(400);
		this.setHeight(550);
		this.setResizable(false);
	}

	public void valideEmail() {
		this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName, this.btnEmail);

		this.btnEmail.setOnMouseClicked(e -> {
			if (checkFields.isTextFieldEmpty(txtEmailUserName)) {
				this.layout.getChildren().clear();
				this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName, this.btnEmail);
				this.layout.getChildren().add(new Label("Informe o seu email"));
				return;
			}

		});
		this.txtEmailUserName.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (checkFields.isTextFieldEmpty(txtEmailUserName)) {
					this.layout.getChildren().clear();
					this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName, this.btnEmail);
					this.layout.getChildren().add(new Label("Informe o seu email"));
					return;
				}
				findUser();
			}
		});
		this.txtEmailUserName.setOnMouseClicked(e -> {
			this.layout.getChildren().clear();
			this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName, this.btnEmail);
		});
	}

	private void findUser() {
		if (this.em == null)
			this.em = Database.createEntityManager();

		this.q = em.createQuery("from UserRegistration where email =:email or userName =:username");
		this.q.setParameter("email", txtEmailUserName.getText());
		this.q.setParameter("username", txtEmailUserName.getText());

		if (this.q.getResultList().isEmpty()) {
			this.layout.getChildren().add(new Label("Nenhum usuario encontrado"));
			return;
		}
		if (!this.q.getResultList().isEmpty()) {
			this.layout.getChildren().clear();

			this.u = (UserRegistration) this.q.getResultList().get(0);
			this.p = u.getProfile();
			try {
				this.hbp = new HBProfileContentForgotPassword(p);
				this.layout.getChildren().addAll(hbp, hbButtons);
				this.tbGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> old_value, Toggle toggle, Toggle new_toggle) {
						if (tbGroup.getSelectedToggle() == tbNot) {
						}
						if (tbGroup.getSelectedToggle() == tbYes) {
							securityQuestion();
						}
					}
				});
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	private void securityQuestion() {
		this.layout.getChildren().clear();
		this.lblQuestion.setText(this.u.getSecurityQuestion());

		this.layout.getChildren().addAll(this.hbp, this.lblQuestion, this.txtAnswer, this.btnAnswer);

		this.txtAnswer.setOnMouseClicked(e -> {
			this.layout.getChildren().clear();
			this.lblQuestion.setText(this.u.getSecurityQuestion());

			this.layout.getChildren().addAll(this.lblQuestion, this.txtAnswer, this.btnAnswer);

		});

		this.txtAnswer.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (checkFields.isTextFieldEmpty(this.txtAnswer)) {
					this.layout.getChildren().add(new Label("Digite a resposta"));
					return;
				}
				if (valideAnswer()) {
					setNewPassword();
					return;
				}
			}

		});
		this.btnAnswer.setOnAction(e -> {
			if (checkFields.isTextFieldEmpty(this.txtAnswer)) {
				this.layout.getChildren().add(new Label("Digite a resposta"));
				return;
			}

			if (valideAnswer()) {
				setNewPassword();
				return;
			}

		});

	}

	private boolean valideAnswer() {
		if (this.txtAnswer.getText().equals(this.u.getSecurityQuestionAnswer().toString()))
			return true;
		return false;

	}

	private void setNewPassword() {
		this.layout.getChildren().clear();
		this.layout.getChildren().addAll(this.hbp, this.lblNewPasswrd, this.newPassword, this.lblConfirmPassword, this.passwordConfirmation, this.btnPasswords);

		this.btnPasswords.setOnAction(e -> {
			if (checkFields.isPasswordFieldEmpty(newPassword) || checkFields.isPasswordFieldEmpty(passwordConfirmation)) {
				this.layout.getChildren().add(new Label("Informe uma nova senha"));
				return;
			}
			if(!newPassword.getText().equals(passwordConfirmation.getText())) { 
				this.layout.getChildren().add(new Label("Senhas não correspondem"));
				return;
			}
			if(newPassword.getText().length() < 8 || passwordConfirmation.getText().length() < 8) { 
				this.layout.getChildren().add(new Label("A senha deve ter no minimo 8 caracteres"));
				return;
			}
			UserRegistration update = this.u;
			
			update.setPassword(newPassword.getText());
			
			this.em.getTransaction().begin();
			this.em.merge(update );
			this.em.getTransaction().commit();
			this.em.clear();
			
			this.layout.getChildren().add(new Label("Senha alterada"));
			
			
		});
		this.passwordConfirmation.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER) { 
				if (checkFields.isPasswordFieldEmpty(newPassword) || checkFields.isPasswordFieldEmpty(passwordConfirmation)) {
					this.layout.getChildren().add(new Label("Informe uma nova senha"));
					return;
				}
			}
		});
		
		this.passwordConfirmation.setOnMouseClicked(e -> {
			drawComponentsNewPassword();
		});
		this.newPassword.setOnMouseClicked(e -> {
			drawComponentsNewPassword();
		});

		this.layout.setOnMouseClicked(e -> {
			drawComponentsNewPassword();
		});

	}
	private void drawComponentsNewPassword() {
		this.layout.getChildren().clear();
		this.layout.getChildren().addAll(this.hbp, this.lblNewPasswrd, this.newPassword, this.lblConfirmPassword, this.passwordConfirmation, this.btnPasswords);
	}

}
