package org.example;

/**
 * This class represents the Tic-Tac-Toe game. It manages the game board and the current player,
 * handles making moves, checking for a winner, and determining if the board is full.
 */
public class Game {

    /**
     * The 3x3 game board represented as a 2D character array.
     * Empty cells are represented by '-' and filled cells are either 'X' or 'O'.
     */
    private char[][] board;

    /**
     * The current player, either 'X' or 'O'. The player alternates after each valid move.
     */
    private char currentPlayer;

    /**
     * Initializes a new game with an empty board and sets the current player to 'X'.
     */
    public Game() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
        currentPlayer = 'X'; // First player 'X'
    }

    /**
     * Makes a move on the board for the current player.
     * If the move is valid, the board is updated, and the player is switched.
     *
     * @param row the row index for the move (0-2)
     * @param col the column index for the move (0-2)
     * @return true if the move was valid and the board was updated, false otherwise
     */
    public boolean makeMove(int row, int col) {
        if (row < 0 || col < 0 || row >= 3 || col >= 3 || board[row][col] != '-') {
            return false; // Illegal move
        }
        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch player
        return true;
    }

    /**
     * Checks if there is a winner on the board.
     * A winner is determined if one player has three marks in a row, column, or diagonal.
     *
     * @return the character ('X' or 'O') if there is a winner, or '-' if there is no winner
     */
    public char checkWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {
                return board[i][0];
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-') {
                return board[0][i];
            }
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
            return board[0][2];
        }
        // No winner yet
        return '-';
    }

    /**
     * Checks if the board is full, meaning all cells are occupied.
     *
     * @return true if the board is full, false if there are empty cells
     */
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the current state of the board.
     *
     * @return the 2D character array representing the board
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Returns the current player ('X' or 'O').
     *
     * @return the current player
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }
}
