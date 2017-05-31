package testverktygclient.models;

import java.util.ArrayList;

public class Question {
    private int id;
    private boolean multi;
    private String question;
    private ArrayList<Option> options;

    public Question() {}

    public Question(int id, String questionText, ArrayList<Option> options, boolean multi) {
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

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }
}
