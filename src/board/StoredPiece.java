package board;

import game.Position;

/**
 * Class for the long-term storage and management of a piece. Stores information
 * on the type of piece, color, and position of the piece.
 * 
 * @author Brendan Nenninger
 *
 */
class StoredPiece extends Piece {

	private PieceType pieceType;
	private ChessColor color;

	private Position position;

	/**
	 * Creates a new Piece. A new instance should be created for every piece on the
	 * chess board.
	 * 
	 * @param piece the type of the piece
	 * @param color the color of the piece
	 */
	public StoredPiece(PieceType piece, ChessColor color) {
		this.pieceType = piece;
		this.color = color;
	}

	/**
	 * Returns the piece as a String, in the format "Piece[&lt;color&gt;
	 * &lt;type&gt;]"
	 */
	public String toString() {
		return "Piece[" + color.toString() + " " + pieceType.toString() + "]";
	}

	/**
	 * Creates a 2-character abbreviated name of the piece and the color, for
	 * display on a console chess board
	 * 
	 * @return String abbreviation of the piece
	 */
	public String toStringShort() {
		char colorChar = color.toString().charAt(0);
		return colorChar + pieceType.getAbbreviatedName();
	}

	@Override
	public PieceType getPieceType() {
		return pieceType;
	}

	@Override
	public ChessColor getColor() {
		return color;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	/**
	 * Changes the position stored by the piece. Used when the piece is moved.
	 * 
	 * @param newPosition
	 */
	void setPosition(Position newPosition) {
		this.position = newPosition;
	}
}
