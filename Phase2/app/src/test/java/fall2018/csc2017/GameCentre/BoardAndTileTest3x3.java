//package fall2018.csc2017.GameCentre;
//
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class BoardAndTileTest3x3 {
//    // 3x3 tests
//
//    /**
//     * The board manager for testing.
//     */
//    private BoardManager boardManager;
//
//    /**
//     * Make a set of tiles that are in order.
//     *
//     * @return a set of tiles that are in order
//     */
//    private List<Tile> makeTiles() {
//        List<Tile> tiles = new ArrayList<>();
//        final int numTiles = 3 * 3;
//        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
//            tiles.add(new Tile(tileNum + 1, tileNum));
//        }
//
//        return tiles;
//    }
//
//    /**
//     * Make a solved Board.
//     */
//    private void setUpCorrect() {
//        List<Tile> tiles = makeTiles();
//        Board board = new Board(tiles, 3, 3);
//        boardManager = new BoardManager(board);
//    }
//
//    /**
//     * Shuffle a few tiles.
//     */
//    private void swapFirstTwoTiles() {
//        boardManager.getBoard().swapTiles(0, 0, 0, 1);
//    }
//
//    /**
//     * Test whether swapping two tiles makes a solved board unsolved.
//     */
//    @Test
//    public void testIsSolved() {
//        setUpCorrect();
//        assertTrue(boardManager.puzzleSolved());
//        swapFirstTwoTiles();
//        assertFalse(boardManager.puzzleSolved());
//    }
//
//    /**
//     * Test whether swapping the first two tiles works.
//     */
//    @Test
//    public void testSwapFirstTwo() {
//        setUpCorrect();
//        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
//        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
//        boardManager.getBoard().swapTiles(0, 0, 0, 1);
//        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
//        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
//    }
//
//    public void testSwapRandomTiles() {
//        setUpCorrect();
//
//    }
//
//    /**
//     * Test whether swapping the last two tiles works.
//     */
//    @Test
//    public void testSwapLastTwo() {
//        setUpCorrect();
//        assertEquals(8, boardManager.getBoard().getTile(2, 1).getId());
//        assertEquals(9, boardManager.getBoard().getTile(2, 2).getId());
//        boardManager.getBoard().swapTiles(2, 2, 2, 1);
//        assertEquals(9, boardManager.getBoard().getTile(2, 1).getId());
//        assertEquals(8, boardManager.getBoard().getTile(2, 2).getId());
//    }
//
//    /**
//     * Test whether isValidHelp works.
//     */
//    @Test
//    public void testIsValidTap() {
//        setUpCorrect();
//        //assertTrue(boardManager.isValidTap(3));
//        //assertTrue(boardManager.isValidTap(1));
//        //assertFalse(boardManager.isValidTap(10));
//    }
//
//    @Test
//    public void testSwap() {
//        setUpCorrect();
//        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
//        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
//        boardManager.getBoard().swapTiles(0, 0, 0, 0);
//        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
//        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
//    }
//}
