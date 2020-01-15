package fall2018.csc2017.GameCentre.games;

public abstract class GameBoardManager {

    /**
     * The number of rows in the board game.
     */
    protected int numRows;

    /**
     * The number of columns in the board game.
     */

    protected int numCols;

    /**
     * The GameBoard for the Manager
     */
    protected GameBoard board;


    /**
     * Manage a new shuffled set of game pieces of a given size .
     */
    public GameBoardManager(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    /**
     * Default constructor
     */
    public GameBoardManager() {}

    /**
     * Return the number of rows in the board.
     */
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * Return the number of columns in the board.
     */

    public int getNumCols() {
        return this.numCols;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public abstract boolean puzzleSolved();


    public abstract void touchMove(int position);


    public abstract boolean isValidTap(int position);


    /**
     * Returns the current score.
     *
     * @return the current score.
     */
    public abstract int getScore();

}
