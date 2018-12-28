package game;

/**
 * Class to store a single movement, describing a start and and position. 
 * Intended for use communicating the turn from the player/UI to the game system.
 * @author Brendan Nenninger
 *
 */
public class Turn {
	/**
	 * The start position of the turn
	 */
	private final Position current;
	/**
	 * The final position of the turn
	 */
	private final Position proposed;
	
	/**
	 * Creates a new turn based on current and proposed positions
	 * @param current
	 * @param proposed
	 */
	public Turn(Position current, Position proposed) {
		this.current = current;
		this.proposed = proposed;
	}
	
	/**
	 * Returns the current (starting) position of the piece
	 * @return the current position of the piece
	 */
	public final Position getCurrent() {
		return current;
	}
	
	/**
	 * Returns the proposed (final) position of the piece
	 * @return the proposed position of the piece
	 */
	public final Position getProposed() {
		return proposed;
	}
	
	/**
	 * Returns the Turn as a String, in the format "current proposed"
	 */
	public String toString() {
		return current + " " + proposed;
	}
}
