package testverktygclient.models;

import java.util.ArrayList;

public class Question {
    private int id;
    private String questionText;
    private ArrayList<Option> options;

    public Question() {}

    public Question(int id, String questionText, ArrayList<Option> options) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }
}
