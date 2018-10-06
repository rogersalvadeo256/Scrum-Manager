package application.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import application.main.Window;
import db.util.Login;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import view.scenes.HomePageScene;

public class LoginSceneController {

	private Login login;
	/**
	 * this variable is just for update the message of wrog login on the page
	 */
	private boolean invalidLogin = false;

	public LoginSceneController() {
		this.login = new Login();
	}

	public void setEventBtnLogin(ActionEvent e, TextField txtLogin, PasswordField txtPassword,
																					RadioButton btnStayConnected)
																					throws IOException {
		if (this.login.valideLogin(txtLogin, txtPassword, btnStayConnected)) {
			try {
				Window.mainStage.setScene(new HomePageScene());

				return;
			} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		this.setInvalidLogin(true);
		return;
	}

	public void setEventPasswordField(KeyEvent e, TextField txtLogin, PasswordField txtPassword,
																					RadioButton btnStayConnected) throws IOException {
		if (e.getCode() == KeyCode.ENTER) {
			if (this.login.valideLogin(txtLogin, txtPassword, btnStayConnected)) {
				try {
					Window.mainStage.setScene(new HomePageScene());
					return;
				} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			this.setInvalidLogin(true);
		}
	}

	public boolean isInvalidLogin() {
		return invalidLogin;
	}

	public void setInvalidLogin(boolean invalidLogin) {
		this.invalidLogin = invalidLogin;
	}
}
