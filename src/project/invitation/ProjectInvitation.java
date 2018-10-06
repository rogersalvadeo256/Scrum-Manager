package project.invitation;

import java.util.ArrayList;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER_INVITATION;
import db.pojos.USER_PROFILE;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;

public class ProjectInvitation {
      public void invite(USER_PROFILE invited, PROJECT p) {
            PROJECT_MEMBER_INVITATION invite = new PROJECT_MEMBER_INVITATION ();

            invite.setMbrInvitedBy(SESSION.getProfileLogged().getCod());
            invite.setMbrProfCod(invited.getCod());
            invite.setMbrProjectCod(p.getProjectCod());
            invite.setMbrInviteSendedDate();
            invite.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

            DB_OPERATION.PERSIST(invite);
      }
      public void invite(ArrayList<USER_PROFILE> list, PROJECT p) {
            for (USER_PROFILE var : list) {
                  PROJECT_MEMBER_INVITATION  invite = new PROJECT_MEMBER_INVITATION ();

                  invite.setMbrInvitedBy(SESSION.getProfileLogged().getCod());
                  invite.setMbrProfCod(var.getCod());
                  invite.setMbrProjectCod(p.getProjectCod());
                  invite.setMbrInviteSendedDate();
                  invite.setMbrInviteStatus(ENUMS.REQUEST_STATUS.ON_HOLD.getValue());

                  DB_OPERATION.PERSIST(invite);
            }
            TEMP_STORE_INVITATIONS.LIST_INVITATION().clear();
      }
}
