package friendship;

import java.io.IOException;
import java.util.List;

import db.pojos.FRIENDSHIP;
import db.pojos.USER_PROFILE;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.GENERAL_STORE;
import statics.SESSION;
import statics.ENUMS.REQUEST_STATUS;

public class FriendshipActions {

	private USER_PROFILE p;

	/**
	 * The parameter is the profile of who send the request, this profile is removed
	 * of the list of friendship requests and moved to the friends list in the two
	 * profiles ( sender and receiver )
	 * 
	 * @author jefter66
	 * @param USER_PROFILE pRequest
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

		List<?> listFriends = DB_OPERATION.QUERY(
				" FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER = :COD_USER_ONLINE AND FRQ_COD_PROF_REQUESTED_BY = :COD_USER OR FRQ_COD_PROF_RECEIVER = :COD_USER AND FRQ_COD_PROF_REQUESTED_BY = :COD_USER_ONLINE AND FRQ_REQUEST_STATUS='ON_HOLD'",
				new String[] { "COD_USER_ONLINE", "COD_USER" },
				new int[] { SESSION.getProfileLogged().getCod(), p.getCod() });

		if (listFriends.isEmpty()) {

			friendshipRequest.setRequestedBy(SESSION.getProfileLogged().getCod());
			friendshipRequest.setReceiver(this.p.getCod());
			friendshipRequest.setSendDate();
			friendshipRequest.setStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

			DB_OPERATION.PERSIST(friendshipRequest);
		}

	}

	public void answerRequest(REQUEST_STATUS type) {

		FRIENDSHIP fr = (FRIENDSHIP) DB_OPERATION.QUERY(
				"FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER = :COD_PROF_RECEIVER AND FRQ_COD_PROF_REQUESTED_BY = :COD_PROF_SENDER",
				new String[] { "COD_PROF_RECEIVER", "COD_PROF_SENDER" },
				new int[] { SESSION.getProfileLogged().getCod(), this.p.getCod() }).get(0);

		switch (type) {
		case REFUSED:
			fr.setStatus(ENUMS.REQUEST_STATUS.REFUSED.getValue());
			break;
		case ACCEPTED:
			fr.setStatus(ENUMS.REQUEST_STATUS.ACCEPTED.getValue());
			break;
		default:
			break;
		}
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
				GENERAL_STORE.loadComponentsHOME();
				return;
			}
		}
	}
}
