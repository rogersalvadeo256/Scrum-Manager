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
	private String biography;
	@Column 
	private String name;
	
	@Column(columnDefinition = "LONGBLOB")
	private byte[] photo;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="codFriendRequest")
	private List<Profile> friendshipRequest;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="codFriend")
	private List<Profile> friend;
	
	public Profile() { 
		this.friend = new ArrayList<Profile>();
		this.friendshipRequest = new ArrayList<Profile>();
	}
	
	public byte[] getPhoto() {return photo;}
	public void setPhoto(byte[] photo) {this.photo = photo;}
	
	public void setCod(int codProfile) {this.codProfile = codProfile;}
	public int getCod() {return codProfile;}

	public String getName(){ return name;}
	public void setName(String name){this.name = name;}
	
	public String getBiography() {return biography;}
	public void setBiography(String biography) {this.biography = biography;}
	
	public List<Profile> getFriendshipRequest() {return friendshipRequest;}

	
	public List<Profile> getFriend() {return friend;}
	public void setFriend(List<Profile> friend) {this.friend = friend;}
}


























