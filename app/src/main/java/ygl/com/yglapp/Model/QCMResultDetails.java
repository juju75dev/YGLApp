package ygl.com.yglapp.Model;

import java.io.Serializable;

/**
 * Created by juju on 26/06/2017.
 */

public class QCMResultDetails implements Serializable {

    private String question;
    private String enonce;
    private String answer;
    private String realAnswer;
    private boolean isCorrect;



    public QCMResultDetails(String question, String enonce, String answer, String realAnswer, boolean isCorrect) {
        this.question = question;
        this.answer = answer;
        this.realAnswer = realAnswer;
        this.isCorrect = isCorrect;
        this.enonce = enonce;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
