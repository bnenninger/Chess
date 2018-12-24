package moveRules;

import main.Board;
import main.Position;
import pieces.ChessColor;

/**
 * Rule for linear movement of a piece, which is restricted to the piece moving forward or horizontally. 
 * Allows for vertical, horizontal, and diagonal movement, 
 * each of which can be turned on and off for each piece. Includes a maximum movement distance.
 * @author Brendan Nenninger
 *
 */
public class ForwardMoveRule extends StandardMoveRule {

	/**
	 * Creates a MoveRule that governs linear movement of pieces, restricted to the forward direction
	 * @param verticalMove whether the piece can move vertically, within one column
	 * @param horizontalMove whether the piece can move horizontally, within one row
	 * @param diagonalMove whether the piece can move diagonally
	 * @param maxDistance the maximum distance that a piece can move
	 */
	public ForwardMoveRule(boolean verticalMove, boolean horizontalMove, boolean diagonalMove, int distance) {
		super(verticalMove, horizontalMove, diagonalMove, distance);
	}

	public boolean isValidMove(Position current, Position proposed, ChessColor color, Board board) {
		int direction = -1;
		if(color.equals(ChessColor.WHITE)) {
			direction = 1;
		}
		//checks if piece moves forward, return false for invalid if it moves back
		int forwardDistance = (proposed.getRow().toZeroBasedIndex() - current.getRow().toZeroBasedIndex()) * direction;
		if(forwardDistance < 0) {
			return false;
		}
		//checks for standard movement patterns
		return super.isValidMove(current, proposed, color, board);
	}
}
