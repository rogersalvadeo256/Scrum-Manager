package hibernatebook.annotations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
public class Profile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 	
	@Column(name="codProfile")
	private long id;
	@Column
	private  String bio;
	@Column 
	private String name;
	
	
//	@Column
//	private Friend friends;

	public void setId(long id) {this.id = id;}
	public long getId() {return id;}
	
	public String getName(){ return name;}
	public void setName(String name){this.name = name;}
	
	public String getBio() {return bio;}
	public void setDetails(String details) {this.bio = details;}
}
