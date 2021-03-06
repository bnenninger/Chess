package board;

import moveRules.MoveRule;

/**
 * Stores the different possible chess pieces. Stores the rules that govern the pieces movement, 
 * the rules that govern their capture moves, and the chess algebraic notation abbreviation of the piece
 * @author Brendan Nenninger
 *
 */
public enum PieceType {
	KING	(MoveRule.KING_MOVE_RULE, 	MoveRule.KING_MOVE_RULE,	"K"),
	QUEEN	(MoveRule.QUEEN_MOVE_RULE, 	MoveRule.QUEEN_MOVE_RULE,	"Q"),
	ROOK	(MoveRule.ROOK_MOVE_RULE,	MoveRule.ROOK_MOVE_RULE,	"R"),
	BISHOP	(MoveRule.BISHOP_MOVE_RULE,	MoveRule.BISHOP_MOVE_RULE,	"B"),
	KNIGHT	(MoveRule.KNIGHT_MOVE_RULE,	MoveRule.KNIGHT_MOVE_RULE,	"N"),
	PAWN	(MoveRule.PAWN_MOVE_RULE,	MoveRule.PAWN_CAPTURE_RULE,	"");
	
	/**
	 * stores the rule that governs how a piece moves, specifically when capturing
	 */
	private final MoveRule moveRule;
	/**
	 * stores the rule that governs how a piece captures
	 */
	private final MoveRule captureRule;
	/**
	 * the letter used to represent the piece in algebraic notation
	 */
	private final String abbreviatedName;
	
	PieceType(MoveRule moveRule, MoveRule captureRule, String abbreviatedName){
		this.moveRule = moveRule;
		this.captureRule = captureRule;
		this.abbreviatedName = abbreviatedName;
	}
	
	/**
	 * returns the name of the piece
	 */
	public String toString() {
		return name().toLowerCase();
	}
	
	/**
	 * Returns the one-character abbreviation of a piece, or a blank string in the case of a pawn
	 * @return String representation of the 1- or 0-letter abbreviation of the piece
	 */
	public String getAbbreviatedName() {
		return this.abbreviatedName;
	}
	
	/**
	 * Returns the move rule that governs the movement of the piece when it is moving to an empty square. 
	 * This will generally be the same as the capture rule, except in case of pawns.
	 * @return MoveRule that governs standard movement
	 */
	public MoveRule getMoveRule() {
		return moveRule;
	}
	
	/**
	 * Returns the move rule that governs the movement of the piece when it captures a piece. 
	 * This will generally be the same as the move rule, except in case of pawns.
	 * @return MoveRule that governs movement to a square occupied by an opponent
	 */
	public MoveRule getCaptureRule() {
		return captureRule;
	}
}
