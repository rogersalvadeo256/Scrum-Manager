package auto.instance.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import POJOs.Profile;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HBFriendRequest extends HBox {
	private Label lblName, lblUserName, lblEmail;
	private VBox vbAlignItemsLeft, vbAlignItemsRight;

	private ImageView image;
	private File path;
	private FileInputStream fis;
	
	private HBox layout;
	private Button btnAccept, btnRefuse;
	public HBFriendRequest() throws FileNotFoundException {
		this.image = new ImageView();

		this.lblName = new Label("nome");
		this.lblUserName = new Label("username");
		this.lblEmail = new Label("email");

		this.btnAccept = new Button("Aceitar");
		this.btnAccept.setOnAction(e -> { 
				
			
		});
		
		this.btnRefuse = new Button("Recusar");
		this.btnRefuse.setOnAction(e -> { 
			
		});
		
		this.vbAlignItemsLeft = new VBox();
		this.vbAlignItemsRight = new VBox();

		this.image.setFitHeight(100);
		this.image.setFitWidth(100);
		this.path = new File("/home/jefter66/java-workspace/TCC/resources/images/icons/scrum_icon.png");
		this.fis = new FileInputStream(path);
		
		this.image.setImage(new Image(fis));

		this.vbAlignItemsLeft.getChildren().add(image);

		this.vbAlignItemsRight.getChildren().addAll(lblName, lblUserName, lblEmail);
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
