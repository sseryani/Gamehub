package fall2018.csc2017.GameCentre.games.puzzle.presenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BoardManagerTest {
    private BoardManager boardManager = new BoardManager(4, 4, 1, 5);

    public BoardManagerTest() {
    }

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
     * Test the constructor on a 3x3 Board for BoardManager.
     */
    @Test
    public void testConstructor3x3() {
        boardManager = new BoardManager(3, 3, 3, 5);
        assertEquals(3, boardManager.getNumRows());
        assertEquals(3, boardManager.getNumCols());
        assertEquals(3, boardManager.getUndos());
        assertEquals(5, boardManager.getNumMoves());
    }

    /**
     * Test the constructor on a 4x4 Board for BoardManager.
     */
    @Test
    public void testConstructor4x4() {
        boardManager = new BoardManager(4, 4, 2, 5);
        assertEquals(4, boardManager.getNumRows());
        assertEquals(4, boardManager.getNumCols());
        assertEquals(2, boardManager.getUndos());
        assertEquals(5, boardManager.getNumMoves());
    }

    /**
     * Test the constructor on a 5x5 Board for BoardManager.
     */
    @Test
    public void testConstructor5x5() {
        boardManager = new BoardManager(5, 5, 1, 5);
        assertEquals(5, boardManager.getNumRows());
        assertEquals(5, boardManager.getNumCols());
        assertEquals(1, boardManager.getUndos());
        assertEquals(5, boardManager.getNumMoves());
    }

    /**
     * Test whether the getBoard() method returns a Board.
     */
    @Test
    public void testGetBoard() {
        assertTrue(Board.class.isInstance(boardManager.getBoard()));
    }

    /**
     * Test whether setBoard() changes the BoardManager's board.
     */
    @Test
    public void testSetBoard() {
        BoardManager testBoardManager = new BoardManager(4, 4, 1, 5);
        Board testBoard = new Board(setUpOrderedTiles(), 4, 4);
        assertNotEquals(testBoardManager.getBoard(), testBoard);
        testBoardManager.setBoard(testBoard);
        assertEquals(testBoardManager.getBoard(), testBoard);
    }

    /**
     * Test whether setNumMoves() changes the number of moves.
     */
    @Test
    public void testSetNumMoves() {
        BoardManager testBoardManager = new BoardManager(4, 4, 1, 5);
        testBoardManager.setNumMoves(10);
        assertEquals(10, testBoardManager.getNumMoves());
    }

    /**
     * Test whether the BoardManager is solved with a solved board.
     */
    @Test
    public void testPuzzleSolvedWithSolvedBoard() {
        Board solvedBoard = new Board(setUpOrderedTiles(), 4, 4);
        boardManager.setBoard(solvedBoard);
        assertTrue(boardManager.puzzleSolved());
    }

    /**
     * Test whether the BoardManager is solved with an unsolved board.
     */
    @Test
    public void testPuzzleSolvedWithUnsolvedBoard() {
        Board unsolvedBoard = new Board(setUpUnorderedTiles(), 4, 4);
        boardManager.setBoard(unsolvedBoard);
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether a solvable board in BoardManager is solvable.
     */
    @Test
    public void testIsSolvableWithSolvableBoard() {
        Board solvableBoard = new Board(setUpUnorderedTiles(), 4, 4);
        boardManager.setBoard(solvableBoard);
        assertTrue(boardManager.isSolvable());
    }

    /**
     * Test whether an unsolvable board in BoardManager is solvable.
     */
    @Test
    public void testIsSolvableWithUnsolvableBoard() {
        Board unsolvableBoard = new Board(setUpOrderedTiles(), 4, 4);
        boardManager.setBoard(unsolvableBoard);
        boardManager.getBoard().exchangeTiles(3, 1, 3, 2); // Swap tile 14 and 15
        assertFalse(boardManager.isSolvable());
    }

    /**
     * Test whether an already solved board in BoardManager is solvable.
     */
    @Test
    public void testIsSolvableWithSolvedBoard() {
        Board solvableBoard = new Board(setUpOrderedTiles(), 4, 4);
        boardManager.setBoard(solvableBoard);
        assertTrue(boardManager.isSolvable());
    }

    /**
     * Test whether the positions given are valid taps.
     */
    @Test
    public void testIsValidTapOnValidTaps() {
        boardManager.setBoard(new Board(setUpOrderedTiles(), 4, 4));
        boardManager.getBoard().exchangeTiles(0, 0, 3, 3);
        assertTrue(boardManager.isValidTap(1));
        //assertTrue(boardManager.isValidTap(4));
        boardManager.getBoard().exchangeTiles(0, 0, 3, 3);
        assertTrue(boardManager.isValidTap(14));
        //assertTrue(boardManager.isValidTap(11));
    }

    /**
     * Test whether the positions given are valid taps.
     */
    @Test
    public void testIsValidTapOnInvalidTaps() {
        boardManager.setBoard(new Board(setUpOrderedTiles(), 4, 4));
        boardManager.getBoard().exchangeTiles(0, 0, 3, 3);
        assertFalse(boardManager.isValidTap(0));
        assertFalse(boardManager.isValidTap(2));
        boardManager.getBoard().exchangeTiles(0, 0, 3, 3);
        assertFalse(boardManager.isValidTap(0));
        assertFalse(boardManager.isValidTap(1));
    }

    /**
     * Test whether the position given is a valid tap.
     */
    @Test
    public void testIsValidTapOnBlankTile() {
        boardManager.setBoard(new Board(setUpUnorderedTiles(), 4, 4));
        assertFalse(boardManager.isValidTap(1)); // Blank tile position ??
    }

    /**
     * Test whether tiles were swapped with a valid tap.
     */
    @Test
    public void testTouchMoveValid() {
        boardManager.setBoard(new Board(setUpUnorderedTiles(), 4, 4));
        int old_id = boardManager.getBoard().getTile(0, 0).getId();
        int old_blank_id = boardManager.getBoard().getTile(0, 1).getId();
        boardManager.touchMove(0);
        assertEquals(old_id, boardManager.getBoard().getTile(0, 1).getId());
        assertEquals(old_blank_id, boardManager.getBoard().getTile(0, 0).getId());
    }

    /**
     * Test whether tiles were swapped with an invalid tap.
     */
    @Test
    public void testTouchMoveInvalid() {
        boardManager.setBoard(new Board(setUpUnorderedTiles(), 4, 4));
        int old_id = boardManager.getBoard().getTile(0, 0).getId();
        int old_blank_id = boardManager.getBoard().getTile(0, 1).getId();
        boardManager.touchMove(1);
        assertEquals(old_id, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(old_blank_id, boardManager.getBoard().getTile(0, 1).getId());
    }
}