package testverktygclient.models;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<CompletedTest> completedTests;

    public Student() {}

    public Student(ArrayList<CompletedTest> completedTests, int userId, 
            String userName, String password, String firstName, String lastName) {
        super(userId, userName, password, firstName, lastName);
        this.completedTests = completedTests;
    }

    public ArrayList<CompletedTest> getCompletedTests() {
        return completedTests;
    }

    public void setCompletedTests(ArrayList<CompletedTest> completedTests) {
        this.completedTests = completedTests;
    }
}
