package db.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="FRIENDSHIP")
public class FRIENDSHIP {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FR_REQUEST_ID")
	private int friendshipRequestId;
	
	@Column(name="FR_PROF_1")
	private USER_PROFILE p1;
	
	@Column(name="FR_PROF_2")
	private USER_PROFILE p2;
	
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false, name="FR_FRIENDSHIP_BEGIN")
	private java.util.Date dateBegin;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
