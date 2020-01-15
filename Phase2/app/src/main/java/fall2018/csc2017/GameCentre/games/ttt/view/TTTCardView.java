package fall2018.csc2017.GameCentre.games.ttt.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import fall2018.csc2017.GameCentre.games.ttt.presenter.TTTPresenter;

public class TTTCardView extends AppCompatButton {


    TTTPresenter tttPresenter;

    public TTTCardView(final Context context) {
        super(context);
    }

    public TTTCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setPresenter(TTTPresenter tttPresenter) {
        this.tttPresenter = tttPresenter;
    }

    /**
     * Overwrite the card with either X or O depending on move
     */
    public void move(boolean playerOne) {
        tttPresenter.flip(playerOne);
    }
}
