package ygl.com.yglapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ygl.com.yglapp.Model.MyEventBus;
import ygl.com.yglapp.Model.Proposition;
import ygl.com.yglapp.Model.Question;
import ygl.com.yglapp.Model.Quizz;
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

    private MyCountDownTimer countDownTimer;
    private Question question;
    private  int index = 0;
    private int score = 0;
    private int nbFreeQuestionsAnswered=0;
    private ArrayList<Question> listAnswersLibres;
    private Quizz myQuizz;
    private  AlphaAnimation alphaAanimation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.question_layout, container, false);
        ButterKnife.bind(this,fragmentView);

        listAnswersLibres=new ArrayList<>();

        alphaAanimation= AppUtils.initAlphaAnim();

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(myQuizz.getQuestions().get(index).getType()==1){

                    InputMethodManager imm = (InputMethodManager)getActivity().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editAnswerView.getWindowToken(), 0);

                }
                checkAnswer(index);

                if (index < myQuizz.getQuestions().size() - 1) {

                    radioGroup.clearCheck();
                    questionLayout.startAnimation(alphaAanimation);
                    setQuestion(++index);

                } else {

                    GlobalBus.getBus().post(new MyEventBus.
                            QuizzOverMessage(score,listAnswersLibres,
                            nbFreeQuestionsAnswered,countDownTimer.timeRemaining));
                    countDownTimer.cancel();
                    hideFragment();

                }
            }
        });


        return fragmentView;
    }


    private void setQuestion(int position) {

        question = myQuizz.getQuestions().get(position);
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

    @Subscribe
    public void getMessage(MyEventBus.QuizzReadyMessage quizMessage) {

        myQuizz=quizMessage.getQuizz();
        countDownTimer = new MyCountDownTimer(myQuizz.getDuration() * 60000, 1000, timerView);
        countDownTimer.start();
        setQuestion(index);

    }

    @Subscribe
    public void getTimerMessage(MyEventBus.TimeisOver message) {

        GlobalBus.getBus().post(new MyEventBus.
                QuizzOverMessage(score,listAnswersLibres,nbFreeQuestionsAnswered,countDownTimer.timeRemaining));
        countDownTimer.cancel();
        hideFragment();

    }


}