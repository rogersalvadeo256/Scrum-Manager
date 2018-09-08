package scenes.popoups;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.pojos.UserRegistration;
import db.util.LoadHomePage;
import db.util.ProfileImg;
import db.util.SESSION;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import validation.CheckEmptyFields;
import widgets.alertMessage.CustomAlert;

public class ProfileEditPOPOUP extends Stage {
	
	private VBox layout;
	private HBox hbCurrentPasswordContent, hbNewPasswordContent, hbNameContent, hbBio, hbButtons;
	private ImageView imgProfile;
	private Label lblName, lblBio, lblCurrentPassword, lblNewP;
	private TextField txtName;
	private TextArea txtBio;
	private PasswordField txtNewPassword, txtCurrentPassword;
	private Button btnSave, btnBack;
	private Scene scene;
	private ProfileImg pi;
	private EntityManager em;
	// private enum updates {
	// NAME, PASSWORD, BIO,PHOTO
	// };
	// private ArrayList<updates> message;
	
	public ProfileEditPOPOUP(Window parent) throws IOException {
		
		this.layout = new VBox();
		this.scene = new Scene(layout);
		this.setScene(scene);
		this.pi = new ProfileImg();
		
		// this.scene.getStylesheets().add(this.getClass().getResource("/css/EDIT_PROFILE.css").toExternalForm());
		
		this.imgProfile = new ImageView();
		
		if (SESSION.getProfileLogged().getPhoto() == null || SESSION.getProfileLogged().getPhoto().length == 0) {
			this.imgProfile.setImage(
					new Image(new FileInputStream(new File("resources/images/icons/scrum_icon.png"))));
		} else {
			this.imgProfile.setImage(ProfileImg.loadImage());
		}
		this.imgProfile.setFitWidth(200);
		this.imgProfile.setFitHeight(200);
		
		this.imgProfile.setOnMouseClicked(e -> {
			try {
				pi.setImage(application.main.Window.mainStage);
				this.imgProfile.setImage(pi.loadImage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		this.lblName = new Label("Nome: ");
		this.lblBio = new Label("Bio: ");
		this.lblCurrentPassword = new Label("Senha atual: ");
		this.lblNewP = new Label("Nova Senha: ");
		
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
		this.hbNewPasswordContent.getChildren().addAll(this.lblNewP, this.txtNewPassword);
		this.hbNewPasswordContent.setAlignment(Pos.CENTER);
		
		this.hbButtons = new HBox();
		
		this.btnBack = new Button("Voltar");
		this.btnBack.setId("back");
		this.btnSave = new Button("Salvar");
		this.btnSave.setId("save");
		
		// this.message = new ArrayList<updates>();
		
		this.btnSave.setOnAction(e -> {
			
			// this.message.clear();
			CheckEmptyFields c = new CheckEmptyFields();
			
			UserRegistration u = SESSION.getUserLogged();
			Profile p = SESSION.getProfileLogged();
			
			if (em == null)
				em = Database.createEntityManager();
			
			if (!c.isPasswordFieldEmpty(txtCurrentPassword) && !c.isPasswordFieldEmpty(txtNewPassword)) {
				if (txtCurrentPassword.getText().equals(SESSION.getUserLogged().getPassword())) {
					if (txtNewPassword.getText().length() >= 8) {
						String np = txtNewPassword.getText();
						em.getTransaction().begin();
						u.setPassword(np);
						em.merge(u);
						em.getTransaction().commit();
						em.clear();
						// this.message.add(updates.PASSWORD);
					} else {
						new CustomAlert(AlertType.ERROR, "Erro", "Senha muito curta",
								"A senha deve conter no minimo 8 caracteres");
						return;
					}
				}
			}
			if (!c.isTextFieldEmpty(txtName)) {
				String nn = txtName.getText();
				em.getTransaction().begin();
				p.setName(nn);
				em.merge(p);
				em.getTransaction().commit();
				em.clear();
				// this.message.add(updates.NAME);
			}
			if (!c.isTextAreaEmpty(txtBio)) {
				em.getTransaction().begin();
				p.setBiography(txtBio.getText());
				em.merge(p);
				em.getTransaction().commit();
				em.clear();
				// this.message.add(updates.BIO);
			}
			/*
			 * building the return message
			 */
			// if (!this.message.isEmpty()) {
			// ArrayList<String> message = new ArrayList<String>();
			//
			// for (int i = 0; i < this.message.size(); i++) {
			// if (this.message.get(i).equals(updates.NAME)) {
			// message.add("nome");
			// }
			// if (this.message.get(i).equals(updates.BIO)) {
			// message.add("biografia");
			// }
			// if (this.message.get(i).equals(updates.PASSWORD)) {
			// message.add("senha");
			// }
			// }
			// StringBuilder stb = new StringBuilder();
			// for (int i = 0; i < this.message.size(); i++) {
			//
			// stb.append(message.get(i).toString());
			//
			// if (i != message.size() - 1) {
			// stb.append(", ");
			// }
			// if (i == message.size() - 1) {
			// stb.append(".");
			// }
			// }
			
			Query q = em.createQuery("from Profile where codProfile =: cod");
			q.setParameter("cod", SESSION.getProfileLogged().getCod());
			Query q1 = em.createQuery("from UserRegistration where codUser =:cod");
			q1.setParameter("cod", SESSION.getUserLogged().getCodUser());
			SESSION.UPDATE_SESSION((Profile) q.getResultList().get(0),
					(UserRegistration) q1.getResultList().get(0));
			em.clear();
			em.close();
			try {
				LoadHomePage.updateComponents();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// new CustomAlert(AlertType.CONFIRMATION, "Sucesso", "Mudanças adicionadas",
			// "Informações alteradas: " + stb.toString());
			// message.clear();
			// this.message.clear();
			return;
			// }
		});
		this.btnBack.setOnAction(e -> {
			if (!this.txtBio.getText().isEmpty() || !this.txtName.getText().isEmpty()
					|| !this.txtCurrentPassword.getText().isEmpty()
					|| !this.txtNewPassword.getText().isEmpty()) {
				new CustomAlert(AlertType.WARNING, "Saindo no meio da edição", "certeza que quer sair?",
						"se sair agora as modificações vao ser perdidas", true);
			}
			this.close();
		});
		this.hbButtons.getChildren().addAll(this.btnSave, this.btnBack);
		this.hbButtons.setSpacing(50);
		this.hbButtons.setAlignment(Pos.CENTER);
		
		this.layout.setSpacing(10);
		this.layout.setAlignment(Pos.CENTER);
		this.layout.getChildren().addAll(this.imgProfile, this.hbNameContent, this.hbBio,
				this.hbCurrentPasswordContent, this.hbNewPasswordContent, this.hbButtons);
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);
		
		this.initOwner(parent);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(400);
		this.setHeight(600);
		this.setResizable(false);
	}
}
