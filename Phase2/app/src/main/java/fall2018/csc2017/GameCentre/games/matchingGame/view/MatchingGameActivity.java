package fall2018.csc2017.GameCentre.games.matchingGame.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.games.CustomAdapter;
import fall2018.csc2017.GameCentre.games.GestureDetectGridView;
import fall2018.csc2017.GameCentre.games.matchingGame.model.CardSetModel;
import fall2018.csc2017.GameCentre.games.matchingGame.presenter.CardSetManager;
import fall2018.csc2017.GameCentre.games.timer.model.TimerModel;
import fall2018.csc2017.GameCentre.games.timer.presenter.TimerPresenter;
import fall2018.csc2017.GameCentre.games.timer.view.TimerView;

public class MatchingGameActivity extends AppCompatActivity implements Observer {


    /**
     * The board manager.
     */
    private CardSetManager cardSetManager;

    private int dimensions;

    /**
     * The buttons to display.
     */
    private ArrayList<CardView> tileButtons;

    /**
     * The gesture detector
     */
    private GestureDetectGridView gridView;

    /**
     * column width and height based on device size
     */
    private static int columnWidth, columnHeight;

    /**
     * A timer to manage the GUI update thread.
     */
    private Timer timer;

    /**
     * A Timer presenter to interact with the TimerView.
     */
    TimerPresenter tp;

    TimerTask updateTask;

    Runnable updateRunnable;


    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dimensions = 4;
        createTileButtons(this);
        cardSetManager = new CardSetManager(4, 4, this.tileButtons) {
        };
        setContentView(R.layout.activity_matching_game);
        setUpGrid();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (CardView cv : tileButtons) {
                    cv.flip();
                }
            }
        }, 3000);
        setUpTimer();
    }

    private void setUpGrid() {
        // Add View to activity_tiles_scores
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(cardSetManager.getNumCols());
        gridView.setBoardManager(cardSetManager);
        cardSetManager.getCardSetModel().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function

        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / cardSetManager.getNumCols();
                        columnHeight = displayHeight / cardSetManager.getNumRows();

                        display();
                    }
                });
    }

    private void setUpTimer() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        TimerView timerView = (TimerView) fragmentManager.findFragmentById(R.id.timerFrag);
        if (timerView == null) {
            timerView = TimerView.newInstance();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.timerFrag, timerView);
            fragmentTransaction.commit();
        }

        startTimer(timerView);
    }

    private void startTimer(TimerView timerView) {
        timer = new Timer();
        tp = new TimerPresenter(timerView, ViewModelProviders.of(this).get(TimerModel.class));
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                tp.updateView();
            }
        };

        updateTask = new TimerTask() {
            @Override
            public void run() {
                if (!cardSetManager.puzzleSolved()) {
                    runOnUiThread(updateRunnable);
                } else {
                    tp.stop();
                    timer.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(updateTask, 12000, 1000);
    }


    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        tileButtons = new ArrayList<>();
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                CardView tmp = new CardView(context);
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        CardSetModel board = cardSetManager.getCardSetModel();
        int nextPos = 0;
        for (CardView b : tileButtons) {
            int row = nextPos / cardSetManager.getNumRows();
            int col = nextPos % cardSetManager.getNumCols();
            if ((b.cardPresenter.isFlipped())) {
                b.setBackgroundResource(board.getCard(row, col).getBackground());
            }
            nextPos++;
        }
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        tp.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        tp.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        tp.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tp.updateView();
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
