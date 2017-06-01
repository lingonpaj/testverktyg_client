package testverktygclient.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{
    private int id;
    private boolean multi;
    private String question;
    private ArrayList<Answer> options;

    public Question() {}

    public Question(int id, String questionText, ArrayList<Answer> options, boolean multi) {
        this.id = id;
        this.multi = multi;
        this.question = questionText;
        this.options = options;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Answer> options) {
        this.options = options;
    }
}
