package widgets.designComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import db.pojos.Profile;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class HBFriendRequest extends HBox {
	private Label lblName, lblBio;
	private VBox vbAlignItemsLeft, vbAlignItemsMiddle, vbAlignItemsRight;

	private ImageView image;
	private File path;
	private FileInputStream fis;
	
	private HBox layout;
	private Button btnAccept, btnRefuse;
	public HBFriendRequest(Profile p) throws FileNotFoundException {
		this.image = new ImageView();

		this.lblName = new Label(p.getName());
		this.lblBio = new Label(p.getBiography());
		
		
		this.btnAccept = new Button("Aceitar");
		this.btnAccept.setOnAction(e -> { 
			
		});
		
		this.btnRefuse = new Button("Recusar");
		this.btnRefuse.setOnAction(e -> { 
			
		});
		
		this.vbAlignItemsLeft = new VBox();
		this.vbAlignItemsMiddle = new VBox();
		this.vbAlignItemsRight=new VBox();
		
		this.image.setFitHeight(100);
		this.image.setFitWidth(100)	 ;
		this.path = new File("resources/images/icons/scrum_icon.png");
		this.fis = new FileInputStream(path);
		
		this.image.setImage(new Image(fis));

		this.vbAlignItemsLeft.getChildren().add(image);

		this.vbAlignItemsMiddle.getChildren().addAll(lblName, lblBio);
		this.vbAlignItemsMiddle.setSpacing(10);
		this.vbAlignItemsMiddle.setAlignment(Pos.CENTER);
		
		
		this.vbAlignItemsRight.getChildren().addAll(btnAccept, btnRefuse);
		this.vbAlignItemsRight.setSpacing(10);
		
		
		this.layout = new HBox();
		
		this.layout.setSpacing(50);

		this.layout.getChildren().addAll(vbAlignItemsLeft, vbAlignItemsMiddle);
		this.layout.getChildren().addAll(btnAccept, btnRefuse);
		
		this.setSpacing(10);
		this.getChildren().add(layout);

		
		
	}
}