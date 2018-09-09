package scenes.popoups;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.UserRegistration;
import db.util.ProfileImg;
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
import validation.CheckEmptyFields;
import widgets.alertMessage.CustomAlert;
import widgets.designComponents.HBProfileContentForgotPassword;

public class ForgotPasswordPOPOUP extends Stage {
	
	private Label lblQuestion, lblEmail, lblNPass, lblCNpass;
	private TextField txtEmail;
	private PasswordField npass, cnpass;
	private EntityManager em;
	private Query q;
	private VBox layout;
	private Scene scene;
	private Button btnOk, btnCancel;
	private HBox hbButtons;
	private ToggleButton tbYes, tbNot;
	private HBProfileContentForgotPassword hbp;
	private ToggleGroup tbGroup;
	private CheckEmptyFields checkFields;
	private ImageView img;
	private ArrayList<String> message;
	private boolean needEmail = false;
	public ForgotPasswordPOPOUP(Window owner) {
		init(owner);
		drawNegativeAnswer();
	}
	
	public ForgotPasswordPOPOUP(Window owner, TextField userNameOrEmail) throws IOException {
		init(owner);
		if (valideEmail(userNameOrEmail)) {
			this.layout.getChildren().add(hbp);
			this.layout.getChildren().addAll(this.lblQuestion, hbButtons);
		}
	}
	
	public <T> void init(Window owner) {
		
		this.checkFields = new CheckEmptyFields();
		this.message = new ArrayList<String>();
		
		this.lblQuestion = new Label("Este(ª) é voce?");
		
		this.lblEmail = new Label("Digite seu email: ");
		this.txtEmail = new TextField();
		
		this.npass = new PasswordField();
		this.lblNPass = new Label("Digite uma nova senha: ");
		
		this.lblCNpass = new Label("Confirmação de senha: ");
		this.cnpass = new PasswordField();
		
		this.hbButtons = new HBox();
		
		this.tbNot = new ToggleButton("Não");
		this.tbYes = new ToggleButton("Sim");
		
		this.hbButtons.setAlignment(Pos.CENTER);
		this.hbButtons.getChildren().addAll(tbNot, tbYes);
		
		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setOnAction(e -> {
			
		});
		this.btnOk = new Button("Ok");
		this.btnOk.setOnAction(e -> {
			if (!valideFieldsEmpty()) {
				if (this.q.getResultList().isEmpty()) {
					if (!(changePassword(txtEmail.getText(), npass.getText(), cnpass.getText(), true))){ 
						new CustomAlert(AlertType.ERROR, "ERRO", "Algo errado", "Senhas não correspondem");
					}else { 
						new CustomAlert(AlertType.CONFIRMATION, "Pronto", "Senha alterada", "Sua senha foi alterada");
					}
				} else { 
					if(!(changePassword(txtEmail.getText(), npass.getText(), cnpass.getText(), false))){
						new CustomAlert(AlertType.ERROR, "ERRO", "Algo errado", "Senhas não correspondem");
					} else { 
						new CustomAlert(AlertType.CONFIRMATION, "Pronto", "Senha alterada", "Sua senha foi alterada");
					}
				}
			}
		});
		this.tbGroup = new ToggleGroup();
		this.tbNot.setToggleGroup(tbGroup);
		this.tbYes.setToggleGroup(tbGroup);
		
		this.tbGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> old_value, Toggle toggle,
					Toggle new_toggle) {
				if (tbGroup.getSelectedToggle() == tbNot) {
					needEmail = true;
					drawNegativeAnswer();
				}
				if (tbGroup.getSelectedToggle() == tbYes) {
					needEmail = false;
					try {
						drawPositiveAnswser();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		this.layout = new VBox(10);
		this.scene = new Scene(layout);
		
		this.layout.setAlignment(Pos.CENTER);
		
		this.setScene(scene);
		
		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(400);
		this.setHeight(550);
		this.setResizable(false);
	}
	
	/**
	 * if the return are false, none email was found if the return are true, the
	 * email is valid
	 * 
	 * @param  TextField
	 *                            email
	 * @return             boolean
	 * @throws IOException
	 */
	private boolean valideEmail(TextField emailOrUserName) throws IOException {
		if (em == null)
			em = Database.createEntityManager();
		this.q = em.createQuery("from UserRegistration where email =: email or userName =:userName");
		q.setParameter("email", emailOrUserName.getText());
		q.setParameter("userName", emailOrUserName.getText());
		if (q.getResultList().isEmpty()) {
			em = null;
			return false;
		}
		this.hbp = new HBProfileContentForgotPassword((UserRegistration) q.getResultList().get(0));
		em = null;
		return true;
	}
	
	private void drawNegativeAnswer() {
		this.layout.getChildren().clear();
		this.hbButtons.getChildren().clear();
		this.hbButtons.getChildren().addAll(this.btnOk, this.btnCancel);
		
		this.layout.getChildren().addAll(lblEmail, txtEmail);
		this.layout.getChildren().addAll(lblNPass, npass);
		this.layout.getChildren().addAll(lblCNpass, cnpass);
		this.layout.getChildren().add(this.hbButtons);
	}
	
	private void drawPositiveAnswser() throws IOException {
		this.layout.getChildren().clear();
		this.hbButtons.getChildren().clear();
		this.hbButtons.getChildren().addAll(this.btnOk, this.btnCancel);
		/*
		 */
		UserRegistration u = (UserRegistration) q.getResultList().get(0);
		lblEmail.setText(u.getEmail());
		this.img = new ImageView();
		this.img.setImage(ProfileImg.getImage(u.getProfile()));
		this.img.setFitHeight(200);
		this.img.setFitWidth(200);
		//
		this.layout.getChildren().add(img);
		this.layout.getChildren().add(lblEmail);
		this.layout.getChildren().addAll(lblNPass, npass);
		this.layout.getChildren().addAll(lblCNpass, cnpass);
		this.layout.getChildren().add(this.hbButtons);
	}
	
	private boolean valideFieldsEmpty() {
		if(needEmail) {
		if (this.checkFields.isTextFieldEmpty(this.txtEmail))
			this.message.add("email");
		}
		if (this.checkFields.isPasswordFieldEmpty(this.npass))
			this.message.add("senha");
		if (this.checkFields.isPasswordFieldEmpty(this.cnpass))
			this.message.add("confirmação de senha");
		if (!this.message.isEmpty()) {
			StringBuilder stb = new StringBuilder();
			
			if (this.message.size() > 1)
				stb.append("Os campos ");
			if (this.message.size() == 1)
				stb.append("O campo ");
			
			for (int i = 0; i < this.message.size(); i++) {
				stb.append(this.message.get(i));
				if (this.message.get(i) != null) {
					stb.append(", ");
				}
				int j = i;
				try {
					if (this.message.get(j++) != null) {
						stb.append("\n");
					}
				} catch (IndexOutOfBoundsException a) {
					if (j++ == this.message.size() - 1) {
						stb.append("\n");
					}
				}
			}
			if (this.message.size() > 1)
				stb.append(" estão vazios.");
			if (this.message.size() == 1)
				stb.append(" está vazio.");
			new CustomAlert(AlertType.ERROR, "ERRO", "Campos Vazios", stb.toString());
			this.message.clear();
			return true;
		}
		return false;
	}
	
	/**
	 * I don't know why i did this shit like this im sorry if you had to read this
	 * code
	 * 
	 * @param  email
	 * @param  password
	 * @param  passwordConfirmation
	 * @param  needEmail
	 * @return
	 */
	private boolean changePassword(String email, String password, String passwordConfirmation, boolean needEmail) {
		Query q = null;
		if (em == null)
			em = Database.createEntityManager();
		if (needEmail) {
			q = em.createQuery("from UserRegistration where email =: email");
			q.setParameter("email", email);
		}
		if (!needEmail) {
			q = em.createQuery("from UserRegistration where email =: email");
			UserRegistration  u  = (UserRegistration) this.q.getResultList().get(0);
			q.setParameter("email", u.getEmail());
		}
		UserRegistration u = null;
		if (q != null)	u = (UserRegistration) q.getResultList().get(0);
		
		if (!needEmail && this.q.getResultList().get(0) != null)	u = (UserRegistration) this.q.getResultList().get(0);
		
		
		
		if (password.equals(passwordConfirmation)) {
			em.getTransaction().begin();
			em.merge(u);
			em.getTransaction().commit();
			em.clear();
			em.close();
			em = null;
			return true;
		}
		return false;
	}
}
