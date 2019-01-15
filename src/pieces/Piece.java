package pieces;

import game.Position;

/**
 * Class that stores all of the information regarding a chess piece,
 * specifically its PieceType, color, and position.
 * 
 * @author Brendan Nenninger
 *
 */
public abstract class Piece {
	/**
	 * Returns the type of this piece
	 * 
	 * @return the PieceType of this particular piece
	 */
	public abstract PieceType getPieceType();

	/**
	 * Returns the color of the piece
	 * 
	 * @return ChessColor of the piece
	 */
	public abstract ChessColor getColor();

	/**
	 * Returns the Position of the piece
	 * 
	 * @return Position of the piece
	 */
	public abstract Position getPosition();

	/**
	 * Compares the equality of two objects, returning true if they are equal.
	 * Considers all children of Piece equally, provided that they have the same
	 * apparent object state, regardless of how they achieve that state.
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Piece)) {
			return false;
		}
		Piece confirmedOther = (Piece) o;
		return confirmedOther.getPieceType().equals(getPieceType()) && confirmedOther.getColor().equals(getColor())
				&& confirmedOther.getPosition().equals(getPosition());
	}
}
