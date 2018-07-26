package hibernatebook.queries;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.omg.CORBA.PUBLIC_MEMBER;

import alert.message.CustomAlert;
import hibernatebook.annotations.UserRegistration;
import hibernatebook.entity.provider.EntityProvider.Factory;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.Window;
import net.bytebuddy.asm.Advice.This;
import scenes.HomePageScene;
import validation.CheckEmptyFields;

public class Login {
	
	private CheckEmptyFields checkField;
	private TextField txtUser;
	private PasswordField txtPassword;
	private Query query;
	private List<UserRegistration> queryResult;
	
	
	public Login() {
		this.checkField=new CheckEmptyFields();
		this.txtUser=new TextField();
		this.txtPassword=new PasswordField();
		this.queryResult=new ArrayList<UserRegistration>();
	}
	
	
	public void field(TextField username, PasswordField password) throws ClassNotFoundException, FileNotFoundException, SQLException { 
		this.txtUser=username;
		this.txtPassword=password;
		
		if(checkField.isTextFieldEmpty(txtUser) || checkField.isPasswordFieldEmpty(txtPassword)) { 
			 new CustomAlert(AlertType.ERROR,"Nada digitado","Informe seu login para entrar", "Campos estão vazios");
			 return;
		}
	}
	
	public String dataIsValid() throws ClassNotFoundException, FileNotFoundException, SQLException {
		
		this.query = Factory.entityManager.createQuery("from user_registration");
		
		this.queryResult = query.getResultList();
		
		for (UserRegistration user : queryResult) {
			
			if(user.getUserName().equals(this.txtUser.getText()) && user.getPassword().equals(this.txtPassword.getText())) {
				return new String();
			}
			if(user.getUserName().equals(this.txtUser.getText()) && !user.getPassword().equals(this.txtPassword.getText())) {
				return "Nome de usuario invalido";
			}
			if(user.getPassword().equals(this.txtUser.getText()) && !user.getUserName().equals(this.txtUser.getText())){
				return "Algo errado com a senha";
			}
		}
		return new String();
	}
	
	
}
































