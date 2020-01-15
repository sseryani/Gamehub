package fall2018.csc2017.GameCentre.games.ttt.presenter;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCentre.games.GameBoardManager;
import fall2018.csc2017.GameCentre.games.ttt.model.CardSetModelTTT;
import fall2018.csc2017.GameCentre.games.ttt.view.TTTCardView;

public class TTTSetManager extends GameBoardManager {

    /**
     * The set of cards being managed.
     */
    private CardSetModelTTT cardSetModel;


    /**
     * The list of CardPresenters as they appear on the CardSet.
     */
    private List<TTTPresenter> cards = new ArrayList<>();

    /**
     * Indicates if it is player one's turn.
     */
    private boolean playerOneTurn;


    /**
     * TODO: make this work
     *
     * @return The score.
     */
    public int getScore() {
        return 3;
    }

    /**
     * Manage a new shuffled card set of a given size and number of undos.
     */
    protected TTTSetManager(int numRows, int numCols, ArrayList<TTTCardView> cards) {
        super(numRows, numCols);
        createCardSet(cards);
        playerOneTurn = true;
        this.cardSetModel = new CardSetModelTTT(this.cards, numRows, numCols);
    }


    public CardSetModelTTT getCardSetModel() {
        return cardSetModel;
    }

    public List<TTTPresenter> getCards() {
        return cards;
    }


    /**
     * Create a cardSetModel and shuffle it given the size of the cardSetModel. Each TTTPresenter
     * will be present exactly twice on the CardSet.
     */
    private void createCardSet(ArrayList<TTTCardView> cardViews) {
        for (int cardNum = 0; cardNum != (numCols * numRows); cardNum++) {
            cards.add(new TTTPresenter(numCols, cardViews.get(cardNum)));
        }
    }


    /**
     * Return whether the game has been completed. Return true if and only if all the cards in the
     * CardSet have been matched.
     *
     * @return A boolean value indicating whether the game is over.
     */
    public boolean puzzleSolved() {

        boolean row = checkRows();

        boolean col = checkCols();

        boolean diag = checkDiags();

        return row | col | diag;
    }

    /**
     * Return whether one of the diagonals was conquered by a user.
     *
     * @return A boolean value indicating that a user has conquered a column.
     */
    private boolean checkDiags() {
        boolean diag1 = this.cards.get(0).getBackground() != 0 &&
                this.cards.get(0).getBackground() == this.cards.get(4).getBackground() &&
                this.cards.get(0).getBackground() == this.cards.get(8).getBackground();
        boolean diag2 = this.cards.get(2).getBackground() != 0 &&
                this.cards.get(2).getBackground() == this.cards.get(4).getBackground() &&
                this.cards.get(2).getBackground() == this.cards.get(6).getBackground();
        return diag1 || diag2;
    }

    /**
     * Iterate over the board's columns, and check if the game has been completed.
     *
     * @return A boolean value indicating that a user has conquered a column.
     */
    private boolean checkCols() {
        ArrayList<TTTPresenter> cols = new ArrayList<>(this.numCols);
        for (int position = 0; position < this.numCols; position = position + 1) {
            int i = position;
            for (int index = 0; index < this.numRows; index = index + 1) { // Populate row
                cols.add(index, cards.get(i));
                i = i + 3;
            }
            if (areEqual(cols)) {
                return true;
            }
            cols.clear();
        }
        return false;
    }

    /**
     * Iterate over the board's rows, and check if the game has been completed.
     *
     * @return A boolean value indicating that a user has conquered a row.
     */
    private boolean checkRows() {
        ArrayList<TTTPresenter> row = new ArrayList<>(this.numRows);
        for (int position = 0; position < this.numRows; position = position + 3) {
            int i = position;
            for (int index = 0; index < this.numRows; index++) { // Populate row
                row.add(index, cards.get(i));
                i++;
            }
            if (areEqual(row)) {
                return true;
            }
            row.clear();
        }
        return false;
    }

    /**
     * Return whether the three cards in the ArrayList are equal. By equal, cards must be flipped,
     * and have the same background ID's.
     *
     * @param threeCards An ArrayList containing three cards.
     * @return A boolean indicating whether these three cards are equal.
     */
    private boolean areEqual(ArrayList<TTTPresenter> threeCards) {
        if (!threeCards.get(0).isSet()) {
            return false;
        }
        return threeCards.get(0).getBackground() == threeCards.get(1).getBackground() &&
                threeCards.get(0).getBackground() == threeCards.get(2).getBackground();
    }


    /**
     * Process a touch at position in the CardSet, flipping it as appropriate.
     *
     * @param position The position of the user's tap.
     */
    @Override
    public void touchMove(int position) {
        final int[] positions = getCardSetPositions(position);
        if (isValidTap(position)) {
            getCardSetModel().swapCards(positions[0], positions[1], playerOneTurn);
            playerOneTurn = !playerOneTurn;
        }
    }


    @Override
    public boolean isValidTap(int position) {
        int[] positions = getCardSetPositions(position);
        TTTPresenter card = getCardSetModel().getCard(positions[0], positions[1]);
        return !card.isSet();
    }

    /**
     * Calculates the row and column of the card located at the current position.
     *
     * @param position The position of the tap.
     */
    private int[] getCardSetPositions(int position) {
        int[] rowCol = new int[2];
        int row = position / numRows;
        int col = position % numCols;
        rowCol[0] = row;
        rowCol[1] = col;
        return rowCol;

    }

}
