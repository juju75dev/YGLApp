package ygl.com.yglapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Model.OnTimerFinished;
import ygl.com.yglapp.Model.Proposition;
import ygl.com.yglapp.Model.Question;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.MyCountDownTimer;
import ygl.com.yglapp.R;

import static android.R.id.list;


public class QuizzActivity extends AppCompatActivity implements OnTimerFinished {


    //WARNING LAYOUT
    @BindView(R.id.start_quizz_button)
    Button startQuizzButton;
    @BindView(R.id.warning_layout)
    LinearLayout warningLayout;
    @BindView(R.id.warning_title_view)
    TextView warningTitleView;
    @BindView(R.id.warning_desc_view)
    TextView warningDescView;


    //QUESTION LAYOUT
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
    @BindView(R.id.enonce_layout)
    CardView enonce_layout;
    @BindView(R.id.question_layout)
    LinearLayout questionLayout;
    @BindView(R.id.card_edit_answer_view)
    CardView cardEditAnswerView;
    @BindView(R.id.edit_answer_view)
    EditText editAnswerView;
    @BindView(R.id.card_radio_group)
    CardView cardRadioGroup;
    @BindView(R.id.timer_view)
    TextView timerView;
    @BindView(R.id.questions_counter_view)
    TextView questionsCounterView;
    @BindView(R.id.question_text)
    TextView question_text;
    @BindView(R.id.enonce_text)
    TextView enonce_text;


    //SCORE LAYOUT
    @BindView(R.id.back_home_button)
    Button backHomeButton;
    @BindView(R.id.score_quiz_name_view)
    TextView scoreQuizzNameView;
    @BindView(R.id.score_time_left)
    TextView scoreTimeView;
    @BindView(R.id.score_view)
    TextView scoreView;
    @BindView(R.id.score_questions_free_view)
    TextView scoreQuestionsFreeView;

    private MyCountDownTimer countDownTimer;
    private Quizz quiz;
    Question question;
    int index = 0;

    private int score = 0;
    private int nbFreeQuestionsAnswered=0;
    private int quizTotalPoints=0;
    private boolean quizStarted=false;

    private ArrayList<Question> listAnswersLibres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizz);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quiz = (Quizz) getIntent().getSerializableExtra("quiz");

        setTitle(quiz.getName());

        countDownTimer = new MyCountDownTimer(quiz.getDuration() * 60000, 1000, timerView, this);

        for(int i=0;i<quiz.getQuestions().size();i++){

            quizTotalPoints+=quiz.getQuestions().get(i).getWeight();

        }

        warningTitleView.setText(getString(R.string.you_choose) + " : " + quiz.getName());

        warningDescView.setText("- " + getString(R.string.questions_number) + " : " + quiz.getQuestions().size() +
                "\n\n- " + getString(R.string.duration) + " : " + quiz.getDuration() + "min" +
                "\n\n- " + getString(R.string.quizz_contains_two_types) +
                "\n\n- " + getString(R.string.dont_getout_of_app) + " !");

        setQuestion(index);

        //questionsCounterView.setText("Question " + 1 + "/" + quiz.getQuestions().size());

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(quiz.getQuestions().get(index).getType()==1){

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editAnswerView.getWindowToken(), 0);

                }
                checkAnswer(index);

                if (index < quiz.getQuestions().size() - 1) {

                    radioGroup.clearCheck();
                    setQuestion(++index);

                } else {
                    stopQuizz();
                }
            }
        });


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
                            quizStarted=true;
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

        if(question.getType()==2){

            cardEditAnswerView.setVisibility(View.GONE);
            cardRadioGroup.setVisibility(View.VISIBLE);

            prop1.setText(question.getPropositions().get(0).getText());
            prop2.setText(question.getPropositions().get(1).getText());
            prop3.setText(question.getPropositions().get(2).getText());
            prop4.setText(question.getPropositions().get(3).getText());

        }else{

            cardRadioGroup.setVisibility(View.GONE);
            cardEditAnswerView.setVisibility(View.VISIBLE);
        }


        questionsCounterView.setText(getString(R.string.question)+" "+ String.valueOf(index+1)+"/"+quiz.getQuestions().size());

        if (question.getEnonce() == null)
            enonce_layout.setVisibility(View.GONE);
        else
            enonce_layout.setVisibility(View.VISIBLE);

    }

    private void startQuizz(){

        quizStarted=true;
        warningLayout.setVisibility(View.GONE);
        countDownTimer.start();
        getSupportActionBar().hide();
        listAnswersLibres=new ArrayList<>();

    }

    private void stopQuizz() {

        quizStarted=false;
        countDownTimer.cancel();
        displayScore();
        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void displayScore(){

        int scorePercent=0;

        if(quizTotalPoints>0 && score>0){
            scorePercent = score/(quizTotalPoints/100);

        }
        scoreView.setText("Score Qcm : "+scorePercent+"%");
        scoreQuizzNameView.setText(quiz.getName());
        scoreTimeView.setText(countDownTimer.getFormatedTimeRemaining());

        scoreQuestionsFreeView.setText(getString(R.string.questions_free_answered)+" : "+nbFreeQuestionsAnswered);

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

        Question question = quiz.getQuestions().get(indexQuestion);
        //POUR LE TYPE 2
        if(question.getType()==2){

            List<Proposition> propositions = question.getPropositions();

            int questionWeight = question.getWeight();

            if (prop1.isChecked() && propositions.get(0).isCorrectResponse() ||
                    prop2.isChecked() && propositions.get(1).isCorrectResponse() ||
                    prop3.isChecked() && propositions.get(2).isCorrectResponse() ||
                    prop4.isChecked() && propositions.get(3).isCorrectResponse()) {

                //TRUE ANSWER
                score += questionWeight;

            } else {
                //WRONG ANSWER
            }

        }else{
         //POUR LE TYPE 1
            if(!editAnswerView.getText().toString().equals(""))
                nbFreeQuestionsAnswered++;


            Question questionAnswered = question;

            Proposition proposition = new Proposition();
            proposition.setText(editAnswerView.getText().toString());
            proposition.setCorrectResponse(true);
            proposition.setKey(0);
            proposition.setTarget(2);

            List<Proposition> list = Arrays.asList(proposition);
            questionAnswered.setPropositions(list);
            listAnswersLibres.add(questionAnswered);


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
        if (!quizStarted) {
            super.onBackPressed();
        }

    }

}
