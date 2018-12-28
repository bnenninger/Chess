package main;

/**
 * Stores the value of an index, and allows for easy conversion between zero-based, one-based, and a-based index values. 
 * Intended to handle all conversions between zero-based indexing for arrays and the one- and a-based indexing used by 
 * people and chess algebraic notation.
 * @author Brendan Nenninger
 *
 */
public final class IndexValue {
	
	/*
	 * Value of the object as a zero-based index
	 */
	private final int index;
	
	/**
	 * Creates a new index value based on an integer index
	 * @param index the index
	 * @param zeroBased true if the given index is zero-based, false if one-based
	 */
	public IndexValue(int index, boolean zeroBased) {
		if(zeroBased) {
			this.index = index;
		}
		else {
			this.index = index - 1;
		}
	}
	
	/**
	 * Creates a new index value based on a zero-based integer value
	 * @param indexZeroBased the index, zero-based
	 */
	public IndexValue(int indexZeroBased) {
		this(indexZeroBased, true);
	}
	
	/**
	 * Creates a new index value based on a character
	 * @param indexChar the char value of the index, a-based
	 */
	public IndexValue(char indexChar) {
		this(indexChar - 'a');
	}
	
	/**
	 * returns the zero-based value of the index
	 * @return zero-based index
	 */
	public final int toZeroBasedIndex() {
		return index;
	}
	
	/**
	 * returns the one-based value of the index
	 * @return one-based value
	 */
	public final int toOneBasedIndex() {
		return index + 1;
	}
	
	/**
	 * returns the character value of the index, a-based
	 * @return a-based character
	 */
	public final char toChar() {
		return (char)(index + 'a');
	}
	
	/**
	 * Converts the IndexValue to a string as a zero-based index, in the format IndexValue[<zero-based index>].
	 */
	public String toString() {
		return "IndexValue[" + index + "]";
	}
	
	/**
	 * Compares the IndexValue to another IndexValue/Object
	 */
	public final boolean equals(Object other) {
		if(!(other instanceof IndexValue)) {
			return false;
		}
		IndexValue confirmedIndexValue = (IndexValue)other;
		return confirmedIndexValue.index == this.index;
	}
}
