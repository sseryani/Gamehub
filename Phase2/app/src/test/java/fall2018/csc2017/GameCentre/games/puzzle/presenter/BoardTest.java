package fall2018.csc2017.GameCentre.games.puzzle.presenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> setUpOrderedTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 4 * 4;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, numTiles));
        }

        return tiles;
    }

    /**
     * Make a set of tiles that are not in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> setUpUnorderedTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 4 * 4;
        int[] unorderedTiles = {13, 15, 1, 5, 8, 2, 12, 10, 14, 3, 4, 7, 11, 0, 6, 9};
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(unorderedTiles[tileNum] + 1, numTiles));
        }

        return tiles;
    }

    /*
    BEGIN TESTS
     */

    /**
     *
     */
    @Test
    public void TestConstructorWithListOfTiles() {
        List<Tile> unorderedTiles = setUpUnorderedTiles();
        Board testBoard = new Board(unorderedTiles, 4, 4);

        assertEquals(4, testBoard.getNumRows());
        assertEquals(4, testBoard.getNumCols());
        assertEquals(unorderedTiles.get(0), testBoard.getTile(0, 0));
    }

    /**
     *
     */
    @Test
    public void TestConstructorWithBoardString() {

    }

    /**
     *
     */
    @Test
    public void testGetTile() {
        List<Tile> unorderedTiles = setUpUnorderedTiles();
        Board testBoard = new Board(unorderedTiles, 4, 4);
        assertEquals(unorderedTiles.get(0), testBoard.getTile(0, 0));
        // add more?
    }

    /**
     *
     */
    @Test
    public void testExchangeTiles() {
        Board testBoard = new Board(setUpOrderedTiles(), 4, 4);
        assertEquals(1, testBoard.getTile(0, 0).getId());
        assertEquals(2, testBoard.getTile(0, 1).getId());
        testBoard.exchangeTiles(0, 0, 0, 1);
        assertEquals(2, testBoard.getTile(0, 0).getId());
        assertEquals(1, testBoard.getTile(0, 1).getId());
    }

    /**
     *
     */
    @Test
    public void testIterator() {
        Board testBoard = new Board(setUpUnorderedTiles(), 4, 4);
        Iterator<Tile> iter = testBoard.iterator();
        assertTrue(iter.hasNext());
        assertEquals(0, iter.next().getBackground());
        assertEquals(16, iter.next().getId());
        int[] unorderedTiles = {13, 15, 1, 5, 8, 2, 12, 10, 14, 3, 4, 7, 11, 0, 6, 9};
        int i = 0;
        for (Tile T : testBoard) {
            assertEquals(T.getId(), unorderedTiles[i] + 1);
            assertEquals(T.getBackground(), i);
            i++;
            iter.next();
        }
        assertTrue(!iter.hasNext());
    }
}