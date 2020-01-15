package fall2018.csc2017.GameCentre.games.puzzle.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.games.puzzle.presenter.BoardManager;
import fall2018.csc2017.GameCentre.games.puzzle.presenter.TileFirebaseConnection;
import fall2018.csc2017.GameCentre.games.puzzle.presenter.TileState;
import fall2018.csc2017.GameCentre.games.view.GameActivity;

/**
 * The difficulty activity_tiles_scores class
 */

//TODO find out a way template off of 1 xml activity for difficulty but rebase it to meet each game's requirement
public class DifficultyActivity extends AppCompatActivity {

    private LinearLayout easy, okay, difficult, extreme, load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
        createBindings();

        addLoadOnClickListener();
        add3x3OnClickListener();
        add4x4OnClickListener();
        add5x5OnClickListener();
        noUndosOnClickListener();
    }


    private void createBindings()
    {
        load = findViewById(R.id.loadBtn);
        easy = findViewById(R.id.nineTiles);
        okay = findViewById(R.id.sixteenTiles);
        difficult = findViewById(R.id.twentyFiveTiles);
        extreme = findViewById(R.id.thirtySixTiles);
    }

    /**
     * Click listener for the load button
     */
    private void addLoadOnClickListener() {
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true) {
                //if(TileFirebaseConnection.canLoad()) {
                    loadGame(v);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Play a game first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Click listener for the 9 tile difficulty with 5 undo's.
     */
    private void add3x3OnClickListener() {
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginGame(v, 5, 3, 3);
            }
        });
    }

    /**
     * Click listener for the 16 tile difficulty with 3 undo's.
     */
    private void add4x4OnClickListener() {
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginGame(v, 3, 4, 4);
            }
        });
    }

    /**
     * Activate the 5x5 button.
     */
    private void add5x5OnClickListener() {
        difficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginGame(v,1, 5, 5);
            }
        });
    }

    /**
     * Activate 5x5 no undos button
     */
    private void noUndosOnClickListener() {
        extreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginGame(v, 0, 0, 5);
            }
        });
    }

    /**
     * Begin the GameActivity to play the game, telling it to load.
     */
    public void loadGame(View v) {
        Intent game = new Intent(this, GameActivity.class);
        game.putExtra("load", true);
        startActivity(game);
        finish();
    }

    /**
     * Begin the GameActivity to play the game.
     */
    public void beginGame(View v, int undos, int numRows, int numCols) {
        Intent game = new Intent(this, GameActivity.class);
        game.putExtra("undos", undos);
        game.putExtra("cols", numRows);
        game.putExtra("rows", numCols);
        game.putExtra("load", false);
        startActivity(game);
        finish();
    }
}


