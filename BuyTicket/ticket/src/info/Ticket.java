package info;
import info.User;
import java.io.Serializable;
public class Ticket implements Serializable {
	private long id = 0;
	private Shuttle shuttle;
	private User user;
	private String status = new String("鏈箻鍧�");

	public Ticket() {
	}
	
	public void setId(Shuttle shuttle) {
		this.id = 51 - shuttle.getSeating();
	}

	public long getId() {
		return id;
	}

	public Shuttle getShuttle() {
		return shuttle;
	}

	public void setShuttle(Shuttle shuttle) {
		this.shuttle = shuttle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void show(){
	System.out.println("=== Ticket Information ===");
		user.show();
		shuttle.show();
	System.out.println("*** Status : " + status);
	System.out.println();
	}

}
