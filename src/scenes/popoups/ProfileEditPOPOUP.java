package scenes.popoups;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.pojos.UserRegistration;
import db.util.ProfileImg;
import db.util.SESSION;
import javafx.embed.swing.SwingFXUtils;
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
	private ImageView graphicButton;
	private Label lblName, lblBio, lblCurrentPassword, lblNewP;
	private TextField txtName;
	private TextArea txtBio;
	private PasswordField txtNewPassword, txtCurrentPassword;
	private Button btnSave, btnBack;
	private Scene scene;
	private Button btnImg;
	private ProfileImg pi ;
	private enum updates {
		NAME, PASSWORD, BIO
	};
	
	private ArrayList<updates> message;
	
	public ProfileEditPOPOUP(Window parent) throws IOException {
		
		this.layout = new VBox();
		this.scene = new Scene(layout);
		this.setScene(scene);
		this.pi = new ProfileImg();
		
		this.scene.getStylesheets().add(this.getClass().getResource("/css/EDIT_PROFILE.css").toExternalForm());
		
		this.graphicButton = new ImageView();
		
		if (SESSION.getProfileLogged().getPhoto().length == 0) {
			this.graphicButton.setImage(new Image(new FileInputStream(new File("resources/images/icons/scrum_icon.png"))));
		}
		else { 	
			this.graphicButton.setImage(ProfileImg.loadImage());
		}
		this.graphicButton.setFitWidth(200);
		this.graphicButton.setFitHeight(200);
		
		this.btnImg = new Button();
		this.btnImg.setGraphic(graphicButton);
		
		this.btnImg.setPrefHeight(200);
		this.btnImg.setPrefWidth(200);
		
		this.btnImg.setOnAction(e -> {
		
			
			try {
				pi.setImage(application.main.Window.mainStage);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
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
		
		this.message = new ArrayList<updates>();
		
		this.btnSave.setOnAction(e -> {
			
			this.message.clear();
			CheckEmptyFields c = new CheckEmptyFields();
			EntityManager em = Database.createEntityManager();
			if (!c.isPasswordFieldEmpty(txtCurrentPassword) && !c.isPasswordFieldEmpty(txtNewPassword)) {
				if (txtCurrentPassword.getText().equals(SESSION.getUserLogged().getPassword())) {
					String np = txtNewPassword.getText();
					if (em == null)
						em = Database.createEntityManager();
					Query q = em.createQuery("from UserRegistration where codUser =:cod");
					q.setParameter("cod", SESSION.getUserLogged().getCodUser());
					if (q.getResultList().size() > 0) {
						UserRegistration u = (UserRegistration) q.getResultList().get(0);
						em.getTransaction().begin();
						u.setPassword(np);
						em.merge(u);
						em.getTransaction().commit();
						em.clear();
						em.close();
						em = null;
						this.message.add(updates.PASSWORD);
					}
				}
			}
			if (!c.isTextFieldEmpty(txtName)) {
				String nn = txtName.getText();
				if (em == null)
					em = Database.createEntityManager();
				Query q = em.createQuery("from Profile where codProfile =:cod");
				q.setParameter("cod", SESSION.getProfileLogged().getCod());
				if (q.getResultList().size() > 0) {
					Profile p = (Profile) q.getResultList().get(0);
					em.getTransaction().begin();
					p.setName(nn);
					em.merge(p);
					em.getTransaction().commit();
					em.clear();
					em.close();
					em = null;
					this.message.add(updates.NAME);
				}
			}
			if (!c.isTextAreaEmpty(txtBio)) {
				if (em == null)
					em = Database.createEntityManager();
				Query q = em.createQuery("from Profile where codProfile=:codProfile");
				q.setParameter("codProfile", SESSION.getProfileLogged().getCod());
				if (q.getResultList().size() > 0) {
					Profile p = (Profile) q.getResultList().get(0);
					em.getTransaction().begin();
					p.setBiography(txtBio.getText());
					em.merge(p);
					em.getTransaction().commit();
					em.clear();
					em.close();
					em = null;
					this.message.add(updates.BIO);
				}
			}
			if (!this.message.isEmpty()) {
				ArrayList<String> message = new ArrayList<String>();
				
				for (int i = 0; i < this.message.size(); i++) {
					if (this.message.get(i).equals(updates.NAME)) {
						message.add("nome");
					}
					if (this.message.get(i).equals(updates.BIO)) {
						message.add("biografia");
					}
					if (this.message.get(i).equals(updates.PASSWORD)) {
						message.add("senha");
					}
				}
				StringBuilder stb = new StringBuilder();
				for (int i = 0; i < this.message.size(); i++) {
					
					stb.append(message.get(i).toString());
					
					if (i != message.size() - 1) {
						stb.append(", ");
					}
					if (i == message.size() - 1) {
						stb.append(".");
					}
				}
				
				new CustomAlert(AlertType.CONFIRMATION, "Sucesso", "Mudanças adicionadas",
						"Informações alteradas: " + stb.toString());
				message.clear();
				this.message.clear();
				return;
			}
		});
		this.btnBack.setOnAction(e -> {
			
			this.close();
		});
		this.hbButtons.getChildren().addAll(this.btnSave, this.btnBack);
		this.hbButtons.setSpacing(50);
		this.hbButtons.setAlignment(Pos.CENTER);
		
		this.layout.setSpacing(10);
		this.layout.setAlignment(Pos.CENTER);
		this.layout.getChildren().addAll(this.btnImg, this.hbNameContent, this.hbBio,
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
