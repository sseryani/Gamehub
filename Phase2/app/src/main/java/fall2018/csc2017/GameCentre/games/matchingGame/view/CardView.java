package fall2018.csc2017.GameCentre.games.matchingGame.view;

import android.content.Context;
import android.util.AttributeSet;

import fall2018.csc2017.GameCentre.games.matchingGame.presenter.CardPresenter;

/**
 * A custom CardView for a Matching Cards game.
 */
public class CardView extends android.support.v7.widget.AppCompatButton {

    /**
     * The CardPresenter for this CardView.
     */
    CardPresenter cardPresenter;


    public CardView(final Context context) {
        super(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setPresenter(CardPresenter cardPresenter) {
        this.cardPresenter = cardPresenter;
    }

    /**
     * Flip the card.
     */
    public void flip() {
        cardPresenter.flip();
    }

}