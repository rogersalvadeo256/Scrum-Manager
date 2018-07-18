package hibernatebook.annotations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javassist.bytecode.ByteArray;

@Embeddable
@Table(name="profile")
public class Profile {
	
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO) 	
	@Column(name="id")
	private int id;


	@Column
	String details;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	

}
