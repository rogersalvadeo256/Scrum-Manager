package POJOs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 	
	private int codProject;
	@Column(nullable = false)
	private String nameProject;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
