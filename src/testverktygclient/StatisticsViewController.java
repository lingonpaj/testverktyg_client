package testverktygclient;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Course;
import testverktygclient.models.Student;
import testverktygclient.models.Test;
import testverktygclient.serverconnection.ServerConnection;

public class StatisticsViewController implements Initializable {
    
    private ServerConnection serverConnection;
    ArrayList<Student> allStudents;
    
    @FXML
    private Label loggedInAsLabel;
    @FXML
    private Label percentageLabel;
    @FXML
    private Label studentBestScoreLabel;
    @FXML
    private Label averageScoreLabel;
    @FXML
    private Label numberOFStudentsLabel;
    @FXML
    private ChoiceBox<String> testBox;
    
    @FXML
    private void signOut(ActionEvent event) {
    }

    @FXML
    private void backToTeacherView(ActionEvent event) {
    }
    
    private int getNumberOfTimesTestHasBeenMade(String testName) {
        int numberOfTimesTestHasBeenMade = 0;
        ArrayList<CompletedTest> allCompletedTests = getCompletedTests();
        for(CompletedTest compTest : allCompletedTests) {
            if(compTest.getTestName().equals(testName)) {
                numberOfTimesTestHasBeenMade++;
            }
        }
        return numberOfTimesTestHasBeenMade;
    }
    
    private ArrayList<CompletedTest> getCompletedTests() {
        ArrayList<CompletedTest> allCompletedTests = new ArrayList();
        for(Student student : allStudents) {
            for(CompletedTest compTest : student.getCompletedTests()) {
                allCompletedTests.add(compTest);
            }
        }
        return allCompletedTests;
    }
    
    private double getPercentageOfStudentsWhoCompletedTest(String testName) {
        double percentageOfStudentsWhoCompletedTest;
        double numOfStudents = allStudents.size();
        double numOfStudentsWhoTookTest = 0;
        
        for(Student student : allStudents) {
            for(CompletedTest completedTest : student.getCompletedTests()) {
                if(completedTest.getTestName().equals(testName)) {
                    numOfStudentsWhoTookTest++;
                    break;
                }
            }
        }
        
        percentageOfStudentsWhoCompletedTest = numOfStudentsWhoTookTest / numOfStudents * 100;
        
        return percentageOfStudentsWhoCompletedTest;
    }
    
    private String getHighestScorerOnATest(String testName) {
        String highestScorerOnATest = "";
        int highestScoreOnATest = 0;
        for(Student student : allStudents) {
            for(CompletedTest compTest : student.getCompletedTests()) {
                if(compTest.getUserScore() > highestScoreOnATest) {
                    highestScoreOnATest = compTest.getUserScore();
                    highestScorerOnATest = student.getFirstName() + " " + student.getLastName();
                }
            }
        }
        return highestScoreOnATest + " (" + highestScorerOnATest + ")";
    }
    
    private double getAverageScoreOfATest(String testName) {
        double sumOfAllPointsOnTest = 0;
        double numberOfCompletedTests = 0;
        
        ArrayList<CompletedTest> allCompletedTests = getCompletedTests();
        for(CompletedTest compTest : allCompletedTests) {
            if(compTest.getTestName().equals(testName)) {
                numberOfCompletedTests++;
                sumOfAllPointsOnTest += compTest.getUserScore();
            }
        }
        
        return roundAmount(sumOfAllPointsOnTest / numberOfCompletedTests);
    }
    
    private double roundAmount(double amount) {
        BigDecimal bd = new BigDecimal(amount);
        bd = bd.round(new MathContext(2));
        double roundedAmount = bd.doubleValue();
        return roundedAmount;
    }
    
    private void initTestBox() {
        //ArrayList<String> testNames = getNamesOfAllTests();
        testBox.setItems(FXCollections.observableArrayList(getNamesOfAllTests()));
    }
    
    private ArrayList<String> getNamesOfAllTests() {
        ArrayList<String> namesOfAllTests = new ArrayList();
        ArrayList<Course> allCourses = (ArrayList) serverConnection.getCourses();
        for(Course course : allCourses) {
            for(Test test : course.getTests()) {
                namesOfAllTests.add(test.getName());
            }
        }
        return namesOfAllTests;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getInstance();
        allStudents = (ArrayList) serverConnection.getStudents();
        initTestBox();
        System.out.println(getNumberOfTimesTestHasBeenMade("The animal test"));
        System.out.println(getPercentageOfStudentsWhoCompletedTest("The animal test"));
        System.out.println(getHighestScorerOnATest("The animal test"));
        System.out.println(getAverageScoreOfATest("The animal test"));
    }    
}
