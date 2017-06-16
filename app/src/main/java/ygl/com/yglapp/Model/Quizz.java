package ygl.com.yglapp.Model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by juju on 06/06/2017.
 */


public class Quizz implements Serializable{


    private String description;
    private long duration;
    private long level;
    private String name;
    private List<Question> questions;

    boolean isChecked;

    @JsonProperty("quizz")
    private static Quizz mInstance;

    public Quizz() {
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Quizz(String description, int duration, int level, String name, List<Question> questions) {
        this.description = description;
        this.duration = duration;
        this.name = name;

        this.level=level;


    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
