package Model;

import javafx.scene.paint.Color;

/**
 * @author al9226
 *
 */

public class Player {

	private Color color_;
	private String type_;
	private int score_;
	Color[] colors_;
	String[] colorStrings_;


	public Player(Color color, String type) {

		color_ = color;
		type_ = type;
		score_ = 0;
		colors_= new Color[] {Color.RED, Color.BLUE, Color.GREEN,
		                      Color.YELLOW, Color.PINK, Color.PURPLE};
		colorStrings_ = new String[] {"Red", "Blue", "Green", "Yellow", "Pink", "Purple"};

	}

	public Color getColor() {

		return color_;

	}

	/**
	 * returns what type of player it is
	 * @return
	 */
	public String getType() {
		return type_;
	}

	public int getScore() {

		return score_;

	}
	
/**
 * adds 1 to the player score
 */
public void addScore(int score) {
	score_ = score_ + score;
}
	
	/**
	 * returns string value of the players color
	 * 
	 */
	public String getStringColor() {
		for(int i = 0; i < colors_.length; i++) {
			if(colors_[i] == color_) {
				return colorStrings_[i];
			}
		}
		return null;
	}
}