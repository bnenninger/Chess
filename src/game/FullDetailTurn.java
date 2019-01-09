package game;

import pieces.Piece;

/**
 * Version of PieceDetailTurn class that includes additional information about
 * the turn, including all of the information required to properly write the
 * algebraic notation of a chess turn: Includes the following information:
 * starting location, final location, piece moved, piece captured (if any), if
 * the opposing king was put in check, and if the opposing king was put in
 * checkmate. Intended for the storage of a chess game.
 * 
 * @author Brendan Nenninger
 *
 */
public class FullDetailTurn extends PieceDetailTurn {

	/**
	 * Stores whether the opposing king was put in check, true if it was put in
	 * check
	 */
	private final boolean check;
	/**
	 * Stores whether the opposing king was put in checkmate, true if it was put in
	 * checkmate
	 */
	private final boolean checkmate;

	/**
	 * Creates a new FullDetailTurn, which stores all the information required for
	 * chess algebraic notation
	 * 
	 * @param current   the current (initial) position of the piece
	 * @param proposed  the proposed (final) position of the piece
	 * @param piece     the piece moved
	 * @param captured  the piece captured (if any, null if none)
	 * @param check     whether the opposing king was put in check on the turn
	 * @param checkmate whether the opposing king was put in checkmate on the turn
	 */
	public FullDetailTurn(Position current, Position proposed, Piece piece, Piece captured, boolean check,
			boolean checkmate) {
		super(current, proposed, piece, captured);
		this.check = check;
		this.checkmate = checkmate;
	}

	/**
	 * Creates a new FullDetailTurn, which stores all the information required for
	 * chess algebraic notation. This constructor creates a FullDetailTurn based on a
	 * copy of a regular Turn
	 * 
	 * @param turn      the Turn information being copied
	 * @param piece     the piece moved
	 * @param capture   whether there was a capture on the turn
	 * @param check     whether the opposing king was put in check on the turn
	 * @param checkmate whether the opposing king was put in checkmate on the turn
	 */
	public FullDetailTurn(Turn turn, Piece piece, Piece captured, boolean check, boolean checkmate) {
		this(turn.getCurrent(), turn.getProposed(), piece, captured, check, checkmate);
	}

	/**
	 * Returns a boolean indicating whether the opposing king was put in check.
	 * 
	 * @return true if the opposing king was put in check, false otherwise
	 */
	public boolean isCheck() {
		return check;
	}

	/**
	 * Returns a boolean indicating whether the opposing king was put in checkmate.
	 * 
	 * @return true if the opposing king was put in checkmate, false otherwise
	 */
	public boolean isCheckmate() {
		return checkmate;
	}

	/**
	 * Returns the DetailedMove as a String, in chess algebraic notation
	 */
	public String toString() {
		String output = getPiece().getPieceType().getAbbreviatedName();
		output += super.getCurrent().getAlgebraicNotation();
		output += getLinkCharacter();
		output += super.getProposed().getAlgebraicNotation();
		output += getCheckSuffix();
		return output;
	}

	/**
	 * Returns the character that links the current and proposed positions,
	 * indicating if the piece captured on the turn.
	 * 
	 * @return 'x' if the piece captured or '-' if it did not
	 */
	private char getLinkCharacter() {
		if (isCapture()) {
			return 'x';
		}
		return '-';
	}

	/**
	 * Returns the suffix that indicates whether the opposing king was put in check
	 * on the turn.
	 * 
	 * @return "#" if checkmate, "+" if check, or a blank string otherwise
	 */
	private String getCheckSuffix() {
		if (checkmate) {
			return "#";
		}
		if (check) {
			return "+";
		}
		return "";
	}
}
