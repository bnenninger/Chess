package moveRules;

import game.Position;
import main.Board;
import pieces.ChessColor;

/**
 * Rule for the linear movement of typical pieces, such as rooks and the queen. Allows for vertical, horizontal, and diagonal movement, 
 * each of which can be turned on and off for each piece. Includes a maximum movement distance.
 * @author Brendan Nenninger
 *
 */
public class StandardMoveRule implements MoveRule {
	/**
	 * stores whether the piece is allowed to move vertically
	 */
	private final boolean verticalMove;
	/**
	 * stores whether the piece is allowed to move horizontally
	 */
	private final boolean horizontalMove;
	/**
	 * stores whether the piece is allowed to move diagonally
	 */
	private final boolean diagonalMove;
	/**
	 * stores the maximum distance a piece can move
	 */
	private final int maxDistance;

	/**
	 * Creates a MoveRule that governs standard, linear movement of pieces
	 * @param verticalMove whether the piece can move vertically, within one column
	 * @param horizontalMove whether the piece can move horizontally, within one row
	 * @param diagonalMove whether the piece can move diagonally
	 * @param maxDistance the maximum distance that a piece can move
	 */
	public StandardMoveRule(boolean verticalMove, boolean horizontalMove, boolean diagonalMove, int maxDistance) {
		this.verticalMove = verticalMove;
		this.horizontalMove = horizontalMove;
		this.diagonalMove = diagonalMove;
		this.maxDistance = maxDistance;
	}

	@Override
	public boolean isValidMove(Position current, Position proposed, ChessColor color, Board board) {
		if(current.equals(proposed)) {
			throw new IllegalArgumentException("Proposed move is current position");
		}
		//establishes the difference in column and row
		int currentRow = current.getRow().toZeroBasedIndex(),
				proposedRow = proposed.getRow().toZeroBasedIndex(),
				currentColumn = current.getColumn().toZeroBasedIndex(),
				proposedColumn = proposed.getColumn().toZeroBasedIndex();
		int rowDifference = Math.abs(proposedRow - currentRow);
		int columnDifference = Math.abs(proposedColumn - currentColumn);
		
		return isValidDistance(columnDifference, rowDifference)
				&& isValidLinearMove(columnDifference, rowDifference)
				&& checkNoCollisions(current, proposed, board);
	}
	
	/**
	 * Checks that there are no collisions with other pieces between the current and proposed positions, exclusive
	 * @param current the current position of the piece
	 * @param proposed the proposed new position of the piece
	 * @param board the board that the piece is being moved on
	 * @return true if there is no collision
	 */
	private boolean checkNoCollisions(Position current, Position proposed, Board board) {
		LinearPositionIterator positionIterator = new LinearPositionIterator(current, proposed);
		while(positionIterator.hasNext()) {
			Position check = positionIterator.next();
			if(board.getPosition(check) != null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the distance between the current and proposed positions is legal. Does NOT account for collisions.
	 * @param columnDifference the difference in distance between the current and proposed columns
	 * @param rowDifference the difference in distance between the current and proposed rows
	 * @return true if the distance travelled is legal based on this rule
	 */
	private boolean isValidDistance(int columnDifference, int rowDifference) {
		/* checks distance traveled
		 * Finds the maximum row of column difference
		 * this is valid because it covers both the diagonal and linear cases
		 * Diagonal: the row and column difference will be the same
		 * Linear: one of the distances will be zero, and therefore the maximum of the two will be the distance traveled
		 * all other cases are illegal, and therefore do not need to be considered
		 * Negative cases not included, so takes absolute value
		 */
		columnDifference = Math.abs(rowDifference);
		rowDifference = Math.abs(columnDifference);
		return Math.max(columnDifference, rowDifference) <= maxDistance;
	}
	
	/**
	 * Checks if the move is a valid linear move, including diagonal cases. Does NOT account for collisions.
	 * @param columnDifference the difference in distance between the current and proposed columns
	 * @param rowDifference the difference in distance between the current and proposed rows
	 * @return true if the movement is legal
	 */
	private boolean isValidLinearMove(int columnDifference, int rowDifference) {
		return (verticalMove && columnDifference == 0) //checks valid vertical move
				|| (horizontalMove && rowDifference == 0) //checks valid vertical and horizontal move
				|| (diagonalMove && Math.abs(columnDifference) == Math.abs(rowDifference)); //checks valid diagonal move
	}
}
