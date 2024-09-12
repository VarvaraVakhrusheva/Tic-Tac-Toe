import org.example.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for {@link GameService}.
 * This class tests the core functionality of the game logic, such as making moves, checking for a winner,
 * verifying if the board is full, and resetting the game.
 */
class GameServiceTest {

    /**
     * The service instance being tested.
     */
    private GameService gameService;

    /**
     * Initializes the {@link GameService} instance before each test.
     */
    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    /**
     * Tests that a valid move is accepted and the board is updated accordingly.
     * The first move should be made by player 'X' and placed at the correct position on the board.
     */
    @Test
    void testMakeMoveValid() {
        boolean result = gameService.makeMove(0, 0);
        assertTrue(result, "The move should be valid");
        char[][] board = gameService.getBoard();
        assertEquals('X', board[0][0], "The first move should be 'X'");
    }

    /**
     * Tests that an invalid move (attempting to move on an already occupied space) is rejected.
     * The method should return false when trying to place a mark on an already filled cell.
     */
    @Test
    void testMakeMoveInvalid() {
        gameService.makeMove(0, 0);
        boolean result = gameService.makeMove(0, 0);
        assertFalse(result, "The move should be invalid");
    }

    /**
     * Tests that the {@link GameService#checkWinner()} method correctly identifies a winner.
     * Simulates a winning condition for player 'X' and ensures the winner is correctly detected.
     */
    @Test
    void testCheckWinner() {
        gameService.makeMove(0, 0); // X
        gameService.makeMove(1, 0); // O
        gameService.makeMove(0, 1); // X
        gameService.makeMove(1, 1); // O
        gameService.makeMove(0, 2); // X

        char winner = gameService.checkWinner();
        assertEquals('X', winner, "The winner should be 'X'");
    }

    /**
     * Tests that the {@link GameService#isBoardFull()} method correctly identifies a full board.
     * Simulates filling the entire board and ensures the method returns true when the board is full.
     */
    @Test
    void testIsBoardFull() {
        gameService.makeMove(0, 0); // X
        gameService.makeMove(0, 1); // O
        gameService.makeMove(0, 2); // X
        gameService.makeMove(1, 0); // O
        gameService.makeMove(1, 1); // X
        gameService.makeMove(1, 2); // O
        gameService.makeMove(2, 0); // X
        gameService.makeMove(2, 1); // O
        gameService.makeMove(2, 2); // X

        assertTrue(gameService.isBoardFull(), "The board should be full");
    }

    /**
     * Tests that the {@link GameService#resetGame()} method correctly resets the game board.
     * After resetting, all cells in the board should be empty ('-').
     */
    @Test
    void testResetGame() {
        gameService.makeMove(0, 0);
        gameService.resetGame();

        char[][] board = gameService.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals('-', board[i][j], "The board should be reset");
            }
        }
    }
}
