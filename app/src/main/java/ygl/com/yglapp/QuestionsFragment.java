package ygl.com.yglapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Model.Candidat;
import ygl.com.yglapp.Model.MyEventBus;
import ygl.com.yglapp.Model.Proposition;
import ygl.com.yglapp.Model.QCMResultDetails;
import ygl.com.yglapp.Model.Question;
import ygl.com.yglapp.Model.QuizResult;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.Model.QuizzGroup;
import ygl.com.yglapp.Utlities.AppUtils;

/**
 * Created by juju on 09/06/2017.
 */

public class QuestionsFragment extends Fragment {

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
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.question_layout)
    LinearLayout questionLayout;
//    @BindView(R.id.card_edit_answer_view)
//    CardView cardEditAnswerView;
    @BindView(R.id.edit_answer_view)
    EditText editAnswerView;
    @BindView(R.id.radio_group_layout)
    LinearLayout cardRadioGroup;
    @BindView(R.id.timer_view)
    TextView timerView;
    @BindView(R.id.questions_counter_view)
    TextView questionsCounterView;
    @BindView(R.id.question_text)
    TextView question_text;
    @BindView(R.id.enonce_text)
    TextView enonce_text;

    private MyCountDownTimer countDownTimer;
    private Question question;
    private  int index = 0;
    private int score = 0;
    private int nbFreeQuestionsAnswered=0;
    private ArrayList<Question> listAnswersLibres;
    private ArrayList<QCMResultDetails> listQCMResultsDetails;
    private ArrayList<Pair> pairAnswersLibres;
    private Quizz myQuizz;
    private QuizzGroup quizzGroup;
    private  AlphaAnimation alphaAanimation;
    private int quizTotalPoints = 0;
    private Candidat candidat;
    private Handler handler;
    private Runnable runnable;
    private boolean enableValidation=true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.question_layout, container, false);
        ButterKnife.bind(this,fragmentView);


        handler = new Handler();


        runnable = new Runnable() {
            @Override
            public void run() {
                enableValidation=true;
            }
        };

        listAnswersLibres=new ArrayList<>();
        pairAnswersLibres=new ArrayList<>();

        alphaAanimation= AppUtils.initAlphaAnim();

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(enableValidation){

                    enableValidation=false;
                    scrollView.scrollTo(0, 0);

                    if(myQuizz.getQuestions().get(index).getType()==1){

                        InputMethodManager imm = (InputMethodManager)getActivity().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editAnswerView.getWindowToken(), 0);

                    }
                    checkAnswer(index);

                    if (index < myQuizz.getQuestions().size() - 1) {

                        questionLayout.startAnimation(alphaAanimation);
                        setQuestion(++index);

                    } else {

                        //QUIZ TERMINE !!!

                        QuizResult quizResult = new QuizResult(candidat.getEmail(),myQuizz.getLevel(),candidat.getPrenom(),
                                candidat.getNom(),scoreTopercent(score),myQuizz.getName(),countDownTimer.timeRemaining,
                                new Date().getTime(),pairAnswersLibres,listQCMResultsDetails);

                        GlobalBus.getBus().post(new MyEventBus.
                                QuizzOverMessage(quizResult,nbFreeQuestionsAnswered));

                    /*
                    GlobalBus.getBus().post(new MyEventBus.
                            QuizzOverMessage(score,listAnswersLibres,
                            nbFreeQuestionsAnswered,countDownTimer.timeRemaining));*/

                        countDownTimer.cancel();
                        hideFragment();

                    }

                    // 800 MILI SECONDES DE COOLDOWN POUR EVITER LES DOUBLES CLICK
                    handler.postDelayed(runnable,600);

                }

            }
        });

        return fragmentView;
    }


    private void setQuestion(int position) {

        radioGroup.clearCheck();
        question = myQuizz.getQuestions().get(position);
        question_text.setText(String.valueOf(index+1)+" - "+question.getText());
        enonce_text.setText(question.getEnonce());

        if(question.getType()==2){

            editAnswerView.setVisibility(View.GONE);
            radioGroup.setVisibility(View.VISIBLE);

            prop1.setText(question.getPropositions().get(0).getText());
            prop2.setText(question.getPropositions().get(1).getText());
            prop3.setText(question.getPropositions().get(2).getText());
            prop4.setText(question.getPropositions().get(3).getText());

        }else{

            radioGroup.setVisibility(View.GONE);
            editAnswerView.setVisibility(View.VISIBLE);
        }


        questionsCounterView.setText(getString(R.string.question)+" "+ String.valueOf(index+1)+"/"+myQuizz.getQuestions().size());

        if (question.getEnonce() == null)
            enonce_layout.setVisibility(View.GONE);
        else
            enonce_layout.setVisibility(View.VISIBLE);

    }

    private void checkAnswer(int indexQuestion) {

        Question question = myQuizz.getQuestions().get(indexQuestion);

        if(question.getType()==2){

            List<Proposition> propositions = question.getPropositions();

            long questionWeight = question.getWeight();

            String realAnswer="";

            for(int i=0;i<4;i++){

                if(propositions.get(i).getValue()==1){

                    realAnswer=propositions.get(i).getText();
                }

            }

            if(prop1.isChecked()){

                QCMResultDetails details;

                if(propositions.get(0).getValue()==1){

                    score += questionWeight;
                    details = new QCMResultDetails(question.getText(),question.getEnonce(),
                            prop1.getText().toString(),realAnswer,true);

                }else{

                    details = new QCMResultDetails(question.getText(),question.getEnonce(),
                            prop1.getText().toString(),realAnswer,false);
                }

                listQCMResultsDetails.add(details);



            }else if(prop2.isChecked()){

                QCMResultDetails details;
                if(propositions.get(1).getValue()==1){
                    score += questionWeight;
                     details = new QCMResultDetails(question.getText(),question.getEnonce(),
                            prop2.getText().toString(),realAnswer,true);

                }else{

                     details = new QCMResultDetails(question.getText(),question.getEnonce(),
                            prop2.getText().toString(),realAnswer,false);
                }

                listQCMResultsDetails.add(details);



            }else if(prop3.isChecked() && propositions.get(2).getValue()==1 ){

                QCMResultDetails details;
                if(propositions.get(2).getValue()==1 ){

                    score += questionWeight;
                     details = new QCMResultDetails(question.getText(),question.getEnonce(),
                            prop3.getText().toString(),realAnswer,true);

                }else{

                     details = new QCMResultDetails(question.getText(),question.getEnonce(),
                            prop3.getText().toString(),realAnswer,false);
                }

                listQCMResultsDetails.add(details);


            }else if(prop4.isChecked()){
                QCMResultDetails details;
                if(propositions.get(3).getValue()==1){

                    score += questionWeight;
                     details = new QCMResultDetails(question.getText(),question.getEnonce(),
                            prop4.getText().toString(),realAnswer,true);

                }else{

                     details = new QCMResultDetails(question.getText(),question.getEnonce(),
                            prop4.getText().toString(),realAnswer,false);

                }

                listQCMResultsDetails.add(details);

            }else{

                //NO ANSWER CHECKED
                QCMResultDetails details = new QCMResultDetails(question.getText(),question.getEnonce(),
                        "Pas de réponse",realAnswer,false);

                listQCMResultsDetails.add(details);

            }

           /* if (prop1.isChecked() && propositions.get(0).getValue()==1 ||
                    prop2.isChecked() && propositions.get(1).getValue()==1 ||
                    prop3.isChecked() && propositions.get(2).getValue()==1 ||
                    prop4.isChecked() && propositions.get(3).getValue()==1) {

                //TRUE ANSWER

               // QCMResultDetails details = new QCMResultDetails(question.getText(),question.getEnonce());
                Log.d("score","scorescore"+score);

            } else {


                //WRONG ANSWER
            }*/

        }else{
            //POUR LE TYPE 1

            Log.d("score","scorescoreYYYY"+score);
            if(!editAnswerView.getText().toString().equals(""))
                nbFreeQuestionsAnswered++;

            Question questionAnswered = question;

            Proposition proposition = new Proposition();
            proposition.setText(editAnswerView.getText().toString());
            proposition.setValue(1);// 1 --> true
            proposition.setKey("0");
            proposition.setTarget("2");

            List<Proposition> list = Arrays.asList(proposition);
            questionAnswered.setPropositions(list);
            listAnswersLibres.add(questionAnswered);

            Pair pair = new Pair(question.getText(),editAnswerView.getText().toString());
            pairAnswersLibres.add(pair);

        }

    }

    private void hideFragment(){

        final View myView = this.getView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Animator anim  = AppUtils.initCircularAnim(this.getView());
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.GONE);
                }
            });

            anim.start();
        } else {

            myView.setVisibility(View.GONE);

        }
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
    public void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null)
        countDownTimer.cancel();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(countDownTimer!=null ){

            if(countDownTimer.timeRemaining<=0)
            GlobalBus.getBus().post(new MyEventBus.TimeisOver());

        }
    }

    @Subscribe
    public void getMessage(MyEventBus.QuizzReadyMessage quizMessage) {

        getView().setVisibility(View.VISIBLE);
        index=0;
        quizTotalPoints=0;
        myQuizz=quizMessage.getQuizz();
        listQCMResultsDetails=new ArrayList<>();

        for (int i = 0; i < myQuizz.getQuestions().size(); i++) {

            quizTotalPoints += myQuizz.getQuestions().get(i).getWeight();

        }

        Log.d("scccc","scccccc"+quizTotalPoints);

        candidat=quizMessage.getCandidat();
        countDownTimer = new MyCountDownTimer(myQuizz.getDuration() * 60000, 1000, timerView);
        countDownTimer.start();
        setQuestion(index);
        score=0;

    }

    private double scoreTopercent(long myScore){

        double scorePercent = 0;

        if (quizTotalPoints > 0 && myScore > 0) {
            scorePercent = myScore / (((double) quizTotalPoints / 100));
        }

        return scorePercent;
    }

    @Subscribe
    public void getTimerMessage(MyEventBus.TimeisOver message) {


        QuizResult quizResult = new QuizResult(candidat.getEmail(),myQuizz.getLevel(),candidat.getPrenom(),
                candidat.getNom(),scoreTopercent(score),myQuizz.getName(),
                countDownTimer.timeRemaining,new Date().getTime(),pairAnswersLibres,listQCMResultsDetails);


        GlobalBus.getBus().post(new MyEventBus.
                QuizzOverMessage(quizResult,nbFreeQuestionsAnswered));

        /*
        GlobalBus.getBus().post(new MyEventBus.
                QuizzOverMessage(score,listAnswersLibres,nbFreeQuestionsAnswered,countDownTimer.timeRemaining));*/
        countDownTimer.cancel();
        hideFragment();

    }


}