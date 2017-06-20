package ygl.com.yglapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.GlobalBus;
import ygl.com.yglapp.Model.MyEventBus;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.Model.QuizzGroup;
import ygl.com.yglapp.R;
import ygl.com.yglapp.Utlities.AppUtils;


public class QuizzActivity extends AppCompatActivity {

    //WARNING LAYOUT
    @BindView(R.id.start_quizz_button)
    Button startQuizzButton;
    @BindView(R.id.warning_layout)
    LinearLayout warningLayout;
    @BindView(R.id.warning_title_view)
    TextView warningTitleView;
    @BindView(R.id.warning_desc_view)
    TextView warningDescView;

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

    private Quizz quiz;
    ArrayList<QuizzGroup> checkedquizzGroup;
    private int quizTotalPoints = 0;
    private boolean quizStarted = false;
    private static int quizindex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizz);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        checkedquizzGroup = (ArrayList<QuizzGroup>) getIntent().getSerializableExtra("quizgroup");
        quizindex = 0;
        if (quizindex < checkedquizzGroup.size() - 1) {
            backHomeButton.setText(R.string.next_question_test);

        } else
            backHomeButton.setText(R.string.back_home);


        quiz = checkedquizzGroup.get(0).getListQuiz().get(checkedquizzGroup.get(0).getIdcheckedQuiz());


        setTitle(quiz.getName());

        for (int i = 0; i < quiz.getQuestions().size(); i++) {

            quizTotalPoints += quiz.getQuestions().get(i).getWeight();

        }

        warningTitleView.setText(getString(R.string.you_choose) + " : " + quiz.getName());

        warningDescView.setText("- " + getString(R.string.questions_number) + " : " + quiz.getQuestions().size() +
                "\n\n- " + getString(R.string.duration) + " : " + quiz.getDuration() + "min" +
                "\n\n- " + getString(R.string.quizz_contains_two_types) +
                "\n\n- " + getString(R.string.dont_getout_of_app) + " !" +
                "\n\n- " + getString(R.string.can_jump_question) + " ! ");


        startQuizzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  GlobalBus.getBus().post(new MyEventBus.QuizzReadyMessage(quiz));
                GlobalBus.getBus().post(new MyEventBus.QuizzReadyMessage(checkedquizzGroup.get(0).getListQuiz().get(checkedquizzGroup.get(0).getIdcheckedQuiz())));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Animator anim = AppUtils.initCircularAnim(warningLayout);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            startQuizz();
                            quizStarted = true;
                        }
                    });

                    anim.start();
                } else {

                    startQuizz();
                    quizStarted = true;

                }
                quizindex++;
            }
        });
    }

    private void startQuizz() {

        quizStarted = true;
        warningLayout.setVisibility(View.GONE);
        getSupportActionBar().hide();

    }

    private void displayScore(int score, int nbFreeQuestionsAnswered, long timeRemaining) {

        quizStarted = false;

        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (quizindex < checkedquizzGroup.size()) {
                    GlobalBus.getBus().post(new MyEventBus.QuizzReadyMessage(checkedquizzGroup.get(quizindex).getListQuiz().get(checkedquizzGroup.get(quizindex).getIdcheckedQuiz())));

                    if (quizindex == checkedquizzGroup.size() - 1) {
                        backHomeButton.setText(R.string.back_home);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        Animator anim = AppUtils.initCircularAnim(warningLayout);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                startQuizz();
                                quizStarted = true;
                            }
                        });

                        anim.start();
                    } else {

                        startQuizz();
                        quizStarted = true;

                    }
                } else {
                    checkedquizzGroup.clear();
                    Intent intent = new Intent(QuizzActivity.this, MainActivity.class);
                    startActivity(intent);
                    // backHomeButton.setText("Retour à l'acceuil");
//                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    //   finish();
                }


                quizindex++;


                // finish();
            }
        });

        double scorePercent = 0;

        if (quizTotalPoints > 0 && score > 0) {
            scorePercent = score / (((double) quizTotalPoints / 100));
        }

        scoreView.setText("Score Qcm : " + scorePercent + "%");
        scoreQuizzNameView.setText(quiz.getName());
        scoreTimeView.setText(AppUtils.getFormatedTimeRemaining(timeRemaining));
        scoreQuestionsFreeView.setText(getString(R.string.questions_free_answered) + " : " + nbFreeQuestionsAnswered);

    }


    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (!quizStarted) {
            super.onBackPressed();
        }

    }

    @Subscribe
    public void getMessage(MyEventBus.QuizzGroupReadyMessage quizMessage) {

//        quizzGroup=quizMessage.getQuizz();
//        quiz =  quizzGroup.getListQuiz().get(0);


    }

    @Subscribe
    public void getMessage(MyEventBus.QuizzOverMessage message) {

        displayScore(message.getScore(), message.getNbFreeQAnswered(), message.getTimeRemaining());


    }

}
