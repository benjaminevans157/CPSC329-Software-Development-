package Model;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @author ma8705
 * Represents an individual position on
 * the board. 
 *
 */
public class Position {

	Space space_;//The type of space this position is
	Stack<Piece> pieces_;//The pieces on this space
	int row_;//The row of the pos
	int col_;//The column of the pos
	
	
	/**
	 * creates a new position with a stack of
	 * pieces. Defaults to yellow space
	 */
	public Position(int row, int col) {
		pieces_ = new Stack<Piece>();
		space_ = Space.YELLOW;
		row_ = row;
		col_ = col;
	}

	/**
	 * adds a piece to this position
	 */
	public void addPiece(Piece piece) {
		pieces_.add(piece);
	}
	
	/**
	 * removes the top piece 
	 */
	public void removePiece() {
		try {
		pieces_.pop();	
		}
		catch(EmptyStackException e) {
			return;
		}
	}
	
	/**
	 * sets this position to a Black space
	 */
	public void setBlack() {
		space_ = Space.BLACK;
	}

	/**
	 * sets this position to a Yellow space
	 */
	public void setYellow() {
		space_ = Space.YELLOW;
	}

	/**
	 * sets this position to a Start space
	 */
	public void setStart() {
		space_ = Space.START;
	}

	/**
	 * sets this position to a ENd space
	 */
	public void setEnd() {
		space_ = Space.END;
	}


	/**
	 * returns the row of this position
	 */
	public int getRow() {
		return row_;
	}
	
	/**
	 * returns the column of this position
	 */
	public int getCol() {
		return col_;
	}
	
	/**
	 * returns the type of space this position is
	 */
	public Space getSpace() {
		return space_;
	}

	/**
	 * returns the top piece in the stack
	 * returns null if there is no piece
	 */
	public Piece getPiece() {
		try {
		return pieces_.peek();
		}
		catch(EmptyStackException e) {
			return null;
		}
	}
	
	/**
	 * returns the pieces
	 * 
	 */
	public Stack<Piece> getPieces(){
		return pieces_;
	}
	
	/**
	 * returns how many pieces are in this position
	 */
	public int getNumPieces() {
		return pieces_.size();
	}
	
	
}
