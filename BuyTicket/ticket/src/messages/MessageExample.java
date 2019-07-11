package messages;

import java.io.Serializable;

public class MessageExample extends Message implements Serializable {
   private String data  = null;
   
   public MessageExample() {
      super(MSG_TYPE.MSG_EXAMPLE);
   }

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
