package messages;

import info.Shuttle;

import java.io.Serializable;
import java.util.Vector;

public class MessageShuttleQueryAck extends Message  implements Serializable{
	Vector<Shuttle> shuttles = null;
	
	public Vector<Shuttle> getShuttles() {
		return shuttles;
	}

	public void setShuttles(Vector<Shuttle> shuttles) {
		this.shuttles = shuttles;
	}

	public MessageShuttleQueryAck() {
		super(MSG_TYPE.MSG_SHUTTLE_QUERY_ACK);
	}

	public void show() {
		if(shuttles != null){
			for(Shuttle shuttle : shuttles){
				shuttle.show();
			}
		}		
	}	
}
