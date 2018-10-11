package widgets.designComponents.profileContents;

import java.io.IOException;

import db.pojos.USER_PROFILE;
import javafx.geometry.Pos;

public class HBProfileInvitedBy  extends HBProfileContentForInvite{

	public HBProfileInvitedBy(USER_PROFILE p) throws IOException {
		super(p);
		this.layout.getChildren().remove(circle);
		this.layout.getChildren().remove(hbStatus);
		this.setAlignment(Pos.CENTER_LEFT);
	}
}
