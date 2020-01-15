package fall2018.csc2017.GameCentre.games.puzzle.presenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class old_BoardManagerTest {

    /**
     * The board manager for testing.
     */
    private BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
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
    private List<Tile> randomMakeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 4 * 4;
        int[] unorderedTiles = {13, 15, 1, 5, 8, 2, 12, 10, 14, 3, 4, 7, 11, 0, 6, 9};
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(unorderedTiles[tileNum] + 1, numTiles));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles, 4, 4);
        boardManager = new BoardManager(4, 4, 5);
        boardManager.setBoard(board);
    }

    /**
     * Make an unsolved Board.
     */
    private void setUpIncorrect() {
        List<Tile> tiles = randomMakeTiles();
        Board board = new Board(tiles, 4, 4);
        boardManager = new BoardManager(4, 4, 5);
        boardManager.setBoard(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().exchangeTiles(0, 0, 0, 1);
    }

    /**
     * Test whether board constructor functions as planned.
     */
    @Test
    public void boardConstructor() {
        setUpIncorrect();
        assertFalse(boardManager.puzzleSolved());
        assertEquals(4, boardManager.getBoard().getNumCols());
        assertEquals(4, boardManager.getBoard().getNumRows());
        assertEquals(16, boardManager.getBoard().getNumCols() *
                boardManager.getBoard().getNumCols());
        assertEquals(14, boardManager.getBoard().getTile(0, 0).getId());
        assertTrue(boardManager.getBoard().iterator().hasNext());
    }

    /**
     * Test whether the iterator is working properly.
     */
    @Test
    public void iteratorTest() {
        setUpIncorrect();
        Iterator<Tile> iter = boardManager.getBoard().iterator();
        assertTrue(iter.hasNext());
        assertEquals(0, iter.next().getBackground());
        assertEquals(16, iter.next().getId());
        int[] unorderedTiles = {13, 15, 1, 5, 8, 2, 12, 10, 14, 3, 4, 7, 11, 0, 6, 9};
        int i = 0;
        for (Tile T : boardManager.getBoard()) {
            assertEquals(T.getId(), unorderedTiles[i] + 1);
            assertEquals(T.getBackground(), i);
            i++;
            iter.next();
        }
        assertTrue(!iter.hasNext());
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether, starting from a completely random board, we can achieve a win.
     */
    @Test
    public void randomTestIsSolved() {
        setUpIncorrect();
        assertFalse(boardManager.puzzleSolved());
    }


    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().exchangeTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether BoardManager methods work with a number of inputs*
     */
    @Test
    public void randomSwaps() {
        setUpCorrect();
        boardManager.getBoard().exchangeTiles(0, 0, 3, 3); // Swap blank and 1
        assertEquals(16, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(3, 3).getId());
        assertTrue(boardManager.isValidTap(1));
        assertTrue(boardManager.isValidTap(4));
        assertFalse(boardManager.isValidTap(0));
        assertFalse(boardManager.isValidTap(2));
        assertFalse(boardManager.isValidTap(15));
        assertFalse(boardManager.puzzleSolved());
        boardManager.getBoard().exchangeTiles(0, 0, 3, 3);
        assertTrue(boardManager.puzzleSolved());
        assertFalse(boardManager.isValidTap(0));
        assertFalse(boardManager.isValidTap(1));
        assertTrue(boardManager.isValidTap(14));
        assertTrue(boardManager.isValidTap(11));
        assertEquals(4, boardManager.getNumRows());
        assertNotEquals(3, boardManager.getNumRows());
        boardManager.getBoard().exchangeTiles(2, 1, 1, 2);
        assertFalse(boardManager.puzzleSolved());
        assertEquals(4, boardManager.getNumRows());
        assertNotEquals(3, boardManager.getNumRows());
        boardManager.getBoard().exchangeTiles(2, 1, 1, 2);
        assertTrue(boardManager.puzzleSolved());
    }

    /**
     * Test whether Board methods work
     */
    @Test
    public void boardMethods() {
        setUpCorrect();
        assertNotNull(boardManager.getBoard());
        assertEquals(4, boardManager.getBoard().getNumCols());
        assertEquals(4, boardManager.getBoard().getNumRows());
        assertNotEquals(5, boardManager.getBoard().getNumCols());
        assertNotEquals(3, boardManager.getBoard().getNumCols());
        assertEquals(4, boardManager.getBoard().getNumRows());
    }


    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().exchangeTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(boardManager.isValidTap(11));
        assertTrue(boardManager.isValidTap(14));
        assertFalse(boardManager.isValidTap(10));
    }

    @Test
    public void testSwap() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().exchangeTiles(0, 0, 0, 0);
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
    }
}