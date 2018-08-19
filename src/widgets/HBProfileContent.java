package widgets;
import POJOs.Profile;
import POJOs.UserRegistration;
import db.user.util.UserOnline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public  class HBProfileContent extends HBox {
		private VBox vbUsrIMG, vbUsrLABEL, vbUsrBUTTON;
		private ImageView usrImage;
		private Label usrName, usrBio;
		private Button btnAdd;
		public HBProfileContent(Profile p) {
			init(p);
			this.usrName = new Label(p.getName());
			this.usrBio = new Label(p.getBiography());
		}
		public HBProfileContent(UserRegistration u) {
			init(u.getProfile());
			this.usrName = new Label(u.getProfile().getName());
			this.usrBio = new Label(u.getProfile().getBiography());
		}
		private void init(Profile p) { 
			this.vbUsrIMG=new VBox();
			this.vbUsrLABEL=new VBox();
			this.vbUsrBUTTON=new VBox();
			this.usrImage=new ImageView();
			this.btnAdd=new Button("Adicionar");
			this.btnAdd.setOnAction(e -> {
				AddFriend(p);
			});
			this.vbUsrIMG.getChildren().add(this.usrImage);
			this.vbUsrBUTTON.getChildren().add(btnAdd);
			this.vbUsrLABEL.getChildren().addAll(this.usrName, this.usrBio);
			this.getChildren().addAll(vbUsrIMG,vbUsrLABEL,vbUsrBUTTON);
		}
		private void AddFriend(Profile p) { 
			UserOnline.getProfile().getFriend().add(p);
		}
	}