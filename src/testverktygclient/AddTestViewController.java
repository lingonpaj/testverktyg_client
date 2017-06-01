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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static testverktygclient.CreateTestViewController.courseChosen;

import testverktygclient.models.CompletedTest;
import testverktygclient.models.Course;
import testverktygclient.models.Option;
import testverktygclient.models.Question;
import testverktygclient.models.Test;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddTestViewController implements Initializable {
    
    //<!-- TableView questionsTable, TableColumn (Questioncolumn, Question1Column, Question2Column, Question3Column, Question4Column, CorrectQuestionColumn) -->
    //<!-- Button QuestionButton, Text chosenCourseText, Button (finishTestButton, backButton) -->
    
    @FXML
    private TableView questionsTable;
    
    @FXML
    private TableColumn QuestionColumn, Question1Column, Question2Column, Question3Column, Question4Column, CorrectQuestionColumn;
    
    @FXML
    private Button QuestionButton, finishTestButton, backButton;
    
    @FXML
    private Text chosenCourseText, errorText;
    
    @FXML
    private TextField minutesBox, secondsBox, TestNameField;
    
    public static ArrayList<QuestionOptions> toReturn = new ArrayList<QuestionOptions>();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellValueFactories();
        setText();
        //spawnList();
    }   
    
    public void setText(){
        chosenCourseText.setText("Adding Test for Course: " + courseChosen);
    }
    
    public int getTime(){
        String minutes = minutesBox.getText();
        String minutesPurged = minutes.replaceAll("[^0-9]", "");
        
        errorText.setText("");
        
        String seconds = secondsBox.getText();
        String secondsPurged = seconds.replaceAll("[^0-9]", "");
        
        if(secondsPurged.equals("0") && minutesPurged.equals("0")){
            errorText.setText("Cannot input 0 on both fields.");
            return -1;
        }
        
        
        
        if(secondsPurged.length() == 0 || minutesPurged.length() == 0){
            errorText.setText("Cannot input empty fields.");
            return -1;
        }
        
        if(Integer.parseInt(minutesPurged) < 0){
            errorText.setText("Cannot input minus values for minutes");
            return -1;
        }
        if(Integer.parseInt(secondsPurged) < 0){
            errorText.setText("Cannot input minus values for seconds");
            return -1;
        }
        errorText.setText("");
        int minutesConverted = Integer.parseInt(minutesPurged) * 60;
        int secondsConverted = Integer.parseInt(secondsPurged);

        return (minutesConverted + secondsConverted);
    }
    public void setCellValueFactories(){
        QuestionColumn.setCellValueFactory(new PropertyValueFactory<QuestionOptions, String>("question"));
        Question1Column.setCellValueFactory(new PropertyValueFactory<QuestionOptions, String>("option1"));
        Question2Column.setCellValueFactory(new PropertyValueFactory<QuestionOptions, String>("option2"));
        Question3Column.setCellValueFactory(new PropertyValueFactory<QuestionOptions, String>("option3"));
        Question4Column.setCellValueFactory(new PropertyValueFactory<QuestionOptions, String>("option4"));
        CorrectQuestionColumn.setCellValueFactory(new PropertyValueFactory<QuestionOptions, String>("correct"));
    }
    
    public void spawnList(){
//        ObservableList<QuestionOptions> questionList2 = FXCollections.observableArrayList();
//        Option option1 = new Option(1, "blergh", false);
//        Option option2 = new Option(2, "JOHN CEENA", true);
//        Option option3 = new Option(3, "CORNFLAKES GOD", true);
//        Option option4 = new Option(4, "bla", false);
//
//        questionList2.add(q);
//        questionsTable.setItems(questionList2);
//        questionsTable.refresh();
        
    //}
    }
    
    public void updateList(ArrayList<QuestionOptions> input){
        ObservableList<QuestionOptions> questionList2 = FXCollections.observableArrayList();
        
        ArrayList<QuestionOptions> oldList = new ArrayList<QuestionOptions>();
        
        for(Object c :questionsTable.getItems()){
            QuestionOptions o = (QuestionOptions) c;
            oldList.add(o);
        }
        
        for(QuestionOptions q : input){
            System.out.println("This is how many we have in input: ");
            oldList.add(q);
        }
        
        for(QuestionOptions q : oldList){
            questionList2.add(q);
        }
        questionsTable.setItems(questionList2);
        
        questionsTable.refresh();
    }
    
    @FXML
    private void addTest(){
        System.out.println("Gettime returns: " + getTime());
        int time = getTime();
        if(time < 0){
            errorText.setText("The time must be positive, and both windows filled!");
            //int id, String name, ArrayList<Question> questions, int time
            
        }
        else{
            System.out.println("We found the time of: " + getTime());
            if(TestNameField.getText().length() < 1){
                errorText.setText("Cannot make a test with a empty name.");
            }
            else{
                
                //QuestionOptions String question, Option option1, Option option2, Option option3, Option option4
                //Question int id, String questionText, ArrayList<Option> options
                
                ArrayList<Question> toFill = new ArrayList<Question>();
                
                ArrayList<Option> optionsTouse = new ArrayList<Option>();
                for(QuestionOptions q : toReturn){
                    String name = q.getQuestion();
                    optionsTouse.add(q.getOption1());
                    optionsTouse.add(q.getOption2());
                    optionsTouse.add(q.getOption3());
                    optionsTouse.add(q.getOption4());
                    
                    boolean multi = q.getMulti();
                    
                    Question toAdd = new Question(1,name, optionsTouse, multi);
                    toFill.add(toAdd);
                }
                Test testToReturn = new Test(1,TestNameField.getText(), toFill, getTime());
                
                
                
                CreateTestViewController.getSelectedcourse();
                
                //List<Course> listofcourses = getcoursesfromdb();
                
                //listofcourses.get()
                
                
//                for(Object c : CreateTestViewController.getCoursesBox().getItems()){
//                    Course foundCourse = (Course) c;
//                    for(Test t : foundCourse.getTests()){
//                        System.out.println("Found these tests, BEFORE " + t.toString());
//                    }
//                    
//                }
//                
//                
//                for(Object c : CreateTestViewController.getCoursesBox().getItems()){
//                        Course foundCourse = (Course) c;
//                        if(foundCourse.toString().equals(courseChosen)){
//                            ArrayList<Test> oldList = foundCourse.getTests();
//                            oldList.add(testToReturn);
//                            foundCourse.setTests(oldList);
//                            
//                    }
//                }
//                
//                for(Object c : CreateTestViewController.getCoursesBox().getItems()){
//                    Course foundCourse = (Course) c;
//                    for(Test t : foundCourse.getTests()){
//                        System.out.println("Found these tests " + t.toString());
//                    }
//                    
//                }
            }
            
        }
    }
    
    @FXML
    private void spawnQuestionPopup() throws IOException{
        
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("addQuestionView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add a Question to the test!");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        
        try{
            if(toReturn.get(0).getOption2().toString().length() == 0){
                Option option2 = new Option(2, "n/a", false);
                toReturn.get(0).setOption2(option2);
            }
            if(toReturn.get(0).getOption3().toString().length() == 0){
                Option option3 = new Option(3, "n/a", false);
                toReturn.get(0).setOption3(option3);
            }
             if(toReturn.get(0).getOption4().toString().length() == 0){
                Option option4 = new Option(4, "n/a", false);
                toReturn.get(0).setOption4(option4);
            }
        }
        catch(Exception e){
            System.out.println("Got a empty list to operate on!");
        }
        updateList(toReturn);
        
        
    }
    
}
