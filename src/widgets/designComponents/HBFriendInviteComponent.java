package widgets.designComponents;

import java.io.IOException;

import db.pojos.Profile;

public class HBFriendInviteComponent extends HBFriendContent{

	public HBFriendInviteComponent(Profile p) throws IOException {
		super(p);
		this.getChildren().removeAll(this.btnDelete,this.btnInvite);
		this.getChildren().add(this.btnInvite);
		
		
		
	}

	
	
	
}
