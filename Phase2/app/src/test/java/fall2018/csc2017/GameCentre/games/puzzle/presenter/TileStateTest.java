package fall2018.csc2017.GameCentre.games.puzzle.presenter;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class TileStateTest {
    Map<String, String> moves;
    private TileState tileState = new TileState(moves, 1, "time", "dimensions", "highscore");

    @Test
    public void testGetHighScore() {
        assertEquals("highscore", tileState.getHighScore());
    }

    @Test
    public void testSetHighScore() {
        tileState.setHighScore("new_highscore");
        assertEquals("new_highscore", tileState.getHighScore());
    }

//    @Test
//    public void testGetLatestMoveStr() {
//    }

    @Test
    public void testGetDimensions() {
        assertEquals("dimensions", tileState.getDimensions());
    }

    @Test
    public void testSetDimensions() {
        tileState.setDimensions("new_dimensions");
        assertEquals("new_dimensions", tileState.getDimensions());
    }

//    @Test
//    public void testGetMoves() {
//    }

//    @Test
//    public void testSetMoves() {
//    }

    @Test
    public void testGetUndos() {
        assertEquals(1, tileState.getUndos());
    }

    @Test
    public void testSetUndos() {
        tileState.setUndos(2);
        assertEquals(2, tileState.getUndos());
    }

    @Test
    public void testGetTime() {
        assertEquals("time", tileState.getTime());
    }

    @Test
    public void testSetTime() {
        tileState.setTime("new_time");
        assertEquals("new_time", tileState.getTime());
    }
}