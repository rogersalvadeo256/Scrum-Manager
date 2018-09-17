package widgets.designComponents;

import java.io.FileInputStream;
import java.io.IOException;

import db.pojos.Profile;
import db.pojos.UserRegistration;
import db.util.ProfileImg;
import friendship.FriendshipRequest;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HBProfileContent extends HBox {
	protected VBox vbUsrIMG, vbUsrLABEL, vbUsrBUTTON;
	private ImageView usrImage;
	private Label lblName, lblBio;
	private Button btnAdd;
	private FriendshipRequest fRequest;
	public HBProfileContent(Profile p) throws IOException {
		this.lblName = new Label(p.getName());
		this.lblBio = new Label(p.getBio());
		init(p);
	}
	
	public HBProfileContent(UserRegistration u) throws IOException {
		this.lblName = new Label(u.getProfile().getName());
		this.lblBio = new Label(u.getProfile().getBio());
		init(u.getProfile());
	}
	
	private void init(Profile p) throws IOException {
		this.vbUsrIMG = new VBox();
		this.vbUsrLABEL = new VBox();
		this.vbUsrBUTTON = new VBox();
		this.usrImage = new ImageView();
		this.btnAdd = new Button("Adicionar");
		this.fRequest = new FriendshipRequest(p);
		this.btnAdd.setOnAction(e -> {
			fRequest.sendFriendshipRequest();
		});
		
		if(p.getPhoto() == null || p.getPhoto().length == 0){ 
				this.usrImage.setImage(new Image(new FileInputStream("resources/images/icons/scrum_icon.png")));
		}else { 
				this.usrImage.setImage(ProfileImg.getImage(p));
		}
		this.usrImage.setFitWidth(100);
		this.usrImage.setFitHeight(100);
		
		this.vbUsrIMG.getChildren().addAll(this.usrImage);
		this.vbUsrBUTTON.getChildren().add(btnAdd);
		this.vbUsrLABEL.getChildren().addAll(this.lblName, this.lblBio);
		
		this.getChildren().addAll(vbUsrIMG, vbUsrLABEL, vbUsrBUTTON);
	}
	public Button getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(Button btnAdd) {
		this.btnAdd = btnAdd;
	}
}
