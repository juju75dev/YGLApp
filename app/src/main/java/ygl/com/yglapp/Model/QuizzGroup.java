package ygl.com.yglapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by juju on 16/06/2017.
 */

public class QuizzGroup implements Serializable {
    private String name;
    private String iconName;
    private ArrayList<Quizz> listQuiz;
    boolean isChecked;
    private int idcheckedQuiz=-1;

    public QuizzGroup(String name, String iconName, ArrayList<Quizz> listQuiz) {
        this.name = name;
        this.iconName = iconName;
        this.listQuiz = listQuiz;


    }

    public QuizzGroup() {
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public ArrayList<Quizz> getListQuiz() {
        return listQuiz;
    }

    public void setListQuiz(ArrayList<Quizz> listQuiz) {
        this.listQuiz = listQuiz;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getIdcheckedQuiz() {
        return idcheckedQuiz;
    }

    public void setIdcheckedQuiz(int idcheckedQuiz) {
        this.idcheckedQuiz = idcheckedQuiz;
    }
}
