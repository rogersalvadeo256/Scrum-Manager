package db.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codProject;
	
	@Column(nullable = false)
	private String projectName;
	
	@Column
	private String projectDescription; 
	
	@Column(name = "members")
	@ElementCollection(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<Profile> listMembers;

	
//	@Column(name = "creator")
//	@OneToOne
//	private Profile projectCreator;
	
	
	public Project() {
		this.listMembers = new ArrayList<Profile>();
	}

	public int getCodProject() {
		return codProject;
	}

	public void setCodProject(int codProject) {
		this.codProject = codProject;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String nameProject) {
		this.projectName = nameProject;
	}

	public List<Profile> getListMembers() {
		return listMembers;
	}

	public void setListMembers(List<Profile> listMembers) {
		this.listMembers = listMembers;
	}
	public Profile getProjectCreator() {
		return projectCreator;
	}

	public void setProjectCreator(Profile projectCreator) {
		this.projectCreator = projectCreator;
	}
	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
}
