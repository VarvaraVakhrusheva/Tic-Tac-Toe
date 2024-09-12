package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for the Tic-Tac-Toe application.
 * This class bootstraps the Spring Boot application and enables scheduling for automatic game moves.
 *
 * <p>
 * The {@link EnableScheduling} annotation is used to enable scheduling functionality in the application,
 * allowing timed tasks, such as automatic moves, to be executed.
 * </p>
 */
@SpringBootApplication
@EnableScheduling
public class TicTacToeApplication {

    /**
     * The main method serves as the entry point for the Spring Boot application.
     * It launches the application by invoking {@link SpringApplication#run(Class, String...)}.
     *
     * @param args command-line arguments (optional)
     */
    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApplication.class, args);
    }
}
