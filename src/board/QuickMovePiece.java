package board;

import game.Position;
import pieces.ChessColor;
import pieces.Piece;
import pieces.PieceType;

/**
 * Class to store a piece and a modified position, intended for use with the
 * quickmove system. Lightweight, can be created from another Piece object, and
 * does not result in any modification of the given piece
 * 
 * @author Brendan Nenninger
 *
 */
class QuickMovePiece extends Piece {

	private Piece modifiedPiece;
	private Position newPosition;

	public QuickMovePiece(Piece modifiedPiece, Position newPosition) {
		this.modifiedPiece = modifiedPiece;
		this.newPosition = newPosition;
	}

	@Override
	public PieceType getPieceType() {
		return modifiedPiece.getPieceType();
	}

	@Override
	public ChessColor getColor() {
		return modifiedPiece.getColor();
	}

	@Override
	public Position getPosition() {
		return newPosition;
	}

}
