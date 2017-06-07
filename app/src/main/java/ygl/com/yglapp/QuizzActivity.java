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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Model.OnTimerFinished;
import ygl.com.yglapp.Model.Proposition;
import ygl.com.yglapp.Model.Question;
import ygl.com.yglapp.Model.Quizz;

public class QuizzActivity extends AppCompatActivity implements OnTimerFinished {

    @BindView(R.id.timer_view)
    TextView timerView;
    @BindView(R.id.question_text)
    TextView question_text;
    @BindView(R.id.enonce_text)
    TextView enonce_text;
    @BindView(R.id.start_quizz_button)
    Button startQuizzButton;
    @BindView(R.id.warning_layout)
    LinearLayout warningLayout;


    @BindView(R.id.validate)
    Button validate;

    @BindView(R.id.prop1)
    RadioButton prop1;
    @BindView(R.id.prop2)
    RadioButton prop2;
    @BindView(R.id.prop3)
    RadioButton prop3;
    @BindView(R.id.prop4)
    RadioButton prop4;


    private MyCountDownTimer countDownTimer;
    private Quizz quiz;
    Question question;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizz);
        ButterKnife.bind(this);

        setQuestion(0);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i < quiz.getQuestions().size())
                    setQuestion(i++);
            }
        });


        // 120000 = EXAMPLE DUREE QUIZZ
        countDownTimer = new MyCountDownTimer(120000, 1000, timerView, this);


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
                } else {

                    warningLayout.setVisibility(View.GONE);
                    countDownTimer.start();

                }

            }
        });


    }

    private void setQuestion(int position) {
        quiz = (Quizz) getIntent().getSerializableExtra("quiz");
        question = quiz.getQuestions().get(position);
        question_text.setText(question.getText());
        enonce_text.setText(question.getEnonce());
        prop1.setText(question.getPropositions().get(0).getText());
        prop2.setText(question.getPropositions().get(1).getText());
        prop3.setText(question.getPropositions().get(2).getText());
        prop4.setText(question.getPropositions().get(3).getText());
        setTitle(quiz.getName());
    }

    public void stopQuizz() {

        //ARRET DU QUIZZ + AFFICHAGE RESULTATS

    }


    @Override
    public void onTimerFinished() {

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
