package ygl.com.yglapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Model.OnTimerFinished;
import ygl.com.yglapp.Model.Proposition;
import ygl.com.yglapp.Model.Question;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.MyCountDownTimer;
import ygl.com.yglapp.R;


public class QuizzActivity extends AppCompatActivity implements OnTimerFinished {

    @BindView(R.id.timer_view)
    TextView timerView;
    @BindView(R.id.questions_counter_view)
    TextView questionsCounterView;
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
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.warning_title_view)
    TextView warningTitleView;
    @BindView(R.id.warning_desc_view)
    TextView warningDescView;
    @BindView(R.id.enonce_layout)
    LinearLayout enonce_layout;
    @BindView(R.id.question_layout)
    LinearLayout questionLayout;

    //SCORE LAYOUT
    @BindView(R.id.back_home_button)
    Button backHomeButton;
    @BindView(R.id.score_quiz_name_view)
    TextView scoreQuizzNameView;
    @BindView(R.id.score_time_left)
    TextView scoreTimeView;
    @BindView(R.id.score_view)
    TextView scoreView;

    private MyCountDownTimer countDownTimer;
    private Quizz quiz;
    Question question;
    int index = 0;

    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizz);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        quiz = (Quizz) getIntent().getSerializableExtra("quiz");
        setTitle(quiz.getName());

        warningTitleView.setText(getString(R.string.you_choose) + " : " + quiz.getName());

        warningDescView.setText("- " + getString(R.string.questions_number) + " : " + quiz.getQuestions().size() +
                "\n\n- " + getString(R.string.duration) + " : " + quiz.getDuration() + "min" +
                "\n\n- " + getString(R.string.quizz_contains_two_types) +
                "\n\n- " + getString(R.string.dont_getout_of_app) + " !");

        setQuestion(index);
        questionsCounterView.setText("Question " + 1 + "/" + quiz.getQuestions().size());

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer(index);

                if (index < quiz.getQuestions().size() - 1) {

                    radioGroup.clearCheck();
                    setQuestion(++index);

                } else {
                    stopQuizz();
                }
            }
        });

        countDownTimer = new MyCountDownTimer(quiz.getDuration() * 60000, 1000, timerView, this);


        startQuizzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Animator anim  = initCircularAnim(warningLayout);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            startQuizz();
                        }
                    });

                    anim.start();
                } else {

                   startQuizz();

                }
            }
        });
    }

    private void setQuestion(int position) {

        question = quiz.getQuestions().get(position);
        question_text.setText(String.valueOf(index+1)+" - "+question.getText());
        enonce_text.setText(question.getEnonce());
        prop1.setText(question.getPropositions().get(0).getText());
        prop2.setText(question.getPropositions().get(1).getText());
        prop3.setText(question.getPropositions().get(2).getText());
        prop4.setText(question.getPropositions().get(3).getText());

        questionsCounterView.setText("Question "+ String.valueOf(index+1)+"/"+quiz.getQuestions().size());

        if (question.getEnonce() == null)
            enonce_layout.setVisibility(View.GONE);
        else
            enonce_layout.setVisibility(View.VISIBLE);

    }


    private void startQuizz(){

        warningLayout.setVisibility(View.GONE);
        countDownTimer.start();
        getSupportActionBar().hide();

    }

    private void stopQuizz() {

        countDownTimer.cancel();

        scoreView.setText("Score : "+score);
        scoreQuizzNameView.setText(quiz.getName());
        scoreTimeView.setText(countDownTimer.getFormatedTimeRemaining());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Animator anim  = initCircularAnim(questionLayout);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    questionLayout.setVisibility(View.GONE);
                }
            });

            anim.start();
        } else {

            questionLayout.setVisibility(View.GONE);

        }

        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private Animator initCircularAnim(View view){

        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;
        float initialRadius = (float) Math.hypot(cx, cy);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

        return anim;
    }

    private void checkAnswer(int indexQuestion) {

        //POUR LE TYPE 2
        List<Proposition> propositions = quiz.getQuestions().get(indexQuestion).getPropositions();

        int questionWeight = quiz.getQuestions().get(indexQuestion).getWeight();

        if (prop1.isChecked() && propositions.get(0).isCorrectResponse() ||
                prop2.isChecked() && propositions.get(1).isCorrectResponse() ||
                prop3.isChecked() && propositions.get(2).isCorrectResponse() ||
                prop4.isChecked() && propositions.get(3).isCorrectResponse()) {

            //TRUE ANSWER
            score += questionWeight;

        } else {
            //WRONG ANSWER
        }

    }

    @Override
    public void onTimerFinished() {

        stopQuizz();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();

    }

    @Override
    public void onBackPressed() {

    }


}
