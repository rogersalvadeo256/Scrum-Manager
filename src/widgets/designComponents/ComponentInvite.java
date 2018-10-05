package widgets.designComponents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.pojos.USER_PROFILE;
import friendship.QUERYs_FRIENDSHIP;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ComponentInvite extends HBox {

	private List<HBProfileContentForInvite> listComponents;

	private VBox vbLeft;
	private VBox vbRight;

	public ComponentInvite() throws IOException {

		this.vbLeft = new VBox();
		this.vbRight = new VBox();

		loadFriendContent();

	}
	public void loadFriendContent() throws IOException {

		populateList();
		int value = this.listComponents.size() / 2;

		this.vbRight.getChildren().clear();
		this.vbLeft.getChildren().clear();

		for (int i = 0; i < value; i++) {
			this.vbLeft.getChildren().add(this.listComponents.get(i));
		}
		for (int i = value; i < value * 2; i++) {
			this.vbRight.getChildren().add(this.listComponents.get(i));
		}

		if (this.listComponents.size() % 2 > 0) {

			for(int i = value * 2; i < this.listComponents.size(); i++) { 

				if(this.vbRight.getChildren().size() < this.vbLeft.getChildren().size()) { 
					this.vbRight.getChildren().add(this.listComponents.get(i));
					break;
				}
				this.vbLeft.getChildren().add(this.listComponents.get(i));

			}

		}

		this.getChildren().addAll(vbLeft,vbRight);

	}

	private void populateList() throws IOException {

		if (this.listComponents == null)
			this.listComponents = new ArrayList<HBProfileContentForInvite>();

		this.listComponents.clear();

		for (USER_PROFILE p : QUERYs_FRIENDSHIP.friendsList()) {

			HBProfileContentForInvite component = new HBProfileContentForInvite(p);
			this.listComponents.add(component);
		}
	}
}
