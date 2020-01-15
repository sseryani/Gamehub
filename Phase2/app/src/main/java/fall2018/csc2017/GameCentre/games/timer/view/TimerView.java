package fall2018.csc2017.GameCentre.games.timer.view;

/*
Adapted from : https://github.com/shanestaples/android-timer-mvp-example/blob/master/app/src/main/java/com/shanestaples/example/timermvp/timer/TimerFragment.java

This adaptation of a timer view is better suited to our needs (and counts forwards instead of backwards)
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.games.timer.TimerContract;

/**
 * A basic TimerView activity. This is meant as fragment in a bigger screen.
 */
public class TimerView extends Fragment implements TimerContract.View {

    /**
     * A presenter used to act on and update the data.
     */
    private TimerContract.Presenter timerPresenter;

    /**
     * To help manage the binding.
     */
    private Unbinder unbinder;

    @BindView(R.id.curTime)
    TextView textView;

    public static TimerView newInstance() {
        return new TimerView();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.sample_timer_activity,
                container, false); // Inflate layout in new container
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        timerPresenter.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void start() {
        timerPresenter.start();
    }


    @Override
    public void setPresenter(TimerContract.Presenter presenter) {
        this.timerPresenter = presenter;
    }


    @Override
    public void update(String time) {
        String newTime = "Time - " + time;
        if (textView != null) {
            textView.setText(newTime);
        }
    }


    @Override
    public String getCurTime() {
        return formatTime(textView.getText().toString());
    }

    /**
     * Take oldTime and format it so we return it in a minute minute : second second format.
     *
     * @param oldTime The time, in the format Time - mm:ss
     * @return A String object of the format mm:ss
     */
    public String formatTime(String oldTime) {
        int dashIndex = oldTime.indexOf('-') + 1;
        return oldTime.substring(dashIndex);
    }
}