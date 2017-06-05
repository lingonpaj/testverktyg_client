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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Student;
import testverktygclient.serverconnection.ServerConnection;

/**
 *
 * @author User
 */
public class TeacherController implements Initializable {
    @FXML
    private Label loggedInAsLabel;
    
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

    private ServerConnection serverConnection;

    @FXML
    private Button createTestButton;

    @FXML
    private void signOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Hämta knappen, hämta stagen
        stage.setScene(s); // byter ut gamla stage mot nya, sätt en ny stage
        stage.show();
    }
    
    private void initLoggedInUser() {
        loggedInAsLabel.setText("Logged in as: " + serverConnection.loggedInUser.getFirstName()
                + " " + serverConnection.loggedInUser.getLastName());
    }
    
    private void initStudents() {
        studentsBox.setItems(FXCollections.observableArrayList(serverConnection.getStudents()));
    }

    Parent root;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getInstance();
        initLoggedInUser();
        initStudents();
        addListenerForBox();
        setCellValueFactories();
        
        completedColumn.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth){
                TableHeaderRow header = (TableHeaderRow) completedColumn.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
        
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
                    
                    System.out.println("This is completedTests: " + completedTests.toString());                

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
    
    @FXML
    private void spawnStatistics(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StatisticsView.fxml"));
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Hämta knappen, hämta stagen
        stage.setScene(s); // byter ut gamla stage mot nya, sätt en ny stage
        stage.show();
    }
    
}
    
