package testverktygclient.models;

import java.util.ArrayList;

public class Question {
    private int id;
    private String question;
    private ArrayList<Option> options;
    private boolean multi;
    
    public Question() {}

    public Question(int id, String questionText, ArrayList<Option> options, boolean multi) {
        this.id = id;
        this.question = questionText;
        this.options = options;
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
