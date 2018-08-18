package friendship;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import POJOs.Profile;
import auto.instance.objects.HBFriendRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FriendRequest  extends Stage {
	
	private ArrayList<HBFriendRequest> hbFriendRequest;
	private ArrayList<Profile> listFriendRequest;
	private VBox layout;
	
	public FriendRequest( Window parent) throws FileNotFoundException {
		
		this.hbFriendRequest=new ArrayList<HBFriendRequest>();
		this.layout=new VBox();
		this.hbFriendRequest.add(new HBFriendRequest());

		
		drawHbox();
		Scene scene = new Scene(layout);
		this.setScene(scene);
		this.initOwner(parent);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(400);
		this.setHeight(500);
		this.setResizable(false);
	}
		
	private void drawHbox() throws FileNotFoundException { 
		for(int i = 0; i < this.hbFriendRequest.size(); i++) { 
			HBFriendRequest f = new HBFriendRequest();
			this.hbFriendRequest.add(f);
			this.layout.getChildren().add(hbFriendRequest.get(i));
			this.hbFriendRequest.clear();
		}
	}
	
	
	
	
	
	
	
	
	
}
