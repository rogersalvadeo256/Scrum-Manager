package db.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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






















