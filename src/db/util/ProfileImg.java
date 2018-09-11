package db.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import widgets.alertMessage.CustomAlert;

public class ProfileImg {
	private EntityManager em;
	public ProfileImg() {}
	/**
	 * Update the profile image of the user in the database
	 * 
	 * @param  Stage
	 * @throws IOException
	 * @author             jefter66
	 */
	public void setImage(Stage owner) throws IOException {
		FileChooser fc = new FileChooser();
		File f = fc.showOpenDialog(owner);
			
		byte[] img = new byte[(int) f.length()];
		
		FileInputStream fis = new FileInputStream(f);
		fis.read(img);
		fis.close();
		if (img.length < 52428800) {
			if (em == null) {em = Database.createEntityManager();	}
			Profile p = SESSION.getProfileLogged();
			this.em.getTransaction().begin();
			p.setPhoto(img);
			this.em.merge(p);
			this.em.getTransaction().commit();
			this.em.clear();
			this.em.close();
			this.em = null;
			return;
		}
		new CustomAlert(AlertType.INFORMATION, "Erro", "Arquivo muito grande", "Foto deve ser menor que 50 mb");
		return;
	}
	
	
	public static  Image getImage(Profile p ) throws IOException {
		BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(p.getPhoto()));
		Image img = SwingFXUtils.toFXImage(bfi, null);
		return img;
	}
	/**
	 * Return the profile image of the user online
	 * 
	 * @return             Image
	 * @throws IOException
	 * @author             jefter66
	 */
	public static Image loadImage() throws IOException {
		BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(SESSION.getProfileLogged().getPhoto()));
		Image img = SwingFXUtils.toFXImage(bfi, null);
		return img;
	}
}













