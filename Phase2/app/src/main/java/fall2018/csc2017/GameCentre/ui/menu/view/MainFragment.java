package fall2018.csc2017.GameCentre.ui.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.games.matchingGame.view.MatchingGameActivity;
import fall2018.csc2017.GameCentre.games.puzzle.view.DifficultyActivity;
import fall2018.csc2017.GameCentre.games.ttt.view.TTTGameActivity;
import fall2018.csc2017.GameCentre.ui.menu.MenuContract;

public class MainFragment extends Fragment implements MenuContract.View {

    private static final String TAG = MenuActivity.class.getSimpleName();

    LinearLayout puzzle, ttt, matching;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        puzzle = view.findViewById(R.id.puzzle);
        ttt = view.findViewById(R.id.ttt);
        matching = view.findViewById(R.id.matchingGame);

        openMatchingGame();
        openPuzzleGame();
        openTTT();

        return view;
    }

    @Override
    public void openPuzzleGame() {

        puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DifficultyActivity.class));
            }
        });

    }

    @Override
    public void openTTT() {
        ttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TTTGameActivity.class));
            }
        });
    }

    @Override
    public void openMatchingGame() {
        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MatchingGameActivity.class));
            }
        });

    }
}
