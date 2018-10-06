package db.pojos;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "PROJECT_INVITATION")
public class PROJECT_INVITATION {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PR_INV_COD")
	private int projInviteCod;

	@Column(name="PR_INV_PROF_COD")
	private int profCod;

	@Column(name="PR_INV_STATUS")
	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name = "PR_INV_DATE_REQUEST")
	private java.util.Date requestDate;

	/**
	 * @return the profCod
	 */
	public int getProfCod() {
		return profCod;
	}

	/**
	 * @return the projInviteCod
	 */
	public int getProjInviteCod() {
		return projInviteCod;
	}

	/**
	 * @param profCod the profCod to set
	 */
	public void setProfCod(int profCod) {
		this.profCod = profCod;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the requestDate
	 */
	public java.util.Date getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate() {
		this.requestDate = Calendar.getInstance().getTime();
	}












	

























}

