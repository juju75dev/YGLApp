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


    private MyCountDownTimer countDownTimer;
    private Quizz quiz;
    Question question;
    int index = 0;

    private int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizz);
        ButterKnife.bind(this);

        quiz = (Quizz) getIntent().getSerializableExtra("quiz");
        setTitle(quiz.getName());

        setQuestion(index);
        questionsCounterView.setText("Question "+1+"/"+quiz.getQuestions().size());

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer(index);

                if (index < quiz.getQuestions().size()-1){

                    radioGroup.clearCheck();
                    setQuestion(++index);

                }else{
                    //QUIZ FINI
                    stopQuizz();
                }
            }
        });

        // 120000 = EXAMPLE DUREE QUIZZ
        countDownTimer = new MyCountDownTimer(10000, 1000, timerView, this);


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

        question = quiz.getQuestions().get(position);
        question_text.setText(question.getText());
        enonce_text.setText(question.getEnonce());
        prop1.setText(question.getPropositions().get(0).getText());
        prop2.setText(question.getPropositions().get(1).getText());
        prop3.setText(question.getPropositions().get(2).getText());
        prop4.setText(question.getPropositions().get(3).getText());

        int questionNumber = index+1;
        questionsCounterView.setText("Question "+questionNumber+"/"+quiz.getQuestions().size());

    }

    public void stopQuizz() {

        countDownTimer.cancel();
        Toast.makeText(getApplicationContext(),"Score : "+score,Toast.LENGTH_SHORT).show();

    }

    private void checkAnswer(int indexQuestion){

        //POUR LE TYPE 2
        List<Proposition> propositions = quiz.getQuestions().get(indexQuestion).getPropositions();

        int questionWeight = quiz.getQuestions().get(indexQuestion).getWeight();

        if(prop1.isChecked() && propositions.get(0).isCorrectResponse()||
                prop2.isChecked()&& propositions.get(1).isCorrectResponse()||
                prop3.isChecked() && propositions.get(2).isCorrectResponse()||
                prop4.isChecked() && propositions.get(3).isCorrectResponse()){

            //TRUE ANSWER
            score+=questionWeight;

        }else{
            //WRONG ANSWER
        }

    }

    @Override
    public void onTimerFinished() {

        //LE TEMPS EST ECOULE
        stopQuizz();

    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();

    }
}
