package game;

import board.Piece;

/**
 * Version of Turn that also includes information on the piece moved and the
 * piece captured, if there is one. Intended for use for testing turns, as it
 * gives all the information required to roll back a turn if needed.
 * 
 * @author Brendan Nenninger
 *
 */
public class PieceDetailTurn extends Turn {

	private final Piece moving, captured;

	/**
	 * Creates a new PieceDetailTurn, which stores all the information required for
	 * chess algebraic notation
	 * 
	 * @param current   the current (initial) position of the piece
	 * @param proposed  the proposed (final) position of the piece
	 * @param piece     the piece moved
	 * @param captured  the piece captured (if any, null if none)
	 */
	public PieceDetailTurn(Position current, Position proposed, Piece moving, Piece captured) {
		super(current, proposed);
		this.moving = moving;
		this.captured = captured;
	}
	
	/**
	 * Creates a new PieceDetailTurn, which stores all the information required for
	 * chess algebraic notation. This constructor takes an existing Turn object to copy into a PieceDetailTurn
	 * 
	 * @param turn      the Turn information being copied
	 * @param piece     the piece moved
	 * @param captured  the piece captured (if any, null if none)
	 */
	public PieceDetailTurn(Turn turn, Piece moving, Piece captured) {
		this(turn.getCurrent(), turn.getProposed(), moving, captured);
	}

	/**
	 * Gets the Piece moved on this turn.
	 * @return the Piece moved
	 */
	public Piece getPiece() {
		return moving;
	}
	
	/**
	 * Gets the Piece captured on this turn.
	 * @return the Piece captured, or null if no piece was captured
	 */
	public Piece getCaptured() {
		return captured;
	}
	
	/**
	 * Whether a piece was captured on this turn.
	 * @return true if a piece was captured, false otherwise
	 */
	public boolean isCapture() {
		return captured != null;
	}
}
