package fall2018.csc2017.GameCentre.games.matchingGame.model;


import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.GameCentre.games.GameBoard;
import fall2018.csc2017.GameCentre.games.matchingGame.presenter.CardPresenter;


/**
 * A set of cards used in a Matching Cards game.
 */
public class CardSetModel extends GameBoard {


    /**
     * The cards on the card set in row-major order.
     */
    private CardPresenter[][] cards;


    /**
     * A new card set  of cards in row-major order.
     * Precondition: len(cards) == numRows * numCols
     *
     * @param cards the cards for the card set.
     */
    public CardSetModel(List<CardPresenter> cards, int numRows, int numCols) {
        super(numRows, numCols);
        this.cards = new CardPresenter[numRows][numCols];
        createSet(cards, this.cards);
    }


    /**
     * Return the card positioned at coordinates (row, col).
     *
     * @param row The card's row.
     * @param col The card's column.
     * @return The card located at position (row, col).
     */
    public CardPresenter getCard(int row, int col) {
        return cards[row][col];
    }

    /**
     * Send a message to observers, that a move was just processed on the card situated at
     * position (row, index).
     *
     * @param row The card's row.
     * @param col The card's column.
     */
    public void swapCards(int row, int col) {
        CardPresenter cardToSwap = getCard(row, col);
        cardToSwap.flip();
        setChanged();
        notifyObservers();
    }

    @Override
    @NonNull
    public String toString() {
        return "Set of Cards{" +
                "cards=" + Arrays.toString(cards) +
                '}';
    }

    /**
     * Take an empty two-dimensional array setToFill, and populate it in row-major order
     * using a list of CardPresenters cardPresenterList.
     *
     * @param cardPresenterList - A List of CardPresenters, with the CardPresenters in the order
     *                          seen on the CardSet.
     * @param setToFill         - An empty, two-dimensional array to be populated with CardPresenters.
     */
    private void createSet(List<CardPresenter> cardPresenterList, CardPresenter[][] setToFill) {

        Iterator<CardPresenter> iterator = cardPresenterList.iterator();
        for (int row = 0; row != this.getNumRows(); row++) {
            for (int col = 0; col != this.getNumCols(); col++) {
                setToFill[row][col] = iterator.next();
            }
        }

    }

    /**
     * Return an Iterator that can iterate over the CardPresenters in row-major order.
     *
     * @return A new iterator, that can iterate over CardPresenters.
     */
    public Iterator<CardPresenter> iterator() {
        return new CardIterator();
    }

    private class CardIterator implements Iterator<CardPresenter> {
        /**
         * Initial starting row of the 2D Card Array
         */

        private int row = 0;

        /**
         * Initial starting column of the 2D Card Array
         */

        private int col = 0;

        /**
         * Current CardPresenter object for Iterator.
         */
        private CardPresenter currT;

        @Override
        public boolean hasNext() {
            return row < numRows && col < numCols;
        }


        @Override
        public CardPresenter next() {
            if (!hasNext()) {
                return null;
            }
            while (row < numRows) {
                while (col < numCols) {
                    if (gettingCurrentAndNextCards()) {
                        return currT;
                    }
                }
                row++;
            }
            return null;
        }

        /**
         * Performs operations in iterating through the 2D array and assigning the next card
         * object for the Iterable
         *
         * @return whether the operation to assign a current card and increment the column count
         * is possible
         */

        private boolean gettingCurrentAndNextCards() {
            CardPresenter currGetTile = getCard(row, col);
            if (currGetTile != null) {
                currT = currGetTile;
                col++;
                reset();
                return true;
            }
            return false;
        }

        /**
         * Checks whether the column card is the last column card in the row. Increments the row
         * if condition is satisfied.
         */
        private void reset() {
            if (col == numCols) {
                col = 0;
                row++;
            }
        }

    }
}

