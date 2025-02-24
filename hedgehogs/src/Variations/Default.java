package Variations;

import Model.Board;
import Model.Piece;
import Model.Player;
import Model.Position;
import Model.Space;
import javafx.scene.paint.Color;

/**
 * @author ls8081
 *
 */
public class Default implements GameRules {

	private Board board_;//The game board
	private Space[] scoreSpace_;
	
	public Default() {

	}

	/**
	 * initializes a standard board
	 */
	@Override
	public Board InitBoard () {
		scoreSpace_ = new Space[] {Space.END};
		board_ = new Board(6,9);
		Position[][] spaces = board_.getSpaces();
		for(int i = 0; i < board_.getHeight(); i++) {
			for(int j = 0; j < board_.getWidth(); j++) {
				spaces[i][j] = new Position(i,j);
			}
		}
		spaces[0][3].setBlack();
		spaces[1][6].setBlack();
		spaces[2][4].setBlack();
		spaces[3][5].setBlack();
		spaces[4][2].setBlack();
		spaces[5][7].setBlack();
		spaces[0][0].setStart();
		spaces[1][0].setStart();
		spaces[2][0].setStart(); 
		spaces[3][0].setStart();
		spaces[4][0].setStart();
		spaces[5][0].setStart();
		spaces[0][8].setEnd();
		spaces[1][board_.getWidth() - 1].setEnd();
		spaces[2][board_.getWidth() - 1].setEnd();
		spaces[3][board_.getWidth() - 1].setEnd();
		spaces[4][board_.getWidth() - 1].setEnd();
		spaces[5][board_.getWidth() - 1].setEnd();
		board_.setPieces(spaces);
		return board_;
	}




	/**
	 * returns whether a given move is legal
	 * 
	 */

	@Override
	public boolean isLegalMove (Piece piece, int startRow, int startCol, int endRow,int endCol, Player currPlayer, int roll, int phase) {
		//SideSTepping
		try {
			if(board_.isLegalPosition(endRow,endCol)== true) {
				if(board_.getPos(startRow,startCol).getSpace() == Space.BLACK) {
					for(int col = 0; col < startCol - 1; col++) {
						for(int row = 0; row < board_.getHeight(); row++) {
							if(board_.getPos(row,col).getPiece() != null) {
								return false;
							}
						}
					}
				}
				if(currPlayer.getColor() == piece.getColor()) {
					if(Math.abs(endRow - startRow) == 1) {
						if(endCol == startCol) {
							if(phase == 0) {
								return true;
							}
						}
					}
				}

				//Forward move
				int rollRow = roll - 1;
				if(startRow == rollRow && endRow == rollRow) {
					if((endCol - startCol) == 1){
						return true;
					}
				}
			}
			return false;
		}
		catch(NullPointerException e) {
			return false;
		}
	}
	@Override
	public boolean isAvaliableMove (int roll) {
		for (int col = 0; col < board_.getWidth() -2; col++) {
			if (board_.getPos(roll - 1,col).getPiece() != null && board_.getPos(roll -1,col).getSpace() != Space.BLACK	)
				return true;
		}
		return false;
	}


	@Override
	public boolean isLegalPlacement ( int row, int col ) {
		if(col == 0) {//Make sure it is the start Column

			//Finding the value of the highest stack of pieces in the start row
			int highest = 0;
			for(int i = 0; i < board_.getHeight(); i++) {
				int temp = board_.getPos(i,0).getNumPieces();
				if(temp > highest)
					highest = temp;
			}
			//If zero, piece may be placed anywhere
			if(highest == 0) {
				return true;
			}
			//If stacks are uniform, piece may be placed anywhere
			boolean isUniform = true;
			for(int i = 0; i < board_.getHeight(); i++) {
				if(board_.getPos(i,0).getNumPieces() != highest) {//If there is a stack not equal to the highest stack, not uniform
					isUniform = false;
				}
			}
			if(isUniform == true) {
				return true;
			}
			//If highest is not zero and pieces are not uniform, then if the selected stack is not equal to the highest stack, it may be placed
			if(board_.getPos(row,col).getNumPieces() != highest) {
				return true;
			}
		}
		//All else return false
		return false;
	}

	
	public Space[] getWinSpaces () {

		return scoreSpace_;
	}

	@Override
	public void setCurrentPlayer ( Player player ) {
		// TODO Auto-generated method stub
		
	}


}
