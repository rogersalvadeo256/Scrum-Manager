package db.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="PROJECT")
public class PROJECT {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROJ_COD")
	private int projectCod;
	
	@Column(name ="PROJ_NAME")
	private String proName;
	
	@Column(name ="PROJ_DESCRIPTION")
	private String proDescription;
	
	
	@Column(name ="PROJ_CREATOR")
	private String proCreator;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}