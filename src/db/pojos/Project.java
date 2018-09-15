package db.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
	private String nameProject;
	
	@Column(name = "members")
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Profile> listMembers;
	
	public Project() {
		
		this.listMembers = new ArrayList<Profile>();
	}
	
	public int getCodProject() {
		return codProject;
	}
	
	public void setCodProject(int codProject) {
		this.codProject = codProject;
	}
	
	public String getNameProject() {
		return nameProject;
	}
	
	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}
	
	public List<Profile> getListMembers() {
		return listMembers;
	}
	
	public void setListMembers(List<Profile> listMembers) {
		this.listMembers = listMembers;
	}
	
}
