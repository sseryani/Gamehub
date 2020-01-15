package fall2018.csc2017.GameCentre.games.matchingGame.presenter;


import android.support.annotation.NonNull;
import android.util.SparseIntArray;


import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.games.GamePiece;
import fall2018.csc2017.GameCentre.games.matchingGame.view.CardView;
import fall2018.csc2017.GameCentre.games.matchingGame.model.CardModel;

/**
 * A Presenter class for a card.
 */
public class CardPresenter extends GamePiece {

    /**
     * A CardModel, to store and retrieve information.
     */
    private CardModel cardModel;

    /**
     * A CardView, to display the card to the user
     */
    private CardView cardView;


    /**
     * Create a CardPresenter with a background id.
     *
     * @param backgroundId the background id, to assign a Drawable resource.
     */
    CardPresenter(int backgroundId, int boardSize, @NonNull final CardView cardView) {
        super(backgroundId, boardSize);
        cardModel = new CardModel(CreateLookUp().get(backgroundId));
        this.cardView = cardView;
        this.cardView.setPresenter(this);
        this.cardView.setBackgroundResource(R.drawable.card_w);
    }

    /**
     * Decide what happens to the card after it has been clicked. If Card is matched, do nothing.
     * If it is flipped, then show the back card. If it is not flipped, then show the front image.
     */
    public void flip() {
        if (!cardModel.isFlipped() && !cardModel.isMatched()) {
            cardModel.setFlipped(true);
            cardView.setBackgroundResource(getBackground());
        } else if (cardModel.isFlipped() && !cardModel.isMatched()) {
            cardModel.setFlipped(false);
            cardView.setBackgroundResource(R.drawable.card_w);
        }

    }

    @Override
    public int getBackground() {
        return cardModel.getFrontImage();
    }

    boolean isMatched() {
        return this.cardModel.isMatched();
    }

    public boolean isFlipped() {
        return this.cardModel.isFlipped();
    }

    /**
     * The Card has been matched.
     */
    void setMatched() {
        this.cardModel.setMatched(true);
    }

    /**
     * Returns a SparseIntArray that maps each card key to a drawable integer image pointer
     *
     * @return returns an object has a mapped range of ints to images
     */
    public SparseIntArray CreateLookUp() {
        SparseIntArray backgroundIDLookup = new SparseIntArray();
        int[] drawables = new int[]{R.drawable.card_1, R.drawable.card_2,
                R.drawable.card_3, R.drawable.card_4, R.drawable.card_5, R.drawable.card_6,
                R.drawable.card_7, R.drawable.card_8, R.drawable.card_9,
                R.drawable.card_10, R.drawable.card_11, R.drawable.card_12, R.drawable.card_13,
                R.drawable.card_14, R.drawable.card_15};

        for (int i = 0; i < drawables.length; i++) {
            backgroundIDLookup.append(i + 1, drawables[i]);
        }
        return backgroundIDLookup;
    }

}

