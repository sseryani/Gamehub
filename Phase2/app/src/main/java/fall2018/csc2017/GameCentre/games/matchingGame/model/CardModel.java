package fall2018.csc2017.GameCentre.games.matchingGame.model;

import android.arch.lifecycle.ViewModel;

public class CardModel extends ViewModel {

    /**
     * Indicates whether the card is matched.
     */
    private boolean isMatched;

    /**
     * Indicates wbether the card is flipped.
     */
    private boolean isFlipped;

    /**
     * The id to find the corresponding drawable for the front of the card.
     */
    private int frontImage;

    /**
     * Create a new model for a card, with background ID frontImage. It will start flipped, with
     * the front image displaying.
     *
     * @param frontImage The id, to match this card with the corresponding drawable.
     */
    public CardModel(int frontImage) {
        this.isMatched = false;
        this.isFlipped = true;
        this.frontImage = frontImage;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public int getFrontImage() {
        return frontImage;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }
}
