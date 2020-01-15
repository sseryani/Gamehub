package fall2018.csc2017.GameCentre.ui.menu.view.leaderboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fall2018.csc2017.GameCentre.R;

public class LeaderboardFragment extends Fragment  {

    private static final String TAG = LeaderboardFragment.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View tabView = inflater.inflate(R.layout.leaderboard_fragment, container, false);

        FragmentTabHost tabHost = tabView.findViewById(android.R.id.tabhost);

        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontentData);

        tabHost.addTab(tabHost.newTabSpec("TTT").setIndicator("Tic Tac Toe"), TTTTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("ST").setIndicator("Puzzle"), PuzzleTab.class, null);
        tabHost.addTab(tabHost.newTabSpec("MG").setIndicator("Matching"), MatchingGameTab.class, null);

        return tabView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
