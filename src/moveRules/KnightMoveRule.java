package moveRules;

import board.Board;
import board.ChessColor;
import game.Position;

/**
 * Rule for the movement of a piece that travels a particular, distinct distance along two different dimensions. 
 * Does not account for collisions with other pieces. 
 * Used for the movement of knights in chess. 
 * @author Brendan Nenninger
 *
 */
class KnightMoveRule extends MoveRule {

	//stores the maximum distance the piece can travel in each direction 
	private final int largeDimension;
	private final int smallDimension;
	
	/**
	 * Creates a rule for a piece that moves a distinct distance in two dimensions, and can jump other pieces
	 * @param distanceA the distance the piece can travel in one dimension
	 * @param distanceB the distance the piece can travel in the other dimension
	 */
	public KnightMoveRule(int distanceA, int distanceB) {
		this.largeDimension = Math.max(distanceA, distanceB);
		this.smallDimension = Math.min(distanceA, distanceB);
	}
	
	@Override
	public boolean isValidPieceMove(Position current, Position proposed, ChessColor color, Board board) {
		int rowDistance = Math.abs(proposed.getRow() - current.getRow());
		int columnDistance = Math.abs(proposed.getColumn() - current.getColumn());
		return Math.max(rowDistance, columnDistance) == largeDimension
				&& Math.min(rowDistance, columnDistance) == smallDimension;
	}

	@Override
	public Position[] getIntermediaryPositions(Position current, Position proposed, ChessColor color) {
		return new Position[0];//Returns a blank array, as the knight is not blocked by pieces in the way.
	}
}
