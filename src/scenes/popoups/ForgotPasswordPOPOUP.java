package scenes.popoups;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.UserRegistration;
import db.util.ProfileImg;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import listeners.Close;
import validation.CheckEmptyFields;
import widgets.alertMessage.CustomAlert;
import widgets.designComponents.HBProfileContentForgotPassword;

public class ForgotPasswordPOPOUP extends Stage {
	
	private Label lblQuestion, lblAnswer, lblEmailOrUsername, lblNewPasswrd, lblConfirmPassword;
	private TextField txtEmailUserName,txtAnswer;
	private PasswordField newPassword, passwordConfirmation;
	private EntityManager em;
	private Query q;
	private VBox layout;
	private Scene scene;
	private Button btnOk, btnCancel,btnEmail;
	private HBox hbButtons;
	private ToggleButton tbYes, tbNot;
	private HBProfileContentForgotPassword hbp;
	private ToggleGroup tbGroup;
	private CheckEmptyFields checkFields;
	private ImageView img;
	private ArrayList<String> message;

	public ForgotPasswordPOPOUP(Window owner) throws IOException {
		init(owner);
	}
	
	public void init(Window owner) {
		
		this.checkFields = new CheckEmptyFields();
		this.message = new ArrayList<String>();
		
		this.lblQuestion = new Label("Este(ª) é voce?");
		
		this.lblEmailOrUsername = new Label("Digite seu email ou nome de usuario ");
		this.txtEmailUserName = new TextField();
		
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
		this.btnCancel.setOnAction(e-> { this.close();});
		this.btnOk = new Button("Ok");
		this.btnOk.setOnAction(e -> {
		});

		this.tbGroup = new ToggleGroup();
		this.tbNot.setToggleGroup(tbGroup);
		this.tbYes.setToggleGroup(tbGroup);
		
		this.tbGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> old_value, Toggle toggle,
					Toggle new_toggle) {
				if (tbGroup.getSelectedToggle() == tbNot) {
				}
				if (tbGroup.getSelectedToggle() == tbYes) {
				}
			}
		});
		this.layout = new VBox(10);
		this.scene = new Scene(layout);
		
		this.btnEmail=new Button("Enviar");
		
		this.layout.getChildren().addAll(this.lblEmailOrUsername, this.txtEmailUserName,this.btnEmail);
		btnEmail.setOnAction(e -> { 
			
			
			
		});
		
		
		
		
		this.layout.setAlignment(Pos.CENTER);
		
		this.setScene(scene);
		
		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(400);
		this.setHeight(550);
		this.setResizable(false);
	}
}

	public void valideEmail() { 
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	