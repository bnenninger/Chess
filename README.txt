Chess
Brendan Nenninger
V0.1 unfinished

Functionality:
	Console input, Console output.
	Plays a chess game between two people using hotseat multiplayer. Does not allow for any storage of a game.

Definitions:
	current position: the position of a piece before it moves.
	proposed position: the suggested new position of a piece. This is the position proposed by the player, and is not considered "new" both because "new" is a keyword in Java and because the move may not be allowed.
	existing move: a move that is not fundamentally impossible. Examples on nonexistent moves are moves from a position with no piece, moves of the other color's piece, and moves that capture a same-colored piece.
	valid move pattern: a move that is consistent with the rules that govern the piece moving. Example: a diagonal move for a bishop is a valid move pattern, a horizontal move is not.
	legal move: a move that does not lead to an illegal condition for their color, such as a move that exposes the king to check.
	quickmove: A temporary move that does not change the state of a PlayingBoard object. This system is used to verify that a player is not moving themself into check. A quickmove is required to perform this verification because a piece could move in such a way that they continue to block an opposing piece from checking the king, so simply removing the piece would prohibit legal moves. This also avoids altering the state of the PlayingBoard, which could cause problems for future versions of the program that continuously display the board.

Move handling:
	take player input
	parse into a move
	check if the move is valid pattern
		call isValidMove
	check if move is legal
		create a quickmove
		verify king not in check
	if both valid pattern and legal, make the move
		call PlayingBoard.move
	if move is somehow disallowed, return to asking for user input
	find information required to store and report the move properly
	find whether opposing king is in check, store
	if the opposing king is in check, find if it is in checkmate
		cases:
		king can move out of check, not checkmate
		if one piece checking king, that can be blocked, not checkmate
		if more than one piece check the king, both cannot be blocked, is checkmate.
		Note on algorithm to detect the number of pieces threatening the king:
			The number of threats to the king is guaranteed to be 0-2, inclusive. The cap at two pieces threatening the king is caused by the fact that three pieces would require two separate moves to put the king in check, meaning that the opposing player left their king in check, which would make their move not legal. Two pieces could be exposed to check the king if one piece moved to expose the other, and both ended checking the king.
	Store the piece moved, the piece captured (if any), whether the move was check, and whether the move was checkmate.
	If the move was checkmate, end the game and declare the player that made the move the winner.	
