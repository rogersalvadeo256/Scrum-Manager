package db.pojos;

import java.io.Serializable;
import java.util.Calendar;

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
@SuppressWarnings("serial")
@Entity(name = "USER_REGISTRATION")
public class USER_REGISTRATION implements Serializable {
	public USER_REGISTRATION() {
		this.userProfile = new USER_PROFILE();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "USER_COD")
	private int uCod;
	@Column(nullable = false, name = "USER_NAME")
	private String uName;
	@Column(nullable = false, name = "USER_EMAIL")
	private String uEmail;
	@Column(nullable = false, name = "USER_PASSWORD")
	private String uPassword;
	@Column(nullable = false, name = "USER_SECURITY_QUESTION")
	private String uSecurityQuestion;
	@Column(nullable = false, name = "USER_SECURITY_ANSWER")
	private String uSecurityAnswer;
	
	@Temporal(TemporalType.DATE)
	@Column (nullable = false, name="USER_DATE_REGISTRATION")
	private java.util.Date uDateRegistrated;
	
	
	public java.util.Date getuDateRegistrated() {
		return uDateRegistrated;
	}

	public void setuDateRegistrated() {
		this.uDateRegistrated = Calendar.getInstance().getTime();
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_PROFILE")
	private USER_PROFILE userProfile;
	
	@Column(nullable = false, name = "USER_ACCOUNT_STATUS")
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String value) {
		this.status = value;
	}

	public String getSecurityQuestion() {
		return uSecurityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.uSecurityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return uSecurityAnswer;
	}

	public void setSecurityAnswer(String securityQuestionAnswer) {
		this.uSecurityAnswer = securityQuestionAnswer;
	}

	public String getUserName() {
		return uName;
	}

	public void setUserName(String userName) {
		this.uName = userName;
	}

	public String getPassword() {
		return uPassword;
	}

	public void setPassword(String password) {
		this.uPassword = password;
	}

	public String getEmail() {
		return uEmail;
	}

	public void setEmail(String email) {
		this.uEmail = email;
	}

	public void setCodUser(int codUser) {
		this.uCod = codUser;
	}

	public int getCodUser() {
		return uCod;
	}

	public USER_PROFILE getProfile() {
		return userProfile;
	}

	public void setProfile(USER_PROFILE userProfile) {
		this.userProfile = userProfile;
	}

}
