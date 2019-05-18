package moveRules;

import java.util.NoSuchElementException;

import game.Position;

/**
 * Iterates through the positions that a piece crosses, from the current (exclusive) to the final (exclusive)
 * @author Brendan Nenninger
 *
 */
//excludes the final position because that is handled by the the board, as it is either captured or ensured to be empty
class LinearPositionIterator {
	
	private int currentColumn;
	private int currentRow;
	
	private final int finalColumn;
	private final int finalRow;
	
	//stores whether the columns and rows increase or decrease with every iteration
	private int columnChangeDirectionFactor;
	private int rowChangeDirectionFactor;
	
	/**
	 * Creates an iterator to go through the positions between the current (exclusive) and proposed (exclusive) positions. 
	 * Both positions must be in horizontal, vertical, or diagonal from each other, 
	 * otherwise the behavior of this object is unspecified  
	 * @param current the current position of the piece, excluded from the iteration
	 * @param proposed the proposed new position of the piece, excluded from the iteration
	 */
	public LinearPositionIterator(Position current, Position proposed) {
		currentColumn = current.getColumn();
		currentRow = current.getRow();
		finalColumn = proposed.getColumn();
		finalRow = proposed.getRow();
		columnChangeDirectionFactor = getDirectionFactor(currentColumn, finalColumn);
		rowChangeDirectionFactor = getDirectionFactor(currentRow, finalRow);
		iteratePosition();
	}
	
	/**
	 * Gets the next value between the current and proposed position
	 * @return next Position value between the current and proposed positions
	 */
	public Position next() {
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		Position output = new Position(currentColumn, currentRow);
		iteratePosition();
		return output;
	}
	
	/**
	 * Determines if there is another value to the iteration
	 * @return true if another value exists, false otherwise
	 */
	public boolean hasNext() {
		if(currentColumn == finalColumn && currentRow == finalRow) {
			return false;
		}
		return true;
	}
	
	private void iteratePosition() {
		currentColumn = nextColumnIndex();
		currentRow = nextRowIndex();
	}
	
	private int nextColumnIndex() {
		return nextPositionIndex(currentColumn, columnChangeDirectionFactor);
	}
	
	private int nextRowIndex() {
		return nextPositionIndex(currentRow, rowChangeDirectionFactor);
	}
	
	private int nextPositionIndex(int current, int directionFactor) {
		return current + (1 * directionFactor);
	}
	
	private int getDirectionFactor(int initialIndex, int finalIndex) {
		if(initialIndex == finalIndex) {
			return 0;
		}
		if(initialIndex > finalIndex) {
			return -1;
		}
		return 1;
	}
}
