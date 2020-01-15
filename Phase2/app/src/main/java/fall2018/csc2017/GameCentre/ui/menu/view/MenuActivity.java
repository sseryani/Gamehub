package fall2018.csc2017.GameCentre.ui.menu.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Objects;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.ui.menu.view.leaderboard.LeaderboardFragment;

public class MenuActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelist);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MainFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.mainView)
                    {
                        selectedFragment = new MainFragment();
                    }
                    else if (item.getItemId() == R.id.leaderboards)
                    {
                        selectedFragment = new LeaderboardFragment();
                    }
                    else if (item.getItemId() == R.id.profileMenu)
                    {
                        selectedFragment = new ProfileFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            Objects.requireNonNull(selectedFragment)).commit();

                    return true;
                }
            };
}
