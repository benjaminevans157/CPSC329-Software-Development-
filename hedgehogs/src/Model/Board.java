package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author al9226
 *
 */

public class Board {
	
	private int numCols_;
	private int numRows_;
	private Position[][] spaces_;// The spaces on the board
	
	

	/**
	 * creates a new empty board
	 * @param width
	 * @param height
	 */
	public Board(int height, int width) {
		numCols_ = width;
		numRows_ = height;
		spaces_ = new Position[height][width];
		
		
	}
	
	
	/**
	 * takes a start position and an end position and moves the piece on the top 
	 * of the stack to the desired location
	 * 
	 */
	public void movePiece(Position startPos, Position endPos) {
		
		
		
	}
	
	/**
	 * returns the top piece in a given spot
	 * Returns null if there is no piece
	 */
	public Piece getPiece(int row, int col) {
		try {
			return spaces_[row][col].getPiece();
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * returns a the pieces of a
	 * position in the specified
	 * row and column.
	 * Illegal parameters throws an exception
	 */
	public Stack<Piece> getPieces(int row, int col) {
		try {
			return spaces_[row][col].getPieces();
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.print("Invalid board position");
			return null;
		}
	}
	
	/**
	 * returns a
	 * position in the specified
	 * row and column.
	 * Illegal parameters throws an exception and return null
	 */
	public Position getPos(int row, int col) {
		try {
			return spaces_[row][col];
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * returns if the position is a legal space on the board
	 * @param row
	 * @param col
	 */
	public boolean isLegalPosition(int row, int col) {
		try {
			Position temp = spaces_[row][col];
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	/**
	 * adds a piece to the board
	 * 
	 */
	public void placePiece(int row, int col, Piece piece) {
		System.out.println(row + " " + col);
		spaces_[row][col].addPiece(piece);
	}
	
	/**
	 * removes a piece from the board
	 */
	public void removePiece(int row, int col) {
		try {
			
		spaces_[row][col].removePiece();
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return;
		}
	}
	
	/**
	 * returns the width of the board
	 * @return int
	 */
	public int getWidth() {
		return numCols_;
	}
	
	/**
	 * returns the height of board
	 * @return int
	 */
	public int getHeight() {
		return numRows_;
	}
	
	/**
	 * returns the type of space in 
	 * a specified position
	 */
	public Space getSpace(int row, int col) {
		return spaces_[row][col].getSpace();
	}
	
	/**
	 * returns the board of spaces
	 */
	public Position[][] getSpaces() {
		return spaces_;
	}
	
	/**
	 * sets the pieces of the board
	 */
	public void setPieces(Position[][]pos) {
		spaces_ = pos;
	}
	

	
	
}