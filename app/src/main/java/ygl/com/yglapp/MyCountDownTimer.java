package ygl.com.yglapp;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import ygl.com.yglapp.Model.OnTimerFinished;

/**
 * Created by juju on 06/06/2017.
 */

public class MyCountDownTimer extends CountDownTimer {

    private TextView countTextView;
    private OnTimerFinished timerCallback;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView nCountTextView, OnTimerFinished callback) {
        super(millisInFuture, countDownInterval);
        countTextView = nCountTextView;
        timerCallback=callback;

    }

    @Override
    public void onTick(long millisUntilFinished) {

        Log.d("timerFF","timerFF"+millisUntilFinished);
        long minutes = 0;
        long secondes = millisUntilFinished/1000;
        String minuString="00";
        String seconString;

        if(millisUntilFinished/60000>=1){

            minutes=millisUntilFinished / 60000;
            secondes= (millisUntilFinished -minutes*60000)/1000;

            minuString = minutes<10 ? "0"+minutes:String.valueOf(minutes);
            seconString = secondes<10 ? "0"+secondes:String.valueOf(secondes);

        }else{

            seconString = secondes<10 ? "0"+secondes:String.valueOf(secondes);

        }


        countTextView.setText("Temps restant "+ minuString+":"+seconString);

    }

    @Override
    public void onFinish() {

        countTextView.setText(R.string.finished);
        timerCallback.onTimerFinished();
    }
}
