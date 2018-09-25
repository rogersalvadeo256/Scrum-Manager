package db.pojos;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import statics.ENUMS.REQUEST_STATUS;

@Entity(name = "FRIENDSHIP_REQUEST")
public class FRIENDSHIP_REQUEST {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FRQ_REQUEST_ID")
	private int friendshipRequestId;

	@Column(nullable = false, name = "FRQ_COD_PROF_REQUESTED_BY")
	private int requestedBy;

	@Column(nullable = false, name = "FRQ_COD_PROF_RECEIVER")
	private int receiver;

	@Column(name = "FRQ_REQUEST_STATUS")
	private String status;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false, name = "FRQ_DATE_REQUEST_SENDED")
	private java.util.Date sendDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "FRQ_DATE_REQUEST_ANSWERED")
	private java.util.Date answredDate;

	public java.util.Date getSendDate() {
		return sendDate;
	}

	public void setSendDate() {
		this.sendDate = Calendar.getInstance().getTime();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;

	}

	public int getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(int requestedByCod) {
		this.requestedBy = requestedByCod;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int profileCod) {
		this.receiver = profileCod;
	}

	public int getFriendshipRequestId() {
		return friendshipRequestId;
	}

	public void setFriendshipRequestId(int friendshipRequestId) {
		this.friendshipRequestId = friendshipRequestId;
	}

	public java.util.Date getAnswredDate() {
		return answredDate;
	}

	public void setAnswredDate(java.util.Date answredDate) {
		this.answredDate = answredDate;
	}

}
