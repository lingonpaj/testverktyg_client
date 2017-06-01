package testverktygclient.models;

import java.util.ArrayList;

public class Test {
    private int id, time;
    private String name;
    private ArrayList<Question> questions;

    public Test() {}

    public Test(int id, String name, ArrayList<Question> questions, int time) {
        this.id = id;
        this.name = name;
        this.questions = questions;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    
    @Override
    public String toString(){
        //int id, String name, ArrayList<Question> questions
        return name;      
    }
}
