package ygl.com.yglapp.Model;

import java.io.Serializable;

/**
 * Created by ahmed on 06/06/2017.
 */

public class Proposition implements Serializable {


    private String key;
    private String target;
    private String text;
    private boolean isCorrectResponse;

    public Proposition() {
    }


    public boolean isCorrectResponse() {
        return isCorrectResponse;
    }

    public void setCorrectResponse(boolean correctResponse) {
        isCorrectResponse = correctResponse;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
