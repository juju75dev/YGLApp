package ygl.com.yglapp.Model;

import java.util.ArrayList;

/**
 * Created by ahmed on 20/06/2017.
 */

public class QuizResultGroup {



        ArrayList<QuizResult> quizResults;
        private static final QuizResultGroup ourInstance = new QuizResultGroup();

        public static QuizResultGroup getInstance() {
            return ourInstance;
        }

        private QuizResultGroup() {
        }

        public ArrayList<QuizResult> getQuizResults() {
            return quizResults;
        }

        public void setQuizResults(ArrayList<QuizResult> quizResults) {
            this.quizResults = quizResults;
        }


}
