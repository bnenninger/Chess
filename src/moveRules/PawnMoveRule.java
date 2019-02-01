package moveRules;

import board.Board;
import board.ChessColor;
import game.IndexValue;
import game.Position;
import main.Constants;

/**
 * Rule for the movement of pawns, accounts for the legal two-square move a pawn may perform on its first turn.
 * @author Brendan Nenninger
 *
 */
public class PawnMoveRule extends ForwardMoveRule {

	static final MoveRule PAWN_FIRST_MOVE_EXTRA_RULE = new ForwardMoveRule(true, false, false, 2);

	/**
	 * Creates a move rule specifically for pawn standard moves, allows for two-square first move
	 */
	public PawnMoveRule() {
		super(true, false, false, 1);
	}

	public boolean isValidPieceMove(Position current, Position proposed, ChessColor color, Board board) {
		if(super.isValidPieceMove(current, proposed, color, board)) {
			return true;
		}
		if((current.getRow().equals(Constants.WHITE_PAWN_ROW) && color.equals(ChessColor.WHITE))
				|| (current.getRow().equals(Constants.BLACK_PAWN_ROW) && color.equals(ChessColor.BLACK))) {
			return PAWN_FIRST_MOVE_EXTRA_RULE.isValidPieceMove(current, proposed, color, board);
		}
		return false;
	}
}
