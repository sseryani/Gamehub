package fall2018.csc2017.GameCentre.games.puzzle.presenter;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class TileFirebaseConnection {

    private BoardManager manager;

    // Database Path Variables
    private final String GAME = "slidingTiles";
    private final String ACCOUNTS = "accounts";
    private final String MOVESTR = "moveStr";
    private final String DIMENSIONS = "dimensions";
    private final String UNDOS = "undos";
    private final String MOVES = "moves";
    private final String HIGHSCORE = "highScore";

    // Firebase Variables
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final DatabaseReference userRef = myRef.child(ACCOUNTS).child(user.getUid()).child(GAME);

    TileFirebaseConnection(BoardManager manager) {
        this.manager = manager;
    }

    public boolean canLoad() {
        return (userRef.child(MOVESTR).getKey() != null);
    }

    /**
     * Saves the board manager to the current user's current game data on the database
     * @param manager the BoardManager to be saved
     */
    public void save(BoardManager manager) {
        DatabaseReference userRef = myRef.child(ACCOUNTS).child(user.getUid()).child(GAME);
        // Push all the user data to the database
        if(!canLoad()) { userRef.child(HIGHSCORE).setValue("0"); }
        userRef.child(DIMENSIONS).setValue(Integer.toString(manager.getNumRows()) + "x" + Integer.toString(manager.getNumCols()));
        userRef.child(UNDOS).setValue(manager.getUndos());
        userRef.child(MOVES).setValue(manager.getNumMoves());
    }

    /**
     * Saves the board manager to the current user's current game data on the database
     */
    public void saveRegular() {
        save(manager);
        userRef.child(MOVESTR).push().setValue(manager.getBoard().toString());
    }

    /**
     * Saves the board manager to the current user's current game data on the database.
     * This only happens on the initial save, overwriting previous moveStrings
     */
    public void saveInit() {
        save(manager);
        // Remove any old values
        userRef.child(MOVESTR).removeValue();
        userRef.child(MOVESTR).push().setValue(manager.getBoard().toString());
    }

    /**
     * Loads the current user's latest data once from the current game tab in the database
     */
    public void load() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TileState state = dataSnapshot.getValue(TileState.class);

                String[] split = state.getDimensions().split("x");
                // Enforce the data obtained
                System.out.println("FB String: " + state.getLatestMoveStr());
                manager.setNumMoves(state.getMoves());
                manager.setNumRows(Integer.parseInt(split[0]));
                manager.setNumCols(Integer.parseInt(split[1]));
                manager.createBoard();
                manager.setBoard(new Board(state.getLatestMoveStr(), Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                manager.setUndos(state.getUndos());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    /**
     * Loads the user's previous data from the current game tab in the database
     * @return the TileState associated with all the user's previous data from the database
     * TODO: make it work, should be similar to load
     */
    public TileState loadPrevMoves() {
        final TileState state = new TileState();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TileState currState = dataSnapshot.getValue(TileState.class);
                // Give all the moves, except the last
                //state.setMoves(currState.getMoves().subList(0, currState.getMoves().size() - 2));
                state.setUndos(currState.getUndos());
                state.setDimensions(currState.getDimensions());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return state;
    }

    public void loadUndo() {

    }

    /**
     * Gets score atributes from the database, and calculates the score.
     * @return the current score
     */
    public int getScore() {
        return 3;
//        Map<String, String> moves = loadState.getMoves();
//        // If we havent made any moves yet, score is 0
//        if(moves == null) {
//            return 0;
//        }
//        return moves.size() - 1;
    }

//    private void readDataOnce(final OnGetDataListener listener) {
//        listener.onStart();
//        ValueEventListener event = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                listener.onSuccess(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                listener.onFailed(databaseError);
//            }
//        };
//        userRef.addListenerForSingleValueEvent(event);
//    }
//
//    private interface OnGetDataListener {
//        void onStart();
//        void onSuccess(DataSnapshot ds);
//        void onFailed(DatabaseError err);
//    }
}
