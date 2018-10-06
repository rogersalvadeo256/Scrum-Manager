package statics;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import db.pojos.USER_PROFILE;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import widgets.alertMessage.CustomAlert;

public class PROFILE_IMG {
	public PROFILE_IMG() {
	}
	/**
	 * Update the profile image of the user in the database
	 * 
	 * @param Stage
	 * @throws IOException
	 * @author jefter66
	 */
	public void setImage(Stage owner) throws IOException {
		FileChooser fc = new FileChooser();

		File f = fc.showOpenDialog(owner);

		if (f != null) {
			byte[] img = new byte[(int) f.length()];

			FileInputStream fis = new FileInputStream(f);
			fis.read(img);
			fis.close();
			if (img.length < 52428800) {
				USER_PROFILE p = SESSION.getProfileLogged();
				DB_OPERATION.MERGE(p);
				return;
			}
			new CustomAlert(AlertType.INFORMATION, "Erro", "Arquivo muito grande", "Foto deve ser menor que 50 mb");
			return;
		}
	}

	public static Image getImage(USER_PROFILE p) throws IOException {
		BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(p.getPhoto()));
		Image img = SwingFXUtils.toFXImage(bfi, null);
		return img;
	}

	public static Image getImage (byte[] i) throws IOException { 
		BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(i));
		Image img = SwingFXUtils.toFXImage(bfi, null);
		
//		ImageView image = new ImageView();
//		image.setImage(img);
		return img;
	}
	
	/**
	 * Return the profile image of the user online
	 * 
	 * @return Image
	 * @throws IOException
	 * @author jefter66
	 */
	public static Image loadImage() throws IOException {
		BufferedImage bfi;
		if (SESSION.getProfileLogged().getPhoto() == null || SESSION.getProfileLogged().getPhoto().length == 0) {
			bfi = ImageIO.read((new File("resources/images/icons/profile_picture.png")));
		} else
			bfi = ImageIO.read(new ByteArrayInputStream(SESSION.getProfileLogged().getPhoto()));

		Image img = SwingFXUtils.toFXImage(bfi, null);
		return img;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
