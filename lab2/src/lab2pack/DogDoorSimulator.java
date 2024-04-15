package lab2pack;
public class DogDoorSimulator {

	public static void main ( String[] args ) {
		DogDoor door = new DogDoor();
		Remote remote = new Remote(door);
		
		System.out.println("Fido barks to go outside...");
		remote.pressButton();

		System.out.println("\nFido has gone outside...");
	
		try {
		    Thread.sleep(7500);
		} catch ( InterruptedException e ) {}
  
		System.out.println("\nFido's all done...");
		System.out.println("\nFido's back inside...");
	}
}
