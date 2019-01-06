package pieces;

import game.Position;

/**
 * A specific piece in the chess game. Stores both the color and type of the piece.
 * @author Brendan Nenninger
 *
 */
public class Piece {
	
	private PieceType pieceType;
	private ChessColor color;
	
	private Position position;
	
	/**
	 * Creates a new Piece. A new instance should be created for every piece on the chess board.
	 * @param piece the type of the piece
	 * @param color the color of the piece
	 */
	public Piece(PieceType piece, ChessColor color, Position position) {
		this.pieceType = piece;
		this.color = color;
		this.position = position;
	}
	
	/**
	 * Returns the piece as a String, in the format "Piece[&lt;color&gt; &lt;type&gt;]"
	 */
	public String toString() {
		return "Piece[" + color.toString() + " " + pieceType.toString() + "]";
	}
	
	/**
	 * Creates a 2-character abbreviated name of the piece and the color, for display on a console chess board
	 * @return String abbreviation of the piece
	 */
	public String toStringShort() {
		char colorChar = color.toString().charAt(0);
		return colorChar + pieceType.getAbbreviatedName();
	}
	
	/**
	 * Returns the type of this piece
	 * @return the PieceType of this particular piece
	 */
	public PieceType getPieceType() {
		return pieceType;
	}
	
	/**
	 * Returns the color of the piece
	 * @return ChessColor of the piece
	 */
	public ChessColor getColor() {
		return color;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position newPosition) {
		this.position = newPosition;
	}
}
