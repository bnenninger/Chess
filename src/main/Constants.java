package main;

import game.IndexValue;

public class Constants {
	/**
	 * Stores the column length of the chess board
	 */
	public static final int BOARD_COLUMN_NUMBER = 8;
	/**
	 * Stores the row length of the chess board
	 */
	public static final int BOARD_ROW_NUMBER = 8;
	/**
	 * Stores the IndexValue of the row white pawns start on
	 */
	public static final IndexValue WHITE_PAWN_ROW = new IndexValue(2, false);
	/**
	 * Stores the IndexValue of the row black pawns start on
	 */
	public static final IndexValue BLACK_PAWN_ROW = new IndexValue(7, false);
}
