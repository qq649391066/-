package messages;

import java.io.Serializable;

public class MessageTicketBookAck extends Message  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7324398235063814357L;
	private long shuttleId = 0;
	private long userId = 0;
	private boolean success = false;
	@SuppressWarnings("unused")
	private long ld;
	
	public MessageTicketBookAck(long shuttleId, long userId, long ld) {
		super(MSG_TYPE.MSG_TICKET_BOOK_ACK);
		this.shuttleId = shuttleId;
		this.userId = userId;
		this.ld=ld;
	}

	public long getShuttleId() {
		return shuttleId;
	}

	public long getUserId() {
		return userId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
    }
}
