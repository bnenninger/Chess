package board;

import java.util.ArrayList;

import game.IndexValue;
import game.Position;
import game.Turn;

public abstract class Board {
	/**
	 * Return the piece at a given Position.
	 * @param position the position of the piece to return
	 * @return Piece at that Position
	 */
	public abstract Piece getPosition(Position position);

	public abstract Piece getPosition(IndexValue column, IndexValue row);
	
	public abstract Piece[] getPieceArray(ChessColor color);
	
	abstract ArrayList<Piece> getPieceList(ChessColor color);
	
	/**
	 * Returns the king of the given color
	 * 
	 * @param color color of the king to return
	 * @return king of that color
	 */
	public abstract Piece getKing(ChessColor color);
	
	public Board getQuickMove(Turn turn){
		return new QuickMoveBoard(this, turn);
	}
	
	public boolean isInCheck(ChessColor kingColor) {
		return isInCheck(getKing(kingColor));
	}
	
	public boolean isInCheck(Piece king) {
		return isThreatened(king.getPosition(), king.getColor().getOppositeColor());
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
	 * Checks if there is a possible move out of check for the king stored by the
	 * CheckAnalyzer.
	 * 
	 * @param king Piece representing the moving king.
	 * @return true if the king can legally move out of check, false otherwise.
	 */
	public boolean isMoveOutofCheck(Piece king) {
		// loops through the columns and rows surrounding the king
		for (int columnShift = -1; columnShift <= 1; columnShift++) {
			int newColumn = king.getPosition().getColumn().toZeroBasedIndex() + columnShift;
			for (int rowShift = -1; rowShift <= 1; rowShift++) {
				// Don't count when both shifts are zero, that is the current position of the
				// king
				if (!(columnShift == 0 && rowShift == 0)) {
					try {
						int newRow = king.getPosition().getRow().toZeroBasedIndex() + rowShift;
						Position newPosition = new Position(new IndexValue(newColumn), new IndexValue(newRow));
						if (king.getPieceType().getMoveRule().isValidMove(king.getPosition(), newPosition,
								king.getColor(), this)
								&& !isThreatened(king.getPosition(), king.getColor().getOppositeColor())) {
							return true;
						}
					} catch (IllegalArgumentException e) {
						// do nothing, just don't break the program
						// Exception thrown if index is out of bounds for a chess board
					}
				}
			}
		}
		return false;
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
		Piece[] movingPieces = getPieceArray(movingColor);
		for (Piece piece : movingPieces) {
			if (piece.getPieceType().getMoveRule().isValidMove(piece.getPosition(), position, movingColor, this)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns an array of all the pieces that threaten the given position.
	 * 
	 * @param position    the position to check for threats on
	 * @param movingColor the color of the moving pieces
	 * @return Array of all the Pieces that threaten the position
	 */
	public Piece[] getThreateningArray(Position position, ChessColor movingColor) {
		Piece[] movingPieces = getPieceArray(movingColor);
		ArrayList<Piece> threateningPieces = new ArrayList<Piece>();
		for (Piece piece : movingPieces) {
			if (piece.getPieceType().getMoveRule().isValidMove(piece.getPosition(), position, movingColor, this)) {
				threateningPieces.add(piece);
			}
		}
		return threateningPieces.toArray(new Piece[threateningPieces.size()]);
	}
}
