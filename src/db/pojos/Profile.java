package db.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Profile {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 	
	@Column(name="codProfile")
	private int codProfile;
	@Column
	private String bio;
	@Column 
	private String name;
	
	@Column(columnDefinition = "LONGBLOB")
	private byte[] photo;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="codFriendRequest")
	private List<Profile> friendshipRequest;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="codFriend")
	private List<Profile> friendList;
	
	public Profile() { 
		this.friendList = new ArrayList<Profile>();
		this.friendshipRequest = new ArrayList<Profile>();
	}
	
	public byte[] getPhoto() {return photo;}
	public void setPhoto(byte[] photo) {this.photo = photo;}
	
	public void setCod(int codProfile) {this.codProfile = codProfile;}
	public int getCod() {return codProfile;}

	public String getName(){ return name;}
	public void setName(String name){this.name = name;}
	
	public String getBio() {return bio;}
	public void setBio(String biography) {this.bio = biography;}
	
	public List<Profile> getFriendshipRequests() {return friendshipRequest;}
	public void setFriendshipRequestList(List<Profile> friendshipRequestList) { 
		this.friendshipRequest = friendshipRequestList;
	}
	public List<Profile> getFriendsList() {return friendList;}
	public void setFriendList(List<Profile> friend) {this.friendList = friend;}
}



























