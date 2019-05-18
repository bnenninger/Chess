package game;

import board.ChessColor;
import board.Piece;
import board.PlayingBoard;
import board.QuickMoveBoard;
import moveRules.MoveRule;

public class ActiveGame {
	private PlayingBoard gameBoard;
	private ChessColor movingColor;
	// TODO add storage for the game, remove following variables
	private int turnNumber;
	private boolean gameFinished;
	private ChessColor winner;

	public ActiveGame() {
		gameBoard = new PlayingBoard();
		movingColor = ChessColor.WHITE;

		turnNumber = 1;
		gameFinished = false;
		winner = null;
	}

	/**
	 * Applies a turn to the game, or returns false if the passed move is not
	 * allowed.
	 * 
	 * @param turn the turn to apply
	 * @return true if the move was applied, false if it was not allowed and not
	 *         able to be applied
	 */
	public boolean move(Turn turn) {
		Piece moving = gameBoard.getPosition(turn.getCurrent());
		Piece destination = gameBoard.getPosition(turn.getProposed());
		// returns false if the move does not exist, the move is not a valid pattern, or
		// the move is illegal (puts own king in check)
		if (!isExistingMove(turn, moving, destination) || !isValidPattern(turn, moving, destination)
				) {//|| !isLegalMove(turn, moving, destination)) {
			return false;
		}
		// the move is now known to be allowed, perform it
		FullDetailTurn fullTurn = gameBoard.move(turn);
		System.out.println(fullTurn);
		// if the move is checkmate, end the game
		if (fullTurn.isCheckmate()) {
			gameFinished = true;
			winner = fullTurn.getPiece().getColor();
		}
		// otherwise, if the game has reached 50 moves for both players, end the game
		else if (turnNumber == 50 && movingColor == ChessColor.BLACK) {
			gameFinished = true;
			winner = ChessColor.DRAW;
		}
		// if the game is not over, prepare for the next turn
		else {
			// if black has moved, that means that the player of both colors has moved their
			// piece. Therefore, a turn has occurred for both players and the turn number
			// can be incremented.
			if (movingColor == ChessColor.BLACK) {
				turnNumber++;
			}
			movingColor = movingColor.getOppositeColor();
		}
		return true;
	}

	/**
	 * Gets the color of the player currently making a move
	 * 
	 * @return ChessColor of the player to call move() next
	 */
	public ChessColor getCurrentPlayer() {
		return movingColor;
	}

	/**
	 * Returns whether the game has finished.
	 * 
	 * @return true if the game has finished, false if it is ongoing.
	 */
	public boolean isFinished() {
		return gameFinished;
	}

	/**
	 * Returns the ChessColor of the winner of the game, or the ChessColor state
	 * DRAW if the game is a draw. Throws an UnsupportedOperationException if the
	 * method is called when the game is not finished.
	 * 
	 * @return ChessColor of the winner or draw condition
	 */
	public ChessColor getWinner() {
		if (gameFinished) {
			return winner;
		}
		throw new UnsupportedOperationException("Game not yet finished, no winner");
	}

	/**
	 * Returns a String representation of the chess board.
	 * 
	 * @return String of the chess board
	 */
	// Does not return the PlayingBoard itself so as to prevent abuse and
	// modification of the board.
	public String getBoardString() {
		return gameBoard.toString();
	}

	/**
	 * Checks if a move exists. A move exists if the moving piece exists, is of the
	 * color of the moving player, and does not capture a same colored piece.
	 * 
	 * @param turn        the turn being tested
	 * @param moving      the piece moving
	 * @param destination the piece at the position being moved to
	 * @return true if the move exists, false otherwise.
	 */
	private boolean isExistingMove(Turn turn, Piece moving, Piece destination) {
		// checks if there is a movable piece at that position, of the color that is
		// currently taking a turn
		if (moving == null || !moving.getColor().equals(movingColor)) {
			return false;
		}
		// checks if the destination is of the same color as the moving piece, which
		// would be illegal
		if (destination != null && destination.getColor().equals(movingColor)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a move is a valid move pattern.
	 * 
	 * @param turn        the turn being checked
	 * @param moving      the piece moving on that turn
	 * @param destination the piece at the destination position
	 * @return true if the move is a valid pattern, false if it is not a valid
	 *         pattern
	 */
	private boolean isValidPattern(Turn turn, Piece moving, Piece destination) {
		// gets the MoveRule to use, move rule if destination is null and capture rule
		// if there is apiece at the destination.
		MoveRule moveRule = (destination == null) ? moving.getPieceType().getMoveRule()
				: moving.getPieceType().getCaptureRule();
		return moveRule.isValidMove(turn.getCurrent(), turn.getProposed(), movingColor, gameBoard);
	}
}
