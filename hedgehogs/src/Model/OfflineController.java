package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import Variations.GameRules;
import View.HedgehogGUI;
import javafx.scene.paint.Color;

/**
 * @author al9226
 *
 */

public class OfflineController implements Controller {

	private ArrayList<Player> players_;//The players of the game
	private Player currPlayer_;//The player who's turn it is
	private int currTurn_;//The number of the current player
	private int numPlayers_;//The number of players
	private int currRoll_;//The roll for the current turn 
	private int turnPhase_;//The phase of the turn the player is in

	private HedgehogGUI gui_;//The GUI 
	private GameRules rules_;//The gameRule
	private Board board_;// The game board of pieces and spaces

	/**
	 * Creates a new controller and initializes the board according 
	 * to the Rules given
	 */
	public OfflineController(HedgehogGUI gui, GameRules rules){
		gui_ = gui;
		rules_ = rules;
	}

	/**
	 * starts the turn taking for setup phase
	 */
	@Override
	public void startGame(ArrayList<Player> players) {
		board_ = rules_.InitBoard();
		players_ = players;
		numPlayers_ = players.size();
		currTurn_ = 0;//Initialization
		currPlayer_ = players.get(currTurn_);
		beginningTurn(0,0);


	}

	/**
	 * Starts the rotation of normal game turns until a victory has been reached
	 */

	@Override
	public void startGameTurns() {

		currPlayer_ = players_.get(0);
		currTurn_ = 0;
		turnPhase_ = 0;
		takeTurn();

	}


	/**
	 * Handles Taking individual turns for players
	 */
	@Override
	public void takeTurn() {
		if(currPlayer_.getType().equals("Player")) {
			turnPhase_ = 0;
			gui_.startPlayerRoll();
		}
		else {
			CPUTurn();
		}
	}

	/**
	 * Ends the current turn
	 */
	@Override
	public void EndTurn() {
		turnPhase_ = 0;
		gui_.endPlayerTurn();
		if(isFinished() != null) {
			gui_.exitGame(isFinished());

		}
		if(currTurn_ == numPlayers_ -1) {
			currTurn_ = 0;
		}
		else {
			currTurn_++;
		}
		currPlayer_ = players_.get(currTurn_);

		turnPhase_ = 0;
		takeTurn();
	}

	/**
	 * Handles taking a startup-phase turn for a human player
	 */
	@Override
	public void beginningTurn(int round, int player) {
		gui_.startBeginningTurn(round, player);
	}

	/**
	 * Ends a startup-phase turn 
	 * If the final startup turn, starts the Main Games Turns 
	 */
	@Override
	public void endStartupTurn(int round, int player) {
		gui_.EndBeginningTurn();
		if(round == 3 && player == numPlayers_ - 1) {//Final round final player
			startGameTurns();
		}
		else if(player == numPlayers_ -1) {//Not final round, final player
			player = 0;
			int newRound = round + 1;
			startNextBeginnerTurn(newRound, player);
		}
		//All else, not final round or final player
		else {

			int newPlayer = player + 1;
			startNextBeginnerTurn(round, newPlayer);
		}


	}

	/**
	 * Handles CPU beginning turn logic
	 *
	 **/
	@Override
	public void beginningCPUTurn(int round, int player) {
		//Choose a random row until a legal row is chosen and place a peice
		boolean isDone = false;
		while(isDone == false) {
			int row = (int)(Math.random() * board_.getHeight());	
			if(isLegalPlacement(row, 0) == true) {
				board_.getPos(row,0).addPiece(new Piece(currPlayer_.getColor()));

				isDone = true;
			}
		}
		endStartupTurn(round, player);
	}

	/**
	 * Depending on the next player type, starts the next appropriate 
	 * start up turn
	 */
	@Override
	public void startNextBeginnerTurn(int round, int player) {
		currPlayer_ = players_.get(player);
		if(currPlayer_.getType().equals("Player")) {
			beginningTurn(round, player);
		}
		else {
			gui_.StartCPUTurn(round, player);
		}
	}

	/**
	 * Handles a CPU Turn 
	 */
	@Override
	public void CPUTurn() {
		System.out.println("CPU TURN CALLED");
		int roll = (int)(Math.random()*5);//Roll the die
		int[] values = new int[] {1,2,3,4,5,6};
		currRoll_ = values[roll];
		if(isAvaliableMove()==true) {
			for(int col = 0; col < board_.getWidth() - 2; col++) {
				Piece tempPiece = board_.getPiece(roll,col);
				if(tempPiece != null) {
					movePiece(roll, col, roll, col + 1);
					break;
				}
			}
		}
		else {
			EndTurn();
		}
	}


	/**
	 * Returns whether a given attempted
	 * move is a legal A move
	 * 
	 */
	@Override
	public boolean isLegalMove(Piece piece, int startRow, int startCol, int placeRow, int placeCol) {
		return rules_.isLegalMove(piece,startRow,startCol,placeRow,placeCol,currPlayer_, currRoll_, turnPhase_);
	}

	/**
	 * Returns whether a given attempted
	 * move is a legal A move
	 * 
	 */
	@Override
	public boolean isAvaliableMove() {
		return rules_.isAvaliableMove(currRoll_);
	}

	/**
	 * returns whether an attempted Piece placement is 
	 * a valid placement for the start phase
	 */
	@Override
	public boolean isLegalPlacement(int row, int col) {
		return rules_.isLegalPlacement(row,col);
	}

	/**
	 * checks if any player has gotten 3 of 
	 * their pieces into the end Column.
	 * returns null if nobody has won
	 */
	@Override
	public Player isFinished() {
		for(Player player: players_) {
			if(player.getScore() == 4) {
				return player;
			}


			int numPieces = 0;
			for(int row = 0; row < board_.getHeight(); row++) {
				Stack<Piece> tempPieces = board_.getPieces(row,board_.getWidth()-1);
				Stack<Piece> newPieces = new Stack<Piece>();
				Piece tempPiece;
				if(tempPieces != null) {
					for(int i = 0; i < tempPieces.size(); i++) {
						tempPiece = tempPieces.pop();
						if(tempPiece.getColor() == player.getColor()) {
							numPieces++;
						}
						newPieces.add(tempPiece);
					}
					for(int j = 0; j < tempPieces.size(); j++) {
						tempPieces.add(newPieces.pop());
					}
				}
			}
			if(numPieces >= 3) {
				System.out.println(currPlayer_.getStringColor());
				return player;

			}

		}
		return null;

	}

	/**
	 * Moves a piece from one place to another if it is a legal A move
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 */

	@Override
	public void movePiece(int fromRow, int fromCol, int toRow, int toCol ) {
		Piece piece = board_.getPiece(fromRow,fromCol);
		if(isLegalMove(piece, fromRow, fromCol, toRow, toCol)) {
			if (fromRow==toRow && fromCol != toCol) {
				for(int i = 0; i < rules_.getWinSpaces().length; i++) {
					if(rules_.getWinSpaces()[i] == board_.getPos(toRow,toCol).getSpace()) {
						currPlayer_.addScore(1);
					}

				}
				board_.removePiece(fromRow,fromCol);
				board_.placePiece(toRow,toCol,piece);
				EndTurn();
			}
			else {
				turnPhase_ = 1;
				board_.removePiece(fromRow,fromCol);
				board_.placePiece(toRow,toCol,piece);
			}
		}
	}


	/**
	 * Adds a new piece to the board of the color 
	 * of the current player. For the startup phase
	 */
	@Override
	public void AddNewPiece(int row, int col) {
		board_.getPos(row,col).addPiece(new Piece(currPlayer_.getColor()));
	}


	/**
	 * returns the type of Space a 
	 * position on the board is
	 * @param row
	 * @param col
	 */
	@Override
	public Space getSpace(int row, int col) {
		return board_.getSpace(row,col);
	}

	/**
	 * returns if a position on the board is legal space
	 * @param row
	 * @param col
	 */
	@Override
	public boolean isLegalPosition(int row, int col) {
		return board_.isLegalPosition(row,col);
	}

	/**
	 * returns the current turn phase
	 */
	@Override
	public int getPhase() {
		return turnPhase_;
	}

	/**
	 * sets the value of the current roll
	 */
	@Override
	public void setRoll(int roll) {
		currRoll_ = roll;
	}

	/**
	 * returns board
	 * @return
	 */
	@Override
	public Board getBoard() {
		return board_;
	}

	/**
	 * returns the current player
	 */
	@Override
	public Player getCurrPlayer() {
		return currPlayer_;
	}

}