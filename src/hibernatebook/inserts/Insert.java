
package hibernatebook.inserts;

import hibernatebook.annotations.Register;
import hibernatebook.entity.provider.EntityProvider;

public class Insert {

	EntityProvider insert;
	Register data;

	public Insert() {
		this.data = new Register();
		this.insert = new EntityProvider();
	}	

	public void insertRegister(Object recordData) {
		this.insert.entityManager.getTransaction().begin();
		this.insert.entityManager.persist(recordData);
		this.insert.entityManager.getTransaction().commit();
		this.insert.entityManager.close();
	}

}

