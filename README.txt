Chess
Brendan Nenninger
V0.1

Definitions:
	current position: the position of a piece before it moves.
	proposed position: the suggested new position of a piece. This is the position proposed by the player, and is not considered "new" both because "new" is a keyword in Java and because the move may not be allowed.
	valid move pattern: a move that is consistent with the rules that govern the piece moving. Example: a diagonal move for a bishop is a valid move pattern, a horizontal move is not.
	legal move: a move that does not lead to an illegal condition for their color, such as a move that exposes the king to check.

Console input, Console output

Move handling
take player input
parse into a move
check if the move is valid pattern
	call isValidMove
check if move is legal
make the move
	call PlayingBoard.move
