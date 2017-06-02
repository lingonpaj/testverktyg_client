package testverktygclient;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import static testverktygclient.StudentViewController.ObservableTestList;
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
    private Label numberOfTimesLabel;
    @FXML
    private ChoiceBox<String> testBox;
    
    @FXML
    private void signOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Hämta knappen, hämta stagen
        stage.setScene(s); // byter ut gamla stage mot nya, sätt en ny stage
        stage.show();
    }

    @FXML
    private void backToTeacherView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TeacherView.fxml"));
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Hämta knappen, hämta stagen
        stage.setScene(s); // byter ut gamla stage mot nya, sätt en ny stage
        stage.show();
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
                if(compTest.getUserScore() > highestScoreOnATest && compTest.getTestName().equals(testName)) {
                    highestScoreOnATest = compTest.getUserScore();
                    highestScorerOnATest = student.getFirstName() + " " + student.getLastName();
                }
            }
        }
        if(highestScoreOnATest == 0) {
            return "-";
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
        try {
            BigDecimal bd = new BigDecimal(amount);
            bd = bd.round(new MathContext(2));
            double roundedAmount = bd.doubleValue();
            return roundedAmount;
        } catch (Exception e) {
            return 0;
        }
    }
    
    private void initTestBox() {
        testBox.setItems(FXCollections.observableArrayList(getNamesOfAllTests()));
        testBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                updateStatistics(t1);
            }    
        });
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
    
    private void updateStatistics(String testName) {
        System.out.println(testName);
        percentageLabel.setText(getPercentageOfStudentsWhoCompletedTest(testName) + "%");
        studentBestScoreLabel.setText(getHighestScorerOnATest(testName));
        averageScoreLabel.setText(getAverageScoreOfATest(testName) + " points");
        numberOfTimesLabel.setText("" + getNumberOfTimesTestHasBeenMade(testName));
        
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
