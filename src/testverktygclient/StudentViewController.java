/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static testverktygclient.StudentViewController.ObservableCompletedTestList;
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Course;
import testverktygclient.models.Student;
import testverktygclient.models.Test;
import testverktygclient.serverconnection.ServerConnection;

/**
 * FXML Controller class
 *
 * @author bujam
 */
public class StudentViewController implements Initializable {

    @FXML
    private Label loggedInAsLabel;
    @FXML
    private Label chooseTest;
    @FXML
    private TableView<CompletedTest> completedTestTable;
    @FXML
    private TableColumn<Test, String> availableTestColumn;
    @FXML
    private TableColumn<CompletedTest, String> completedCourseNameColumn;
    @FXML
    private TableColumn<CompletedTest, String> completedTestNameColumn;
    @FXML
    private TableColumn<CompletedTest, Integer> completedTestPointsColumn;
    @FXML
    private TableColumn<CompletedTest, Integer> completedTestMaxColumn;
    @FXML
    private ChoiceBox chooseCourseDropDown;
    @FXML
    private TableView<Test> availableTestTable;
    
    private Course selectedCourse;
    public Test selectedTest;

    public static List<Course> courseArrayList = new ArrayList<>();
    public static List<CompletedTest> completedTestArrayList = new ArrayList<>();
    public static ObservableList<Course> ObservableCourseList = FXCollections.observableArrayList();
    public static ObservableList<CompletedTest> ObservableCompletedTestList = FXCollections.observableArrayList();
    public static ObservableList<Test> ObservableTestList = FXCollections.observableArrayList();

    ServerConnection serverConnection = ServerConnection.getInstance();
    Student std = new Student();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        availableTestColumn.setCellValueFactory(new PropertyValueFactory<Test, String>("name"));
        ObservableCourseList = FXCollections.observableArrayList(serverConnection.getCourses());
        chooseCourseDropDown.setItems(ObservableCourseList);
        
        Student loggedinstudent = (Student) serverConnection.loggedInUser;
        
        ObservableCompletedTestList = FXCollections.observableArrayList(loggedinstudent.getCompletedTests());
        completedTestTable.setItems(ObservableCompletedTestList);
        
        availableTestTable.setColumnResizePolicy((param) -> false);
        completedTestTable.setColumnResizePolicy((param) -> false);
        
        loggedInAsLabel.setText("Logged in as: " + serverConnection.loggedInUser.getFirstName()
                + " " + serverConnection.loggedInUser.getLastName());

        
        chooseCourseDropDown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            int newValueCasted = (int) newValue;
            selectedCourse = (Course) chooseCourseDropDown.getItems().get(newValueCasted);

            ObservableTestList = FXCollections.observableArrayList(selectedCourse.getTests());
            availableTestTable.setItems(ObservableTestList);
        }

        });
        ObservableTestList = FXCollections.observableArrayList(serverConnection.hardCodedTests);
        availableTestTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int newValueCasted = (int) newValue;
                try {
                    selectedTest = (Test) availableTestTable.getItems().get(newValueCasted);
                } catch (Exception e) {
                }

                
            }

        });
        
        
        
        
        
        Student std = (Student) serverConnection.loggedInUser;
        
        //String courseName, String TestName, 
        //    int userScore, int testMaxScore
        completedCourseNameColumn.setCellValueFactory(new PropertyValueFactory("courseName"));
        completedTestNameColumn.setCellValueFactory(new PropertyValueFactory("TestName"));
        completedTestPointsColumn.setCellValueFactory(new PropertyValueFactory("userScore"));
        completedTestMaxColumn.setCellValueFactory(new PropertyValueFactory("testMaxScore"));
        
        completedTestTable.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth){
                TableHeaderRow header = (TableHeaderRow) completedTestTable.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
        
    }
    
    
    @FXML
    private void startTest(ActionEvent event) throws IOException   {
        
            if(selectedTest == null){
                chooseTest.setText("Please choose a test.");
            } else {
                serverConnection.testToTake = selectedTest;
                serverConnection.courseNameOfTest = selectedCourse.getName();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLTakeTestView.fxml"));
                Scene s = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(s);
                stage.show();
            }      
    }

    @FXML
    private void signOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Hämta knappen, hämta stagen
        stage.setScene(s); // byter ut gamla stage mot nya, sätt en ny stage
        stage.show();
    }
}
