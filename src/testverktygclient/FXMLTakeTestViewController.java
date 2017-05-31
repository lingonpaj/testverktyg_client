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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    
    ServerConnection serverconnection = new ServerConnection();
    
    CheckBox[] currentcheckboxes;

    int[][] selectedoptions;
    
    public void finishTest(){
        int score = 0;
        for (int i = 0; i < selectedoptions.length; i++) {
            System.out.println(selectedoptions[i][0]);
        }
        for(int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size(); i++){
            int possiblescore = 0;
            int multiscore = 0;
            System.out.println("checking new question");
            if(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(i).isMulti()){ 
                for(int n = 0; n < selectedoptions[i].length; n++){
                    System.out.println("what is this? " + selectedoptions[i][n] + "loop: " + n);
                    System.out.println(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(i).getOptions().get(n).isCorrect());
                    if(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(i).getOptions().get(n).isCorrect()){

                        if(selectedoptions[i][n] > 0){
                            multiscore++;
                        }
                        possiblescore++;
                    }else{
                        System.out.println("does this ever happen?");
                        System.out.println("plesasas" + selectedoptions[i][n]);
                        if(selectedoptions[i][n] > 0){
                            multiscore--;
                        }
                    }
                }
                System.out.println("multi: "+multiscore + "poss: " + possiblescore);
                if(multiscore == possiblescore){
                    score++;
                }
            }
            else{
                if((selectedoptions[i][0]) > 0){
                    for (int j = 0; j < selectedoptions[i].length; j++) {
                        if(selectedoptions[i][0]-1 == j){
                            if(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(i).getOptions().get(j).isCorrect()){
                                score++;
                            }   
                        }
                    }
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
            
            
            if(!serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).isMulti()){
                ToggleGroup togglegroup = new ToggleGroup();
                for(int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).getOptions().size(); i++){
                    RadioButton newoption = new RadioButton(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).getOptions().get(i).getOptionText());
                    newoption.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            for(int i = 0; i < newoption.getToggleGroup().getToggles().size(); i++){
                                if(newoption == newoption.getToggleGroup().getToggles().get(i)){
                                    selectedoptions[currentquestion-1][0] = i+1;
                                }
                            }
                        }
                    });
                    newoption.getStyleClass().add("radiobutton");
                    newoption.setToggleGroup(togglegroup);
                    option_list.getChildren().add(newoption);
                }
                if(selectedoptions[currentquestion-1][0] > 0){
                    togglegroup.getToggles().get(selectedoptions[currentquestion-1][0]-1).setSelected(true);
                }
            }else{
                currentcheckboxes = new CheckBox[serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).getOptions().size()];
                for(int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).getOptions().size(); i++){
                    CheckBox newoption = new CheckBox(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value-1).getOptions().get(i).getOptionText());
                    newoption.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            for(int i = 0; i < currentcheckboxes.length; i++){
                                if(newoption == currentcheckboxes[i]){
                                    if(newoption.isSelected()){
                                        selectedoptions[currentquestion-1][i] = i+1;
                                    }else{
                                        selectedoptions[currentquestion-1][i] = 0;
                                    }
                                }
                            }
                        }
                    });
                    currentcheckboxes[i] = newoption;
                    newoption.getStyleClass().add("checkbox");
                    option_list.getChildren().add(newoption);
                    if(selectedoptions[currentquestion-1][i] > 0){
                        newoption.setSelected(true);
                    }
                }
            }
    }
    
    public void updateSelectedButton(int value){
        int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
        question_list.getChildren().get(currentquestion-1).getStyleClass().remove("questioninactivebutton");
        question_list.getChildren().get(currentquestion-1).getStyleClass().add("questioninactivebutton");
        question_list.getChildren().get(value-1).getStyleClass().remove("questioninactivebutton");
        question_list.getChildren().get(value-1).getStyleClass().add("questionactivebutton");
    }
    
    public void createButtons(){
        question_list.setSpacing(2);
        for(int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size(); i++){
            
            Button newbutton = new Button("Question " + (i+1));
            if(i == 0){
                newbutton.getStyleClass().add("questionactivebutton");
            }else{
                newbutton.getStyleClass().add("questioninactivebutton");
            }
            newbutton.prefWidthProperty().bind(question_list.widthProperty());
            
            newbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int value = Integer.parseInt(newbutton.getText().replaceAll("[^0-9]", ""));
                    updateSelectedButton(value);
                    createOptions(value);
                }
            });

            question_list.getChildren().add(newbutton);
        }        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerConnection serverconnection = new ServerConnection();
        question_text.setText(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(0).getQuestion());
        question_number.setText("Question 1");
        question_name.setText(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getName());
        option_list.getChildren().clear();
        option_list.setSpacing(10);
        
        selectedoptions = new int[serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size()][];

        for(int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size(); i++){
            selectedoptions[i] = new int[serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(i).getOptions().size()];
        }
        
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
                        updateSelectedButton(nextquestion);
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
                        updateSelectedButton(nextquestion);
                        createOptions(nextquestion);
                    }
        });
    }    
}
