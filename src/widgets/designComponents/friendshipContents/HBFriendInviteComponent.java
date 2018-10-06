package widgets.designComponents.friendshipContents;


import java.io.IOException;

import db.pojos.USER_PROFILE;

public class HBFriendInviteComponent extends HBFriendContent{

	public HBFriendInviteComponent(USER_PROFILE p) throws IOException {
		super(p);
		this.getChildren().removeAll(this.btnDelete,this.btnInvite);
		this.getChildren().add(this.btnInvite);
		
	}

	
	
	
}
