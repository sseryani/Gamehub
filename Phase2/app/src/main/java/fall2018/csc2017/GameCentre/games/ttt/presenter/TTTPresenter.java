package fall2018.csc2017.GameCentre.games.ttt.presenter;


import android.support.annotation.NonNull;
import android.util.SparseIntArray;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.games.GamePiece;
import fall2018.csc2017.GameCentre.games.ttt.view.TTTCardView;
import fall2018.csc2017.GameCentre.games.ttt.model.CardModelTTT;

/**
 * A Presenter class for a card.
 */
public class TTTPresenter extends GamePiece {

    /**
     * A CardModelTTT, to store and retrieve information.
     */
    private CardModelTTT cardModelTTT;

    /**
     * A TTTCardView, to display the card to the user
     */
    private TTTCardView TTTCardView;


    /**
     * Create a TTTPresenter with a background id.
     */
    TTTPresenter(int boardSize, @NonNull final TTTCardView TTTCardView) {
        super(0, boardSize);
        SparseIntArray pics = CreateLookUp();
        cardModelTTT = new CardModelTTT(pics.get(1), pics.get(2));
        this.TTTCardView = TTTCardView;
        this.TTTCardView.setPresenter(this);
        this.TTTCardView.setBackgroundResource(R.drawable.tap_w);
    }

    /**
     * Decide what happens to the card after it has been clicked. If Card is matched, do nothing.
     */
    public void flip(boolean playerOne) {
        if (!cardModelTTT.isSet()) {
            cardModelTTT.setFlipped(playerOne);
            if (playerOne) {
                TTTCardView.setBackgroundResource(getXCard());
            } else {
                TTTCardView.setBackgroundResource(getOCard());
            }
        }

    }

    @Override
    public int getBackground() {
        return cardModelTTT.getBackground();
    }

    private int getXCard(){
        return cardModelTTT.getXCard();
    }

    private int getOCard() {
        return cardModelTTT.getOCard();
    }

    public boolean isSet() {
        return this.cardModelTTT.isSet();
    }


    /**
     * Returns a SparseIntArray that maps each card key to a drawable integer image pointer
     *
     * @return returns an object has a mapped range of ints to images
     */
    public SparseIntArray CreateLookUp() {
        SparseIntArray backgroundIDLookup = new SparseIntArray();
        int[] drawables = new int[]{R.drawable.tap_x, R.drawable.tap_o};

        for (int i = 0; i < drawables.length; i++) {
            backgroundIDLookup.append(i + 1, drawables[i]);
        }
        return backgroundIDLookup;
    }

}

