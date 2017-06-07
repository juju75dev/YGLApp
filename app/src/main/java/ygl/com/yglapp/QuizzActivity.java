package ygl.com.yglapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Model.OnTimerFinished;

public class QuizzActivity extends AppCompatActivity implements OnTimerFinished {

    @BindView(R.id.timer_view) TextView timerView;
    @BindView(R.id.start_quizz_button) Button startQuizzButton;
    @BindView(R.id.warning_layout) LinearLayout warningLayout;
    @BindView(R.id.enonce_view) TextView enonceView;
    @BindView(R.id.question_text_view) TextView questionTextView;


    private MyCountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        ButterKnife.bind(this);
        setTitle("Nom du quizz");
        // 120000 = EXAMPLE DUREE QUIZZ
        countDownTimer = new MyCountDownTimer(120000,1000,timerView,this);


        startQuizzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    int cx = warningLayout.getWidth() / 2;
                    int cy = warningLayout.getHeight() / 2;
                    float initialRadius = (float) Math.hypot(cx, cy);

                    final Animator anim =
                            ViewAnimationUtils.createCircularReveal(warningLayout, cx, cy, initialRadius, 0);

                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            warningLayout.setVisibility(View.GONE);
                            countDownTimer.start();
                        }
                    });

                    anim.start();
                 }else{

                    warningLayout.setVisibility(View.GONE);
                    countDownTimer.start();

                }

            }
        });


    }

    public void stopQuizz(){

        //ARRET DU QUIZZ + AFFICHAGE RESULTATS

    }


    @Override
    public void onTimerFinished(){

        //LE TEMPS EST ECOULE
        Toast.makeText(this, "Terminé !!!", Toast.LENGTH_SHORT).show();
        stopQuizz();

    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();

    }
}
