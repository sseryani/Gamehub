package fall2018.csc2017.GameCentre.games.ttt.model;

import android.arch.lifecycle.ViewModel;


public class CardModelTTT extends ViewModel {


    /**
     * Indicates whether the card is set.
     */
    private boolean isSet;

    /**
     * An id referring to a drawable resource representing x.
     */
    private final int xCard;

    /**
     * An id referring to a drawable resource representing o.
     */
    private final int oCard;


    /**
     * The id for the picture set on Card (X or O?)
     */
    private int background;

    /**
     * Create a new model for a card, which will start not flipped (i.e. white background).
     * Model will hold both x and o drawables.
     *
     * @param xCard, An ID for a drawable resource representing X.
     * @param oCard, An ID for a drawable resource representing O.
     */
    public CardModelTTT(int xCard, int oCard) {
        this.isSet = false;
        this.xCard = xCard;
        this.oCard = oCard;
        this.background = 0;
    }

    public boolean isSet() {
        return isSet;
    }

    public int getXCard() {
        return xCard;
    }

    public int getOCard() {
        return oCard;
    }

    public int getBackground() {
        return background;
    }

    public void setFlipped(boolean playerOne) {
        isSet = true;
        if (playerOne) {
            background = xCard;
        } else {
            background = oCard;
        }
    }
}
