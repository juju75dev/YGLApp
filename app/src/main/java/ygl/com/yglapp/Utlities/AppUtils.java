package ygl.com.yglapp.Utlities;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by juju on 09/06/2017.
 */

public class AppUtils {

    public static String getFormatedTimeRemaining(long timeRemaining){

        long minutes = 0;
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


        return  minuString+" : "+seconString;
    }

    public static Animator initCircularAnim(View view){

        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;
        float initialRadius = (float) Math.hypot(cx, cy);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

        return anim;
    }

    public static AlphaAnimation initAlphaAnim(){

        AlphaAnimation animation;
        animation = new AlphaAnimation(0.2f, 1.0f);
        animation.setDuration(300);
        animation.setStartOffset(0);
        animation.setFillAfter(false);

        return animation;
    }



}
