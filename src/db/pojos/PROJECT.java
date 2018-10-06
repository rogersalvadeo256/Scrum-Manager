package db.pojos;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity(name = "PROJECT")
public class PROJECT {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROJ_COD")
	private int projectCod;

	@Column(name = "PROJ_NAME")
	private String projName;

	@Column(name = "PROJ_DESCRIPTION")
	private String projDescription;

	@Column(name = "PROJ_CREATOR")
	private int projCreator;

	@Temporal(TemporalType.DATE) 
	@Column(name = "PROJ_DT_START")
	private java.util.Date projDateStart;
	
	@Column(name="PROJ_STATUS")
	private String projStatus;

	/**
	 * @return the projStatus
	 */
	public String getProjStatus() {
		return projStatus;
	}
	/**
	 * @param projDateStart the projDateStart to set
	 */
	public void setProjDateStart(java.util.Date projDateStart) {
		this.projDateStart = projDateStart;
	}
	
	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart() {
		this.projDateStart = Calendar.getInstance().getTime(); ;
	}
	/**
	 * @return the projDateStart
	 */
	public java.util.Date getProjDateStart() {
		return projDateStart;
	}
	

	public int getProjectCod() {
		return projectCod;
	}

	public void setProjectCod(int projectCod) {
		this.projectCod = projectCod;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String proName) {
		this.projName = proName;
	}

	public String getProjDescription() {
		return projDescription;
	}

	public void setProjDescription(String proDescription) {
		this.projDescription = proDescription;
	}

	public int getProjCreator() {
		return projCreator;
	}

	public void setProjCreator(int projCreator) {
		this.projCreator = projCreator;
	}

}






















