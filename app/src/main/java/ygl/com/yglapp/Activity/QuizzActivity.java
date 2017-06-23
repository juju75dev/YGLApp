package ygl.com.yglapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.GlobalBus;
import ygl.com.yglapp.Model.Candidat;
import ygl.com.yglapp.Model.MyEventBus;
import ygl.com.yglapp.Model.QuizResult;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.Model.QuizzGroup;
import ygl.com.yglapp.R;
import ygl.com.yglapp.Utlities.AppUtils;

import static android.view.View.GONE;


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

    @BindView(R.id.score_layout)
    LinearLayout scoreLayout;

    //SCORE LAYOUT
    @BindView(R.id.back_home_button)
    Button backHomeButton;
    //@BindView(R.id.score_quiz_name_view)
    //TextView scoreQuizzNameView;
    //@BindView(R.id.score_time_left)
    //TextView scoreTimeView;
    //@BindView(R.id.score_view)
    //TextView scoreView;
    //@BindView(R.id.score_questions_free_view)
    //TextView scoreQuestionsFreeView;

    private Quizz quiz;
    ArrayList<QuizzGroup> checkedquizzGroup;
    //private int quizTotalPoints = 0;
    private boolean quizStarted = false;
    private static int quizindex = 0;
    private Candidat candidat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quizz);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        candidat = (Candidat) bundle.getSerializable("candidat");
        checkedquizzGroup = (ArrayList<QuizzGroup>) getIntent().getSerializableExtra("quizgroup");

        quizindex = 0;

        quiz = checkedquizzGroup.get(0).getListQuiz().get(checkedquizzGroup.get(0).getIdcheckedQuiz());

        setWarningText(0);

        startQuizzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  GlobalBus.getBus().post(new MyEventBus.QuizzReadyMessage(quiz));
                if(!quizStarted){
                    quizStarted = true;

                    GlobalBus.getBus().post(new MyEventBus.QuizzReadyMessage(checkedquizzGroup.get(quizindex).getListQuiz().
                            get(checkedquizzGroup.get(quizindex).getIdcheckedQuiz()),candidat));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        Animator anim = AppUtils.initCircularAnim(warningLayout);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                startQuizz();
                                //quizStarted = true;
                            }
                        });

                        anim.start();
                    } else {

                        startQuizz();
                        //quizStarted = true;

                    }
                    quizindex++;


                }

            }
        });

        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (quizindex < checkedquizzGroup.size()) {

                        showWarning();
                        setWarningText(quizindex);
                    showWarning();
                    setWarningText(quizindex);

                    if (quizindex == checkedquizzGroup.size() - 1) {

                        backHomeButton.setText(R.string.back_home);


                    }


                } else {

                    checkedquizzGroup.clear();
                    finish();

                }


               // quizindex++;



            }
        });
    }

    private void setWarningText(int i) {

        quiz = checkedquizzGroup.get(i).getListQuiz().get(checkedquizzGroup.get(i).getIdcheckedQuiz());
        setTitle(quiz.getName());

        warningTitleView.setText(getString(R.string.you_choose) + " : " + quiz.getName()+ "\nNiveau: "+quiz.getLevel());

        warningDescView.setText("- " + getString(R.string.questions_number) + " : " + quiz.getQuestions().size() +
                "\n\n- " + getString(R.string.duration) + " : " + quiz.getDuration() + "min" +
                "\n\n- " + getString(R.string.quizz_contains_two_types) +
                "\n\n- " + getString(R.string.dont_getout_of_app) + " !" +
                "\n\n- " + getString(R.string.can_jump_question) + " ! ");
    }

    private void showWarning() {
        warningLayout.setVisibility(View.VISIBLE);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }


    private void startQuizz() {

        quizStarted = true;
        scoreLayout.setVisibility(GONE);
        warningLayout.setVisibility(GONE);
        getSupportActionBar().hide();

    }


    private void displayScore(QuizResult result ) {

        if(quizindex == checkedquizzGroup.size()){
            backHomeButton.setText(R.string.back_home);
        }else{

            backHomeButton.setText(R.string.next_question_test);
        }
        scoreLayout.setVisibility(View.VISIBLE);
        quizStarted = false;

        //scoreView.setText("Score Qcm : " + result.getScore() + "%");
        //scoreQuizzNameView.setText(quiz.getName());
        //scoreTimeView.setText(AppUtils.getFormatedTimeRemaining(result.getTime_remaining()));

    }

    private void sendResultsToFirebase(QuizResult result){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refQuiz = database.getReference("Historic");

        String postId = refQuiz.push().getKey();
        refQuiz.child(postId).setValue(result);

    }

    @Override
    protected void onResume() {
        super.onResume();
        quizStarted=false;
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
//        if (!quizStarted) {
//            super.onBackPressed();
//        }

    }

    @Subscribe
    public void getMessage(MyEventBus.QuizzGroupReadyMessage quizMessage) {

//        quizzGroup=quizMessage.getQuizz();
//        quiz =  quizzGroup.getListQuiz().get(0);

    }

    @Subscribe
    public void getMessage(MyEventBus.QuizzOverMessage message) {

        displayScore(message.getResult());

        sendResultsToFirebase(message.getResult());
    }

}
