/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Student;
import testverktygclient.models.Test;

/**
 *
 * @author User
 */
public class TeacherController implements Initializable {
    
    @FXML
    private TableView<CompletedTest> completedColumn;
    
    //Trigger so that All tests for a chosen person gets set in a ListView
    @FXML
    private TableColumn<CompletedTest, String> courseColumn; 
    
    @FXML
    private TableColumn<CompletedTest, String> testColumn;
    
    @FXML
    private TableColumn<CompletedTest, Integer> scoreColumn;
    
    @FXML
    private TableColumn<CompletedTest, Integer> maxScoreColumn;
    
    @FXML
    private ChoiceBox studentsBox;
    
    @FXML
    private Button createTestButton;
    
    Parent root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListenerForBox();
        setCellValueFactories();
        spawnATest();
    }
    
    public void spawnATest(){
        ObservableList<CompletedTest> completedList = FXCollections.observableArrayList();
        
        CompletedTest newTest = new CompletedTest(1, "404", "PHP Test", 1, 2);
        CompletedTest newTest1 = new CompletedTest(1, "500", "PHP Test 2", 10, 40);
        ArrayList<CompletedTest> completedTests = new ArrayList<CompletedTest>();
        completedTests.add(newTest);
        completedTests.add(newTest1);
        
        Student newGuy = new Student(completedTests, 1, "'Guy'", "pw", "Kyle", "Jenkins");
        
        //Load in user from DB
        //Load in completed test for user from DB
        //Set them to respective loaded guy
        newGuy.setCompletedTests(completedTests);
        
        
        
        ArrayList<CompletedTest> completedTests2 = new ArrayList<CompletedTest>();
        Student secondGuy = new Student(completedTests2, 2, "'Derpy'", "lol", "Stan", "Brukowich");
        CompletedTest newTest2 = new CompletedTest(3, "500", "JS Test", 1, 2);
        completedTests2.add(newTest2);
        
        secondGuy.setCompletedTests(completedTests2);
  
        //Just assign them to the box
        studentsBox.setItems(FXCollections.observableArrayList(newGuy, secondGuy)); 
    }
    
    public void setCellValueFactories(){
        courseColumn.setCellValueFactory(new PropertyValueFactory<CompletedTest, String>("courseName"));
        testColumn.setCellValueFactory(new PropertyValueFactory<CompletedTest, String>("TestName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<CompletedTest, Integer>("userScore"));
        maxScoreColumn.setCellValueFactory(new PropertyValueFactory<CompletedTest, Integer>("testMaxScore"));
    }
    
    public void addListenerForBox(){
        studentsBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            //Should clear list and populate with the list of completed Tests for that specific person 
            //Look up getting item at index specific of selectionModule
            
            int count = 0;
            for(Object s : studentsBox.getItems()){
                if(count != (int) newValue){
                    count += 1;
                }
                else{
                    Student foundGuy = (Student) s;
                    System.out.println(foundGuy.toString());
                    ArrayList<CompletedTest> completedTests = foundGuy.getCompletedTests();
                    
                    System.out.println("This is compeltedTests: " + completedTests.toString());                
                    ObservableList<CompletedTest> completedList = FXCollections.observableArrayList();
                    for(CompletedTest c : completedTests){
                        completedList.add(c);
                    }
                    completedColumn.setItems(completedList);
                    completedColumn.refresh();
                    break;
                }                
            }
        });
    }
    
    @FXML
    private void spawnNewWindow() throws IOException{
        Stage ourStage = (Stage) createTestButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("CreateTestView.fxml"));
        ourStage.setScene(new Scene(root));
    }
}
    