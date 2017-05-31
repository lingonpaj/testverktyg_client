/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import testverktygclient.models.CompletedTest;
import testverktygclient.serverconnection.ServerConnection;

/**
 * FXML Controller class
 *
 * @author Alexander
 */
public class FXMLTakeTestViewController implements Initializable {

    /**
     * Initializes the controller class.
     */

    
    @FXML
    VBox question_list, option_list;
        
    @FXML
    Label question_number, question_name, question_text;
    
    @FXML
    Button next_button, previous_button;
    
    @FXML
    private Label timerLabel;
    
    ServerConnection serverconnection = new ServerConnection();
 
    int timeLeft;
    int[] selectedoptions;
    
    public void finishTest(){
        int score = 0;
        for(int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size(); i++){
            if((selectedoptions[i]-1) > -1){
                if(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(i).getOptions().get(selectedoptions[i]-1).isCorrect()){
                    score++;
                }
            }
        }
        CompletedTest completedtest = new CompletedTest(0, serverconnection.getHardCodedCourses().get(0).getName(), 
                serverconnection.getHardCodedCourses().get(0).getTests().get(0).getName(), score, 
                serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size());
        
        System.out.println("Test completed: " + completedtest.getTestName() + completedtest.getUserScore() + "/" + completedtest.getTestMaxScore());
        
        //below the test should be added to the student currently logged in, then it should load the previous
        //student view page
        
        //add code here
    }
    
    public void createOptions(int value){
            question_text.setText(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).getQuestion());
            question_number.setText("Question " + value);
            option_list.getChildren().clear();
            int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
            ToggleGroup togglegroup = new ToggleGroup();
            for(int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).getOptions().size(); i++){
                RadioButton newoption = new RadioButton(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).getOptions().get(i).getOptionText());
                newoption.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        for(int i = 0; i < newoption.getToggleGroup().getToggles().size(); i++){
                            if(newoption == newoption.getToggleGroup().getToggles().get(i)){
                                selectedoptions[currentquestion-1] = i+1;
                                System.out.println(selectedoptions[currentquestion-1]);
                            }
                        }
                    }
                });
                newoption.setToggleGroup(togglegroup);
                option_list.getChildren().add(newoption);
                System.out.println(newoption.getToggleGroup().getToggles().size());
            }
            if(selectedoptions[currentquestion-1] > 0){
                togglegroup.getToggles().get(selectedoptions[currentquestion-1]-1).setSelected(true);
            }
    }
    
    public void createButtons(){
        for(int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size(); i++){
            
            Button newbutton = new Button("Question " + (i+1));
            newbutton.prefWidthProperty().bind(question_list.widthProperty());
            
            newbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int value = Integer.parseInt(newbutton.getText().replaceAll("[^0-9]", ""));
                    createOptions(value);
                }
            });

            question_list.getChildren().add(newbutton);
        }        
    }
    
    public void timer(){
        timeLeft--;
        timerLabel.setText("Time left: " + timeLeft/60 + ":" + timeLeft%60);
        System.out.println("Time left: " + timeLeft/60 + " minuter");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerConnection serverconnection = new ServerConnection();
        question_text.setText(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(0).getQuestion());
        question_number.setText("Question 1");
        question_name.setText(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getName());
        option_list.getChildren().clear();
 
        selectedoptions = new int[serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size()];
        
        timeLeft = serverconnection.getHardCodedCourses().get(0).getTests().get(0).getTime();
        timerLabel.setText("Time left: " + timeLeft/60 + ":00" );

        
        Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(1000),
            ae -> timer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        createButtons();
        createOptions(1);
        
        next_button.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
                        
                        int nextquestion = currentquestion+1;
                        
                        if(nextquestion > serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size()){
                            nextquestion = 1;
                        }
                        
                        createOptions(nextquestion);
                    }
        });
        
        previous_button.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
                        
                        int nextquestion = currentquestion-1;
                        
                        if(nextquestion < 1){
                            nextquestion = serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size();
                        }
                        
                        createOptions(nextquestion);
                    }
        });   
        }
    }    
