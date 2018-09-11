package friendship;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.util.SESSION;

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
	
	public void answerFriendshipRequest(Profile pRequest) { 
		
		if(this.em == null ) this.em = Database.createEntityManager();
		
		Profile p = SESSION.getProfileLogged();
		
		this.em.getTransaction().begin();
		p.getFriendList().add(pRequest);
		this.em.merge(p);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

































