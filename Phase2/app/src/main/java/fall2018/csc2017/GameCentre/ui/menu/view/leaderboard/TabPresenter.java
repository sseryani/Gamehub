package fall2018.csc2017.GameCentre.ui.menu.view.leaderboard;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import fall2018.csc2017.GameCentre.R;

class TabPresenter {

    private View view;
    private Fragment frag;
    private String tab;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String[][] entries = new String[10][3];

    private final String LEADERBOARDS = "leaderboards";
    private final String HIGHSCORE = "highScore";


    private static String[] probeHeaders={"Player","Game","Points"};

    TabPresenter(View view, Fragment frag, String tab) {
        this.view = view;
        this.frag = frag;
        this.tab = tab;
    }

    /**
     * Updates the scores for the current game with data from the Firebase Database
     */
    void updateScores() {
        // Fetch from the database
        myRef.child(LEADERBOARDS).child(this.tab).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int[] idArr = {R.id.firstID, R.id.secondID, R.id.thirdID, R.id.forthID, R.id.fifthID,
                        R.id.sixthID, R.id.seventhID, R.id.eighthID, R.id.ninthID, R.id.tenthID};
                int[] nameArr = {R.string.first, R.string.second, R.string.third, R.string.forth,
                        R.string.fifth, R.string.sixth, R.string.seventh, R.string.eighth,
                        R.string.ninth, R.string.tenth};


                int idx = 0;
                for (DataSnapshot entryDataSnapshot : dataSnapshot.getChildren()) {
                    if(idx < idArr.length) {
                        try {
                            TextView place = view.findViewById(idArr[idx]);
                            place.setText(frag.getString(nameArr[idx], "", 0));
                            LeaderboardEntry entry = entryDataSnapshot.getValue(LeaderboardEntry.class);
                            place.setText(frag.getString(nameArr[idx], entry.getUsername(), entry.getScore()));
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        idx++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    /**
     * Updates the text that displays your high score from the database for the current game
     */
    void updatePersonalScore() {
        String ACCOUNTS = "accounts";
        myRef.child(ACCOUNTS).child(Objects.requireNonNull(user).getUid()).child(this.tab).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(user.getUid());
                TextView myScore = view.findViewById(R.id.myScore);
                try {
                    myScore.setText(frag.getString(R.string.myScore, dataSnapshot.child(HIGHSCORE).getValue().toString()));
                } catch (NullPointerException n) {
                    try {
                        Thread.sleep(100);
                        myScore.setText(frag.getString(R.string.myScore, "N/A"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    String[][] fetchData()
    {
        final String game = tab;
        myRef.child(LEADERBOARDS).child(this.tab).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int entry = 0;
                for (DataSnapshot entryDataSnapshot : dataSnapshot.getChildren()) {
                    LeaderboardEntry data = entryDataSnapshot.getValue(LeaderboardEntry.class);
                    assert data != null;
                    entries[entry]= new String[]{data.getUsername(), game,
                            String.valueOf(data.getScore())};
                    entry++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return entries;
    }

    String[] getprobeHeaders() {
        return probeHeaders;
    }
}
