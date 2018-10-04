package view.popoups;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import application.controllers.EditProfileController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import statics.PROFILE_IMG;
import statics.SESSION;
import widgets.designComponents.ShowImage;
import widgets.designComponents.VBoxPhotoDecoration;

public class ProfileEditPOPOUP extends StandartLayoutPOPOUP {

	private HBox hbCurrentPasswordContent, hbNewPasswordContent, hbNameContent, hbBio;
	private ImageView imgProfile;
<<<<<<< HEAD
	private PROFILE_IMG profileImage;
	private Label lblName, lblBio, lblCurrentPassword, lblNewPassword;
=======
	private ProfileImg profileImage;
	private Label lblName, lblCurrentPassword, lblNewPassword;
>>>>>>> 08facde7dedf4a0f82fbbfbbc17601f1dbde8d7b
	private TextField txtName;
	private PasswordField txtNewPassword, txtCurrentPassword;
	private Button btnAdvanced;
	private HBox hbChangeQuestion, hbChangeAnswer, hbAdvancedStuff;
	private EditProfileController controller;

	private VBoxPhotoDecoration imageContent;

	private Button btnBack, btnFinish;
	private HBox hbSteadyButtons;

	public ProfileEditPOPOUP(Window owner) throws IOException {
		
		super(owner);
		this.profileImage = new PROFILE_IMG();


		this.btnBack = new Button("Voltar");
		this.btnBack.setId("back");
		this.btnBack.setOnAction(e -> {
			this.close();
		});
		this.btnFinish = new Button("Salvar");
		this.btnFinish.setId("save");
		
		this.hbAdvancedStuff = new HBox();
		this.btnAdvanced = new Button("Avançado");

		this.hbSteadyButtons = new HBox();	
		this.hbSteadyButtons.setAlignment(Pos.CENTER);
		this.hbSteadyButtons.setSpacing(50);
		
		
		this.scene.getStylesheets().add(this.getClass().getResource("/css/EDIT_PROFILE.css").toExternalForm());

		this.imgProfile = new ImageView();

		if (SESSION.getProfileLogged().getPhoto() == null || SESSION.getProfileLogged().getPhoto().length == 0) {
			this.imgProfile.setImage(new Image(new FileInputStream(new File("resources/images/icons/profile_picture.png"))));
		} else {
			this.imgProfile.setImage(PROFILE_IMG.loadImage());
		}
		this.imgProfile.setFitWidth(300);
		this.imgProfile.setFitHeight(300);
		this.imageContent = new VBoxPhotoDecoration(imgProfile, "Alterar imagem");

		this.imageContent.changePhoto().setOnMouseClicked(e -> {
			try {
				profileImage.setImage(application.main.Window.mainStage);
				if(profileImage != null) this.imgProfile.setImage(PROFILE_IMG.loadImage());
				} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		this.imgProfile.setOnMouseClicked(e -> {
			ShowImage show;
			try {
				show = new ShowImage(PROFILE_IMG.loadImage(), this);
				show.showAndWait();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		this.lblName = new Label("Nome: ");
		this.lblCurrentPassword = new Label("Senha atual: ");
		this.lblNewPassword = new Label("Nova Senha: ");

		this.txtName = new TextField();
		this.txtName.setPrefColumnCount(15);
		
		this.txtCurrentPassword = new PasswordField();
		this.txtNewPassword = new PasswordField();

		this.hbNameContent = new HBox();
		this.hbNameContent.getChildren().addAll(this.lblName, this.txtName);
		this.hbNameContent.setAlignment(Pos.CENTER);

		this.hbBio = new HBox();
		this.hbBio.setAlignment(Pos.CENTER);

		this.hbCurrentPasswordContent = new HBox();
		this.hbCurrentPasswordContent.getChildren().addAll(this.lblCurrentPassword, this.txtCurrentPassword);
		this.hbCurrentPasswordContent.setAlignment(Pos.CENTER);

		this.hbNewPasswordContent = new HBox();
		this.hbNewPasswordContent.getChildren().addAll(this.lblNewPassword, this.txtNewPassword);
		this.hbNewPasswordContent.setAlignment(Pos.CENTER);

		

		this.hbAdvancedStuff.setAlignment(Pos.CENTER);
		this.hbAdvancedStuff.getChildren().addAll(this.btnAdvanced);

		this.controller = new EditProfileController();

		this.hbChangeQuestion = new HBox();
		this.hbChangeQuestion.setAlignment(Pos.CENTER);

		this.hbChangeAnswer = new HBox();
		this.hbChangeAnswer.setAlignment(Pos.CENTER);

		this.btnAdvanced.setOnAction(e -> {
			controller.setEventFinish(e, txtName, txtCurrentPassword, txtNewPassword);
			controller.setEventAdvancedOptions(e, ProfileEditPOPOUP.this, this.layout, this, this.hbChangeAnswer, this.hbChangeQuestion, this.hbSteadyButtons, this.btnBack);
		});
		this.btnFinish.setOnAction(e -> {
			controller.setEventFinish(e, txtName, txtCurrentPassword, txtNewPassword);
		});
		this.btnBack.setOnAction(e -> {
			controller.setEventBack(e, ProfileEditPOPOUP.this);
		});
		this.init();
	}

	public void init() {
		this.hbSteadyButtons.getChildren().clear();
		this.hbSteadyButtons.getChildren().addAll(this.btnBack, this.btnFinish);
		this.layout.getChildren().clear();
		this.layout.setSpacing(10);
		this.layout.setAlignment(Pos.CENTER);
		this.layout.getChildren().addAll(this.imageContent, this.hbNameContent, this.hbBio, this.hbCurrentPasswordContent);
		this.layout.getChildren().addAll(this.hbNewPasswordContent, this.hbAdvancedStuff, this.hbSteadyButtons);
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);
	}

}
