package info;
import java.util.Date;
import java.io.Serializable;

public class Shuttle implements Serializable {
	private long id = -1;
	private Route route = new Route(); 
	private Date date = new Date();
	private int HourOfDay ;
	private int Minute ;
	private int seatingCapacity = 50;
	private int seating = 50;
	private int fee = 22;
	
	public Shuttle(int seatingCapacity){
		this.seatingCapacity = seatingCapacity;
	}
	public Shuttle(int seatingCapacity, int seating){
		this.seatingCapacity = seatingCapacity;
		this.seating = seating;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
	public void setRoute(String starting, String ending) {
		if(this.route==null){
			this.route = new Route();
		}
		
		this.route.starting = starting;
		this.route.ending = ending;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSeating() {
		return seating;
	}

	public String bookSeating(int number) {
		if(seating + number > seatingCapacity){
			return "NotEnoughSeating";
		}
		
		seating += number;
		
		return "Success";
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}
	
	public int getSeatingCapacity() {
		return seatingCapacity;
	}
	
	public void show(){
		System.out.println("=== Shuttle Information ===");
		System.out.println("*** ID        : \t" + id);
		route.show();
		System.out.println("*** Date      : \t" + date.toString());
		System.out.println("*** Capacity  : \t" + seatingCapacity);
		System.out.println("*** Seating   : \t" + seating);
		System.out.println("*** Fee       : \t" + fee);	
		System.out.println("*** HourOfDay   : \t"+HourOfDay);
		System.out.println("*** Minute   : \t"+Minute);
	}
}
