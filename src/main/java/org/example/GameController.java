package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * REST controller that handles the Tic-Tac-Toe game.
 * It manages game moves, board state, and game resets between two instances of the game.
 */
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    private final Random random = new Random();

    @Value("${own.url}")
    private String ownUrl;

    @Value("${opponent.url}")
    private String opponentUrl;

    /**
     * A flag that ensures only one player makes the first move after the game is reset.
     */
    private boolean isFirstMoveAfterReset = true;

    /**
     * Handles a move made by the player on a given row and column.
     *
     * @param row the row index (0-2) where the move is being made
     * @param col the column index (0-2) where the move is being made
     * @return a response indicating whether the move was valid or invalid,
     * and declaring the winner or a draw if applicable
     */
    @PostMapping("/move")
    public ResponseEntity<String> makeMove(@RequestParam("row") int row, @RequestParam("col") int col) {
        if (gameService.makeMove(row, col)) {
            char winner = gameService.checkWinner();
            if (winner != '-') {
                resetGameAfterEnd("Winner is " + winner);
                return ResponseEntity.ok("Winner is " + winner);
            } else if (gameService.isBoardFull()) {
                resetGameAfterEnd("It's a draw!");
                return ResponseEntity.ok("It's a draw!");
            }
            return ResponseEntity.ok("Move accepted");
        } else {
            return ResponseEntity.badRequest().body("Invalid move");
        }
    }

    /**
     * Returns the current game board as a 2D character array.
     *
     * @return the current state of the board
     */
    @GetMapping("/board")
    public ResponseEntity<char[][]> getBoard() {
        return ResponseEntity.ok(gameService.getBoard());
    }

    /**
     * Simulates making a move automatically at fixed intervals.
     * The system randomly selects a valid cell for a move and notifies the opponent.
     * This method is triggered every 3 seconds.
     */
    @Scheduled(fixedDelay = 3000)
    public void playGame() {
        if (!gameService.isBoardFull() && gameService.checkWinner() == '-') {
            if (!isFirstMoveAfterReset) {
                int row, col;
                do {
                    row = random.nextInt(3);
                    col = random.nextInt(3);
                } while (!gameService.makeMove(row, col));
                RestTemplate restTemplate = new RestTemplate();
                try {
                    restTemplate.postForEntity(opponentUrl + "/game/move?row=" + row + "&col=" + col, null, String.class);
                } catch (Exception e) {
                    System.out.println("Error notifying opponent: " + e.getMessage());
                }
            } else {
                isFirstMoveAfterReset = false;  // Skip first move flag after reset
            }
        }
    }

    /**
     * Resets the game after a win or draw and notifies the opponent.
     * This method also ensures that the first move is skipped for the resetting instance.
     *
     * @param message a message indicating the result of the game (win or draw)
     */
    private void resetGameAfterEnd(String message) {
        System.out.println(message + " Resetting game...");
        gameService.resetGame();
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(opponentUrl + "/game/reset", null, String.class);
        } catch (Exception e) {
            System.out.println("Error notifying opponent about reset: " + e.getMessage());
        }
        isFirstMoveAfterReset = true;
    }

    /**
     * Handles a reset request from the opponent.
     * This method resets the game and allows the opponent to make the first move after reset.
     *
     * @return a response confirming the game has been reset
     */
    @PostMapping("/reset")
    public ResponseEntity<String> resetGame() {
        gameService.resetGame();
        isFirstMoveAfterReset = false;
        return ResponseEntity.ok("Game reset");
    }

    /**
     * Redirects the root URL of the controller to the board display.
     *
     * @return a redirect string to the /game/board endpoint
     */
    @GetMapping("/")
    public String redirectToBoard() {
        return "redirect:/game/board";
    }
}
