package Variations;

import Model.Board;
import Model.Piece;
import Model.Player;
import Model.Space;
import javafx.scene.paint.Color;

/**
 * @author ls8081
 *
 */
public interface GameRules {

	public Board InitBoard();
	
	public boolean isLegalMove(Piece piece, int startRow, int startCol, int endRow, int endCol, Player currPlayer, int roll, int phase);

	public boolean isAvaliableMove(int roll);
	
	public boolean isLegalPlacement(int row, int col);
	
	public Space[] getWinSpaces();
	
	public void setCurrentPlayer(Player player);
	
	}
