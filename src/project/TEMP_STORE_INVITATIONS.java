package project;

import java.util.ArrayList;

import db.pojos.USER_PROFILE;

public class TEMP_STORE_INVITATIONS {

   private static ArrayList<USER_PROFILE> LIST_INVITES;

   public static ArrayList<USER_PROFILE> LIST_INVITATION() {
      TEMP_STORE_INVITATIONS.LIST_INVITES = LIST_INVITES == null ? new ArrayList<USER_PROFILE>() : LIST_INVITES;
      return TEMP_STORE_INVITATIONS.LIST_INVITES;
   }
   public static void REMOVE_FROM_LIST(USER_PROFILE p) {
      LIST_INVITES.remove(p);
   }
}