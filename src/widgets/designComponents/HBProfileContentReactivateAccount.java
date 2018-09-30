package widgets.designComponents;

import java.io.IOException;

import db.pojos.USER_PROFILE;
import db.pojos.USER_REGISTRATION;

public class HBProfileContentReactivateAccount extends HBProfileContentForgotPassword {

	public HBProfileContentReactivateAccount(USER_PROFILE p) throws IOException {
		super(p);
	}

	public HBProfileContentReactivateAccount(USER_REGISTRATION ur) throws IOException {
		super(ur);

	}


}
