package Model;

import javafx.scene.paint.Color;

/**
 * @author al9226
 *
 */

public class Piece {
	
	private Color color_;
	private boolean isUp_;
	Color[] colors_;
	String[] colorStrings_;
	
	public Piece(Color color) {
		
		color_ = color;
		isUp_ = true;
		colors_= new Color[] {Color.RED, Color.BLUE, Color.GREEN,
		                      Color.YELLOW, Color.PINK, Color.PURPLE};
		colorStrings_ = new String[] {"Red", "Blue", "Green", "Yellow", "Pink", "Purple"};

		
	}
	
	public Color getColor() {
		
		return color_;
		
	}
	
	public boolean isUp() {
		
		return isUp_;
		
	}
	
	/**
	 * returns string value of the pieces color
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