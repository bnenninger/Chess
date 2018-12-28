package moveRules;

import game.IndexValue;
import game.Position;
import main.Board;
import pieces.ChessColor;

/**
 * Rule for the movement of pawns, accounts for the legal two-square move a pawn may perform on its first turn.
 * @author Brendan Nenninger
 *
 */
public class PawnMoveRule extends ForwardMoveRule {

	static final MoveRule PAWN_FIRST_MOVE_EXTRA_RULE = new ForwardMoveRule(true, false, false, 2);

	private static final IndexValue WHITE_START_ROW = new IndexValue(2, false);
	private static final IndexValue BLACK_START_ROW = new IndexValue(7, false);

	/**
	 * Creates a move rule specifically for pawn standard moves, allows for two-square first move
	 */
	public PawnMoveRule() {
		super(true, false, false, 1);
	}

	public boolean isValidMove(Position current, Position proposed, ChessColor color, Board board) {
		if(super.isValidMove(current, proposed, color, board)) {
			return true;
		}
		if((current.getRow().equals(WHITE_START_ROW) && color.equals(ChessColor.WHITE))
				|| (current.getRow().equals(BLACK_START_ROW) && color.equals(ChessColor.BLACK))) {
			return PAWN_FIRST_MOVE_EXTRA_RULE.isValidMove(current, proposed, color, board);
		}
		return false;
	}
}
