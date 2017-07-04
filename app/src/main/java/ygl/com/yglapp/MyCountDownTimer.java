package ygl.com.yglapp;

import android.os.CountDownTimer;
import android.text.Html;
import android.widget.TextView;

import ygl.com.yglapp.Model.MyEventBus;
import ygl.com.yglapp.Utlities.AppUtils;

/**
 * Created by juju on 06/06/2017.
 */

public class MyCountDownTimer extends CountDownTimer {

  //  private TextView countTextView;
    public long timeRemaining=-1;

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
      //  countTextView = nCountTextView;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        timeRemaining=millisUntilFinished;
        String formatedTime = AppUtils.getFormatedTimeRemaining(timeRemaining);

//        if (timeRemaining<60000){
//
//            formatedTime="<font color='red'>"+formatedTime+"</font>";
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                countTextView.setText(Html.fromHtml(formatedTime,Html.FROM_HTML_MODE_LEGACY));
//            } else {
//                countTextView.setText(Html.fromHtml(formatedTime), TextView.BufferType.SPANNABLE);
//            }
//
//        }else{

//            countTextView.setText(formatedTime);
//
//        }

    }

    @Override
    public void onFinish() {

       // countTextView.setText(R.string.finished);
        timeRemaining=0;
        GlobalBus.getBus().post(new MyEventBus.TimeisOver());

    }
}
