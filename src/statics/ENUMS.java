package statics;

public class ENUMS {

	public static enum REQUEST_STATUS {
		ON_HOLD, ACCEPTED, REFUSED
	}
	public static enum FRIEND_STATE {
		NORMAL, BLOCK_FRIEND,
		DELETE_FRIEND
	}

	public static enum DISPONIBILITY_FOR_PROJECT {
		AVAILABLE, NOT_AVAILABLE, BUSY
	}

	public static String GET_FRIEND_STATE(FRIEND_STATE value) {
		switch (value) {
		case NORMAL:
			return "FRIENDS";
		case BLOCK_FRIEND:
			return "FRIEND_BLOCKED";
		case DELETE_FRIEND:
			return "FRIEND_DELETED";
		default:
			break;
		}
		return new String();
	}

	public static String GET_DISPONIBILITY_FOR_PROJECT(DISPONIBILITY_FOR_PROJECT value) {
		switch (value) {
		case AVAILABLE:
			return "AVAILABLE";
		case NOT_AVAILABLE:
			return "NOT_AVAILABLE";
		case BUSY:
			return "BUSY";
		default:
			break;
		}
		return new String();
	}

	public static String GET_REQUEST_STATUS(REQUEST_STATUS value) {
		switch (value) {
		case ACCEPTED:
			return "ACCEPTED";
		case REFUSED:
			return "REFUSED";
		case ON_HOLD:
			return "ON_HOLD";
		default:	
			break;
		}
		return new String();
	}
}




















