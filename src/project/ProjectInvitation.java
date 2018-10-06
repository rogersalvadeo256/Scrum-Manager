package project;

import java.util.ArrayList;

import db.pojos.PROJECT_INVITATION;
import db.pojos.USER_PROFILE;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;

public class ProjectInvitation  { 
      public ProjectInvitation ()  {
            
      }

      public void invite (USER_PROFILE invited) { 
            PROJECT_INVITATION invite=new PROJECT_INVITATION();

            invite.setInvitedBy(SESSION.getProfileLogged().getCod());
            invite.setProfCodInvited(invited.getCod());
            invite.setRequestDate();
            invite.setStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

            DB_OPERATION.PERSIST(invite);
      }
      public void invite (ArrayList<USER_PROFILE>list) { 
            for (USER_PROFILE var : list) {
                  PROJECT_INVITATION invite = new PROJECT_INVITATION();

                  invite.setInvitedBy(SESSION.getProfileLogged().getCod());
                  invite.setProfCodInvited(var.getCod());
                  invite.setRequestDate();
                  invite.setStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

                  DB_OPERATION.PERSIST(invite);
            }
      }




}





