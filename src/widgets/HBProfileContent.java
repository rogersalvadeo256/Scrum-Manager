package widgets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import db.hibernate.factory.Database;
import db.user.util.SESSION;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HBProfileContent extends HBox {
	private VBox vbUsrIMG, vbUsrLABEL, vbUsrBUTTON;
	private ImageView usrImage;
	private Label lblName, lblBio;
	private Button btnAdd;
	private EntityManager em;

	public HBProfileContent(Profile p) throws FileNotFoundException {
		this.lblName = new Label(p.getName());
		this.lblBio = new Label(p.getBiography());
		init(p);
	}

//	public HBProfileContent(UserRegistration u) {
//		this.usrName = new Label(u.getProfile().getName());
//		this.usrBio = new Label(u.getProfile().getBiography());
//		init(u.getProfile());
//	}	
	private void init(Profile p) throws FileNotFoundException {
		this.vbUsrIMG = new VBox();
		this.vbUsrLABEL = new VBox();
		this.vbUsrBUTTON = new VBox();
		this.usrImage = new ImageView();
		this.btnAdd = new Button("Adicionar");
		this.btnAdd.setOnAction(e -> {
			AddFriend(p);
		});

		this.usrImage.setImage(new Image(new FileInputStream("resources/images/icons/scrum_icon.png")));

		this.usrImage.setFitWidth(100);
		this.usrImage.setFitHeight(100);

		this.vbUsrIMG.getChildren().addAll(this.usrImage);
		this.vbUsrBUTTON.getChildren().add(btnAdd);
		this.vbUsrLABEL.getChildren().addAll(this.lblName, this.lblBio);

		this.prefHeight(200);
		this.prefWidth(800);
		this.getChildren().addAll(vbUsrIMG, vbUsrLABEL, vbUsrBUTTON);
	}

	private void AddFriend(Profile p) {
	
		
		if (this.em == null)em = Database.createEntityManager();
	
		Profile pr = new Profile(); 
	
		Query q = em.createQuery("from Profile where codProfile=:codProfile");
		
		if(SESSION.getProfileLogged() != null) {
			q.setParameter("codProfile", SESSION.getProfileLogged().getCod());
		}
		
		
		this.em.getTransaction().begin();
		
		pr.getFriend().add(p);
		
		this.em.merge(pr);
		this.em.clear();
		this.em.close();
		this.em = null;
	}
}

























