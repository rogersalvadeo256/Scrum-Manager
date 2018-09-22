package friendship;

import java.util.List;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import statics.SESSION;

public class FriendshipFunctions {

	private EntityManager em;
	private USER_PROFILE p;;

	public FriendshipFunctions(USER_PROFILE p) {
		this.p = p;
	}

	public void deleteFriend() {

//		List<USER_PROFILE> loggedUser = SESSION.getProfileLogged().getFriendsList();

//		List<USER_PROFILE> senderUser = this.p.getFriendsList();

//		senderUser.remove(SESSION.getProfileLogged());
//		loggedUser.remove(this.p);

		USER_PROFILE update = SESSION.getProfileLogged();

		if (this.em == null)
			this.em = Database.createEntityManager();

		this.em.getTransaction().begin();
		this.em.merge(this.p);
		this.em.merge(update);
		this.em.getTransaction().commit();
		this.em.clear();
		this.em.close();
		this.em = null;

		SESSION.UPDATE_SESSION();
		
	}
}
