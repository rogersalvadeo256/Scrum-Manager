package db.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import statics.ENUMS.DISPONIBILITY_FOR_PROJECT;
import statics.ENUMS.REQUEST_STATUS;
@SuppressWarnings("serial")
@Entity(name="USER_PROFILE")
public class USER_PROFILE implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROF_COD")
	private int codProfile;
	
	@Column(name="PROF_NAME")
	private String name;

	@Column(name="PROF_AVAILABILITY")
	private String status;
	
	@Column(columnDefinition = "LONGBLOB", name="PROF_PHOTO")
	private byte[] photo;

	public void setAvailability(DISPONIBILITY_FOR_PROJECT status) {
		switch (status) {
		case AVAILABLE:
			this.status = "AVAILABLE";
		case NOT_AVAILABLE:
			this.status = "NOT_AVAILABLE";
		case BUSY:
			this.status = "BUSY";
		default:
			break;
		}
	}
	public String getStatus() {
		return this.status;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public void setCod(int codProfile) {
		this.codProfile = codProfile;
	}

	public int getCod() {
		return codProfile;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}





































