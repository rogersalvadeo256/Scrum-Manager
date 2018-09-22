package db.pojos;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="FRIENDSHIP_REQUEST")
public class FRIENDSHIP_REQUEST { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FRQ_REQUEST_ID")
	private int friendshipRequestId;
	
	@Column(nullable = false, name="FRQ_PROF_REQUESTED_BY" )
	private USER_PROFILE requestedBy;
	
	@Column(nullable = false, name="FRQ_PROF_RECEIVER")
	private USER_PROFILE receiver;

	@Column(name="FRQ_REQUEST_STATUS") 
	private int status;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false, name="FRQ_DATE_REQUEST_SENDED")
	private java.util.Date sendDate;

	public java.util.Date getSendDate() {
		return sendDate;
	}
	public void setSendDate() {
		this.sendDate = Calendar.getInstance().getTime();
	}

	private enum REQUEST_STATUS { 
		ACCEPTED, REFUSED, ON_HOLD
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(REQUEST_STATUS status) {
		this.status = Integer.parseInt(String.valueOf(status));
	}

	
	public USER_PROFILE getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(USER_PROFILE requestedBy) {
		this.requestedBy = requestedBy;
	}

	public USER_PROFILE getReceiver() {
		return receiver;
	}

	public void setReceiver(USER_PROFILE receiver) {
		this.receiver = receiver;
	}
	public int getFriendshipRequestId() {
		return friendshipRequestId;
	}

	public void setFriendshipRequestId(int friendshipRequestId) {
		this.friendshipRequestId = friendshipRequestId;
	}

	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}








































































