package fall2018.csc2017.GameCentre.games.puzzle.presenter;
import android.util.SparseIntArray;

import java.io.Serializable;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.games.GamePiece;


/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile extends GamePiece implements Serializable {

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the background
     */
    public Tile(int backgroundId, int boardSize) {
        super(backgroundId, boardSize);
    }

    /**
     * Returns a SparseIntArray that maps each tile key to a drawable integer image pointer
     *
     * @return returns an object has a mapped range of ints to images
     */
    public SparseIntArray CreateLookUp() {
        SparseIntArray backgroundIDLookup = new SparseIntArray();
        int[] drawables = new int[]{
                R.drawable.tile_1, R.drawable.tile_2, R.drawable.tile_3, R.drawable.tile_4,
                R.drawable.tile_5, R.drawable.tile_6, R.drawable.tile_7, R.drawable.tile_8,
                R.drawable.tile_9, R.drawable.tile_10, R.drawable.tile_11, R.drawable.tile_12,
                R.drawable.tile_13, R.drawable.tile_14, R.drawable.tile_15, R.drawable.tile_16,
                R.drawable.tile_17, R.drawable.tile_18, R.drawable.tile_19, R.drawable.tile_20,
                R.drawable.tile_21, R.drawable.tile_22, R.drawable.tile_23, R.drawable.tile_24,
                R.drawable.tile_w
        };

        for (int i = 0; i < boardSize - 1; i++) {
            backgroundIDLookup.append(i + 1, drawables[i]);
        }
        backgroundIDLookup.append(boardSize, drawables[24]);
        return backgroundIDLookup;
    }
}
