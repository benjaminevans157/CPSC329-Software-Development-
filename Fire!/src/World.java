
public class World {
	private int numRows; 
	private int numCols; 
	private int oldCols;
	private int oldRows;
	World(int numRows,int numCols){
		this.numRows = numRows ; 
		this.numCols = numCols ; 
	}
	
	public int getNumRows() {
		return numRows; 
	}
	
	public void setNumRows(int rows) {
		this.numRows = rows;
	}
	
	
	
	public int getNumCol() {
		return numCols ; 
	}
	
	public void setNumCols(int cols) {
		this.numCols = cols;
	}
	
	public void previousWorld(int cols, int rows) {
		this.oldCols = cols;
		this.oldRows = rows;
	}
}
