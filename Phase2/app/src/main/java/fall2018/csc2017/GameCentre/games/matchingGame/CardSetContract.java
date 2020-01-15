package fall2018.csc2017.GameCentre.games.matchingGame;


import fall2018.csc2017.GameCentre.games.puzzle.presenter.Board;
import fall2018.csc2017.GameCentre.games.timer.view.BaseTimerView;
import fall2018.csc2017.GameCentre.games.puzzle.presenter.Tile;

/**
 * Outlines the basic methods an interactive GameBoard must have for a matching tiles game.
 */
public interface CardSetContract {

    interface View extends BaseTimerView<Tile> {

    }

    interface Presenter {

        /**
         * Get and return the Board being managed by this presenter.
         */

        Board getBoard();

        /**
         * Get and return the number of rows of the Board being managed by this presenter.
         */

        int getNumRows();

        /**
         * Create a Board, by populating it with Tiles, and shuffle it given the size of the board.
         */

        void createBoard();


        /**
         * Return whether the board is solved, effectively ending the game.
         *
         * @return A boolean value indicating whether the game is over.
         */
        boolean puzzleSolved();


        /**
         * Return whether the tap at position is valid, i.e. the card isn't already matched or
         * opened.
         *
         * @param position An int value specifying the position of the tap on screen.
         * @return A boolean value indicating whether the tap is valid.
         */
        boolean isValidTap(int position);


        /**
         * Process a move at position, making the necessary changes as appropriate
         *
         * @param position An int value specifying the tap position on screen.
         */
        void touchMove(int position);


    }
}
