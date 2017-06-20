package ygl.com.yglapp.Model;

import java.util.ArrayList;

/**
 * Created by juju on 19/05/2017.
 */

public class MyEventBus {

    public static class QuizzGroupReadyMessage {

        private QuizzGroup quizzGroup;

        public QuizzGroupReadyMessage(QuizzGroup quizzGroup) {
            this.quizzGroup = quizzGroup;
        }

        public QuizzGroup getQuizz() {
            return quizzGroup;
        }

    }

    public static class QuizzReadyMessage {

        private Quizz quizz;

        public QuizzReadyMessage(Quizz quizz) {
            this.quizz = quizz;
        }

        public Quizz getQuizz() {
            return quizz;
        }

    }

    public static class TimeisOver {

        public TimeisOver() {

        }
    }

    public static class LoginSuccessMessage {

        public LoginSuccessMessage() {

        }
    }

    public static class QuizzOverMessage {

        private int score;
        private long timeRemaining;
        private int nbFreeQAnswered;
        private ArrayList<Question> listFreeAnswers;

        public QuizzOverMessage(int score, ArrayList<Question> listFQ, int nbFreeQA, long timeR) {
            this.score = score;
            this.listFreeAnswers = listFQ;
            this.nbFreeQAnswered = nbFreeQA;
            this.timeRemaining = timeR;
        }

        public int getScore() {
            return score;
        }

        public ArrayList<Question> getFreeAnswers() {
            return listFreeAnswers;
        }

        public int getNbFreeQAnswered() {
            return nbFreeQAnswered;
        }

        public long getTimeRemaining() {
            return timeRemaining;
        }

    }

}

