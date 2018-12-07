package db.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import statics.ENUMS;

@Entity(name="PROJECT_SPRINT")
public class PROJECT_SPRINT {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_SPRINT")
	private int spId;

	@Column(name = "SPRINT_TITLE")
	private String sprintTitle;

	@Column(name = "SPRINT_TEXT")
	@Type(type = "text")
	private String 	sprint;
	
	@Column(name = "PROJ_SPRINT_COD")
	private int projSprintCod;
	
	@Column(name = "SPRINT_PONTUATION")
	private int sprintPontuation;
	
	public int getProjectSprintCod() {
		return projSprintCod;
	}

	public void setProjectSprintCod(int sprintCod) {
		this.projSprintCod = sprintCod;
	}

	@Column(name = "SPRINT_STATUS")
	private ENUMS.SPRINT_PROJECT sprintStatus;
	


	public int getSpId() {
		return spId;
	}

	public void setSpId(int mbrId) {
		this.spId = mbrId;
	}

	public String getSprintTitle() {
		return sprintTitle;
	}

	public void setSprintTitle(String sprintTitle) {
		this.sprintTitle = sprintTitle;
	}

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	public int getSprintPontuation() {
		return sprintPontuation;
	}

	public void setSprintPontuation(int sprintPontuation) {
		this.sprintPontuation = sprintPontuation;
	}

	public ENUMS.SPRINT_PROJECT getSprintStatus() {
		return sprintStatus;
	}

	public void setSprintStatus(ENUMS.SPRINT_PROJECT sprintStatus) {
		this.sprintStatus = sprintStatus;
	}

}