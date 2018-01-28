package widgets.designComponents;

import java.io.IOException;

import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;

public class HBProfileContentForgotPassword extends HBProfileContent{

	public HBProfileContentForgotPassword(USER_PROFILE p) throws IOException {
		super(p);
		super.getBtnAdd().setVisible(false);;
	}
	public HBProfileContentForgotPassword(USER_REGISTRATION u) throws IOException {
		super(u);
		super.getBtnAdd().setVisible(false);;
	}	
}
