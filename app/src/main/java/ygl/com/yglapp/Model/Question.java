package ygl.com.yglapp.Model;

import org.json.JSONArray;

/**
 * Created by juju on 06/06/2017.
 */

public class Question {

    private int key;
    private int type;
    private String text;
    private String enonce;
    private int weight;
    private JSONArray propositions;


    public Question(int key, int type, String text, String enonce, int weight, JSONArray propositions) {
        this.key = key;
        this.type = type;
        this.text = text;
        this.enonce = enonce;
        this.weight = weight;
        this.propositions = propositions;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public JSONArray getPropositions() {
        return propositions;
    }

    public void setPropositions(JSONArray propositions) {
        this.propositions = propositions;
    }
}
