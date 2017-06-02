/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import testverktygclient.models.QuestionOptions;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static testverktygclient.CreateTestViewController.courseChosen;
import testverktygclient.models.Answer;
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
    private Text errorText;
    
    @FXML
    private TextField minutesBox, secondsBox, TestNameField;
    public static ObservableList<QuestionOptions> toReturn;

    
    @FXML
    private Label loggedInAsLabel, chosenCourseText;
    
    private ServerConnection serverConnection;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toReturn = FXCollections.observableArrayList();
        questionsTable.setItems(toReturn);
        serverConnection = ServerConnection.getInstance();
        setCellValueFactories();
        setText();
        loggedInAsLabel.setText("Logged in as: " + serverConnection.loggedInUser.getFirstName()
                + " " + serverConnection.loggedInUser.getLastName());
    }   
    
    public void setText(){
        chosenCourseText.setText("Adding Test for Course: " + courseChosen.getName());
    }
    
    @FXML
    private void signOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Hämta knappen, hämta stagen
        stage.setScene(s); // byter ut gamla stage mot nya, sätt en ny stage
        stage.show();
    }
    
    public int getTime(){

        String minutes = minutesBox.getText();
        String minutesPurged = minutes.replaceAll("[^0-9]", "");
        
        int minutesConverted = 0;
        int secondsConverted = 0;
        
        
        errorText.setText("");
        
        String seconds = secondsBox.getText();
        String secondsPurged = seconds.replaceAll("[^0-9]", "");
        
        if(!secondsPurged.isEmpty()){
            secondsConverted = Integer.parseInt(secondsPurged);
        }
        
        if(!minutesPurged.isEmpty()){
            minutesConverted = Integer.parseInt(minutesPurged)*60;
        }
        
        
        if(secondsPurged.equals("0") && minutesPurged.equals("0")){
            errorText.setText("Cannot input 0 on both fields.");
            return -1;
        }
        
        
        
        if(secondsPurged.length() == 0 && minutesPurged.length() == 0){
            errorText.setText("Cannot input empty fields.");
            return -1;
        }
        
        if(secondsConverted < 0){
            errorText.setText("Cannot input minus values for minutes");
            return -1;
        }
        if(minutesConverted < 0){
            errorText.setText("Cannot input minus values for seconds");
            return -1;
        }
        errorText.setText("");

        
        System.out.println(secondsConverted);
        System.out.println(minutesConverted);
        
        return (minutesConverted + secondsConverted);
    }
    public void setCellValueFactories(){
        QuestionColumn.setCellValueFactory(new PropertyValueFactory<QuestionOptions, String>("question"));
        Question1Column.setCellValueFactory(new PropertyValueFactory<QuestionOptions, Answer>("option1string"));
        Question2Column.setCellValueFactory(new PropertyValueFactory<QuestionOptions, Answer>("option2string"));
        Question3Column.setCellValueFactory(new PropertyValueFactory<QuestionOptions, Answer>("option3string"));
        Question4Column.setCellValueFactory(new PropertyValueFactory<QuestionOptions, Answer>("option4string"));
        CorrectQuestionColumn.setCellValueFactory(new PropertyValueFactory<QuestionOptions, String>("correct"));
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
                
                
                System.out.println("size of return: " + toReturn.size());
                for(QuestionOptions q : toReturn){
                    ArrayList<Answer> optionsTouse = new ArrayList<Answer>();
                    String name = q.getQuestion();

                    if(q.getOption1().getOptionText().length() > 0){
                        optionsTouse.add(q.getOption1());
                    }
                    if(q.getOption2().getOptionText().length() > 0){
                        optionsTouse.add(q.getOption2());
                    }
                    if(q.getOption3().getOptionText().length() > 0){
                        optionsTouse.add(q.getOption3());
                    }
                    if(q.getOption4().getOptionText().length() > 0){
                        optionsTouse.add(q.getOption4());
                    }

                    
                    boolean multi = q.getMulti();
                    System.out.println("Size now please?" + optionsTouse.size());
                    Question toAdd = new Question(1,name, optionsTouse, multi);
                    toFill.add(toAdd);
                }
                Test testToReturn = new Test(1,TestNameField.getText(), toFill, getTime());
                
                serverConnection.addTest(testToReturn, courseChosen.getId());
                
                backButton.fire();
               
                
            }
            
        }
    }
    
    @FXML
    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateTestView.fxml"));

        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
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
            
            for (int i = 0; i < toReturn.size(); i++) {
                if(toReturn.get(i).getOption2().toString().length() == 0){
                    Answer option2 = new Answer(2, "n/a", false);
                    toReturn.get(i).setOption2(option2);
                }
                if(toReturn.get(i).getOption3().toString().length() == 0){
                    Answer option3 = new Answer(3, "n/a", false);
                    toReturn.get(i).setOption3(option3);
                }
                if(toReturn.get(i).getOption4().toString().length() == 0){
                    Answer option4 = new Answer(4, "n/a", false);
                    toReturn.get(i).setOption4(option4);
                }
            }
        }
        catch(Exception e){
            System.out.println("Got a empty list to operate on!");
        }
        
        
    }
    
}
