package hibernatebook.annotations;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="register", schema="Scrum")
public class Register {
	
	public Register() { 
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int id;

	@Column
	private String userName;
	@Column 
	private String name;
	@Column
	private String email;
	@Column
	private String password;
//	@Embedded
//	private Profile profile;
//	
//	
	
	
	public String getPassword(){ return password; }
	public void setPassword(String password){this.password = password;}

	public String getEmail(){return email;}
	public void setEmail(String email){this.email = email;}

	public String getName(){ return name;}
	public void setName(String name){this.name = name;}

	public long getId(){ return id;}
	public void setId(int id){this.id = id;}

	public String getUserName(){return userName;}
	public void setUserName(String userName){this.userName = userName;}
	

	
	
	
	
	
}
