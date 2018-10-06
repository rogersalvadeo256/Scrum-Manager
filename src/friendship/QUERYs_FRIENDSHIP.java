package friendship;

import java.util.ArrayList;
import java.util.List;

import db.pojos.FRIENDSHIP;
import db.pojos.USER_PROFILE;
import statics.DB_OPERATION;
import statics.SESSION;

public class QUERYs_FRIENDSHIP {

	/**
	 * Do a query and add to a arraylist the friendship requests of the user that
	 * are on hold
	 * 
	 * @author jefter66
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<USER_PROFILE> friendshipRequestsList() {
		List<USER_PROFILE> listReturn = new ArrayList<USER_PROFILE>();

		List<?> requests = DB_OPERATION.QUERY(
				"FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER =: COD AND FRQ_REQUEST_STATUS = 'ON_HOLD'", "COD",
				SESSION.getProfileLogged().getCod());

		for (FRIENDSHIP request : (List<FRIENDSHIP>) requests) {

			List<?> p = DB_OPERATION.QUERY("FROM USER_PROFILE WHERE PROF_COD = :COD", "COD", request.getRequestedBy());

			for (USER_PROFILE profile : (List<USER_PROFILE>) p) {
				listReturn.add(profile);
			}
		}
		return listReturn;
	}
	/*
	 * SOO FUCKING UGLY
	 */
	@SuppressWarnings("unchecked")
	public static List<USER_PROFILE> friendsList() {

		List<USER_PROFILE> listReturn = new ArrayList<USER_PROFILE>();

		List<?> listFriends = DB_OPERATION.QUERY(
				"FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER = :COD AND FRQ_REQUEST_STATUS= 'ACCEPTED' OR FRQ_COD_PROF_REQUESTED_BY = :COD AND FRQ_REQUEST_STATUS = 'ACCEPTED'",
				"COD", SESSION.getProfileLogged().getCod());

		for (FRIENDSHIP friend : (List<FRIENDSHIP>) listFriends) {
			List<?> profileList = DB_OPERATION.QUERY(
					"FROM USER_PROFILE WHERE PROF_COD = :COD OR PROF_COD = :COD2 AND PROF_COD <> :USER_ONLINE",
					new String[] { "COD", "COD2", "USER_ONLINE" },
					new int[] { friend.getRequestedBy(), friend.getReceiver(), SESSION.getProfileLogged().getCod() });

			for (USER_PROFILE profile : (List<USER_PROFILE>) profileList) {
				listReturn.add(profile);
			}
		}
		return listReturn;
	}


	@SuppressWarnings("unchecked")
	public static List<FRIENDSHIP> friendshipList() {

		List<?> list = DB_OPERATION.QUERY(
				"FROM FRIENDSHIP WHERE FRQ_COD_PROF_RECEIVER = :COD OR FRQ_COD_PROF_REQUESTED_BY = :COD AND FRQ_REQUEST_STATUS ='ACCEPTED'",
				"COD", SESSION.getProfileLogged().getCod());
		return (List<FRIENDSHIP>) list;
	}
}
