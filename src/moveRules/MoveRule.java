package moveRules;

import game.Position;
import main.Board;
import pieces.ChessColor;

/**
 * Interface for the various rules that govern how a chess piece moves
 * @author Brendan Nenninger
 *
 */
public interface MoveRule {
	/**
	 * Checks whether the move is valid and legal, to allow the board to move the pieces based on that command
	 * @param current the current position of the piece to be moved
	 * @param proposed the position the piece is to move to
	 * @param color the color of the piece being moved, governs directional pieces (pawns)
	 * @param board the board the piece is being moved on, used to check that pieces do not collide
	 * @return boolean, true if the movement is valid and can legally be performed
	 */
	public boolean isValidMove(Position current, Position proposed, ChessColor color, Board board);
}
