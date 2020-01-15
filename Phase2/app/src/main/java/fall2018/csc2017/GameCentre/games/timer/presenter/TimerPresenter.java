package fall2018.csc2017.GameCentre.games.timer.presenter;


import android.os.Looper;
import android.support.annotation.NonNull;

import android.os.Handler;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import fall2018.csc2017.GameCentre.games.timer.TimerContract;
import fall2018.csc2017.GameCentre.games.timer.model.TimerModel;


/**
 * A presenter class for timer to communicate between view and model.
 */

public class TimerPresenter implements TimerContract.Presenter {

    /**
     * A TimerModel as a reference to store and manipulate data.
     */

    private final TimerModel timerModel;

    /**
     * A TimerView to visually display data, and to be regularly updated.
     */

    private final TimerContract.View timerView;


    private Timer timer;

    /**
     * A Runnable designed to update the display to the period we want.
     */

    private final TimerTask updateRunnable;


    public TimerPresenter(@NonNull final TimerContract.View timerView,
                          @NonNull final TimerModel timerModel) {
        this.timerModel = timerModel;
        this.timerView = timerView;
        this.timerView.setPresenter(this);
        timer = new Timer();
        this.updateRunnable = new TimerTask() {
            @Override
            public void run() {
                updateView();
            }
        };
    }

    @Override
    public void start() {
        timer.scheduleAtFixedRate(updateRunnable, 0, 1000);
    }


    @Override
    public void stop() {
        timer.cancel();
    }


    @Override
    public void updateView() {
        if (timerView != null) {
            timerView.update(getElapsedTime());
        }
    }


    /**
     * Get the time that has elapsed, since the timer started.
     *
     * @return A string representing the elapsed time, in minute minute : second second format.
     */
    private String getElapsedTime() {
        Calendar calendar = Calendar.getInstance(); // Get a calendar with the current time
        Calendar refCalendar = timerModel.getRefCalendar();

        Integer[] values = getDisplayTimes(calendar, refCalendar);
        String minutes = timerModel.formatString(values[0].toString());
        String seconds = timerModel.formatString(values[1].toString());

        return minutes + ":" + seconds;
    }

    /**
     * Starting from two calendars, one of the current time and one of reference, return an array
     * representing the time difference between the two.
     *
     * @param curCalendar A calendar of the current time.
     * @param refCalendar A calendar of some time of reference.
     * @return An integer array, with the minutes difference at index 0, and the seconds difference
     * at index 1.
     */
    private Integer[] getDisplayTimes(Calendar curCalendar, Calendar refCalendar) {
        Integer seconds = curCalendar.get(Calendar.SECOND) - refCalendar.get(Calendar.SECOND);
        Integer minutes = curCalendar.get(Calendar.MINUTE) - refCalendar.get(Calendar.MINUTE);
        if (seconds < 0) {
            Integer offsetSeconds = minutes * 60 + seconds;
            seconds = offsetSeconds % 60;
            minutes = offsetSeconds / 60;
        }
        Integer displayedSeconds = seconds % 60;
        return new Integer[]{minutes, displayedSeconds};
    }

}
