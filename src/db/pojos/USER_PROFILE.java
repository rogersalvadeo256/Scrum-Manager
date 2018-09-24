package db.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import statics.IMPORTANT_ENUMS.AVAILABILITY;
@SuppressWarnings("serial")
@Entity(name="USER_PROFILE")
public class USER_PROFILE implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROF_COD")
	private int codProfile;
	
	/*
	 * THIS SHIT WILL BECAME THE STATUS
	 */
	@Column(name="PROF_NAME")
	private String name;

	@Column(name="PROF_AVAILABILITY")
	private int availability;
	

	public USER_PROFILE() {
//		this.friendList = new ArrayList<USER_PROFILE>();
//		this.friendshipRequest = new ArrayList<USER_PROFILE>();
	}
	
	/*
	 * REMEMBER TO ADD DISPONIBILITY/ STATUS
	 * IF THE STATUS ARE "BUSY" AND NOT SHOW ALLOWED TO INVITE FOR PROJECTS
	 * 
	 * 	USING ENUM????
	 */

	
	
	@Column(columnDefinition = "LONGBLOB", name="PROF_PHOTO")
	private byte[] photo;

	public void setAvailability(AVAILABILITY status) {
		this.availability = Integer.parseInt(String.valueOf(status));
	}
	public int getAvailability() {
		return this.availability;
	}
	/*
	 * kk
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "codFriendRequest")
	private List<USER_PROFILE> friendshipRequest;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "codFriend")
	private List<USER_PROFILE> friendList;
	 * 
	 */
	

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public void setCod(int codProfile) {
		this.codProfile = codProfile;
	}

	public int getCod() {
		return codProfile;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public List<USER_PROFILE> getFriendshipRequests() {
//		return friendshipRequest;
//	}
//
//	public void setFriendshipRequestList(List<USER_PROFILE> friendshipRequestList) {
//		t	his.friendshipRequest = friendshipRequestList;
//	}
//
//	public List<USER_PROFILE> getFriendsList() {
//		return friendList;
//	}
//
//	public void setFriendList(List<USER_PROFILE> friend) {
//		this.friendList = friend;
//	}
}
