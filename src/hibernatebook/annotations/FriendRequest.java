package hibernatebook.annotations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FriendRequest {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codFriendRequest;
	@Column
	private boolean friendRequest;
	@Column
	@Temporal(TemporalType.DATE)
	private java.util.Calendar calendarDate;
	@Column(nullable=false)
	private Profile requestedBy;
	@Column
	private Profile requestTo;
	
	public boolean isFriendRequest() {return friendRequest;}
	public void setFriendRequest(boolean friendRequest) {this.friendRequest = friendRequest;}

	public Profile getRequestedBy() {return requestedBy;}
	public void setRequestedBy(Profile requestedBy) {this.requestedBy = requestedBy;}
	
	public Profile getRequestTo() {return requestTo;}
	public void setRequestTo(Profile requestTo) {this.requestTo = requestTo;}
	
	public java.util.Calendar getCalendarDate() {return calendarDate;}	
	public void setCalendarDate(java.util.Calendar calendarDate) {this.calendarDate = calendarDate;}	
	
	
}






























