package messages;

import java.io.Serializable;

public class MessageTicketBook extends Message  implements Serializable{
	private long shuttleId = 0;
	private long userId = 0;
	private long Id=0;
	
	public MessageTicketBook(long shuttleId, long userId,long Id) {
		super(MSG_TYPE.MSG_TICKET_BOOK_REQ);
		this.shuttleId = shuttleId;
		this.userId = userId;
		this.Id=Id;
	}

	public long getShuttleId() {
		return shuttleId;
	}

	public long getUserId() {
		return userId;
	}

	public long getId() {
		// TODO 自动生成的方法存根
		return Id;
	}	
}
