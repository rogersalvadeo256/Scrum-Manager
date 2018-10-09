package widgets.designComponents.projectContents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.pojos.USER_PROFILE;
import friendship.QUERYs_FRIENDSHIP;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.querys.TEMP_STORE_INVITATIONS;
import widgets.designComponents.profileContents.HBProfileContentForInvite;

public class HBColumnsInvite extends HBox {


	private List<HBProfileContentForInvite> listComponents;

	private VBox vbLeft;
	private VBox vbRight;

	public HBColumnsInvite() throws IOException {

		loadFriendContent();

	}

	/**
	 * populate the two vbox on the layout simple logic to make the two vbox had the
	 * same amount of components if the number of components are odd, will be setted
	 * one component for the vbox that have the smaller amount of components
	 * 
	 * @author jefter66
	 * @throws IOException
	 */
	public void loadFriendContent() throws IOException {
		this.vbLeft = new VBox();
		this.vbRight = new VBox();
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

			for (int i = value * 2; i < this.listComponents.size(); i++) {

				if (this.vbRight.getChildren().size() < this.vbLeft.getChildren().size()) {
					this.vbRight.getChildren().add(this.listComponents.get(i));
					break;
				}
				this.vbLeft.getChildren().add(this.listComponents.get(i));
			}
		}
		this.getChildren().addAll(vbLeft, vbRight);
	}
	/**
	 * 
	 * Loads the friendlist, set friends informations into one component, this
	 * component is setted to dispare a event when clicked. When the component is
	 * clicked, the friend is added into a list, for later he can be invited for the
	 * project
	 * 
	 * @author jefter66
	 * @throws IOException
	 */
	private void populateList() throws IOException {

		if (this.listComponents == null)
			this.listComponents = new ArrayList<HBProfileContentForInvite>();

		this.listComponents.clear();

		for (USER_PROFILE p : QUERYs_FRIENDSHIP.friendsList()) {
			HBProfileContentForInvite component = new HBProfileContentForInvite(p);
			
			
			component.setClickedEvent(e -> {

					for (USER_PROFILE var : TEMP_STORE_INVITATIONS.LIST_INVITATION()) {
						if (var == component.getProfile()) {
							TEMP_STORE_INVITATIONS.REMOVE_FROM_LIST(var);
							return;
						}
					}
//				}
				TEMP_STORE_INVITATIONS.LIST_INVITATION().add(component.getProfile());
				return;
			});
			this.listComponents.add(component);
		}
	}

}
