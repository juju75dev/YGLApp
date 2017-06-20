package ygl.com.yglapp.Model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juju on 06/06/2017.
 */


public class Quizz implements Serializable{


    private String description;
    private long duration;

    private String name;
    private ArrayList<Question> questions;

    private String level;
    private int id;



    public Quizz() {
    }


    public Quizz(String description, int duration, String name, List<Question> questions) {
        this.description = description;
        this.duration = duration;
        this.name = name;




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



    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
