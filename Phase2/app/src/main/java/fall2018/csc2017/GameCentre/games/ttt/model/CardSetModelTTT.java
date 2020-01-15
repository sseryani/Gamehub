package fall2018.csc2017.GameCentre.games.ttt.model;


import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.GameCentre.games.GameBoard;
import fall2018.csc2017.GameCentre.games.ttt.presenter.TTTPresenter;


/**
 * A set of cards used in a Matching Cards game.
 */
public class CardSetModelTTT extends GameBoard {


    /**
     * The cards on the card set in row-major order.
     */
    private TTTPresenter[][] cards;


    /**
     * A new card set  of cards in row-major order.
     * Precondition: len(cards) == numRows * numCols
     *
     * @param cards the cards for the card set.
     */
    public CardSetModelTTT(List<TTTPresenter> cards, int numRows, int numCols) {
        super(numRows, numCols);
        this.cards = new TTTPresenter[numRows][numCols];
        createSet(cards, this.cards);
    }


    /**
     * Return the card positioned at coordinates (row, col).
     *
     * @param row The card's row.
     * @param col The card's column.
     * @return The card located at position (row, col).
     */
    public TTTPresenter getCard(int row, int col) {
        return cards[row][col];
    }

    /**
     * Send a message to observers, that a move was just processed on the card situated at
     * position (row, index).
     *
     * @param row The card's row.
     * @param col The card's column.
     */
    public void swapCards(int row, int col, boolean playerOne) {
        TTTPresenter cardToSwap = getCard(row, col);
        cardToSwap.flip(playerOne);
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
    private void createSet(List<TTTPresenter> cardPresenterList, TTTPresenter[][] setToFill) {

        Iterator<TTTPresenter> iterator = cardPresenterList.iterator();
        for (int row = 0; row != this.getNumRows(); row++) {
            for (int col = 0; col != this.getNumCols(); col++) {
                setToFill[row][col] = iterator.next();
            }
        }

    }


}

