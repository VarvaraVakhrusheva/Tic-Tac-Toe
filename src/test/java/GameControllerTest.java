import org.example.GameController;
import org.example.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link GameController}.
 * It uses {@link MockMvc} to simulate HTTP requests to the controller's endpoints and verifies the expected responses.
 * It also mocks the {@link GameService} layer to isolate and test the controller logic.
 */
@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    /**
     * Sets up the test environment before each test by mocking the {@link GameService#getBoard()} method.
     */
    @BeforeEach
    void setUp() {
        Mockito.when(gameService.getBoard()).thenReturn(new char[][]{
                {'X', 'O', '-'},
                {'-', 'X', 'O'},
                {'-', '-', 'X'}
        });
    }

    /**
     * Tests the /game/board endpoint to ensure the board is returned correctly.
     * This test simulates an HTTP GET request to /game/board and verifies
     * that the correct board data is returned in JSON format.
     *
     * @throws Exception if the request fails
     */
    @Test
    void testGetBoard() throws Exception {
        mockMvc.perform(get("/game/board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0][0]").value('X'))
                .andExpect(jsonPath("$[1][1]").value('X'))
                .andExpect(jsonPath("$[2][2]").value('X'));
    }

    /**
     * Tests the /game/move endpoint with a valid move.
     * This test simulates an HTTP POST request to /game/move with a valid move and expects a success response.
     *
     * @throws Exception if the request fails
     */
    @Test
    void testMakeMove() throws Exception {
        when(gameService.makeMove(0, 0)).thenReturn(true);
        mockMvc.perform(post("/game/move?row=0&col=0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Move accepted"));
    }

    /**
     * Tests the /game/move endpoint with an invalid move.
     * This test simulates an HTTP POST request to /game/move with an invalid move and expects a bad request response.
     *
     * @throws Exception if the request fails
     */
    @Test
    void testMakeInvalidMove() throws Exception {
        when(gameService.makeMove(0, 0)).thenReturn(false);
        mockMvc.perform(post("/game/move?row=0&col=0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid move"));
    }

    /**
     * Tests the /game/reset endpoint.
     * This test simulates an HTTP POST request to /game/reset and expects a success response indicating
     * that the game has been reset.
     *
     * @throws Exception if the request fails
     */
    @Test
    void testResetGame() throws Exception {
        mockMvc.perform(post("/game/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string("Game reset"));
    }
}
