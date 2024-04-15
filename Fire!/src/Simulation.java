import java.util.ArrayList;
import java.util.List;

public class Simulation {
	public int numRows; 
	public int numCols; 

	public World world; 

	
	public Simulation(int numRows, int numCols) {
		this.numRows = numRows; 
		this.numCols = numCols ; 
		
		
		world = new World(numRows,numCols); 
		

		// TODO
	}
	
	
	public World getWorld() {
		return world; 
	}
	public void setWorld(World world_) {
		this.world = world_;
	}

}
