package db.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="FRIENDSHIP")
public class FRIENDSHIP {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FR_REQUEST_ID")
	private int friendshipRequestId;
	
	@Column(name="FR_PROF_1")
	private int p1;
	
	@Column(name="FR_PROF_2")
	private int p2;
	
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false, name="FR_FRIENDSHIP_BEGIN")
	private java.util.Date dateBegin;
	
	
	@Column(name="FR_STATUS")
	private int status;


	public int getFriendshipRequestId() {
		return friendshipRequestId;
	}


	public void setFriendshipRequestId(int friendshipRequestId) {
		this.friendshipRequestId = friendshipRequestId;
	}


	public int getP1() {
		return p1;
	}


	public void setP1(int p1) {
		this.p1 = p1;
	}


	public int getP2() {
		return p2;
	}


	public void setP2(int p2) {
		this.p2 = p2;
	}


	public java.util.Date getDateBegin() {
		return dateBegin;
	}


	public void setDateBegin(java.util.Date dateBegin) {
		this.dateBegin = dateBegin;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
