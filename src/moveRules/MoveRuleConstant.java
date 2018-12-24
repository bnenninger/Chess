package moveRules;

public class MoveRuleConstant {
	/**
	 * move rule for all the king's moves
	 */
	public static final MoveRule KING_MOVE_RULE = new StandardMoveRule(true, true, true, 1);
	/**
	 * move rule for all the queen's moves
	 */
	public static final MoveRule QUEEN_MOVE_RULE = new StandardMoveRule(true, true, true, 8);
	/**
	 * move rule for all the rook's moves
	 */
	public static final MoveRule ROOK_MOVE_RULE = new StandardMoveRule(true, true, false, 8);
	/**
	 * move rule for all the bishop's moves
	 */
	public static final MoveRule BISHOP_MOVE_RULE = new StandardMoveRule(false, false, true, 8);
	/**
	 * move rule for all the knight's moves
	 */
	public static final MoveRule KNIGHT_MOVE_RULE = new KnightMoveRule(1, 2);
	/**
	 * move rule for the pawn's moves when it doesn't capture
	 */
	public static final MoveRule PAWN_MOVE_RULE = new PawnMoveRule();
	/**
	 * move rule for the pawn's moves when it does capture
	 */
	public static final MoveRule PAWN_CAPTURE_RULE = new ForwardMoveRule(false, false, true, 1);
}
