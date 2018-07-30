package hibernatebook.annotations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 	
	@Column(name="codProfile")
	private long id;
	@Column
	private  String details;
	@Column 
	private String name;
	
//	@Column
//	private Friend friends;

	public long getId() {return id;}
	public void setId(int id) {this.id = id;}

	public String getName(){ return name;}
	public void setName(String name){this.name = name;}

	

}
