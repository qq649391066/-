package messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
   public enum MSG_TYPE {
      MSG_UNKOWN, MSG_EXAMPLE, MSG_LOGIN_ACK, MSG_SHUTTLE_QUERY, MSG_SHUTTLE_QUERY_ACK, MSG_TICKET_BOOK_REQ, MSG_TICKET_BOOK_ACK, MSG_LOGIN_REQ,
      
   }
   
   public MSG_TYPE msgType = MSG_TYPE.MSG_UNKOWN;
   
   public Message(MSG_TYPE mt) {
      msgType = mt;
   }
}
