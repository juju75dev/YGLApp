package ygl.com.yglapp;

import android.os.CountDownTimer;
import android.widget.TextView;

import ygl.com.yglapp.Model.MyEventBus;
import ygl.com.yglapp.Utlities.AppUtils;

/**
 * Created by juju on 06/06/2017.
 */

public class MyCountDownTimer extends CountDownTimer {

    private TextView countTextView;
    public long timeRemaining;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView nCountTextView) {
        super(millisInFuture, countDownInterval);
        countTextView = nCountTextView;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeRemaining=millisUntilFinished;
        countTextView.setText(AppUtils.getFormatedTimeRemaining(timeRemaining));

    }

    @Override
    public void onFinish() {

        countTextView.setText(R.string.finished);
        timeRemaining=0;
        GlobalBus.getBus().post(new MyEventBus.TimeisOver());

    }
}
