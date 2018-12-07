package view.TempFXML;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import db.pojos.USER_PROFILE;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import statics.PROFILE_IMG;
import statics.SESSION;

public class TeamController {

	
		
	
	@FXML
	ImageView imgAndre, imgRoger;
	@FXML
	Label lblAndre, lblRoger;
	@FXML
	VBox vbxAndre, vbxRoger,vbxAddM;
	
	private int i;
	
	PROJECT proj;
	public TeamController(PROJECT pj) throws IOException {
		this.proj=pj;
		this.i = initThings(proj);
	}
	
	@FXML
	public void addMember() {
		new DevelopmentScreen();
	}
	
	
	
	@FXML
	public void initialize() throws IOException {
		
		initImages();
		logicStuf();
		
	}
	
	
	public int initThings(PROJECT proj) {
		int i;
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery("FROM PROJECT_MEMBER WHERE MBR_PROJECT =: pc");
		q.setParameter("pc", proj.getProjectCod());
		
		i = q.getResultList().size();
		
		return i;
		
	}
	
	public void logicStuf() {
		if (i<1) {
			vbxRoger.setVisible(false);
			System.out.println("if");
		}else {
			
			System.out.println("else");
		}
	}
	
	
	public void initImages() throws IOException {
		EntityManager em = Database.createEntityManager();
		Query q = em.createQuery("FROM USER_PROFILE WHERE PROF_NAME=:name");
		q.setParameter("name", "Roger");
		USER_PROFILE p = (USER_PROFILE) q.getResultList().get(0);
		
		if (p.getPhoto() == null || p.getPhoto().length == 0) {
			this.imgRoger.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));
		} else {
			this.imgRoger.setImage(PROFILE_IMG.getImage(p));
		}
		this.lblRoger.setText(p.getName());
		
		Query q2 = em.createQuery("FROM USER_PROFILE WHERE PROF_NAME=:name");
		q2.setParameter("name", "Andre");
		USER_PROFILE p2 = (USER_PROFILE) q2.getResultList().get(0);
		
		if (p2.getPhoto() == null || p2.getPhoto().length == 0) {
			this.imgAndre.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));
		} else {
			this.imgAndre.setImage(PROFILE_IMG.getImage(p2));
		}
		this.lblAndre.setText(SESSION.getProfileLogged().getName());
		
	}
	
	
	
	
}
