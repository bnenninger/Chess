package board;

/**
 * Used to store the white and black colors of chess pieces.
 * Also accounts for game winners, including a draw condition.
 * @author Brendan Nenninger
 *
 */
//smaller than using pre-existing Java colors, more descriptive than a boolean
public enum ChessColor {
	/**
	 * white piece color
	 */
	WHITE,
	/**
	 * black piece color
	 */
	BLACK,
	/**
	 * Draw condition. ONLY to be used to describe the winner of a game, not for the color of pieces.
	 */
	DRAW;
	
	/**
	 * Gives the name of the color, in all lowercase.
	 */
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
	
	/**
	 * Returns the opposite color of the current color, eg. BLACK for WHITE. Throws exception for DRAW case.
	 * @return opposite color of the current color
	 */
	public ChessColor getOppositeColor() {
		switch(this) {
		case WHITE:
			return ChessColor.BLACK;
		case BLACK:
			return ChessColor.WHITE;
		default:
			throw new IllegalArgumentException("No opposite color to DRAW case");
		}
	}
}