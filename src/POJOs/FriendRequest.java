package POJOs;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FriendRequest {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codFriendRequest;

	/**
	 * @author jefter66
	 * values for the requestStatus: 
	 * 0 - pending
	 * 1 - accepted
	 * 2 - declained
	 */
	@Column
	private int requestStatus;
	@Column
	@Temporal(TemporalType.DATE)
	private java.util.Calendar calendarDate;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Profile")
	private Profile requestedBy;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Profile")
	private Profile requestTo;
	
	
	public long getCodFriendRequest() {return codFriendRequest;}
	public void setCodFriendRequest(long codFriendRequest) {this.codFriendRequest = codFriendRequest;}
	
	public int getRequestStatus() {return requestStatus;}
	public void setRequestStatus(int requestStatus) {this.requestStatus = requestStatus;}
	
	public Profile getRequestedBy() {return requestedBy;}
	public void setRequestedBy(Profile requestedBy) {this.requestedBy = requestedBy;}
	
	public Profile getRequestTo() {return requestTo;}
	public void setRequestTo(Profile requestTo) {this.requestTo = requestTo;}
	
	public java.util.Calendar getCalendarDate() {return calendarDate;}	
	public void setCalendarDate(java.util.Calendar calendarDate) {this.calendarDate = calendarDate;}	
	
	
}






























