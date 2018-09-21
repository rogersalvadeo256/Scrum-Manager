package friendship;

import java.util.List;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import statics.SESSION;

public class FriendshipFunctions {

	private EntityManager em;
	private Profile p;;

	public FriendshipFunctions(Profile p) {
		this.p = p;
	}

	public void deleteFriend() {

		List<Profile> loggedUser = SESSION.getProfileLogged().getFriendsList();

		List<Profile> senderUser = this.p.getFriendsList();

		senderUser.remove(SESSION.getProfileLogged());
		loggedUser.remove(this.p);

		Profile update = SESSION.getProfileLogged();

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
