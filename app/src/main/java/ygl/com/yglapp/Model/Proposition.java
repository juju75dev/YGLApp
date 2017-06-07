package ygl.com.yglapp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by ahmed on 06/06/2017.
 */

public class Proposition implements Serializable {

    @JsonProperty("key")
    private int key;
    @JsonProperty("target")
    private int target;
    @JsonProperty("text")
    private String text;
    @JsonProperty("isCorrectResponse")
    private boolean isCorrectResponse;



    public boolean isCorrectResponse() {
        return isCorrectResponse;
    }

    public void setCorrectResponse(boolean correctResponse) {
        isCorrectResponse = correctResponse;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
