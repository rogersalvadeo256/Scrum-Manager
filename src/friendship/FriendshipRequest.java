package friendship;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import db.hibernate.factory.Database;
import db.user.util.SESSION;

public class FriendshipRequest {
	
	private EntityManager em;
	public FriendshipRequest() {
		this.em = null;
	}
	/**
	 * The parameter are the user that going to receive the friendship request
	 * 
	 * @param Profile 
	 */
	public void sendFriendshipRequest(Profile p) {
		if (this.em == null)	em = Database.createEntityManager();
		
		Query q = em.createQuery("from Profile where codProfile =: COD");
		q.setParameter("COD", p.getCod());

		Profile updateP = (Profile) q.getResultList().get(0);
		
		updateP.getFriendshipRequest().add(SESSION.getProfileLogged());
		em.getTransaction().begin();
		em.merge(updateP);
		em.getTransaction().commit();
		em.clear();
		em.close();
		em = null;
	}
}


