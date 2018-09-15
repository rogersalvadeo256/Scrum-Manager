package friendship;


import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.util.SESSION;


public class FriendshipRequest {
	 
	 private EntityManager em;
	 
	 public FriendshipRequest () {
		  this.em = null;
	 }
	 
	 /**
	  * The parameter are the user that going to receive the friendship request
	  * 
	  * @param Profile
	  */
	 public void sendFriendshipRequest ( Profile p) {
		  if (this.em == null)
			   em = Database.createEntityManager();
		  p.getFriendshipRequests().add(SESSION.getProfileLogged());
		  // SESSION.getProfileLogged().getFriendshipRequests().add(p);
		  SESSION.UPDATE_SESSION();
		  em.getTransaction().begin();
		  em.merge(p);
		  em.getTransaction().commit();
		  em.clear();
		  em.close();
		  em = null;
	 }
	 
	 /**
	  * The parameter is the profile of who send the request, this profile is removed of the list of friendship requests and
	  * moved to the friends list in the two profiles ( sender and receiver )
	  * 
	  * @author jefter66
	  * @param Profile pRequest
	  */
	 public void acceptRequest ( Profile pRequest) {
		  
		  pRequest.getFriendsList().add(SESSION.getProfileLogged());
		  pRequest.getFriendshipRequests().remove(SESSION.getProfileLogged());
		  
		  // SESSION.getProfileLogged().getFriendshipRequests().remove(pRequest);
		  SESSION.getProfileLogged().getFriendsList().add(pRequest);
		  
		  if (this.em == null)
			   this.em = Database.createEntityManager();
		  ;
		  
		  this.em.getTransaction().begin();
		  this.em.merge(pRequest);
		  this.em.getTransaction().commit();
		  this.em.clear();
		  this.em.close();
		  this.em = null;
		  SESSION.UPDATE_SESSION();
	 }
	 
	 public void refuseRequest ( Profile p) {
		  
		  
//		  SESSION.getProfileLogged().getFriendshipRequests().remove(pRequest);
		  SESSION.UPDATE_SESSION();
		  
		  if (this.em == null)  this.em = Database.createEntityManager();

	
//		  p.getFriendshipRequests().remove(SESSION.getProfileLogged());
			
	
			for(int i =0;i<p.getFriendshipRequests().size();i++){
				
			}
			
			




 	 	  this.em.getTransaction().begin();
		  this.em.merge(p);
		  this.em.getTransaction().commit();
		  this.em.clear();
		  this.em.close();
		  this.em = null;

		  
	 }
}































































