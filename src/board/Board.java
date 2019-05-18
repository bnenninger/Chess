package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import game.IndexValue;
import game.Position;
import game.Turn;
import moveRules.MoveRule;

public abstract class Board {
	/**
	 * Return the piece at a given Position.
	 * 
	 * @param position the position of the piece to return
	 * @return Piece at the corresponding Position
	 */
	public abstract Piece getPosition(Position position);

	/**
	 * Returns the piece at a given column and row.
	 * 
	 * @param column the column of the piece to return
	 * @param row    the row of the piece to return
	 * @return Piece at the corresponding column and row
	 */
	public Piece getPosition(int column, int row) {
		return getPosition(new Position(column, row));
	}

//	public abstract Piece[] getPieceArray(ChessColor color);

	abstract List<Piece> getPieceList(ChessColor color);

	/**
	 * Returns the king of the given color
	 * 
	 * @param color color of the king to return
	 * @return king of that color
	 */
	public abstract Piece getKing(ChessColor color);

	public Board getQuickMove(Turn turn) {
		return new QuickMoveBoard(this, turn);
	}

	public boolean isInCheck(ChessColor kingColor) {
		return isInCheck(getKing(kingColor));
	}

	public boolean isInCheck(Piece king) {
		return isThreatened(king.getPosition(), king.getColor().getOppositeColor());
	}

	/**
	 * Checks if a position is threatened by pieces of the movingColor color.
	 * 
	 * @param position    the position to be checked for threats
	 * @param movingColor the color of the moving pieces (opposite of the position
	 *                    being checked)
	 * @return true if the position is threatened, false if it is not threatened
	 */
	public boolean isThreatened(Position position, ChessColor movingColor) {
		// checks if there are any pieces threatening the given position
		return getThreateningArray(position, movingColor).length > 0;
		/*
		 * This solution, getting the threatening array and then checking if its length
		 * is greater than one, has benefits and drawbacks. The code between
		 * isThreatened and getThreateningArray is largely redundant, but due to the
		 * difference in the returns cannot easily be split into separate methods. This
		 * solution is simpler, as it removes all redundancy, but is less efficient due
		 * to the additional checks that are performed, as it cannot stop after finding
		 * one threat. The alternate code is commented below. It may be desirable for a
		 * later version, such as a server version, due to its greater speed.
		 */
//		Piece[] movingPieces = getPieceArray(movingColor);
//		for (Piece piece : movingPieces) {
//			if (piece.getPieceType().getMoveRule().isValidMove(piece.getPosition(), position, movingColor, this)) {
//				return true;
//			}
//		}
//		return false;
	}

	// Checkmate testing
	/**
	 * Verifies if the king of the given color is in checkmate
	 * 
	 * @param kingColor the color of the king being tested for checkmate
	 * @return true if the king of the given color is in checkmate, false otherwise.
	 */
	public boolean isCheckMate(ChessColor kingColor) {
		Piece king = getKing(kingColor);
		// pseudocode:
		// if king can move out of check
		// is not checkmate, return false;
		if (isMoveOutofCheck(king)) {
			return false;
		}
		Piece[] threateningPieces = getThreateningArray(king.getPosition(), king.getColor().getOppositeColor());
		// all following cases the king cannot move out of check
		// get number of pieces threatening the king
		// if the number of pieces is >= 2, is checkmate, return true
		// impossible for a piece to block or capture both in the same turn
		if (threateningPieces.length > 1) {
			return true;
		}
		// all following cases only have one piece threatening the king
		// if the piece can be captured or blocked, return false
		// otherwise, return true
		Piece threat = threateningPieces[0];// threateningPieces guaranteed to be length 1, this gets the only threat
		Turn[] blockCaptureTurns = this.getPossibleBlockOrCaptureMoves(king, threat);
		// if the number of possible block or capture turns is equal to zero, the move
		// is checkmate, evaluates to and returns true. Otherwise, not checkmate,
		// evaluates to and returns false.
		return blockCaptureTurns.length == 0;
	}

	/**
	 * Checks if there is a possible move out of check for the king stored by the
	 * CheckAnalyzer.
	 * 
	 * @param king Piece representing the moving king.
	 * @return true if the king can legally move out of check, false otherwise.
	 */
	public boolean isMoveOutofCheck(Piece king) {
		// checks if any moves out of check exist
		return getMovesOutOfCheck(king).length > 0;
		/*
		 * The choice in implementation of this method are largely the same as
		 * isThreatened, as detailed in a comment in that method, as a faster
		 * implementation would be largely redundant.
		 */
// 		// loops through the columns and rows surrounding the king
//		for (int columnShift = -1; columnShift <= 1; columnShift++) {
//			int newColumn = king.getPosition().getColumn().toZeroBasedIndex() + columnShift;
//			for (int rowShift = -1; rowShift <= 1; rowShift++) {
//				// Don't count when both shifts are zero, that is the current position of the
//				// king
//				if (!(columnShift == 0 && rowShift == 0)) {
//					try {
//						int newRow = king.getPosition().getRow().toZeroBasedIndex() + rowShift;
//						Position newPosition = new Position(new IndexValue(newColumn), new IndexValue(newRow));
//						if (king.getPieceType().getMoveRule().isValidMove(king.getPosition(), newPosition,
//								king.getColor(), this)
//								&& !isThreatened(king.getPosition(), king.getColor().getOppositeColor())) {
//							return true;
//						}
//					} catch (IllegalArgumentException e) {
//						// do nothing, just don't break the program
//						// Exception thrown if index is out of bounds for a chess board
//					}
//				}
//			}
//		}
//		return false;
	}

	/**
	 * Sets the movesOutOfCheck Position array to the possible moves that a king
	 * could make to get out of check. Does nothing if that array as already been
	 * set.
	 * 
	 * Intended for use with a function that recommends moves out of check if a
	 * player is stuck. Currently not in use.
	 * 
	 * @param king the king attempting to move out of check
	 * @return array of Positions the king could legally move to
	 */
	public Position[] getMovesOutOfCheck(Piece king) {
		// maximum number of possible moves is 8, only need to have space for that many
		ArrayList<Position> possibleMoves = new ArrayList<Position>(8);
		for (int columnShift = -1; columnShift <= 1; columnShift++) {
			for (int rowShift = -1; rowShift <= 1; rowShift++) {
				// Don't count when both shifts are zero, that is the current position of the
				// king
				if (!(columnShift == 0 && rowShift == 0)) {
					try {
						int newColumn = king.getPosition().getColumn().toZeroBasedIndex() + columnShift;
						int newRow = king.getPosition().getRow().toZeroBasedIndex() + rowShift;
						Position newPosition = new Position(new IndexValue(newColumn), new IndexValue(newRow));
						if (king.getPieceType().getMoveRule().isValidMove(king.getPosition(), newPosition,
								king.getColor(), this)
								&& !isThreatened(king.getPosition(), king.getColor().getOppositeColor())) {
							possibleMoves.add(newPosition);
						}
					} catch (IllegalArgumentException e) {
						// do nothing, just don't break the program
						// Exception thrown if index is out of bounds for a chess board
					}
				}
			}
		}
		return possibleMoves.toArray(new Position[possibleMoves.size()]);
	}

	/**
	 * Evaluates the number of moves that would stop a check either by blocking it
	 * or by capturing the checking piece.
	 * 
	 * @param king        the king being threatened
	 * @param threatening the piece that threatens the king with check
	 * @return array of all possible, legal, and valid moves that would prevent the
	 *         check
	 */
	private Turn[] getPossibleBlockOrCaptureMoves(Piece king, Piece threatening) {
		List<Piece> kingColorPieces = getPieceList(king.getColor());
		Position[] intermediaryPositions = threatening.getPieceType().getMoveRule()
				.getIntermediaryPositions(threatening.getPosition(), king.getPosition(), threatening.getColor());
		ArrayList<Turn> blockingCapturingTurns = new ArrayList<Turn>(7);
		for (Piece piece : kingColorPieces) {
			if (!piece.getPieceType().equals(PieceType.KING)) {// king cannot block check on itself
				MoveRule moveRule = piece.getPieceType().getMoveRule();
				// checks for moves that can block the piece
				for (Position blockPosition : intermediaryPositions) {
					if (moveRule.isValidMove(piece.getPosition(), blockPosition, piece.getColor(), this)) {
						Turn possibleTurn = new Turn(piece.getPosition(), blockPosition);
						blockingCapturingTurns.add(possibleTurn);
					}
				}
				// checks for moves that can capture the piece
				moveRule = piece.getPieceType().getCaptureRule();
				if (moveRule.isValidMove(piece.getPosition(), threatening.getPosition(), piece.getColor(), this)) {
					Turn possibleTurn = new Turn(piece.getPosition(), threatening.getPosition());
					blockingCapturingTurns.add(possibleTurn);
				}
			}
		}
		return blockingCapturingTurns.toArray(new Turn[blockingCapturingTurns.size()]);
	}

	/**
	 * Returns an array of all the pieces that threaten the given position.
	 * 
	 * @param position    the position to check for threats on
	 * @param movingColor the color of the moving pieces
	 * @return Array of all the Pieces that threaten the position
	 */
	public Piece[] getThreateningArray(Position position, ChessColor movingColor) {
		List<Piece> movingPieces = getPieceList(movingColor);
		ArrayList<Piece> threateningPieces = new ArrayList<Piece>();
		for (Piece piece : movingPieces) {
			if (piece.getPieceType().getCaptureRule().isValidMove(piece.getPosition(), position, movingColor, this)) {
				threateningPieces.add(piece);
			}
		}
		return threateningPieces.toArray(new Piece[threateningPieces.size()]);
	}
}
