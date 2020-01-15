package fall2018.csc2017.GameCentre.games.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.games.CustomAdapter;
import fall2018.csc2017.GameCentre.games.GestureDetectGridView;
import fall2018.csc2017.GameCentre.games.puzzle.presenter.Board;
import fall2018.csc2017.GameCentre.games.puzzle.presenter.BoardManager;
import fall2018.csc2017.GameCentre.games.timer.model.TimerModel;
import fall2018.csc2017.GameCentre.games.timer.presenter.TimerPresenter;
import fall2018.csc2017.GameCentre.games.timer.view.TimerView;

/**
 * The game activity_tiles_scores.
 */

//Todo Rework this class entirely, it's bloated

public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

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


    /**
     * Updates the on screen score to match the actual score
     */
    private void updateScore() {
        TextView text = findViewById(R.id.autoCompleteTextView);
        text.setText(getString(R.string.move_counter, boardManager.getScore()));
    }

    private void updateUndos() {
        TextView text = findViewById(R.id.UndoButton);
        text.setText(getString(R.string.undo, boardManager.getUndos()));
    }

    /**
     * Display everything on screen based on what we currently have access to
     */
    public void display() {
        updateTileButtons();
        updateScore();
        updateUndos();
        boardManager.save();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the board manager made in Difficulty activity
        Intent intent = getIntent();
        if(intent.getBooleanExtra("load", false)) {
            // Create a dummy boardManager to avoid a null pointer, then load the values from the database
            boardManager = new BoardManager(3, 3, 5, 0);
            System.out.println("Dummy: " + boardManager.getBoard().toString());
            boardManager.load();
            System.out.println("New: " + boardManager.getBoard().toString());
        } else {
            boardManager = new BoardManager(intent.getIntExtra("rows", -1),
                    intent.getIntExtra("cols", -1), intent.getIntExtra("undos", -1), 0);
        }
        createTileButtons(this);
        setContentView(R.layout.activity_main);

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

        // Add View to activity_tiles_scores
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        updateScore();
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getNumCols();
                        columnHeight = displayHeight / boardManager.getNumRows();

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getNumRows(); row++) {
            for (int col = 0; col != boardManager.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getNumRows();
            int col = nextPos % boardManager.getNumCols();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        tp.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
