package application.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import application.main.Window;
import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import statics.GENERAL_STORE;
import statics.SESSION;
import validation.CheckEmptyFields;
import view.popoups.ProfileEditPOPOUP;
import view.scenes.LoginScene;
import widgets.alertMessage.CustomAlert;

public class EditProfileController {

	private EntityManager em;
	private CheckEmptyFields check;
	private Button btnChangeQuestion, btnChangeAnswer;
	private Button btnDeleteAccount;

	public EditProfileController() {
		this.check = new CheckEmptyFields();
		this.btnChangeAnswer = new Button("Alterar");
		this.btnChangeQuestion = new Button("Alterar");
		this.btnDeleteAccount = new Button("Deletar conta");
	}

	/*
	 * this code is huge and redundant, but was the best way that i find to do this
	 * and understand later
	 */
	public void setEventAdvancedOptions(ActionEvent e, ProfileEditPOPOUP screen, VBox layout,
																					Stage stage,
																					HBox hbChangeAnswer,
																					HBox hbChangeQuestion,
																					HBox hbButtons,
																					Button btnBack) {

		layout.getChildren().clear();
		hbChangeAnswer.getChildren().clear();
		hbChangeQuestion.getChildren().clear();

		hbChangeQuestion.getChildren().add(new Label(SESSION.getUserLogged().getSecurityQuestion().toString()));
		hbChangeAnswer.getChildren().add(new Label(SESSION.getUserLogged().getSecurityAnswer().toString()));

		hbChangeQuestion.getChildren().add(this.btnChangeQuestion);
		hbChangeAnswer.getChildren().add(this.btnChangeAnswer);

		hbChangeAnswer.setAlignment(Pos.CENTER);
		hbChangeQuestion.setAlignment(Pos.CENTER);

		hbChangeAnswer.setSpacing(20);
		hbChangeQuestion.setSpacing(20);

		layout.getChildren().addAll(hbChangeQuestion);
		layout.getChildren().addAll(hbChangeAnswer);
		layout.getChildren().add(this.btnDeleteAccount);
		layout.getChildren().add(hbButtons);
		layout.setSpacing(20);

		this.btnChangeQuestion.setOnAction(e1 -> {

			hbChangeQuestion.getChildren().clear();

			TextField txtQuestion = new TextField();
			Button btnCancel = new Button("Cancelar");
			Button btnChange = new Button("Alterar");

			txtQuestion.setPromptText(SESSION.getUserLogged().getSecurityQuestion().toString());

			hbChangeQuestion.getChildren().addAll(txtQuestion, btnChange, btnCancel);

			btnChange.setOnAction(e2 -> {
				if (em == null)
					em = Database.createEntityManager();
				USER_REGISTRATION u = SESSION.getUserLogged();
				if (!check.isTextFieldEmpty(txtQuestion)) {

					u.setSecurityQuestion(txtQuestion.getText());
					em.getTransaction().begin();
					em.merge(u);
					em.getTransaction().commit();
					em.clear();
					em.close();
					em = null;
					SESSION.UPDATE_SESSION();
					hbChangeQuestion.getChildren().clear();

					hbChangeQuestion.getChildren().add(new Label(SESSION.getUserLogged().getSecurityQuestion().toString()));
					hbChangeQuestion.getChildren().add(btnChangeQuestion);
				}
			});
			btnCancel.setOnAction(e3 -> {
				hbChangeQuestion.getChildren().clear();
				hbChangeQuestion.getChildren().add(new Label(SESSION.getUserLogged().getSecurityQuestion().toString()));
				hbChangeQuestion.getChildren().add(btnChangeQuestion);
			});

		});
		this.btnChangeAnswer.setOnAction(e1 -> {

			hbChangeAnswer.getChildren().clear();
			TextField txtAnswer = new TextField();
			Button btnCancel = new Button("Cancelar");
			Button btnChange = new Button("Alterar");

			txtAnswer.setPromptText(SESSION.getUserLogged().getSecurityAnswer().toString());

			hbChangeAnswer.getChildren().addAll(txtAnswer, btnChange, btnCancel);

			btnChange.setOnAction(e2 -> {
				if (em == null)
					em = Database.createEntityManager();
				USER_REGISTRATION u = SESSION.getUserLogged();
				if (!check.isTextFieldEmpty(txtAnswer)) {
					u.setSecurityAnswer(txtAnswer.getText());
					em.getTransaction().begin();
					em.merge(u);
					em.getTransaction().commit();
					em.clear();
					em.close();
					em = null;
					SESSION.UPDATE_SESSION();
					hbChangeAnswer.getChildren().clear();
					hbChangeAnswer.getChildren().add(new Label(SESSION.getUserLogged().getSecurityAnswer().toString()));
					hbChangeAnswer.getChildren().add(btnChangeAnswer);
				}
			});
			btnCancel.setOnAction(e3 -> {
				hbChangeAnswer.getChildren().clear();
				hbChangeAnswer.getChildren().add(new Label(SESSION.getUserLogged().getSecurityAnswer().toString()));
				hbChangeAnswer.getChildren().add(btnChangeAnswer);
			});
		});

		this.btnDeleteAccount.setOnAction(e4 -> {
			try {
				deleteAccount(e4, stage);
			} catch (ClassNotFoundException | FileNotFoundException | SQLException e2) {
				e2.printStackTrace();
			}
		});

		backToNormalOptions(e, btnBack, screen);

	}

	public void setEventFinish(ActionEvent e, TextField txtName, TextArea txtBio, PasswordField txtCurrentPassword,
																					PasswordField txtNewPassword) {
		USER_REGISTRATION u = SESSION.getUserLogged();
		USER_PROFILE p = SESSION.getProfileLogged();
		if (em == null)
			em = Database.createEntityManager();

		if (!check.isPasswordFieldEmpty(txtCurrentPassword) && !check.isPasswordFieldEmpty(txtNewPassword)) {
			if (txtCurrentPassword.getText().equals(SESSION.getUserLogged().getPassword())) {
				if (txtNewPassword.getText().length() >= 8) {
					String np = txtNewPassword.getText();
					em.getTransaction().begin();
					u.setPassword(np);
					em.merge(u);
					em.getTransaction().commit();
					em.clear();
				} else {
					new CustomAlert(AlertType.ERROR, "Erro", "Senha muito curta", "A senha deve conter no minimo 8 caracteres");
					return;
				}
			}
		}

		if (!check.isTextFieldEmpty(txtName)) {
			String nn = txtName.getText();
			em.getTransaction().begin();
			p.setName(nn);
			em.merge(p);
			em.getTransaction().commit();
			em.clear();
		}
		SESSION.UPDATE_SESSION();

		try {
			GENERAL_STORE.updateComponentsHOME();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return;
	}

	public void setEventBack(ActionEvent e, ProfileEditPOPOUP screen) {
		screen.close();
	}

	private void backToNormalOptions(ActionEvent e, Button btnBack, ProfileEditPOPOUP screen) {
		btnBack.setOnAction(e1 -> {
			screen.init();
			/*
			 * GAMBIARRA A GENTE ACEITA
			 */
			btnBack.setOnAction(e2 -> {
				this.setEventBack(e2, screen);
			});
		});
	}

	private void deleteAccount(ActionEvent e, Stage stage) throws ClassNotFoundException,
																					FileNotFoundException,
																					SQLException {

		if (this.em == null)
			this.em = Database.createEntityManager();

		Optional<ButtonType> result = new CustomAlert(AlertType.WARNING, "Apagar conta", "Sua conta será apagada", "Todos os seus dados serão apagados").showAndWait();

		if (result.get() == ButtonType.OK) {

			Query q = this.em.createQuery("FROM USER_REGISTRATION WHERE USER_COD =: USER_COD");
			q.setParameter("USER_COD", SESSION.getUserLogged().getCodUser());

			USER_REGISTRATION u = (USER_REGISTRATION) q.getResultList().get(0);

			USER_PROFILE p = u.getProfile();

			this.em.getTransaction().begin();
			this.em.remove(p);
			this.em.remove(u);
			this.em.getTransaction().commit();
			this.em.clear();
			this.em.close();
			this.em = null;

			SESSION.RESET();
			stage.close();
			Window.mainStage.setScene(new LoginScene());
		}
	}

}
