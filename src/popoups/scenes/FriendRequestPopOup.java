package popoups.scenes;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import DB.database.functions.definition.UserOnline;
import POJOs.Profile;
import auto.instance.objects.HBFriendRequest;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FriendRequestPopOup  extends Stage {
	
	private ArrayList<HBFriendRequest> hbFriendRequest;
	private ArrayList<Profile> listFriendRequest;
	private VBox layout;
	private final ScrollBar sc ;
	
	public FriendRequestPopOup( Window parent) throws FileNotFoundException {
		this.layout=new VBox();
		
//		this.listFriendRequest = (ArrayList<Profile>) UserOnline.getProfile().getFriendRequest();
		this.listFriendRequest=new ArrayList<Profile>();
		this.hbFriendRequest=new ArrayList<HBFriendRequest>();
		
		listFriendRequest.add(new Profile());
		listFriendRequest.add(new Profile());
		listFriendRequest.add(new Profile());
		
		
		
		drawHbox();
		Scene scene = new Scene(layout);
		this.setScene(scene);
		
		this.sc = new ScrollBar();
		this.sc.setLayoutX(scene.getWidth() - sc.getWidth());
		this.sc.setMin(0);
		this.sc.setOrientation(Orientation.VERTICAL);
		sc.prefHeight(this.getHeight());
		
		
		
		this.initOwner(parent);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(400);
		this.setHeight(500);
		this.setResizable(false);
	}
		
	private void drawHbox() throws FileNotFoundException { 
		for(int i = 0; i < this.listFriendRequest.size(); i++) { 
			HBFriendRequest f = new HBFriendRequest(this.listFriendRequest.get(i));
			this.hbFriendRequest.add(f);
			this.layout.getChildren().add(hbFriendRequest.get(i));
		}
		this.hbFriendRequest.clear();
	}
	
	
	
	
	
	
	
	
	
}
