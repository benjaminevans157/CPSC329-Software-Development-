package Model;

import javafx.scene.paint.Color;

/**
 * @author al9226
 *
 */

public enum Space {
	
	YELLOW,
	BLACK,
	START,
	END;
	
	/**
	 * getter for this enum's color
	 * @returns a color
	 */
	public javafx.scene.paint.Color Color() {
		if (this == YELLOW)
			return Color.rgb(240,170,87);
		else if (this == BLACK)
			return Color.BLACK;
		return Color.RED;
	}
	

}