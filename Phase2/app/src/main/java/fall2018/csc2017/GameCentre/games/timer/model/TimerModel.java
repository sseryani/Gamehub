package fall2018.csc2017.GameCentre.games.timer.model;

import android.arch.lifecycle.ViewModel;

import java.util.Calendar;

/**
 * A model to store, and manipulate data used in presenter.
 */
public class TimerModel extends ViewModel {

    /**
     * A calendar used as a reference point in Presenter.
     */
    private Calendar refCalendar;


    public TimerModel() {
        refCalendar = Calendar.getInstance();
        refCalendar.setTimeInMillis(System.currentTimeMillis());
    }


    /**
     * Return the calendar of reference.
     *
     * @return A Calendar object representing the exact time of when TimerModel was last
     * changed.
     */
    public Calendar getRefCalendar() {
        return this.refCalendar;
    }

    /**
     * Format time and return it in a tt format where tt represents a string representation of time.
     *
     * @param time Current time
     * @return An updated time string better suited for our purpose.
     */
    public String formatString(String time) {
        if (time.length() < 2) {
            return "0" + time;
        }
        return time;
    }
}
