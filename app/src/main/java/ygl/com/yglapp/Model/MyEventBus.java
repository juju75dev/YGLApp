package ygl.com.yglapp.Model;

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
        private Candidat candidat;

        public QuizzReadyMessage(Quizz quizz, Candidat candidat) {
            this.quizz = quizz;
            this.candidat=candidat;
        }

        public Candidat getCandidat() {
            return candidat;
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

    public static class InfoUserValidate {

        public String prenom;
        public String nom;
        public String email;
        public InfoUserValidate(String prenom, String nom, String email) {

            this.prenom=prenom;
            this.nom=nom;
            this.email=email;
        }
    }

    public static class QuizzOverMessage {

        /*
        private int score;
        private long timeRemaining;
        private int nbFreeQAnswered;
        private ArrayList<Question> listFreeAnswers;*/

        private QuizResult result;
        private int nbFreeQAnswered;

        public QuizzOverMessage(QuizResult result, int nbFreeQA/*int score, ArrayList<Question> listFQ, int nbFreeQA, long timeR*/) {
            /*this.score = score;

            this.nbFreeQAnswered = nbFreeQA;
            this.timeRemaining = timeR;*/
            this.result=result;
            this.nbFreeQAnswered = nbFreeQA;

        }

        public QuizResult getResult() {
            return result;
        }

        public int getNbFreeQAnswered() {
            return nbFreeQAnswered;
        }



        /*
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
        }*/

    }

}

