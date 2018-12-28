package game;

import pieces.Piece;

/**
 * Version of Turn class that includes additional information about the turn, including all of the information 
 * required to properly write the algebraic notation of a chess turn: 
 * the piece moved, the color of the piece, whether a piece was captured, if a piece was put in check, if checkmate occurred.
 * Intended for the storage of a chess game.
 * @author Brendan Nenninger
 *
 */
public class DetailedTurn extends Turn {

	/**
	 * Stores the piece moving
	 */
	private final Piece piece;
	/**
	 * Stores whether a piece was captured, true if a piece was captured
	 */
	private final boolean capture;
	/**
	 * Stores whether the opposing king was put in check, true if it was put in check
	 */
	private final boolean check;
	/**
	 * Stores whether the opposing king was put in checkmate, true if it was put in checkmate
	 */
	private final boolean checkmate;

	/**
	 * Creates a new DetailedTurn, which stores all the information required for chess algebraic notation
	 * @param current the current (initial) position of the piece
	 * @param proposed the proposed (final) position of the piece
	 * @param piece the piece moved
	 * @param capture whether there was a capture on the turn
	 * @param check whether the opposing king was put in check on the turn
	 * @param checkmate whether the opposing king was put in checkmate on the turn
	 */
	public DetailedTurn(Position current, Position proposed, Piece piece, boolean capture, boolean check, boolean checkmate) {
		super(current, proposed);
		this.piece = piece;
		this.capture = capture;
		this.check = check;
		this.checkmate = checkmate;
	}

	/**
	 * Returns the piece moved.
	 * @return the piece moved
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Returns a boolean indicating whether a piece was captured on this turn.
	 * @return true if a piece was captured, false otherwise
	 */
	public boolean isCapture() {
		return capture;
	}

	/**
	 * Returns a boolean indicating whether the opposing king was put in check.
	 * @return true if the opposing king was put in check, false otherwise
	 */
	public boolean isCheck() {
		return check;
	}
	
	/**
	 * Returns a boolean indicating whether the opposing king was put in checkmate.
	 * @return true if the opposing king was put in checkmate, false otherwise
	 */
	public boolean isCheckmate() {
		return checkmate;
	}

	/**
	 * Returns the DetailedMove as a String, in chess algebraic notation
	 */
	public String toString() {
		String output = piece.getPieceType().getAbbreviatedName();
		output += super.getCurrent().getAlgebraicNotation();
		output += getLinkCharacter();
		output += super.getProposed().getAlgebraicNotation();
		output += getCheckSuffix();
		return output;
	}
	
	/**
	 * Returns the character that links the current and proposed positions, indicating if the piece captured on the turn.
	 * @return 'x' if the piece captured or '-' if it did not
	 */
	private char getLinkCharacter() {
		if(capture) {
			return 'x';
		}
		return '-';
	}
	
	/**
	 * Returns the suffix that indicates whether the opposing king was put in check on the turn.
	 * @return "#" if checkmate, "+" if check, or a blank string otherwise
	 */
	private String getCheckSuffix() {
		if(check) {
			if(checkmate) {
				return "#";
			}
			else {
				return "+";
			}
		}
		return "";
	}
}
