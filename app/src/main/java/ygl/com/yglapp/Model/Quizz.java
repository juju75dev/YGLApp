package ygl.com.yglapp.Model;

import org.json.JSONArray;

/**
 * Created by juju on 06/06/2017.
 */

public class Quizz {

    private String name;
    private String description;
    private int duration;
    private JSONArray questions;

    public Quizz(String name, String description, int duration, JSONArray questions) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.questions = questions;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public JSONArray getQuestions() {
        return questions;
    }

    public void setQuestions(JSONArray questions) {
        this.questions = questions;
    }
}
