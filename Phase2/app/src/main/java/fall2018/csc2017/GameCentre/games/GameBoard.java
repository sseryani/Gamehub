package fall2018.csc2017.GameCentre.games;

import android.support.annotation.NonNull;

import java.util.Observable;


abstract public class GameBoard extends Observable {
    /**
     * The number of rows.
     */
    protected int numRows;

    /**
     * The number of rows.
     */
    protected int numCols;


    /**
     * A new board with rows and columns.
     */
    public GameBoard(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    /**
     * Default Constructor
     */
    public GameBoard() {}

    /**
     * Return the number of columns in board.
     *
     * @return the number of columns in board.
     */
    public int getNumCols() {
        return this.numCols;
    }

    /**
     * Return the number of rows in the board.
     *
     * @return the number of rows in board.
     */
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * Return the number of game pieces in the board.
     *
     * @return the number of game pieces in the board.
     */
    public int numPieces() {
        return numRows * numCols;
    }


    @NonNull
    @Override
    abstract public String toString();

}
















