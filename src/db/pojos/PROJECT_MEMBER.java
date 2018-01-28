package db.pojos;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="PROJECT_MEMBER")
public class PROJECT_MEMBER {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROJ_MEMBER")
	private int mbrId;

	@Column(name = "MBR_PROF_COD")
	private int mbrProfileCod;

	@Column(name = "MBR_FUNCTION")
	private String mbrFunction;


	@Column(name = "MDR_PROJECT") 
	private int mbrProject;
	
	public int getMbrProject() {
		return mbrProject;
	}

	public void setMbrProject(int mbrProject) {
		this.mbrProject = mbrProject;
	}

	public int getMbrId() {
		return mbrId;
	}

	public void setMbrId(int mbrId) {
		this.mbrId = mbrId;
	}

	public int getMbrProfileCod() {
		return mbrProfileCod;
	}

	public void setMbrProfileCod(int mbrProfileCod) {
		this.mbrProfileCod = mbrProfileCod;
	}

	public String getMbrFunction() {
		return mbrFunction;
	}

	public void setMbrFunction(String mbrFunction) {
		this.mbrFunction = mbrFunction;
	}

	public java.util.Date getMbrDtAdd() {
		return mbrDtAdd;
	}

	public void setMbrDtAdd() {
		this.mbrDtAdd = Calendar.getInstance().getTime();
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable = false, name = "MBR_DATE_ADDED")
	private java.util.Date mbrDtAdd;

}
