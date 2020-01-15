package fall2018.csc2017.GameCentre.games.timer;


import fall2018.csc2017.GameCentre.games.timer.presenter.BaseTimerPresenter;
import fall2018.csc2017.GameCentre.games.timer.view.BaseTimerView;

/**
 * Outlines the basic methods a Timer should have in the MVP model.
 */
public interface TimerContract {

    interface View extends BaseTimerView<Presenter> {

        /**
         * Get the current time.
         *
         * @return A string representation of the current time.
         */
        String getCurTime();

        /**
         * Update the GUI with the new values.
         *
         * @param time A string object representing the time in minute minute : second second format
         */
        void update(String time);
    }


    interface Presenter extends BaseTimerPresenter {

        /**
         * Update the view with the new data.
         */
        void updateView();
    }
}
