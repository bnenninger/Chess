package game;

import java.util.Arrays;

import main.Constants;

/**
 * Class to store a position on the chess board, with both a row and column
 * component.
 * 
 * @author Brendan Nenninger
 *
 */
public final class Position {

	private final int column;
	private final int row;

	/**
	 * Creates a new Position based on two integers.
	 * 
	 * @param column the column value of the position, 1-based
	 * @param row    the row value of the position, 1-based
	 */
	public Position(int column, int row) {
		this.column = column;
		this.row = row;
		if (column < 1 || column > Constants.BOARD_COLUMN_NUMBER || row < 1 || row > Constants.BOARD_ROW_NUMBER) {
			throw new IllegalArgumentException("invalid column or row value");
		}
	}

	/**
	 * Creates a new Position based on a column character and row integer.
	 * 
	 * @param column the column value of the position, a-based
	 * @param row    the row value of the position, 1-based
	 */
	public Position(char column, int row) {
		this(charToInt(column), row);
	}

	/**
	 * Creates a new Position based on chess algebraic notation
	 * 
	 * @param algebraicNotation String representation of the position, in the format
	 *                          "a1"
	 */
	public Position(String algebraicNotation) {
		this(algebraicNotation.charAt(0), Integer.parseInt(algebraicNotation.substring(1, 2)));
	}

	/**
	 * Returns a String representation of the Position, labeled as a Position
	 * object.
	 */
	public String toString() {
		return "Position[" + getAlgebraicNotation() + "]";
	}

	/**
	 * Compares the values of this Position to another position.
	 */
	public final boolean equals(Object other) {
		if (!(other instanceof Position)) {
			return false;
		}
		Position confirmedOther = (Position) other;
		return confirmedOther.column == this.column && confirmedOther.row == this.row;
	}
	
	public final int hashCode() {
		int[] values = {column, row};
		return Arrays.hashCode(values);
	}

	/**
	 * Returns the column value of the Position.
	 * 
	 * @return column value of the Position
	 */
	public final int getColumn() {
		return column;
	}

	/**
	 * Returns the row value of the Position.
	 * 
	 * @return row value of the Position
	 */
	public final int getRow() {
		return row;
	}

	/**
	 * Returns the chess algebraic notation form of the Position.
	 * 
	 * @return the position, in form "a1"
	 */
	public String getAlgebraicNotation() {
		return "" + intToChar(column) + row;
	}

	/**
	 * Converts a character to an integer, such that 'a' becomes 1.
	 * 
	 * @param input the character to convert to an integer
	 * @return integer equivalent of the character
	 */
	private static int charToInt(char input) {
		return input - 'a' + 1;
	}

	/**
	 * Converts an integer to a character, such that 1 becomes 'a'.
	 * 
	 * @param input the integer to convert to a character
	 * @return character equivalent of the integer
	 */
	private static char intToChar(int input) {
		return (char) (input - 1 + 'a');
	}
}
