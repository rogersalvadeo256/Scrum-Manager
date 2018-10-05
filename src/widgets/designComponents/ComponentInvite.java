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
	private VBox leftColumn, rightColumn;
	
	public ComponentInvite() {
		
		this.leftColumn = new VBox();
		this.rightColumn = new VBox();
		
		this.getChildren().add(leftColumn);
		this.getChildren().add(rightColumn);
		
	}
	
	private void loadComponents() {

		
		
		
	}
	
	
	private void populateList() throws IOException {
		
		
		if (this.listComponents == null)
			this.listComponents = new ArrayList<HBProfileContentForInvite>();
		
		for (USER_PROFILE p : QUERYs_FRIENDSHIP.friendsList()) {
			
			HBProfileContentForInvite component = new HBProfileContentForInvite(p);
			this.listComponents.add(component);
		}
	}
}




































