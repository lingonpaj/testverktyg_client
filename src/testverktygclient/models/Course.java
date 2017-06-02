package testverktygclient.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{
    private int id;
    private String name;
    private ArrayList<Test> tests;

    public Course() {}

    public Course(int id, String name, ArrayList<Test> tests) {
        this.id = id;
        this.name = name;
        this.tests = tests;
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

    public ArrayList<Test> getTests() {
        return tests;
    }

    public void setTests(ArrayList<Test> tests) {
        this.tests = tests;
    }

    
    @Override
    public String toString(){
        return name;
    }
}

