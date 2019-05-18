package main;

import java.util.Scanner;

import game.ActiveGame;
import game.Position;
import game.Turn;

public class Main {
	public static void main(String[] args) {
		playGame();
	}

	/**
	 * Plays a game to completion.
	 */
	public static void playGame() {
		Scanner console = new Scanner(System.in);
		ActiveGame game = new ActiveGame();
		System.out.println(game.getBoardString());
		// plays game until it is finished
		while (!game.isFinished()) {
			playTurn(game, console);
		}
		// displays the winner of the game
		System.out.println(game.getWinner().toString() + " is the winner");
	}

	/**
	 * Adds a single move to a game in progress.
	 * 
	 * @param game    the game to apply the move to
	 * @param console Scanner to take game input from
	 */
	public static void playTurn(ActiveGame game, Scanner console) {
		// boolean for tracking if the user has given an acceptable move
		boolean moveCompleted = false;
		while (!moveCompleted) {
			System.out.print(game.getCurrentPlayer().toString() + " turn: ");
			String currentString = console.next();
			String proposedString = console.next();
			console.nextLine();
			// converts the user input to positions
			Position current = new Position(currentString);
			Position proposed = new Position(proposedString);
			Turn turn = new Turn(current, proposed);
			// attempts to move the board with the turn given by the user
			boolean validMove = game.move(turn);
			// prints the board with the move applied if the move was valid
			if (validMove) {
				System.out.println("\n" + game.getBoardString());
				moveCompleted = true;
			}
			// requests a new move otherwise
			else {
				System.out.println("invalid move, please try again");
				System.out.println(game.getBoardString());
			}
		}
	}
}
