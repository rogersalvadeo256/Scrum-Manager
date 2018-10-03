package friendship;

import java.io.IOException;
import java.util.List;

import db.pojos.FRIENDSHIP;
import db.pojos.USER_PROFILE;
import db.querys.QUERYs_FRIENDSHIP;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.GENERAL_STORE;
import statics.SESSION;

public class FriendshipActions {

	private USER_PROFILE p;

	/**
	 * The parameter is the profile of who send the request, this profile is removed of
	 * the list of friendship requests and moved to the friends list in the two profiles (
	 * sender and receiver )
	 * 
	 * @author jefter66
	 * @param USER_PROFILE
	 *            pRequest
	 */
	public FriendshipActions(USER_PROFILE p) {
		this.p = p;
	}

	/**
	 * The parameter are the user that going to receive the friendship request
	 * 
	 * @param USER_PROFILE
	 */
	public void sendFriendshipRequest() {// Profile p) {

		FRIENDSHIP friendshipRequest = new FRIENDSHIP();

		friendshipRequest.setRequestedBy(SESSION.getProfileLogged().getCod());
		friendshipRequest.setReceiver(this.p.getCod());
		friendshipRequest.setSendDate();
		friendshipRequest.setStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

		DB_OPERATION.PERSIST(friendshipRequest);

	}

	public void acceptRequest() {

		FRIENDSHIP frq = (FRIENDSHIP) DB_OPERATION.QUERY("FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER =:COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY =:COD_PROF_SENDER"
				,new String[]{"COD_PROF_RECEIVER", "COD_PROF_SENDER"}, new int[]{SESSION.getProfileLogged().getCod(), this.p.getCod()}).get(0);

		frq.setStatus(ENUMS.REQUEST_STATUS.ACCEPTED.getValue());


		DB_OPERATION.MERGE(frq);

		SESSION.UPDATE_SESSION();
	}

	public void refuseRequest() {

		FRIENDSHIP fr = (FRIENDSHIP) DB_OPERATION.QUERY("FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER = :COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY = :COD_PROF_SENDER"
				,new String[] {"COD_PROF_RECEIVER", "COD_PROF_SENDER"}, new int[]{SESSION.getProfileLogged().getCod(), this.p.getCod()}).get(0);
		
		
		fr.setStatus(ENUMS.REQUEST_STATUS.REFUSED.getValue());

		DB_OPERATION.MERGE(fr);

		SESSION.UPDATE_SESSION();
	}

	public void removeFriend() throws IOException {

		for (FRIENDSHIP r : (List<FRIENDSHIP>) QUERYs_FRIENDSHIP.friendshipList()) {

			if (r.getReceiver() == this.p.getCod() || r.getRequestedBy() == this.p.getCod()) {
				r.setStatus(ENUMS.REQUEST_STATUS.REMOVED.getValue());
				FRIENDSHIP fr = r;

				DB_OPERATION.MERGE(fr);
				SESSION.UPDATE_SESSION();
				GENERAL_STORE.updateComponentsHOME();
				return;
			}
		}
	}
}
