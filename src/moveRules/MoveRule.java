package moveRules;

import board.Board;
import board.ChessColor;
import game.IndexValue;
import game.Position;
import game.Turn;

/**
 * Interface for the various rules that govern how a chess piece moves
 * 
 * @author Brendan Nenninger
 *
 */
public abstract class MoveRule {
	/**
	 * Checks whether the move is valid and legal, to allow the board to move the
	 * pieces based on that command. Includes evaluation of check status.
	 * 
	 * @param current  the current position of the piece to be moved
	 * @param proposed the position the piece is to move to
	 * @param color    the color of the piece being moved, governs directional
	 *                 pieces (pawns)
	 * @param board    the board the piece is being moved on, used to check that
	 *                 pieces do not collide
	 * @return boolean, true if the movement is valid and can legally be performed
	 */
	// Needs to be a separate public class to enforce that classes outside of the
	// package call this, which includes a check evaluation. By having a separate
	// method for checking the actual movement of the piece, it makes this class
	// easier to extend as the check evaluation has to be called.
	public final boolean isValidMove(Position current, Position proposed, ChessColor color, Board board) {
		// checks if the movement of the piece is illegal, if so, returns false.
		if (!isValidPieceMove(current, proposed, color, board)) {
			return false;
		}
		// verifies that the same color king is not put in check
		Board quickMoveBoard = board.getQuickMove(new Turn(current, proposed));
		return !quickMoveBoard.isInCheck(color);
	}

	/**
	 * Checks whether the move is valid and legal based on the type of piece that is
	 * moving, to allow the board to move the pieces based on that command. INCLUDES
	 * NO EVALUATION OF CHECK STATUS. Called by isValidMove, which incorporates the
	 * check evaluation.
	 * 
	 * @param current  the current position of the piece to be moved
	 * @param proposed the position the piece is to move to
	 * @param color    the color of the piece being moved, governs directional
	 *                 pieces (pawns)
	 * @param board    the board the piece is being moved on, used to check that
	 *                 pieces do not collide
	 * @return boolean, true if the movement is valid and can legally be performed
	 */
	abstract boolean isValidPieceMove(Position current, Position proposed, ChessColor color, Board board);

	/**
	 * Returns the positions that a piece passes through between the current and
	 * proposed positions, which if blocked would prevent the move
	 * 
	 * @param current  the current location of the moving piece
	 * @param proposed the proposed position of the moving piece
	 * @param color    the color of the piece moving
	 * @return Position[] of positions that if blocked would prevent the move
	 */
	public abstract Position[] getIntermediaryPositions(Position current, Position proposed, ChessColor color);

	/**
	 * Returns the absolute value difference between the current and proposed
	 * columns.
	 * 
	 * @param current  the current position
	 * @param proposed the proposed new position
	 * @return positive integer value of the difference between the column values
	 */
	protected int getAbsoluteColumnDifference(Position current, Position proposed) {
		return getAbsoluteDifference(current.getColumn(), proposed.getColumn());
	}

	/**
	 * Returns the absolute value difference between the current and proposed rows.
	 * 
	 * @param current  the current position
	 * @param proposed the proposed new position
	 * @return positive integer value of the difference between the row values
	 */
	protected int getAbsoluteRowDifference(Position current, Position proposed) {
		return getAbsoluteDifference(current.getRow(), proposed.getRow());
	}

	/**
	 * Returns the absolute value difference between two IndexValues.
	 * 
	 * @param current  the current IndexValue of the piece
	 * @param proposed the proposed new IndexValue of the piece
	 * @return positive integer value of the difference between the two values
	 */
	private int getAbsoluteDifference(IndexValue current, IndexValue proposed) {
		return Math.abs(current.toZeroBasedIndex() - proposed.toZeroBasedIndex());
	}
}
