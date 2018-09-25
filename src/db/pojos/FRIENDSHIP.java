package db.pojos;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import statics.ENUMS.FRIEND_STATE;

@Entity(name = "FRIENDSHIP")
public class FRIENDSHIP {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FR_REQUEST_ID")
	private int friendshipRequestId;

	@Column(name = "FR_PROF_1")
	private int codProf1;

	@Column(name = "FR_PROF_2")
	private int codProf2;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false, name = "FR_FRIENDSHIP_BEGIN")
	private java.util.Date dateBegin;

	@Column(name = "FR_STATUS")
	private String status;

	public int getFriendshipRequestId() {
		return friendshipRequestId;
	}
	public void setFriendshipRequestId(int friendshipRequestId) {
		this.friendshipRequestId = friendshipRequestId;
	}

	public int getCodProf1() {
		return codProf1;
	}

	public void setCodProf1(int codProf1) {
		this.codProf1 = codProf1;
	}

	public int getCodProf2() {
		return codProf2;
	}

	public void setCodProf2(int codProf2) {
		this.codProf2 = codProf2;
	}

	public java.util.Date getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin() {
		this.dateBegin = Calendar.getInstance().getTime();
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
