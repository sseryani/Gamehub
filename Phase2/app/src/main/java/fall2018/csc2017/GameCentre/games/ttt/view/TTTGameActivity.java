package fall2018.csc2017.GameCentre.games.ttt.view;

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
import fall2018.csc2017.GameCentre.games.timer.model.TimerModel;
import fall2018.csc2017.GameCentre.games.timer.presenter.TimerPresenter;
import fall2018.csc2017.GameCentre.games.timer.view.TimerView;
import fall2018.csc2017.GameCentre.games.ttt.model.CardSetModelTTT;
import fall2018.csc2017.GameCentre.games.ttt.presenter.TTTSetManager;

public class TTTGameActivity extends AppCompatActivity implements Observer {
    /**
     * The board manager.
     */
    private TTTSetManager tttSetManager;

    /**
     * The nxn dimensions of the grid.
     */
    private int dimensions;

    /**
     * The buttons to display.
     */
    private ArrayList<TTTCardView> tileButtons;

    /**
     * The gesture detector
     */
    private GestureDetectGridView gridView;

    /**
     * column width and height based on device size
     */
    private static int columnWidth, columnHeight;

    private Timer timer;

    TimerPresenter tp;


    public void display() {
        if (this.tttSetManager.puzzleSolved()) {
            timer.cancel();
            tp.stop();
        }
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dimensions = 3;
        createTileButtons(this);
        tttSetManager = new TTTSetManager(3, 3, this.tileButtons) {
        };
        setContentView(R.layout.activity_ttt_game);
        setUpGrid();
        setUpTimer();
    }

    private void setUpGrid() {
        // Add View to activity_tiles_scores
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(tttSetManager.getNumCols());
        gridView.setBoardManager(tttSetManager);
        tttSetManager.getCardSetModel().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function

        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / tttSetManager.getNumCols();
                        columnHeight = displayHeight / tttSetManager.getNumRows();

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

        // Setup Presenter
        timer = new Timer();
        tp = new TimerPresenter(timerView, ViewModelProviders.of(this).get(TimerModel.class));
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

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        tileButtons = new ArrayList<>();
        for (int row = 0; row != dimensions; row++) {
            for (int col = 0; col != dimensions; col++) {
                TTTCardView tmp = new TTTCardView(context);
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        CardSetModelTTT board = tttSetManager.getCardSetModel();
        int nextPos = 0;
        for (TTTCardView b : tileButtons) {
            int row = nextPos / tttSetManager.getNumRows();
            int col = nextPos % tttSetManager.getNumCols();
            if ((b.tttPresenter.isSet())) {
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
