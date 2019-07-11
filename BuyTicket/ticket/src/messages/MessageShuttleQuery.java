package messages;

import java.io.Serializable;

public class MessageShuttleQuery extends Message  implements Serializable{
	private String starting = null;
	private String ending = null;
	private int year = 2014;
	private int month = 1;
	private int date = 1;
	
	public MessageShuttleQuery(String starting, String ending, int year, int month, int date) {
		super(MSG_TYPE.MSG_SHUTTLE_QUERY);
		this.starting = starting;
		this.ending = ending;
		this.year = year;
		this.month = month;
		this.date = date;
	}

	public String getStarting() {
		return starting;
	}

	public String getEnding() {
		return ending;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDate() {
		return date;
	}
}

