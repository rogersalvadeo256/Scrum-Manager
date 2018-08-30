package popoups.scenes;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import POJOs.Profile;
import db.user.util.SESSION;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import widgets.HBFriendRequest;

public class FriendshipRequestPopOup extends Stage {

	private ArrayList<HBFriendRequest> hbFriendRequest;
	private ArrayList<Profile> listFriendRequest;
	private VBox layout;
	private final ScrollBar sc;

	/**
	 * This scene will contain the friendship requests of the user the scene have
	 * he's own stage, this because are a popoup window
	 * 
	 * @author jefter66
	 * 
	 */
	public FriendshipRequestPopOup(Window parent) throws FileNotFoundException {
		this.layout = new VBox();

		// this.listFriendRequest = (ArrayList<Profile>)
		// UserOnline.getProfile().getFriendRequest();
		this.listFriendRequest = new ArrayList<Profile>();
		this.hbFriendRequest = new ArrayList<HBFriendRequest>();

		drawHbox();
		Scene scene = new Scene(layout);
		this.setScene(scene);

		this.sc = new ScrollBar();
		this.sc.setLayoutX(scene.getWidth() - sc.getWidth());
		this.sc.setMin(0);
		this.sc.setOrientation(Orientation.VERTICAL);
		this.sc.prefHeight(this.getHeight());

		this.initOwner(parent);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(400);
		this.setHeight(500);
		this.setResizable(false);
	}
	/**
	 * This method are for bring the requests of the database and put on a
	 * HBFriendshipRequest and show for the user, according to the amount of
	 * registers in the database the hbox's will be drawn on the scene
	 * 
	 * @throws FileNotFoundException
	 * @author jefter66
	 */
	private void drawHbox() throws FileNotFoundException {
		this.listFriendRequest.clear();
		this.hbFriendRequest.clear();

		for (int i = 0; i < SESSION.getProfileLogged().getFriendRequest().size(); i++) {
			this.listFriendRequest.add(SESSION.getProfileLogged().getFriendRequest().get(i));
		}
		if (!this.listFriendRequest.isEmpty()) {
			for (int i = 0; i < this.listFriendRequest.size(); i++) {
				HBFriendRequest f = new HBFriendRequest(this.listFriendRequest.get(i));
				this.hbFriendRequest.add(f);
				this.layout.getChildren().add(hbFriendRequest.get(i));
			}
			this.hbFriendRequest.clear();
			return;
		}
		this.layout.getChildren().add(new Label("Nenhum pedido de amizade"));
	}
}
























