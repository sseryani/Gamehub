package fall2018.csc2017.GameCentre.games;

import android.support.annotation.NonNull;
import android.util.SparseIntArray;
import java.io.Serializable;


abstract public class GamePiece implements Comparable<GamePiece> ,Serializable {

    /**
     * The numbers of game pieces needed  to be mapped.
     */
    protected int boardSize;

    /**
     * The background id to find the game piece image.
     */
    protected int background;

    /**
     * The unique id.
     */
    protected int id;


    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the background
     */
    public GamePiece(int backgroundId, int boardSize) {
        this.boardSize = boardSize;
        this.id = backgroundId + 1;
        SparseIntArray lookup = CreateLookUp(); // Set up the background based on id.
        this.background = lookup.get(id);
    }

    public int compareTo(@NonNull GamePiece o) {
        return o.id - this.id;
    }

    public abstract SparseIntArray CreateLookUp();


    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }
}

