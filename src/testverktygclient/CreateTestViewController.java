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
    private Button AddTestButton;
    
    @FXML
    private Button EditTestButton;
    
    @FXML
    private ChoiceBox coursesBox;
    
    @FXML
    private TableView CoursesTable;
    
    Parent root;
    
    private static int selectedcourse;
    
    public static String courseChosen = "";
    
    @FXML
    private TableColumn CoursesColumn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setCellValueFactories();
       spawnACourse();
       addListener();
       
       
    }  
    
    public void setCellValueFactories(){
        CoursesColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));        
    }


    public static int getSelectedcourse() {
        return selectedcourse;
    }
    
    public void addListener(){
        coursesBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedcourse = coursesBox.getSelectionModel().getSelectedIndex();
            int count = 0;
            for(Object c : coursesBox.getItems()){
                if(count != (int) newValue){
                    count += 1;
                }
                else{
                    Course foundCourse = (Course) c;
                    courseChosen = c.toString();
                    ArrayList<Test> ourTests = foundCourse.getTests();
                    
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
   
    public void spawnACourse(){
//        ArrayList<Test> tests1 = new ArrayList<Test>();
//        Test newTest1 = new Test(1, "First Test", new ArrayList<Question>(), 1);
//        tests1.add(newTest1);
//        
//        //int id, String name, ArrayList<Question> questions
//        
//        
//        ArrayList<Test> tests2 = new ArrayList<Test>();
//        Test newTest2 = new Test(2, "clever", new ArrayList<Question>(), 2);
//        tests2.add(newTest2);
//        
//        Course firstCourse = new Course(1, "Derp", tests1);
//        Course secondCourse = new Course(2, "flerp", tests2);
        ServerConnection serv = ServerConnection.getInstance();
        
        System.out.println(serv.getHardCodedCourses());
        coursesBox.getItems().add(serv.getHardCodedCourses().get(0));
        System.out.println(coursesBox.getItems());
        //coursesBox.setItems(FXCollections.observableArrayList(serv.getHardCodedCourses())); 
    }
    
    @FXML
    private void spawnTestWindow() throws IOException{
        Stage ourStage = (Stage) AddTestButton.getScene().getWindow();
        
        root = FXMLLoader.load(getClass().getResource("addTestView.fxml"));
        ourStage.setScene(new Scene(root));
    }
}
