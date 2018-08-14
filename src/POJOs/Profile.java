package POJOs;

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
	private long codProfile;
	@Column
	private String biography;
	@Column 
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="codFriendRequest")
	private List<FriendRequest> friendRequest;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="codFriend")
	private List<Friend> friend;
	
	public Profile() { 
		this.friend = new ArrayList<Friend>();
		this.friendRequest = new ArrayList<FriendRequest>();
	}

	public void setCod(long codProfile) {this.codProfile = codProfile;}
	public long getCod() {return codProfile;}

	public String getName(){ return name;}
	public void setName(String name){this.name = name;}
	
	public String getBiography() {return biography;}
	public void setBiography(String biography) {this.biography = biography;}
	
	public List<FriendRequest> getFriendRequest() {return friendRequest;}
	public void setFriendRequest(List<FriendRequest> friendRequest) {this.friendRequest = friendRequest;}
	
	public List<Friend> getFriend() {return friend;}
	public void setFriend(List<Friend> friend) {this.friend = friend;}
}



































