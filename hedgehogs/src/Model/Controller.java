package Model;

import java.util.ArrayList;

import Variations.GameRules;

/**
 * @author ma8705
 *
 */
public interface Controller {

	/**
	 * starts the turn taking for setup phase
	 */
	void startGame ( ArrayList<Player> players );

	/**
	 * Starts the rotation of normal game turns until a victory has been reached
	 */

	void startGameTurns ();

	/**
	 * Handles Taking individual turns for players
	 */
	void takeTurn ();

	/**
	 * Ends the current turn
	 */
	void EndTurn ();

	/**
	 * Handles taking a startup-phase turn for a human player
	 */
	void beginningTurn ( int round, int player );

	/**
	 * Ends a startup-phase turn 
	 * If the final startup turn, starts the Main Games Turns 
	 */
	void endStartupTurn ( int round, int player );

	/**
	 * Handles CPU beginning turn logic
	 *
	 **/
	void beginningCPUTurn ( int round, int player );

	/**
	 * Depending on the next player type, starts the next appropriate 
	 * start up turn
	 */
	void startNextBeginnerTurn ( int round, int player );

	/**
	 * Handles a CPU Turn 
	 */
	void CPUTurn ();

	/**
	 * Returns whether a given attempted
	 * move is a legal A move
	 * 
	 */
	boolean isLegalMove ( Piece piece, int startRow, int startCol, int placeRow,
	                      int placeCol );

	/**
	 * Returns whether a given attempted
	 * move is a legal A move
	 * 
	 */
	boolean isAvaliableMove ();

	/**
	 * returns whether an attempted Piece placement is 
	 * a valid placement for the start phase
	 */
	boolean isLegalPlacement ( int row, int col );

	/**
	 * checks if any player has gotten 3 of 
	 * their pieces into the end Column.
	 * returns null if nobody has won
	 */
	Player isFinished ();

	/**
	 * Moves a piece from one place to another if it is a legal A move
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 */

	void movePiece ( int fromRow, int fromCol, int toRow, int toCol );

	/**
	 * Adds a new piece to the board of the color 
	 * of the current player. For the startup phase
	 */
	void AddNewPiece ( int row, int col );

	/**
	 * returns the type of Space a 
	 * position on the board is
	 * @param row
	 * @param col
	 */
	Space getSpace ( int row, int col );

	/**
	 * returns if a position on the board is legal space
	 * @param row
	 * @param col
	 */
	boolean isLegalPosition ( int row, int col );

	/**
	 * returns the current turn phase
	 */
	int getPhase ();

	/**
	 * sets the value of the current roll
	 */
	void setRoll ( int roll );

	/**
	 * returns board
	 * @return
	 */
	Board getBoard ();

	/**
	 * returns the current player
	 */
	Player getCurrPlayer ();

}
