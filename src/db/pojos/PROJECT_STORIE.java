package db.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="PROJECT_STORIES")
public class PROJECT_STORIE {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROJ_STORIE")
	private int friendshipRequestId;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
