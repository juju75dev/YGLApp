package ygl.com.yglapp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by juju on 06/06/2017.
 */

public class Question implements Serializable{


    @JsonProperty("key")
    private int key;
    @JsonProperty("type")
    private int type;
    @JsonProperty("text")
    private String text;
    @JsonProperty("enonce")
    private String enonce;
    @JsonProperty("weight")
    private int weight;
    @JsonProperty("propositions")
    private List<Proposition> propositions;


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

    public List<Proposition> getPropositions() {
        return propositions;
    }

    public void setPropositions(List<Proposition> propositions) {
        this.propositions = propositions;
    }
}
