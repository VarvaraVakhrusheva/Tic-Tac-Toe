package org.example;

import org.springframework.stereotype.Service;

/**
 * Service layer that handles the game logic for Tic-Tac-Toe.
 * It interacts with the {@link Game} class to manage the game board, process moves,
 * check for a winner, and reset the game state.
 */
@Service
public class GameService {

    /**
     * The current instance of the game being played.
     */
    private Game game = new Game();

    /**
     * Returns the current {@link Game} object, which represents the state of the game.
     *
     * @return the current game instance
     */
    public Game getGame() {
        return game;
    }

    /**
     * Makes a move on the game board for the current player at the specified row and column.
     * The move will only be made if the position is valid (within bounds and empty).
     *
     * @param row the row index (0-2) where the move is being made
     * @param col the column index (0-2) where the move is being made
     * @return true if the move is valid and successfully made, false if the move is invalid
     */
    public boolean makeMove(int row, int col) {
        return game.makeMove(row, col);
    }

    /**
     * Checks if there is a winner on the current game board. A winner is determined if one player has three
     * marks ('X' or 'O') in a row, column, or diagonal.
     *
     * @return the character representing the winner ('X' or 'O') if a player has won, or '-' if there is no winner yet
     */
    public char checkWinner() {
        return game.checkWinner();
    }

    /**
     * Checks if the game board is full, meaning all positions have been filled with no empty spaces remaining.
     *
     * @return true if the board is full, false if there are empty spaces
     */
    public boolean isBoardFull() {
        return game.isBoardFull();
    }

    /**
     * Returns the current state of the game board, represented as a 2D character array.
     *
     * @return a 2D array representing the game board
     */
    public char[][] getBoard() {
        return game.getBoard();
    }

    /**
     * Resets the game by creating a new instance of the {@link Game} class.
     * This clears the current game state and starts a new game.
     */
    public void resetGame() {
        this.game = new Game();
    }
}
