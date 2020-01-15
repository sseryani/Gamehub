package fall2018.csc2017.GameCentre.games.puzzle.presenter;


import android.support.annotation.NonNull;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.GameCentre.games.GameBoard;
import fall2018.csc2017.GameCentre.games.puzzle.presenter.Tile;


/**
 * The sliding tiles board.
 */
public class Board extends GameBoard implements Serializable, Iterable<Tile> {

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles, int numRows, int numCols) {
        super(numRows, numCols);
        this.tiles = new Tile[numRows][numCols];
        createTileBoard(tiles, this.tiles);
    }

    Board(String boardStr, int numRows, int numCols) {
        super(numRows, numCols);
        this.tiles = stringToBoard(boardStr);
    }

    /**
     * Default Constructor
     */
    public Board() {}

    private Tile[][] stringToBoard(String boardStr) {
        Tile[][] tiles = new Tile[numRows][numCols];
        String[] split = boardStr.split(",");
        int x = 0;
        int y = 0;
        for(String id : split) {
            if(y < numRows && x < numCols) {
                tiles[y][x] = new Tile(Integer.parseInt(id), numCols * numRows);
                if (x == numCols - 1) {
                    x = 0;
                    y++;
                } else { x++; }
            }
        }
        return tiles;
    }

    /**
     * fill tiles with tiles.
     *
     * @param t - tiles
     */
    private void createTileBoard(List<Tile> t, Tile[][] setToFill) {

        Iterator<Tile> iterator = t.iterator();
        for (int row = 0; row != super.getNumRows(); row++) {
            for (int col = 0; col != super.getNumCols(); col++) {
                setToFill[row][col] = iterator.next();
            }
        }
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }


    @NonNull
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Tile[] row : this.tiles) {
            for(Tile col : row) {
                str.append(String.valueOf(col.getId() - 1));
                str.append(',');
            }
        }
        return str.toString();
    }

    void exchangeTiles(int row1, int col1, int row2, int col2) {
        Tile tmp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = tmp;

        setChanged();
        notifyObservers();
    }


    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    private class TileIterator implements Iterator<Tile> {
        /**
         * Initial starting row of the 2D Tile Array
         */

        private int row = 0;

        /**
         * Initial starting column of the 2D Tile Array
         */

        private int col = 0;

        /**
         * Current tile object for Iterator.
         */
        private Tile currT;

        @Override
        public boolean hasNext() {
            return row < numRows && col < numCols;
        }

        @Override
        public Tile next() {
            if (!hasNext()) {
                return null;
            }
            while (row < numRows) {
                while (col < numCols) {
                    if (gettingCurrentAndNextTiles()) {
                        return currT;
                    }
                }
                row++;
            }
            return null;
        }

        /**
         * Performs operations in iterating through the 2D array and assigning the next tile
         * object for the Iterable
         *
         * @return whether the operation to assign a current tile and increment the column count
         * is possible
         */

        private boolean gettingCurrentAndNextTiles() {
            Tile currGetTile = getTile(row, col);
            if (currGetTile != null) {
                currT = currGetTile;
                col++;
                reset();
                return true;
            }
            return false;
        }

        /**
         * Checks whether the column tile is the last column tile in the row. Increments the row
         * if condition is satisfied.
         */
        private void reset() {
            if (col == numCols) {
                col = 0;
                row++;
            }
        }

    }

}
