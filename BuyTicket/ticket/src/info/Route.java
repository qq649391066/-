package info;
import java.io.Serializable;
public class Route implements Serializable {
	public int id = 0;
	public String starting = new String();
	public String ending = new String();
	
	public void show(){
		System.out.println("=== Route Information ===");
		System.out.println("***       ID: \t" + id);
		System.out.println("*** Starting: \t" + starting);
		System.out.println("*** Ending  : \t" + ending);
		System.out.println();
	}
	
}
