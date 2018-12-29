package game;

import main.Constants;

/**
 * Class to store a position on the chess board, with both a row and column component.
 * @author Brendan Nenninger
 *
 */
public final class Position {
	
	private final IndexValue column;
	private final IndexValue row;
	
	/**
	 * Creates a new Position based on two IndexValues.
	 * @param column the column value of the position
	 * @param row the row value of the position
	 */
	public Position(IndexValue column, IndexValue row) {
		if(!(checkValidIndex(column) && checkValidIndex(row))) {
			throw new IllegalArgumentException("Invalid position");
		}
		this.column = column;
		this.row = row;
	}
	
	/**
	 * Creates a new Position based on two integers.
	 * @param column the column value of the position, 1-based
	 * @param row the row value of the position, 1-based
	 */
	public Position(int column, int row) {
		this(new IndexValue(column, false), new IndexValue(row, false));
	}
	
	/**
	 * Creates a new Position based on a column character and row integer.
	 * @param column the column value of the position, a-based
	 * @param row the row value of the position, 1-based
	 */
	public Position(char column, int row) {
		this(new IndexValue(column), new IndexValue(row, false));
	}
	
	/**
	 * Creates a new Position based on chess algebraic notation
	 * @param algebraicNotation String representation of the position, in the format "a1"
	 */
	public Position(String algebraicNotation) {
		this(algebraicNotation.charAt(0), Integer.parseInt(algebraicNotation.substring(1, 2)));
		if(algebraicNotation.length() != 2) {
			throw new IllegalArgumentException("invalid string length");
		}
	}
	
	/**
	 * Returns a String representation of the Position, labelled as a Position object.
	 */
	public String toString() {
		return "Position[" + getAlgebraicNotation() + "]";
	}
	
	/**
	 * Compares the values of this Postion to another position.
	 */
	public final boolean equals(Object other) {
		if(!(other instanceof Position)) {
			return false;
		}
		Position confirmedOther = (Position)other;
		return confirmedOther.column.equals(this.column) && confirmedOther.row.equals(this.row);
	}
	
	/**
	 * Returns the column value of the Position.
	 * @return column value of the Position as an IndexValue
	 */
	public final IndexValue getColumn() {
		return column;
	}
	
	/**
	 * Returns the row value of the Position.
	 * @return row value of the Position as an IndexValue
	 */
	public final IndexValue getRow() {
		return row;
	}
	
	/**
	 * Returns the chess algebraic notation form of the Position.
	 * @return the position, in form "a1"
	 */
	public String getAlgebraicNotation() {
		return "" + column.toChar() + row.toOneBasedIndex();
	}
	
	/**
	 * Checks that the given value is valid relative to the constant MAX_VALUE and 1.
	 * @param index index to be compared
	 * @return boolean, true if the index is between 1 and MAX_VALUE, inclusive
	 */
	private boolean checkValidIndex(IndexValue index) {
		return index.toOneBasedIndex() <= Math.max(Constants.BOARD_COLUMN_NUMBER, Constants.BOARD_ROW_NUMBER) && index.toOneBasedIndex() >= 1; 
	}
}
