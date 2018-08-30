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

@Entity
public class UserRegistration {
	public UserRegistration() { 
		this.userProfile=new Profile();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codUser;
	@Column
	private String userName;
	@Column
	private String email;
	@Column(nullable = false)
	private String password;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Profile")
	private Profile userProfile;
	
	public Profile getProfile() {
		return this.userProfile;
	}
	public void setProfile(Profile profile) {
		this.userProfile = profile;
	}
	
	public String getUserName(){return userName;}
	public void setUserName(String userName){this.userName = userName;}
	
	public String getPassword(){ return password; }
	public void setPassword(String password){this.password = password;}

	public String getEmail(){return email;}
	public void setEmail(String email){this.email = email;}

	public int getCodUser(){ return codUser;}
	public void setId(int id){this.codUser = id;}

}


































