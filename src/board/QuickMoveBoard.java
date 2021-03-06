package board;

import java.util.List;

import game.PieceDetailTurn;
import game.Position;
import game.Turn;

/**
 * Class for temporarily storing a board that is modified by one move in such a
 * way that it does not affect the state of the original board. Intended to be
 * used for the quickmove system, which creates a temporary version of the board
 * so that it can be evaluated to determine whether a player puts themself into
 * check in a turn. This is because there are cases where a piece moves so that
 * it is still blocking the king, so removing it would show the king to be in
 * check despite it still being blocked.
 * 
 * @author Brendan Nenninger
 *
 */
public class QuickMoveBoard extends Board {

	private Board modifiedBoard;
	private PieceDetailTurn quickMove;

	public QuickMoveBoard(Board modifiedBoard, PieceDetailTurn quickMove) {
		this.modifiedBoard = modifiedBoard;
		this.quickMove = quickMove;
	}

	public QuickMoveBoard(Board modifiedBoard, Turn quickMove) {
		this.modifiedBoard = modifiedBoard;
		Piece moved = modifiedBoard.getPosition(quickMove.getCurrent());
		Piece captured = modifiedBoard.getPosition(quickMove.getProposed());
		this.quickMove = new PieceDetailTurn(quickMove, moved, captured);
	}

	@Override
	public Piece getPosition(Position position) {
		// If the position is equal to the original position of the move, then it is
		// empty following the move. Return null.
		if (quickMove.getCurrent().equals(position)) {
			return null;
		}
		// If the position is equal to the proposed position of the quickmove, then it
		// is now occupied by the piece moved in the quickmove.
		// Return the wrapper object QuickMovePiece that reflects the updated position
		// of the piece, but does not modify the original piece.
		if (quickMove.getProposed().equals(position)) {
			return new QuickMovePiece(quickMove.getPiece(), position);
		}
		// In all other cases, return like normal
		return modifiedBoard.getPosition(position);
	}

	@Override
	public List<Piece> getPieceList(ChessColor color) {
		List<Piece> pieces = modifiedBoard.getPieceList(color);
		// Case for if the list retrieved is NOT of the moved color
		if (quickMove.getPiece().getColor() != color) {
			// if there is no captured piece, the list of unmoved pieces remains the same
			if (quickMove.getCaptured() == null) {
				return pieces;
			}
			// otherwise, there is a captured piece, so that piece must be removed from the
			// list
			pieces.remove(quickMove.getCaptured());
			return pieces;
		}

		// all other cases are requests for the color of the moving piece
		// need to replace the moving piece in the array with a QuickMovePiece
		// reflecting the new position
		pieces.set(pieces.indexOf(quickMove.getPiece()),
				new QuickMovePiece(quickMove.getPiece(), quickMove.getProposed()));
		return pieces;
	}

	@Override
	public Piece getKing(ChessColor color) {
		// if the king was not moved, can just return it unmodified
		Piece king = modifiedBoard.getKing(color);
		// if the color of the king to return is different from the moved piece, the
		// king cannot have moved. Returns the king without modification.
		if (quickMove.getPiece().getColor() != color) {
			return king;
		}
		// returns a modified king if it was the king that moved
		// otherwise, returns the king unmodified
		return quickMove.getPiece().getPieceType().equals(PieceType.KING)
				? new QuickMovePiece(king, quickMove.getProposed())
				: king;
	}
}
