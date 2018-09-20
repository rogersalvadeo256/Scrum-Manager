package db.pojos;

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
	@Column(nullable = false) 
	private String securityQuestion;
	@Column(nullable = false)
	private String securityQuestionAnswer;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="Profile")
	private Profile userProfile;
	
	
	public String getSecurityQuestion() {return securityQuestion;}
	public void setSecurityQuestion(String securityQuestion) {this.securityQuestion = securityQuestion;}
	
	public String getSecurityQuestionAnswer() {return securityQuestionAnswer;}
	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {this.securityQuestionAnswer = securityQuestionAnswer;}
	

	public String getUserName(){return userName;}
	public void setUserName(String userName){this.userName = userName;}
	
	public String getPassword(){ return password; }
	public void setPassword(String password){this.password = password;}

	public String getEmail(){return email;}
	public void setEmail(String email){this.email = email;}

	public void setCodUser(int codUser) {this.codUser = codUser;}
	public int getCodUser(){ return codUser;}
	
	public Profile getProfile() {return userProfile;}
	public void setProfile(Profile userProfile) {this.userProfile = userProfile;}
	
	
	
}


































