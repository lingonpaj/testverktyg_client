package testverktygclient.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable{
    private int id;
    private boolean multi;
    private String question;
    private ArrayList<Answer> answers;

    public Question() {}

    public Question(int id, String questionText, ArrayList<Answer> answers, boolean multi) {
        this.id = id;
        this.multi = multi;
        this.question = questionText;
        this.answers = answers;
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

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
