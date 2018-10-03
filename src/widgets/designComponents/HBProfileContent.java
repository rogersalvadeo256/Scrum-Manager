package widgets.designComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;
import friendship.FriendshipActions;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import statics.PROFILE_IMG;

public class HBProfileContent extends HBox {
	protected VBox vbUsrIMG, vbUsrLABEL, vbUsrBUTTON;
	private ImageView usrImage;
	private Label lblName;
	private Button btnAdd;
	private FriendshipActions fRequest;

	public HBProfileContent(USER_PROFILE p) throws IOException {
		this.lblName = new Label(p.getName());
		init(p);
	}

	public HBProfileContent(USER_REGISTRATION u) throws IOException {
		this.lblName = new Label(u.getProfile().getName());
		init(u.getProfile());
	}

	private void init(USER_PROFILE p) throws IOException {
		this.vbUsrIMG = new VBox();
		this.vbUsrLABEL = new VBox();
		this.vbUsrBUTTON = new VBox();
		this.usrImage = new ImageView();
		this.btnAdd = new Button();
		this.fRequest = new FriendshipActions(p);
		this.btnAdd.setOnAction(e -> {
			fRequest.sendFriendshipRequest();
		});

		if (p.getPhoto() == null || p.getPhoto().length == 0) {
			this.usrImage.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));
		} else {
			this.usrImage.setImage(PROFILE_IMG.getImage(p));
		}
		this.usrImage.setFitWidth(100);
		this.usrImage.setFitHeight(100);

		this.vbUsrIMG.getChildren().addAll(this.usrImage);
		this.vbUsrBUTTON.getChildren().add(btnAdd);
		this.vbUsrLABEL.getChildren().addAll(this.lblName);
		this.vbUsrLABEL.setAlignment(Pos.CENTER);

		this.setAlignment(Pos.CENTER);
		this.vbUsrBUTTON.setAlignment(Pos.CENTER);
		
		try {
		ImageView icon_add = new ImageView();
		icon_add.setImage(new Image(new FileInputStream(new File("resources/images/icons/add.png"))));
		icon_add.setFitHeight(100);
		icon_add.setFitWidth(50);
		this.btnAdd.setGraphic(icon_add);
		this.btnAdd.prefHeight(this.getHeight());
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		HBox.setHgrow(vbUsrLABEL, Priority.ALWAYS);
		
		this.getChildren().addAll(vbUsrIMG, vbUsrLABEL, vbUsrBUTTON);
	}

	public Button getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(Button btnAdd) {
		this.btnAdd = btnAdd;
	}
}


























