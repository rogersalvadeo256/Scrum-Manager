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
	private List<Profile> friendRequest;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="codFriend")
	private List<Profile> friend;
	
	public Profile() { 
		this.friend = new ArrayList<Profile>();
		this.friendRequest = new ArrayList<Profile>();
	}

	public void setCod(long codProfile) {this.codProfile = codProfile;}
	public long getCod() {return codProfile;}

	public String getName(){ return name;}
	public void setName(String name){this.name = name;}
	
	public String getBiography() {return biography;}
	public void setBiography(String biography) {this.biography = biography;}
	
	public List<Profile> getFriendRequest() {return friendRequest;}
	public void setFriendRequest(List<Profile> friendRequest) {this.friendRequest = friendRequest;}
	
	public List<Profile> getFriend() {return friend;}
	public void setFriend(List<Profile> friend) {this.friend = friend;}
}



































