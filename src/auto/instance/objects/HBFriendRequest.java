package auto.instance.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import DB.database.functions.definition.UserOnline;
import POJOs.Profile;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HBFriendRequest extends HBox {
	private Label lblName, lblBio;
	private VBox vbAlignItemsLeft, vbAlignItemsRight;

	private ImageView image;
	private File path;
	private FileInputStream fis;
	
	private HBox layout;
	private Button btnAccept, btnRefuse;
	public HBFriendRequest(Profile p) throws FileNotFoundException {
		this.image = new ImageView();

//		this.lblName = new Label(p.getName());
//		this.lblBio = new Label(p.getBiography());
		
		this.lblName = new Label("Nome");
		this.lblBio = new Label("DESCRICAO");
		
		
		this.btnAccept = new Button("Aceitar");
		this.btnAccept.setOnAction(e -> { 
		});
		
		this.btnRefuse = new Button("Recusar");
		this.btnRefuse.setOnAction(e -> { 
			
		});
		
		this.vbAlignItemsLeft = new VBox();
		this.vbAlignItemsRight = new VBox();

		this.image.setFitHeight(100);
		this.image.setFitWidth(100)	 ;
		this.path = new File("/home/jefter66/java-workspace/TCC/src/tempPkg/image.jpg");
		this.fis = new FileInputStream(path);
		
		this.image.setImage(new Image(fis));

		this.vbAlignItemsLeft.getChildren().add(image);

		this.vbAlignItemsRight.getChildren().addAll(lblName, lblBio);
		this.vbAlignItemsRight.setSpacing(20);
		this.vbAlignItemsRight.setAlignment(Pos.CENTER);

		this.layout = new HBox();
		
		this.layout.setSpacing(10);

		this.layout.getChildren().addAll(vbAlignItemsLeft, vbAlignItemsRight);
		this.layout.getChildren().addAll(btnAccept, btnRefuse);
		
		this.setSpacing(10);
		this.getChildren().add(layout);

		
		
	}
}
