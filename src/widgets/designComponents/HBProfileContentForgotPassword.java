package widgets.designComponents;

import java.io.IOException;

import db.pojos.Profile;
import db.pojos.UserRegistration;

public class HBProfileContentForgotPassword extends HBProfileContent{

	public HBProfileContentForgotPassword(Profile p) throws IOException {
		super(p);
		super.getBtnAdd().setVisible(false);;
	}
	public HBProfileContentForgotPassword(UserRegistration u) throws IOException {
		super(u);
		super.getBtnAdd().setVisible(false);;
	}
	
	
	
	
}
