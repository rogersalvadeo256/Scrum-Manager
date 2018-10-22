package db.pojos;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name="SPRINT")
public class SPRINT {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="SPRINT_COD")
	private int mbrCod;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="TASK_COD")
	
	private List<TASK> tasksList;
	
	
	
	
//	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//	@JoinColumn(name="codFriend")
//	private List<Profile> friend;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
