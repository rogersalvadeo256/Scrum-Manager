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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import statics.ENUMS;
import statics.GENERAL_STORE;
import statics.SESSION;
import validation.CheckEmptyFields;
import view.popoups.ProfileEditPOPOUP;
import view.scenes.LoginScene;
import widgets.alertMessage.CustomAlert;
import widgets.designComponents.HBStatusBar;

public class EditProfileController {

	private EntityManager em;
	private CheckEmptyFields check;
	private Button btnChangeQuestion, btnChangeAnswer;
	private HBStatusBar hbDeleteAccount;
	private ProfileEditPOPOUP screen;
	
	public EditProfileController() {
		this.check = new CheckEmptyFields();
		this.btnChangeAnswer = new Button("Alterar");
		this.btnChangeQuestion = new Button("Alterar");
		this.hbDeleteAccount = new HBStatusBar(false);

		// this.btnDeleteAccount = new Button("Deletar conta");

		this.hbDeleteAccount.setGroupEvent(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

				if (newValue.isSelected()) {

					Optional<ButtonType> result = new CustomAlert(AlertType.WARNING, "Apagar conta", "Sua conta ficará inativa", "Para reativar sua conta vá até a opção na tela de login").showAndWait();

					if (result.get() == ButtonType.OK) {
						newValue.setSelected(true);
						EntityManager em = Database.createEntityManager();

						USER_REGISTRATION u = SESSION.getUserLogged();

						u.setStatus(ENUMS.ACCOUNT_STATUS.INACTIVE.getValue());

						em.getTransaction().begin();
						em.merge(u);
						em.getTransaction().commit();
						em.clear();

						SESSION.RESET();
						EditProfileController.this.screen.close();
						try {
							Window.mainStage.setScene(new LoginScene());
						} catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
							e.printStackTrace();
						}
						return;
					}
					newValue.setSelected(true);
				}
			}
		});

	}


	/*
	 * this code is huge and redundant, but was the best way that i find to do this and
	 * understand later
	 */
	public void setEventAdvancedOptions(ActionEvent e, ProfileEditPOPOUP screen, VBox layout, Stage stage, HBox hbChangeAnswer, HBox hbChangeQuestion, HBox hbButtons, Button btnBack) {
		
		this.screen= screen;
		
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
		 layout.getChildren().add(hbDeleteAccount);
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

		// this.btnDeleteAccount.setOnAction(e4 -> {
		// try {
		// deleteAccount(e4, stage);
		// } catch (ClassNotFoundException | FileNotFoundException |
		// SQLException e2) {
		// e2.printStackTrace();
		// }
		// });

		backToNormalOptions(e, btnBack, screen);

	}

	public void setEventFinish(ActionEvent e, TextField txtName, PasswordField txtCurrentPassword, PasswordField txtNewPassword) {
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

	private void deleteAccount(ActionEvent e, Stage stage) throws ClassNotFoundException, FileNotFoundException, SQLException {

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
