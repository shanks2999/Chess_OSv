package wrapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Servlet_RestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TEST_PassingRandomGameID() throws Exception {
        this.mockMvc.perform(get("/move?id=1234567&oldx=2&oldy=2&newx=3&newy=2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Cannot find the game, start a new one.")));
    }

    @Test
    public void TEST_CheckQuitResponse() throws Exception {
        this.mockMvc.perform(get("/quitgame?id=1234567")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("Game is terminated.")));
    }

    @Test
    public void TEST_NewGameStartingAsWhite() throws Exception {
        this.mockMvc.perform(get("/newgame?iswhite=true")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.objPieceTracking.pieceKilled", is("N/A")));
    }

    @Test
    public void TEST_NewGameStartingAsBlack() throws Exception {
        this.mockMvc.perform(get("/newgame?iswhite=false")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.objPieceTracking", nullValue()));
    }
    @Test
    public void TEST_CheckIdGeneratedFunction() throws Exception {
        this.mockMvc.perform(get("/newgame")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", not(isEmptyString())));
    }
}