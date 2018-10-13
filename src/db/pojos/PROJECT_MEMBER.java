package db.pojos;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity(name = "PROJECT_MEMBER")
public class PROJECT_MEMBER {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MBR_COD")
	private int mbrCod;

	@Column(name = "MBR_PROF_COD_INVITED_BY", nullable = false)
	private int mbrInvitedBy;

	@Column(name = "MBR_PROF_COD" , nullable = false)
	private int mbrProfCod;

	@Column(name = "MBR_PROJECT" , nullable = false)
	private int mbrProjectCod;

	@Temporal(TemporalType.DATE)
	@Column(name = "MBR_DT_INVITE")
	private Date mbrInviteSendedDate;

	@Column(name = "MBR_DT_INVITE_ANSWERED")
	private Date mbrInviteAnsweredDate;

	@Column(name = "MBR_INVITE_STATUS" , nullable = false)
	private String mbrInviteStatus;
	/**
	 * if someone drop out the project
	 */
	@Column(name = "MBR_STATUS")
	private String mbrMemberStatus;

	@Column(name = "MBR_FUNCTION")
	private String mbrMemberFunction;

	
	@Column(name = "MBR_PERMISSIONS")
	private String mbrMemberPermissions;

	@Column (name = "MBR_SCRUM_MASTER")
	private boolean mbrScrumMaster;
	
	public boolean isMbrScrumMaster() {
		return mbrScrumMaster;
	}

	public void setMbrScrumMaster(boolean mbrScrumMaster) {
		this.mbrScrumMaster = mbrScrumMaster;
	}

	public String getMbrMemberPermissions() {
		return mbrMemberPermissions;
	}

	public void setMbrMemberPermissions(String mbrMemberPermissions) {
		this.mbrMemberPermissions = mbrMemberPermissions;
	}

	/**
	 * @param mbrInviteAnsweredDate the mbrInviteAnsweredDate to set
	 */
	public void setMbrInviteAnsweredDate() {
		this.mbrInviteAnsweredDate =  Calendar.getInstance().getTime();
	}

	/**
	 * @param mbrInvitedBy the mbrInvitedBy to set
	 */
	public void setMbrInvitedBy(int mbrInvitedBy) {
		this.mbrInvitedBy = mbrInvitedBy;
	}

	/**
	 * @param mbrInviteSendedDate the mbrInviteSendedDate to set
	 */
	public void setMbrInviteSendedDate() {
		this.mbrInviteSendedDate = Calendar.getInstance().getTime();
	}


	/**
	 * @param mbrInviteStatus the mbrInviteStatus to set
	 */
	public void setMbrInviteStatus(String mbrInviteStatus) {
		this.mbrInviteStatus = mbrInviteStatus;
	}

	/**
	 * @param mbrMemberFunction the mbrMemberFunction to set
	 */
	public void setMbrMemberFunction(String mbrMemberFunction) {
		this.mbrMemberFunction = mbrMemberFunction;
	}


	/**
	 * @param mbrMemberStatus the mbrMemberStatus to set
	 */
	public void setMbrMemberStatus(String mbrMemberStatus) {
		this.mbrMemberStatus = mbrMemberStatus;
	}

	/**
	 * @param mbrProfCod the mbrProfCod to set
	 */
	public void setMbrProfCod(int mbrProfCod) {
		this.mbrProfCod = mbrProfCod;
	}

	/**
	 * @param mbrProjectCod the mbrProjectCod to set
	 */
	public void setMbrProjectCod(int mbrProjectCod) {
		this.mbrProjectCod = mbrProjectCod;
	}
	/**
	 * @return the mbrInviteAnsweredDate
	 */
	public Date getMbrInviteAnsweredDate() {
		return mbrInviteAnsweredDate;
	}
	/**
	 * @return the mbrInvitedBy
	 */
	public int getMbrInvitedBy() {
		return mbrInvitedBy;
	}
	/**
	 * @return the mbrInviteSendedDate
	 */
	public Date getMbrInviteSendedDate() {
		return mbrInviteSendedDate;
	}

	/**
	 * @return the mbrInviteStatus
	 */
	public String getMbrInviteStatus() {
		return mbrInviteStatus;
	}

	/**
	 * @return the mbrMemberFunction
	 */
	public String getMbrMemberFunction() {
		return mbrMemberFunction;
	}

	/**
	 * @return the mbrMemberStatus
	 */
	public String getMbrMemberStatus() {
		return mbrMemberStatus;
	}

	/**
	 * @return the mbrProfCod
	 */
	public int getMbrProfCod() {
		return mbrProfCod;
	}
	/**
	 * @return the mbrProjectCod
	 */
	public int getMbrProjectCod() {
		return mbrProjectCod;
	}


}