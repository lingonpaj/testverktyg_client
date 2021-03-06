package testverktygclient.models;

import java.io.Serializable;

public class Answer implements Serializable{
    private int id;
    private String optionText;
    private boolean correct;

    public Answer() {}

    public Answer(int id, String optionText, boolean correct) {
        this.id = id;
        this.optionText = optionText;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
