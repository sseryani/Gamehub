package fall2018.csc2017.GameCentre.games;

import android.content.Context;
import android.widget.Toast;


/**
 * The movement controller class
 */
class MovementController {

    private GameBoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(GameBoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
