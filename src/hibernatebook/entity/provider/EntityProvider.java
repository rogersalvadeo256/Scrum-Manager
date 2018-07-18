package hibernatebook.entity.provider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityProvider {

	private EntityManagerFactory factory;
	public EntityManager entityManager;

	public EntityProvider() {
		this.factory = Persistence.createEntityManagerFactory("HibernatePU");
		this.entityManager = factory.createEntityManager();
	}

}
