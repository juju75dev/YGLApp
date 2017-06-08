package ygl.com.yglapp;

import android.os.CountDownTimer;
import android.widget.TextView;

import ygl.com.yglapp.Model.OnTimerFinished;

/**
 * Created by juju on 06/06/2017.
 */

public class MyCountDownTimer extends CountDownTimer {

    private TextView countTextView;
    private OnTimerFinished timerCallback;
    public long timeRemaining;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView nCountTextView, OnTimerFinished callback) {
        super(millisInFuture, countDownInterval);
        countTextView = nCountTextView;
        timerCallback=callback;

    }

    public String getFormatedTimeRemaining(){

        long minutes = 00;
        long secondes = timeRemaining/1000;
        String minuString="00";
        String seconString;

        if(timeRemaining/60000>=1){

            minutes=timeRemaining / 60000;
            secondes= (timeRemaining -minutes*60000)/1000;

            minuString = minutes<10 ? "0"+minutes:String.valueOf(minutes);
            seconString = secondes<10 ? "0"+secondes:String.valueOf(secondes);

        }else{

            seconString = secondes<10 ? "0"+secondes:String.valueOf(secondes);

        }

        return "Temps restant : "+ minuString+"min : "+seconString+"sec";
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeRemaining=millisUntilFinished;
        countTextView.setText(getFormatedTimeRemaining());

    }

    @Override
    public void onFinish() {

        countTextView.setText(R.string.finished);
        timeRemaining=0;
        timerCallback.onTimerFinished();
    }
}
