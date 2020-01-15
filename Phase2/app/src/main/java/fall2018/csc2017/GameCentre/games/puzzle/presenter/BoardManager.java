package fall2018.csc2017.GameCentre.games.puzzle.presenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.GameCentre.games.GameBoardManager;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager extends GameBoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * Maximum number of tiles on the board.
     */
    private List<Tile> tiles = new ArrayList<>();

    /**
     * Managing board positions
     * col: describes the column of the board
     * row: describes the row of the board
     * blankIdCol; captures the column position of the blank tile
     * blankIdRow: captures the row position of the blank tile.
     * undos: number of undos left for the board     */
    private int col, row, blankIdCol, blankIdRow, undos, numMoves;

    private TileFirebaseConnection connection;

    /**
     * Manage a new shuffled board of a given size and number of undos.
     */
    public BoardManager(int numRows, int numCols, int undos, int numMoves) {
        super(numRows, numCols);
        createBoard();
        this.board = new Board(tiles, numRows, numCols);
        this.undos = undos;
        this.numMoves = numMoves;
        connection = new TileFirebaseConnection(this);
    }

    /**
     * Returns the number of undos left
     * @return the number of undos left
     */
    public int getUndos() { return undos; }

    /**
     * Return the current board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Set the current board to board.
     *
     * @param board the board to set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    public void setUndos(int undos) {
        this.undos = undos;
    }

    /**
     * Create a Board and shuffle it given the size of the board.
     */
    void createBoard() {
        for (int tileNum = 0; tileNum != (numCols * numRows); tileNum++) {
            tiles.add(new Tile(tileNum, numCols * numRows));
        }
        Collections.shuffle(tiles);
        while (!isSolvable()) {
            Collections.shuffle(tiles);
        }
    }

    /**
     * Return whether the current board is solvable following the conditions from
     * https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     *
     * @return whether the current board is solvable
     */
    boolean isSolvable() {
        int inversions = getInversions(tiles);

        if (numCols % 2 != 0) {
            return inversions % 2 == 0;
        } else {
            if (blankIdRow % 2 == 0) {
                return inversions % 2 == 0;
            } else {
                return inversions % 2 != 0;
            }
        }
    }

    /**
     * Return the number of inversions in this board
     *
     * @return the number of inversions in this board
     */
    private int getInversions(List<Tile> tiles) {
        int inversions = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(i).getId() > tiles.get(j).getId()) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    /**
     * Gets the current score for the game
     * @return the score
     */
    public int getScore() { return this.numMoves; }

    /**
     * Loads the current board to the database
     */
    public void load() {
        // Load most recent data
        connection.load();
    }

    /**
     * Undos our current movement
     */
    public void undo() {
        // TODO: Add error handling, ie: this is the first state, and no more undos
        connection.loadUndo();
    }

    /**
     * Saves the current board data to the database
     */
    public void save() {
        if(getScore() > 0) {
            connection.saveRegular();
        } else {
            connection.saveInit();
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        int count = 1;
        for (Tile curr : board) {
            int currentID = curr.getId();
            if (currentID != count || currentID > board.numPieces()) {
                solved = false;
                break;
            }
            count++;
        }

        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        getBoardPositions(position);
        if (checkBlankTile(row == 0 ? null : board.getTile(row - 1, col))) {
            return prepareForSwap(col, row - 1);
        }
        if (checkBlankTile(row == numRows - 1 ? null : board.getTile(row + 1, col))) {
            return prepareForSwap(col, row + 1);
        }
        if (checkBlankTile(col == 0 ? null : board.getTile(row, col - 1))) {
            return prepareForSwap(col - 1, row);
        }
        if (checkBlankTile(col == numCols - 1 ? null : board.getTile(row, col + 1))) {
            return prepareForSwap(col + 1, row);
        }
        return false;
    }

    /**
     * Prepares for swapping by recording the location of the blank tile.
     *
     * @param emptyCol the tile to check
     * @param emptyRow shiftedCol the tile to check
     * @return true by default
     */
    private boolean prepareForSwap(int emptyCol, int emptyRow) {
        blankIdCol = emptyCol;
        blankIdRow = emptyRow;
        return true;
    }

    public void setNumMoves(int numMoves) {
        this.numMoves = numMoves;
    }

    public int getNumMoves() {
        return numMoves;
    }

    /**
     * Returns whether or not the current tile is the blank tile
     *
     * @param tileToCheck the tile to check
     * @return whether the tile provided is the blank tile
     */
    private boolean checkBlankTile(Tile tileToCheck) {
        return tileToCheck != null && tileToCheck.getId() == getBlankID();
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        getBoardPositions(position);
        if (isValidTap(position)) {
            board.exchangeTiles(row, col, blankIdRow, blankIdCol);
            this.numMoves++;
        }
    }

    /**
     * Calculates the row and column given the current position.
     *
     * @param position the position
     */
    private void getBoardPositions(int position) {
        row = position / numRows;
        col = position % numCols;
    }

    /**
     * Returns the Blank Tiles ID.
     */

    private int getBlankID() {
        return board.numPieces();
    }

}





