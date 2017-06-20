package ygl.com.yglapp.Model;

import android.support.v4.util.Pair;

import java.util.ArrayList;

/**
 * Created by juju on 20/06/2017.
 */

public class QuizResult {

    private String email;
    private String id_quiz;
    private String level;
    private String nom;
    private String prenom;
    private double score_qcm;
    private String subject;
    private long time_remaining;
    private long timestamp;
    private ArrayList<Pair> freeAnswers;

    private static final QuizResult ourInstance = new QuizResult();

    public static QuizResult getInstance() {
        return ourInstance;
    }

    public QuizResult() {
    }

    public QuizResult(String email, String id_quiz, String level, String nom,
                      String prenom, long score_qcm, String subject, long time_remaining,
                      long timestamp, ArrayList<Pair> freeAnswers) {

        this.email = email;
        this.id_quiz = id_quiz;
        this.level = level;
        this.nom = nom;
        this.prenom = prenom;
        this.score_qcm = score_qcm;
        this.subject = subject;
        this.time_remaining = time_remaining;
        this.timestamp = timestamp;
        this.freeAnswers = freeAnswers;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(String id_quiz) {
        this.id_quiz = id_quiz;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public double getScore_qcm() {
        return score_qcm;
    }

    public void setScore_qcm(double score_qcm) {
        this.score_qcm = score_qcm;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getTime_remaining() {
        return time_remaining;
    }

    public void setTime_remaining(long time_remaining) {
        this.time_remaining = time_remaining;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Pair> getFreeAnswers() {
        return freeAnswers;
    }

    public void setFreeAnswers(ArrayList<Pair> freeAnswers) {
        this.freeAnswers = freeAnswers;
    }
}
