package ygl.com.yglapp.Utlities;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import ygl.com.yglapp.Model.Proposition;
import ygl.com.yglapp.Model.Question;
import ygl.com.yglapp.Model.Quizz;
import ygl.com.yglapp.Model.QuizzGroup;

/**
 * Created by juju on 16/06/2017.
 */

public class FireBaseQuizParsing {

    public ArrayList<QuizzGroup> quizParsing(DataSnapshot dataSnapshot) {

        ArrayList<QuizzGroup> listQuizGroup = new ArrayList<>();

        for (DataSnapshot groupShot : dataSnapshot.getChildren()) {

            ArrayList<Quizz> listQuizz = new ArrayList<>();
            QuizzGroup quizzGroup = new QuizzGroup();
            quizzGroup.setName(groupShot.getKey().toString());
          //  quizzGroup.setLevel((String)groupShot.child("level").getValue());
            quizzGroup.setIconName(groupShot.child("icon").toString());


            for (DataSnapshot quizShot : groupShot.getChildren()) {

                Quizz fireQuiz = new Quizz();

                fireQuiz.setName(quizShot.child("name").toString());
               // fireQuiz.setLevel((long) quizShot.child("level").getValue());
                fireQuiz.setDuration((long) quizShot.child("duration").getValue());
                fireQuiz.setDescription(quizShot.child("description").getValue().toString());
                fireQuiz.setLevel(quizShot.child("level").getValue().toString());

                ArrayList<Question> questions = new ArrayList<>();

                for (DataSnapshot questionShot : quizShot.child("questions").getChildren()) {

                    Question question = new Question();
                    question.setKey(questionShot.child("key").getValue().toString());
                    question.setType((long) questionShot.child("type").getValue());
                    question.setWeight((long) questionShot.child("weight").getValue());
                    question.setKey(questionShot.child("key").getValue().toString());
                    question.setText(questionShot.child("text").getValue().toString());

                    if (questionShot.child("enonce").getValue() != null) {

                        question.setEnonce(questionShot.child("enonce").getValue().toString());

                    } else {

                        question.setEnonce(null);
                    }


                    ArrayList<Proposition> listProp = new ArrayList<>();
                    for (DataSnapshot propShot : questionShot.child("propositions").getChildren()) {

                        Proposition prop = propShot.getValue(Proposition.class);
                        listProp.add(prop);

                    }

                    question.setPropositions(listProp);
                    questions.add(question);

                }

                fireQuiz.setQuestions(questions);
                listQuizz.add(fireQuiz);

            }
            quizzGroup.setListQuiz(listQuizz);
            listQuizGroup.add(quizzGroup);

        }

        return listQuizGroup;

    }

}
