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
	@Column(name = "PROJ_SPRINT")
	private int mbrId;

	@Column(name = "SPRINT_TITLE")
	private String sprintTitle;

	@Column(name = "SPRINT_TEXT")
	@Type(type = "text")
	private String 	sprint;

	public int getMbrId() {
		return mbrId;
	}

	public void setMbrId(int mbrId) {
		this.mbrId = mbrId;
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

	@Column(name = "SPRINT_PONTUATION")
	private int sprintPontuation;
	
	@Column(name = "SPRINT_STATUS")
	private ENUMS.SPRINT_PROJECT sprintStatus;
	
}
