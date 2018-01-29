package application.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import application.main.Window;
import db.hibernate.factory.Database;
import db.pojos.USER_REGISTRATION;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import statics.ENUMS;
import view.popoups.ActivateAccount;
import view.popoups.ForgotPasswordPOPOUP;
import widgets.alertMessage.CustomAlert;
import widgets.designComponents.HBProfileContentReactivateAccount;

public class ActivateAccountController {


	private EntityManager em;
	private HBProfileContentReactivateAccount profileContent;
	private USER_REGISTRATION ur;
	private VBox layout;
	private ActivateAccount screen;
	private HBox hbYesNot;
	private Button btnYes, btnNot;
	private TextField txtAnswer;

	public ActivateAccountController(ActivateAccount screen, VBox layout) throws IOException {

		this.layout = layout;
		this.screen = screen;

	}

	public void setButtonQueryEvent(ActionEvent e, String loginEmail) throws IOException {

		if (userExists(loginEmail)) {
			userActive();
			loadProfileContent();
		}

	}

	public void setTextFieldEvent(KeyEvent e, String loginEmail) throws IOException {


		if (e.getCode() == KeyCode.ENTER) {
			if (userExists(loginEmail)) {
				userActive();
				loadProfileContent();
			}
		}
	}


	private boolean userExists(String loginEmail) {


		if (this.em == null)
			this.em = Database.createEntityManager();


		Query q = em.createQuery("FROM USER_REGISTRATION WHERE USER_NAME = :USER_OR_EMAIL OR USER_EMAIL = :USER_OR_EMAIL");
		q.setParameter("USER_OR_EMAIL", loginEmail);

		this.em = null;

		if (q.getResultList().size() > 0) {
			this.ur = (USER_REGISTRATION) q.getResultList().get(0);
			return true;
		}
		return false;
	}

	private void userActive() {
		if (ur.getStatus().toString().equals(ENUMS.ACCOUNT_STATUS.ACTIVE.getValue())) {
			Optional<ButtonType> result = new CustomAlert(AlertType.INFORMATION, "Já está ativo", "O usuario informado já está ativo", "Esqueceu sua senha? clique em OK para recuperar senha")
					.showAndWait();

			if (result.get() == ButtonType.OK) {
				screen.close();
				new ForgotPasswordPOPOUP(Window.mainStage).showAndWait();
				return;
			}
			if (result.get() == ButtonType.CANCEL) {
				screen.close();
				return;
			}
		}
	}
	private void loadProfileContent() throws IOException {

		layout.getChildren().clear();

		this.profileContent = new HBProfileContentReactivateAccount(ur);

		layout.getChildren().add(this.profileContent);

		this.btnNot = new Button("Nao");
		this.btnYes = new Button("Sim");

		this.hbYesNot = new HBox();

		this.hbYesNot.getChildren().addAll(btnYes, btnNot);

		layout.getChildren().add(hbYesNot);

		this.btnNot.setOnAction(e -> {
			try {
				btnNotEvent(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		this.btnYes.setOnAction(e -> {
			btnYesEvent();
		});
	}

	private void btnNotEvent(ActionEvent e) throws IOException {
		layout.getChildren().clear();


		Optional<ButtonType> result = new CustomAlert(AlertType.WARNING, "Erro", "Nenhum usuario encontrado", "Clique em OK para tentar novamente, Clique em CANCELAR para sair").showAndWait();

		if (result.get() == ButtonType.OK) {
			this.screen.setScene(new ActivateAccount(this.screen).getScene());
			return;
		}
		if (result.get() == ButtonType.CANCEL) {
			this.screen.close();
			return;
		}
	}
	private void btnYesEvent() {

		layout.getChildren().clear();


		Label lblInformation = new Label("Responda a pergunta de segurança para reativar a sua conta");

		Label lblQuestion = new Label(ur.getSecurityQuestion());
		this.txtAnswer = new TextField();
		Button btnSendAnswer = new Button("Responder");

		HBox hbAnswer = new HBox();

		hbAnswer.getChildren().addAll(txtAnswer, btnSendAnswer);

		VBox vbContent = new VBox();


		vbContent.getChildren().addAll(lblInformation, lblQuestion, hbAnswer);
		vbContent.setAlignment(Pos.CENTER);

		layout.getChildren().add(vbContent);

		btnSendAnswer.setOnAction(e1 -> {
			if (rightAnswer(txtAnswer.getText())) {


				if (this.em == null)
					this.em = Database.createEntityManager();

				USER_REGISTRATION u = this.ur;

				u.setStatus(ENUMS.ACCOUNT_STATUS.ACTIVE.getValue());

				this.em.getTransaction().begin();
				this.em.merge(u);
				this.em.getTransaction().commit();
				this.em.clear();
				this.em.close();
				this.em = null;

				Optional<ButtonType> result = new CustomAlert(AlertType.CONFIRMATION, "Conta Reativada", "Sua conta foi reativada", "Clique em OK para fazer o login").showAndWait();

				if (result.get() == ButtonType.OK) {
					this.screen.close();
					return;
					// try {
					// Window.mainStage.setScene(new LoginScene());
					// } catch (ClassNotFoundException | FileNotFoundException |
					// SQLException e) {
					// e.printStackTrace();
					// }
				}
				return;

			}
		});
	}

	private boolean rightAnswer(String answer) {
		if (answer.equals(this.ur.getSecurityAnswer().toString()))
			return true;
		return false;
	}


}

