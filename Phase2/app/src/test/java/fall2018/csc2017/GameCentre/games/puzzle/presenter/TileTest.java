package fall2018.csc2017.GameCentre.games.puzzle.presenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    /**
     * Test the constructor, with 2 arguments.
     */
    @Test
    public void testConstructor() {

        //getBackground() not working?

        Tile t1 = new Tile(1, 16);
        assertNotNull(t1);
        // assertEquals(1, t1.getBackground());
        assertEquals(2, t1.getId());
        Tile t2 = new Tile(4, 16);
        assertNotNull(t2);
        // assertEquals(5, t2.getBackground());
        assertEquals(5, t2.getId());
        assertEquals(3, t1.compareTo(t2));
        assertNotEquals(4, t2.compareTo(t1));
        Tile t3 = new Tile(4, 16);
        assertNotNull(t3);
        // assertEquals(5, t3.getBackground());
        assertEquals(5, t3.getId());
        assertEquals(3, t1.compareTo(t3));
        assertEquals(t2.compareTo(t3), t3.compareTo(t2));
    }

}