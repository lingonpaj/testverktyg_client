/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Course;
import testverktygclient.models.Question;
import testverktygclient.models.Test;
import testverktygclient.serverconnection.ServerConnection;

/**
 * FXML Controller class
 *
 * @author User
 */



public class CreateTestViewController implements Initializable {
    
    @FXML
    private Button BackButton;
    
    @FXML
    private Label loggedInAsLabel, errorMessage;
    
    @FXML
    private Button AddTestButton;
    
    @FXML
    private Button EditTestButton;
    
    @FXML
    private ChoiceBox coursesBox;
    
    @FXML
    private TableView CoursesTable;
    
    ServerConnection serverConnection;
    
    Parent root;
    
    public static Course courseChosen;
    
    @FXML
    private TableColumn CoursesColumn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       serverConnection = ServerConnection.getInstance();
       setCellValueFactories();
       spawnACourse();
       addListener();
       loggedInAsLabel.setText("Logged in as: " + serverConnection.loggedInUser.getFirstName()
                + " " + serverConnection.loggedInUser.getLastName());
       
    }  
    
    public void setCellValueFactories(){
        CoursesColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));        
    }

    public void addListener(){
        coursesBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int count = 0;
            for(Object c : coursesBox.getItems()){
                if(count != (int) newValue){
                    count += 1;
                }
                else{
                    courseChosen = (Course) c;
                  
                    ArrayList<Test> ourTests = courseChosen.getTests();
                    
                    ObservableList<Test> testList = FXCollections.observableArrayList();
                    for(Test t : ourTests){
                        testList.add(t);
                    }
                    CoursesTable.setItems(testList);
                    CoursesTable.refresh();
                    break;
                }
            }
        });
    }
    
    @FXML
    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TeacherView.fxml"));

        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
    }
   
    @FXML
    private void signOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Hämta knappen, hämta stagen
        stage.setScene(s); // byter ut gamla stage mot nya, sätt en ny stage
        stage.show();
    }
    
    public void spawnACourse(){
       
        List<Course> courses = serverConnection.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            coursesBox.getItems().add(courses.get(i));
        }

    }
    
    @FXML
    private void spawnTestWindow() throws IOException{
        if(coursesBox.getSelectionModel().getSelectedItem() != null){        
        Stage ourStage = (Stage) AddTestButton.getScene().getWindow();
        
        root = FXMLLoader.load(getClass().getResource("addTestView.fxml"));
        ourStage.setScene(new Scene(root));
        }
        else{
            errorMessage.setText("You must select a course before adding a test.");
        }
    }
}
