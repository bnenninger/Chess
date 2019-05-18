package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import game.FullDetailTurn;
import game.Position;
import game.Turn;
import main.Constants;

public class PlayingBoard extends Board {

	private static final int PAD_WIDTH = 3;

	private HashMap<Position, StoredPiece> board;
	private ColorItemStorage<List<StoredPiece>> piecesInPlay;
	private ColorItemStorage<List<StoredPiece>> capturedPieces;

	// Kings are stored individually, as it must be verified if they are in check at
	// every turn
	private ColorItemStorage<Piece> kings;

	/**
	 * Initializes a PlayingBoard with the default chess piece configuration.
	 */
	public PlayingBoard() {
		this(initializePieces());
	}

	/**
	 * Initializes a PlayingBoard with the passed list of pieces.
	 * 
	 * @param pieces list of pieces to place on the board
	 */
	private PlayingBoard(List<StoredPiece> pieces) {
		board = new HashMap<Position, StoredPiece>(32);
		piecesInPlay = new ColorItemStorage<List<StoredPiece>>(new ArrayList<StoredPiece>(16),
				new ArrayList<StoredPiece>(16));
		capturedPieces = new ColorItemStorage<List<StoredPiece>>(new ArrayList<StoredPiece>(),
				new ArrayList<StoredPiece>());
		kings = new ColorItemStorage<Piece>();
		for (StoredPiece piece : pieces) {
			// Stores the piece as a king if it is a king
			if (piece.getPieceType() == PieceType.KING) {
				kings.setItem(piece.getColor(), piece);
			}
			// Stores pieces in their appropriate lists based on their color
			piecesInPlay.getItem(piece.getColor()).add(piece);
			// places the piece on the board
			board.put(piece.getPosition(), piece);
		}
	}

	/**
	 * Moves pieces based on a turn provided by the player. PERFORMS NO VERIFICATION
	 * OF MOVE LEGALITY WHATSOEVER.
	 * 
	 * @param turn the turn to apply to the board
	 * @return a FullDetailTurn containing the given turn, the moving and
	 *         destination pieces, and whether the move is check or checkmate
	 */
	public FullDetailTurn move(Turn turn) {
		StoredPiece destination = board.get(turn.getProposed());
		// if the destination is not empty, capture it
		if (destination != null) {
			capture(destination);
		}
		// move the piece
		//removes the piece from the original place in the board HashMap
		StoredPiece moving = board.remove(turn.getCurrent());
		//sets the position stored in the piece
		moving.setPosition(turn.getProposed());
		//sets the position on the board
		board.put(turn.getProposed(), moving);
		// returns a move with piece information
		ChessColor oppositeColor = moving.getColor().getOppositeColor();
		boolean check = super.isInCheck(oppositeColor);
		boolean checkmate = super.isCheckMate(oppositeColor);
		return new FullDetailTurn(turn, moving, destination, check, checkmate);
	}

	/**
	 * Removes a piece from the game board. For situations where a piece is captured
	 * by another piece. Adds the captured piece to the list of captured pieces.
	 * 
	 * @param captured the piece being captured
	 */
	private void capture(StoredPiece captured) {
		// must remove from piece list first, as the position must still not be null for
		// equality checks
		piecesInPlay.getItem(captured.getColor()).remove(captured);
		board.remove(captured.getPosition());
		captured.setPosition(null);
		capturedPieces.getItem(captured.getColor()).add(captured);
	}

	/**
	 * Returns a copy of the ArrayList of pieces currently in play of the given
	 * color.
	 * 
	 * @param color the color of piece list to return
	 * @return copy of the list of pieces currently in play
	 */
	public List<Piece> getPieceList(ChessColor color) {
		return new ArrayList<Piece>(piecesInPlay.getItem(color));
	}

	/**
	 * Returns the king of the given color
	 * 
	 * @param color color of the king to return
	 * @return king of that color
	 */
	public Piece getKing(ChessColor color) {
		return kings.getItem(color);
	}
	
	public Piece getPosition(Position position) {
		return board.get(position);
	}

	// toString methods
	/**
	 * Converts the Board to a String for printing to the console, in such a way
	 * that a game of chess could theoretically be played on the console.
	 */
	public String toString() {
		String output = formatSpacing(" ");
		for (char column = 'a'; column <= 'h'; column++) {
			output += formatSpacing(column + "");
		}
		for (int row = 1; row <= 8; row++) {
			output += "\n" + formatSpacing((row + 1) + "");
			for (int column = 1; column <= 8; column++) {
				StoredPiece position = board.get(new Position(column, row));
				if (board.get(new Position(column, row)) != null) {
					output += formatSpacing(position.toStringShort());
				} else {
					output += formatSpacing(" ");
				}
			}
		}
		return output;
	}

	/**
	 * Pads a string to PAD_WIDTH characters, plus a '|' at the end, to form the
	 * squares of a chess board
	 * 
	 * @param contents the contents to pad
	 * @return the padded string
	 */
	private String formatSpacing(String contents) {
		return String.format("%" + PAD_WIDTH + "s|", contents);
	}

	/**
	 * ColorItemStorage serves as a class to store both a black and white item, and
	 * make either easily accessible by providing the ChessColor.
	 * 
	 * @author Brendan Nenninger
	 *
	 * @param <T> Type of item the class stores
	 */
	// Class introduced due to the similarity of code between the getPieceList,
	// getCapturedList, and getKing methods.
	private class ColorItemStorage<T> {
		T whiteItem;
		T blackItem;

		/**
		 * Creates a new ColorItemStorage with the passed white and black items.
		 * 
		 * @param whiteItem
		 * @param blackItem
		 */
		ColorItemStorage(T whiteItem, T blackItem) {
			this.whiteItem = whiteItem;
			this.blackItem = blackItem;
		}

		/**
		 * Creates a ColorItemStorage with no values stored. Allows use of the setItem
		 * method to set the values.
		 */
		// Intended for storing the kings, as they cannot be pre-initialized unlike the
		// piece lists
		ColorItemStorage() {
			this(null, null);
		}

		/**
		 * Returns the stored item associated with the passed color.
		 * 
		 * @param color the color of the item to return
		 * @return item associated with the passed color
		 */
		T getItem(ChessColor color) {
			switch (color) {
			case WHITE:
				return whiteItem;
			case BLACK:
				return blackItem;
			default:
				throw new IllegalArgumentException("Invalid item color");
			}
		}

		/**
		 * Sets the value of the item associated with the passed color. Does not allow
		 * reseting items that have already been set.
		 * 
		 * @param color the color of the item to set
		 * @param item  the value to set the item to
		 */
		void setItem(ChessColor color, T item) {
			switch (color) {
			case WHITE:
				// throws exception if the item is not currently null to prevent overwriting
				if (whiteItem != null) {
					throw new IllegalArgumentException("Cannot overwrite previous white item");
				}
				this.whiteItem = item;
				break;
			case BLACK:
				if (blackItem != null) {
					throw new IllegalArgumentException("Cannot overwrite previous black item");
				}
				this.blackItem = item;
				break;
			// throws exception for all other colors, as they are not valid for this purpose
			default:
				throw new IllegalArgumentException("Invalid color");
			}
		}
	}

	// Initialization methods
	/**
	 * Initializes the pieces for a chess game and returns them as a list. Does not
	 * put into chess board.
	 * 
	 * @return list of chess pieces, with positions, to put into a PlayingBoard
	 */
	private static List<StoredPiece> initializePieces() {
		// Initializes output ArrayList to length of 32 so that it will not be resized
		// during this operation for better speed.
		List<StoredPiece> output = new ArrayList<>(32);
		// initializes the pawns
		initializePawns(output, ChessColor.WHITE, Constants.WHITE_PAWN_ROW);
		initializePawns(output, ChessColor.BLACK, Constants.BLACK_PAWN_ROW);
		// initializes the main pieces
		initializePieceFour(output, PieceType.ROOK, 1);
		initializePieceFour(output, PieceType.KNIGHT, 2);
		initializePieceFour(output, PieceType.BISHOP, 3);
		// initializes the royal pieces
		initializePieceTwo(output, PieceType.QUEEN, 4);
		initializePieceTwo(output, PieceType.KING, 5);
		return output;
	}

	/**
	 * Initializes all of the pawns in a row. Adds the initializes pieces to the
	 * passed list of pieces.
	 * 
	 * @param pieceList the list to which the initialized pieces are added.
	 * @param color     the color of the pawns to be initialized
	 * @param row       the row of the pawns
	 */
	private static void initializePawns(List<StoredPiece> pieceList, ChessColor color, int row) {
		for (int column = 1; column <= 8; column++) {
			StoredPiece newPawn = new StoredPiece(PieceType.PAWN, color, new Position(column, row));
			pieceList.add(newPawn);
		}
	}

	/**
	 * Initializes all four of a specific piece, of both colors. Adds the
	 * initialized pieces to the passed list of pieces.
	 * 
	 * @param pieceList the list to which the initialized pieces are added.
	 * @param piece     the type of piece to initialize
	 * @param column    the column of two of the pieces. The other column is
	 *                  calculated within the method.
	 */
	private static void initializePieceFour(List<StoredPiece> pieceList, PieceType piece, int column) {
		int otherColumn = 8 - column;
		initializePieceTwo(pieceList, piece, column);
		initializePieceTwo(pieceList, piece, otherColumn);
	}

	/**
	 * Initializes pairs of pieces, of both colors. Adds the initialized pieces to
	 * the passed list of pieces.
	 * 
	 * @param pieceList the list to which the initialized pieces are added
	 * @param piece     the type of piece to initialize
	 * @param column    the column of the two pieces
	 */
	private static void initializePieceTwo(List<StoredPiece> pieceList, PieceType piece, int column) {
		StoredPiece whitePiece = new StoredPiece(piece, ChessColor.WHITE,
				new Position(column, 1));
		StoredPiece blackPiece = new StoredPiece(piece, ChessColor.BLACK,
				new Position(column, Constants.BOARD_ROW_NUMBER));
		pieceList.add(whitePiece);
		pieceList.add(blackPiece);
	}
}
