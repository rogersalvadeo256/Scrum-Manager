package scenes.popoups;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import application.controllers.EditProfileController;
import db.util.ProfileImg;
import db.util.SESSION;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class ProfileEditPOPOUP extends StandartLayoutPOPOUP {
	
	private HBox hbCurrentPasswordContent, hbNewPasswordContent, hbNameContent, hbBio;
	private ImageView imgProfile;
	private ProfileImg pi;
	private Label lblName, lblBio, lblCurrentPassword, lblNewPassword;
	private TextField txtName, txtAnswer,txtQuestion;
	private TextArea txtBio;
	private PasswordField txtNewPassword, txtCurrentPassword;
	private Button  btnAdvanced,btnDelete;
	private HBox hbChangeQuestion,hbChangeAnswer,hbAdvancedStuff;
	private Button btnChangeQuestion, btnChangeAnswer;
	private EditProfileController controller;
	
	private Button btnBack, btnFinish;
	private HBox hbSteadyButtons;
	
	private void a() { 
	}
	
	
	
	public ProfileEditPOPOUP(Window owner) throws IOException {
		super(owner);
		this.pi = new ProfileImg();
		
		this.hbAdvancedStuff = new HBox();
		this.btnAdvanced=new Button("Avançado");
		
		this.hbAdvancedStuff.setAlignment(Pos.CENTER);
		this.hbAdvancedStuff.getChildren().add(this.btnAdvanced);
		
		this.hbSteadyButtons = new HBox();
	
		
		this.scene.getStylesheets().add(this.getClass().getResource("/css/EDIT_PROFILE.css").toExternalForm());
		
		this.imgProfile = new ImageView();
		
		if (SESSION.getProfileLogged().getPhoto() == null || SESSION.getProfileLogged().getPhoto().length == 0) {
			this.imgProfile.setImage(new Image(new FileInputStream(new File("resources/images/icons/profile_picture.png"))));
		} else {
			this.imgProfile.setImage(ProfileImg.loadImage());
		}
		this.imgProfile.setFitWidth(300);
		this.imgProfile.setFitHeight(300);
		
		this.imgProfile.setOnMouseClicked(e -> {
			try {
				pi.setImage(application.main.Window.mainStage);
				this.imgProfile.setImage(ProfileImg.loadImage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		this.lblName = new Label("Nome: ");
		this.lblBio = new Label("Bio: ");
		this.lblCurrentPassword = new Label("Senha atual: ");
		this.lblNewPassword = new Label("Nova Senha: ");
		
		this.txtName = new TextField();
		this.txtName.setPrefColumnCount(15);
		this.txtBio = new TextArea();
		this.txtBio.setWrapText(true);
		this.txtBio.setPrefRowCount(5);
		this.txtBio.setPrefColumnCount(15);
		
		
		this.txtCurrentPassword = new PasswordField();
		this.txtNewPassword = new PasswordField();
		
		this.hbNameContent = new HBox();
		this.hbNameContent.getChildren().addAll(this.lblName, this.txtName);
		this.hbNameContent.setAlignment(Pos.CENTER);
		
		this.hbBio = new HBox();
		this.hbBio.getChildren().addAll(this.lblBio, this.txtBio);
		this.hbBio.setAlignment(Pos.CENTER);
		
		this.hbCurrentPasswordContent = new HBox();
		this.hbCurrentPasswordContent.getChildren().addAll(this.lblCurrentPassword, this.txtCurrentPassword);
		this.hbCurrentPasswordContent.setAlignment(Pos.CENTER);
		
		this.hbNewPasswordContent = new HBox();
		this.hbNewPasswordContent.getChildren().addAll(this.lblNewPassword, this.txtNewPassword);
		this.hbNewPasswordContent.setAlignment(Pos.CENTER);
		
		
		this.btnBack = new Button("Voltar");
		this.btnBack.setId("back");
		this.btnFinish = new Button("Salvar");
		this.btnFinish.setId("save");
		
		this.txtAnswer = new TextField();
		this.txtQuestion = new TextField();
		
		this.btnDelete=new Button("Deletar conta");
		this.controller = new EditProfileController();
		
		
		this.btnChangeQuestion = new Button("Mudar");
		this.hbChangeQuestion = new HBox();
		this.hbChangeQuestion.setAlignment(Pos.CENTER);
		
		this.hbChangeQuestion.getChildren().add(new Label(SESSION.getUserLogged().getSecurityQuestion().toString(), this.btnChangeQuestion));
		this.btnChangeQuestion.setOnAction(e->{
			controller.setEventChangeSecurity(e,ProfileEditPOPOUP.this, this.hbChangeQuestion, this.txtQuestion,this.btnChangeQuestion, this.btnBack, true);
		});
		this.btnChangeAnswer = new Button("Mudar");
		this.hbChangeAnswer = new HBox();
		this.hbChangeAnswer.setAlignment(Pos.CENTER);
		this.hbChangeAnswer.getChildren().add(new Label(SESSION.getUserLogged().getSecurityQuestionAnswer().toString(), this.btnChangeAnswer));
		
		this.btnChangeAnswer.setOnAction(e->{
			controller.setEventChangeSecurity(e, ProfileEditPOPOUP.this,this.hbChangeAnswer, this.txtAnswer,this.btnChangeAnswer, this.btnBack, false);
		});
		this.btnAdvanced.setOnAction(e->{
			controller.setEventFinish(e, txtName, txtBio, txtCurrentPassword, txtNewPassword);
			controller.setEventAdvancedOptions(e, ProfileEditPOPOUP.this, this.layout,this.hbChangeAnswer,this.hbChangeQuestion,this.hbSteadyButtons,this.btnBack);
		});
		this.btnFinish.setOnAction(e -> {
			controller.setEventFinish(e, txtName, txtBio, txtCurrentPassword, txtNewPassword);
		});
		
		this.btnBack.setOnAction(e -> {
			controller.setEventBack(e, ProfileEditPOPOUP.this);
		});
		
		this.init();
	}
	
	public void init() {
		
		this.hbSteadyButtons.getChildren().clear();
		this.hbSteadyButtons.getChildren().addAll(this.btnBack,this.btnFinish);
		this.layout.getChildren().clear();
		this.layout.setSpacing(10);
		this.layout.setAlignment(Pos.CENTER);
		this.layout.getChildren().addAll(this.imgProfile, this.hbNameContent, this.hbBio,this.hbCurrentPasswordContent, this.hbNewPasswordContent, this.hbAdvancedStuff, this.hbSteadyButtons);
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);
	}

	














}




















