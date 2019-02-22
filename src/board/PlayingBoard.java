package board;

import java.util.ArrayList;

import game.FullDetailTurn;
import game.IndexValue;
import game.PieceDetailTurn;
import game.Position;
import game.Turn;
import moveRules.MoveRule;

public class PlayingBoard extends Board {

	private static final int PAD_WIDTH = 3;

	// TODO change to some kind of map/tree
	// a grid array is not necessary for this to function, as long as you can
	// retrieve any location
	private StoredPiece[][] board;// [column][row]
	private ArrayList<StoredPiece> whitePieces;
	private ArrayList<StoredPiece> blackPieces;
	private ArrayList<StoredPiece> whiteCaptured;
	private ArrayList<StoredPiece> blackCaptured;

	// Kings are stored individually, as it must be verified if they are in check at
	// every turn
	private Piece whiteKing;
	private Piece blackKing;

	public PlayingBoard() {
		board = new StoredPiece[8][8];
		whitePieces = new ArrayList<StoredPiece>(16);
		blackPieces = new ArrayList<StoredPiece>(16);
		whiteCaptured = new ArrayList<StoredPiece>();
		blackCaptured = new ArrayList<StoredPiece>();
		initializePieces();
	}

	private void setPosition(StoredPiece piece, Position position) {
		setPosition(piece, position.getColumn(), position.getRow());
	}

	private void setPosition(StoredPiece piece, IndexValue column, IndexValue row) {
		board[column.toZeroBasedIndex()][row.toZeroBasedIndex()] = piece;
	}

	public StoredPiece getPosition(Position position) {
		return getPosition(position.getColumn(), position.getRow());
	}

	public StoredPiece getPosition(IndexValue column, IndexValue row) {
		return board[column.toZeroBasedIndex()][row.toZeroBasedIndex()];
	}

	/**
	 * Removes and returns the piece at a given position.
	 * 
	 * @param position the position to clear
	 * @return the piece that was cleared from the position
	 */
	private StoredPiece remove(Position position) {
		StoredPiece piece = getPosition(position); 
		setPosition(null, position);
		piece.setPosition(null);
		return piece;
	}

	/**
	 * Removes a piece from the game board. For situations where a piece is captured
	 * by another piece. Adds the captured piece to the list of captured pieces.
	 * 
	 * @param captured the piece being captured
	 */
	private void capture(StoredPiece captured) {
		getPieceList(captured.getColor()).remove(captured);
		getCapturedList(captured.getColor()).add(captured);
		captured.setPosition(null);
	}

	/**
	 * Returns an array of all the pieces currently in play of the given color.
	 * 
	 * @param color the color of the pieces in the array returned
	 * @return Array of all pieces in play of that color.
	 */
	public Piece[] getPieceArray(ChessColor color) {
		ArrayList<Piece> pieceList = getColorAssociatedList(whitePieces, blackPieces, color);
		return pieceList.toArray(new Piece[pieceList.size()]);
	}

	/**
	 * Returns an array of all the pieces that have been captured of the given
	 * color.
	 * 
	 * @param color the color of the pieces in the array returned
	 * @return Array of all the captured pieces of that color
	 */
	public Piece[] getCapturedArray(ChessColor color) {
		ArrayList<Piece> pieceList = getColorAssociatedList(whiteCaptured, blackCaptured, color);
		return pieceList.toArray(new Piece[pieceList.size()]);
	}

	/**
	 * Returns a copy of the ArrayList of pieces currently in play of the given
	 * color.
	 * 
	 * @param color the color of piece list to return
	 * @return list of pieces currently in play
	 */
	public ArrayList<Piece> getPieceList(ChessColor color) {
		return getColorAssociatedList(whitePieces, blackPieces, color);
	}

	/**
	 * Returns a copy of the ArrayList of captured pieces of the given color.
	 * 
	 * @param color the color of the piece list to return
	 * @return list of captured pieces
	 */
	private ArrayList<Piece> getCapturedList(ChessColor color) {
		return getColorAssociatedList(whiteCaptured, blackCaptured, color);
	}

	/**
	 * Returns a copy of the ArrayList of Pieces associated with the color given.
	 * 
	 * @param whitePieces2 the list to return if WHITE is passed as the color
	 * @param blackPieces2 the list to return if BLACK is passed as the color
	 * @param color        the color of the ArrayList to get
	 * @return the ArrayList associated with the given color
	 */
	private ArrayList<Piece> getColorAssociatedList(ArrayList<StoredPiece> whitePieces2,
			ArrayList<StoredPiece> blackPieces2, ChessColor color) {
		if (color.equals(ChessColor.WHITE)) {
			return new ArrayList<Piece>(whitePieces2);
		}
		return new ArrayList<Piece>(blackPieces2);
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
		for (int row = 0; row <= 7; row++) {
			output += "\n" + formatSpacing((row + 1) + "");
			for (char column = 0; column < 8; column++) {
				if (board[column][row] != null) {
					output += formatSpacing(board[column][row].toStringShort());
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

	// TODO return DetailedTurn
	// returns the captured Piece
	/**
	 * Moves pieces based on a turn provided by the player. PERFORMS NO VERIFICATION
	 * OF MOVE LEGALITY WHATSOEVER.
	 * 
	 * @param turn the turn to apply to the board
	 * @return a PieceDetailTurn containing both the turn and the piece moved and
	 *         captured, if any.
	 */
	public PieceDetailTurn move(Turn turn) {
		StoredPiece destination = getPosition(turn.getProposed());
		// if the destination is not empty, capture it
		if (destination != null) {
			capture(destination);
		}
		// move the piece
		StoredPiece moving = remove(turn.getCurrent());
		moving.setPosition(turn.getProposed());
		setPosition(moving, turn.getProposed());
		// returns a move with piece information
		return new PieceDetailTurn(turn, moving, destination);
	}

	public boolean isCheckMate(Piece kingInCheckmate) {
		// pseudocode:
		// if king can move out of check
		// is not checkmate, return false;
		if (super.isMoveOutofCheck(kingInCheckmate)) {
			return false;
		}
		Piece[] threateningPieces = super.getThreateningArray(kingInCheckmate.getPosition(),
				kingInCheckmate.getColor().getOppositeColor());
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
		Turn[] blockCaptureTurns = this.getPossibleBlockOrCaptureMoves(kingInCheckmate, threat);
		// if the number of possible block or capture turns is equal to zero, the move
		// is checkmate, evaluates to and returns true. Otherwise, not checkmate,
		// evaluates to and returns false.
		return blockCaptureTurns.length == 0;
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
		Piece[] kingColorPieces = getPieceArray(king.getColor());
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
	 * Throws exceptions for moves judged illegal due to there not being a moving
	 * piece, there being a same color piece at the destination, there being a king
	 * at the destination, or the move being illegal based on the piece moving.
	 * 
	 * @param moving      the Piece being moved, at the current position
	 * @param destination the Piece at the proposed position
	 * @param color       the color of the piece moving
	 */
	private void illegalMoveExceptionThrower(Turn turn, Piece moving, Piece destination, ChessColor color,
			boolean isCapture) {
		// checks if there is a movable piece at that position, of the color that is
		// currently taking a turn
		if (moving == null || !moving.getColor().equals(color)) {
			throw new IllegalArgumentException("no " + color.toString() + " piece at that position");
		}
		// checks that destination is a piece before checking further info on it
		if (destination != null) {
			// checks if the destination is empty or can be captured
			if (destination.getColor().equals(color)) {
				throw new IllegalArgumentException("same color piece at destination");
			}
			// checks if the destination is a king
			if (destination.getPieceType().equals(PieceType.KING)) {
				throw new IllegalArgumentException("cannot capture king");
			}
		}
		// finds the proper MoveRule for the movement, choosing the capture or regular
		// rule
		MoveRule rule = moving.getPieceType().getMoveRule();
		if (isCapture) {
			rule = moving.getPieceType().getCaptureRule();
		}
		boolean validMove = rule.isValidMove(turn.getCurrent(), turn.getProposed(), color, this);
		if (!validMove) {
			throw new IllegalArgumentException("impossible move");
		}
	}

	/**
	 * Returns the king of the given color
	 * 
	 * @param color color of the king to return
	 * @return king of that color
	 */
	public Piece getKing(ChessColor color) {
		switch (color) {
		case BLACK:
			return blackKing;
		case WHITE:
			return whiteKing;
		default:
			throw new IllegalArgumentException("DRAW king does not exist");
		}
	}

	// Initialization methods
	/**
	 * Initializes the pieces onto the chess board
	 */
	private void initializePieces() {
		// initializes the pawns
		initializePawns(new IndexValue(2, false), ChessColor.WHITE);
		initializePawns(new IndexValue(7, false), ChessColor.BLACK);
		// initializes the main pieces
		initializePieceFour(new IndexValue('a'), PieceType.ROOK);
		initializePieceFour(new IndexValue('b'), PieceType.KNIGHT);
		initializePieceFour(new IndexValue('c'), PieceType.BISHOP);
		// initializes the royal pieces
		initializePieceTwo(new IndexValue('d'), PieceType.QUEEN);
		initializePieceTwo(new IndexValue('e'), PieceType.KING);
	}

	/**
	 * Initalizes all of a row to be pawns
	 * 
	 * @param row   the row to fill with pawns
	 * @param color the color the pawns will be
	 */
	private void initializePawns(IndexValue row, ChessColor color) {
		for (int column = 0; column < 8; column++) {
			StoredPiece newPawn = new StoredPiece(PieceType.PAWN, color);
			addPiece(newPawn, new IndexValue(column), row);
		}
	}

	/**
	 * Initializes quads of pieces, such as rooks, including both colors of the
	 * piece
	 * 
	 * @param lowColumn the lowest column value that the piece is placed at
	 * @param piece     the piece to be placed
	 */
	private void initializePieceFour(IndexValue lowColumn, PieceType piece) {
		int otherColumn = 7 - lowColumn.toZeroBasedIndex();
		initializePieceTwo(lowColumn, piece);
		initializePieceTwo(new IndexValue(otherColumn, true), piece);
	}

	/**
	 * Places pairs of pieces, of both colors. Stores the kings if those are the
	 * pieces added.
	 * 
	 * @param column the column of the pieces initialized
	 * @param piece  the piece to be added
	 */
	private void initializePieceTwo(IndexValue column, PieceType piece) {
		StoredPiece whitePiece = new StoredPiece(piece, ChessColor.WHITE);
		StoredPiece blackPiece = new StoredPiece(piece, ChessColor.BLACK);
		addPiece(whitePiece, column, new IndexValue(1, false));
		addPiece(blackPiece, column, new IndexValue(8, false));
		if (piece.equals(PieceType.KING)) {
			whiteKing = whitePiece;
			blackKing = blackPiece;
		}
	}

	/**
	 * Adds a new Piece to the board and the lists of pieces
	 * 
	 * @param piece  the piece to be added. This reference should be unique
	 * @param column the column to add the piece in
	 * @param row    the row to add the piece in
	 */
	private void addPiece(StoredPiece piece, IndexValue column, IndexValue row) {
		setPosition(piece, column, row);
		getPieceList(piece.getColor()).add(piece);
		piece.setPosition(new Position(column, row));
	}
}
