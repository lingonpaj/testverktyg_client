package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import testverktygclient.models.CompletedTest;
import testverktygclient.serverconnection.ServerConnection;

public class FXMLTakeTestViewController implements Initializable {
    @FXML
    VBox question_list, option_list;
        
    @FXML
    Label question_number, question_name, question_text;
    
    @FXML
    Button next_button, previous_button, finishbutton;
    
    ServerConnection serverconnection;
    
    CheckBox[] currentcheckboxes;
    
    @FXML
    private Label timerLabel;
    
    Timeline timeline;
    
    private static CompletedTest completedtest;
    private static boolean timeOut = false;

    public static boolean isTimeOut() {
        return timeOut;
    }
    int timeLeft;
    int[][] selectedoptions;
    
    public void finishTest(ActionEvent event) throws IOException{
        int score = 0;
        for (int i = 0; i < selectedoptions.length; i++) {
            System.out.println(selectedoptions[i][0]);
        }
        for(int i = 0; i < serverconnection.testToTake.getQuestions().size(); i++){
            int possiblescore = 0;
            int multiscore = 0;
            System.out.println("checking new question");
            if(serverconnection.testToTake.getQuestions().get(i).isMulti()){ 
                for(int n = 0; n < selectedoptions[i].length; n++){
                    System.out.println("what is this? " + selectedoptions[i][n] + "loop: " + n);
                    System.out.println(serverconnection.testToTake.getQuestions().get(i).getAnswers().get(n).isCorrect());
                    if(serverconnection.testToTake.getQuestions().get(i).getAnswers().get(n).isCorrect()){

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
                            if(serverconnection.testToTake.getQuestions().get(i).getAnswers().get(j).isCorrect()){
                                score++;
                            }   
                        }
                    }
                }
            }
        }
        CompletedTest completedTest = new CompletedTest();
        completedTest.setCourseName(serverconnection.courseNameOfTest);
        completedTest.setUserScore(score);
        completedTest.setTestName(serverconnection.testToTake.getName());
        completedTest.setTestMaxScore(serverconnection.testToTake.getQuestions().size());
        
        
        System.out.println(serverconnection.testToTake.getName());
        
        serverconnection.addCompletedTest(completedTest, serverconnection.loggedInUser.getUserId());
        
        System.out.println("Test completed: " + completedTest.getTestName() + completedTest.getUserScore() + "/" + completedTest.getTestMaxScore());
        
        //below the test should be added to the student currently logged in, then it should load the previous
        //student view page
        
        //add code here
        Parent root = FXMLLoader.load(getClass().getResource("FXMLTestSummaryView.fxml"));
        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
        timeline.stop();
    }
    public void updateTimerLabel() {
        String minutes;
        String seconds;
        System.out.println(timeLeft);
        timeLeft--;
        if (timeLeft / 60 < 10) {
            minutes = "0" + timeLeft / 60;
        } else {
            minutes = "" + timeLeft / 60;
        }
        if (timeLeft % 60 < 10) {
            seconds = "0" + timeLeft % 60;

        } else {
            seconds = "" + timeLeft % 60;
        }

        timerLabel.setText("Time left: " + minutes + ":" + seconds);
    }
    
    public void timer() throws IOException {
        updateTimerLabel();
        if (timeLeft == -1) {
            timeline.stop();
            timeOut = true;
            finishbutton.fire();
            
        }
    }
        
    public void createOptions(int value){
            question_text.setText(serverconnection.testToTake.getQuestions().get(value-1).getQuestion());
            question_number.setText("Question " + value);
            option_list.getChildren().clear();
            int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
            
            
            if(!serverconnection.testToTake.getQuestions().get(value-1).isMulti()){
                ToggleGroup togglegroup = new ToggleGroup();
                for(int i = 0; i < serverconnection.testToTake.getQuestions().get(value-1).getAnswers().size(); i++){
                    RadioButton newoption = new RadioButton(serverconnection.testToTake.getQuestions().get(value-1).getAnswers().get(i).getOptionText());
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
                currentcheckboxes = new CheckBox[serverconnection.testToTake.getQuestions().get(value-1).getAnswers().size()];
                for(int i = 0; i < serverconnection.testToTake.getQuestions().get(value-1).getAnswers().size(); i++){
                    CheckBox newoption = new CheckBox(serverconnection.testToTake.getQuestions().get(value-1).getAnswers().get(i).getOptionText());
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
        for(int i = 0; i < serverconnection.testToTake.getQuestions().size(); i++){
            
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
        serverconnection = ServerConnection.getInstance();
        question_text.setText(serverconnection.testToTake.getQuestions().get(0).getQuestion());
        question_number.setText("Question 1");
        question_name.setText(serverconnection.testToTake.getName());
        option_list.getChildren().clear();
        option_list.setSpacing(10);
        
        selectedoptions = new int[serverconnection.testToTake.getQuestions().size()][];

        for(int i = 0; i < serverconnection.testToTake.getQuestions().size(); i++){
            selectedoptions[i] = new int[serverconnection.testToTake.getQuestions().get(i).getAnswers().size()];
        }
        
        timeLeft = serverconnection.testToTake.getTime();
        updateTimerLabel();

        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> {
                    try {
                        timer();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLTakeTestViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        createButtons();
        createOptions(1);
        
        next_button.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
                        
                        int nextquestion = currentquestion+1;
                        
                        if(nextquestion > serverconnection.testToTake.getQuestions().size()){
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
                            nextquestion = serverconnection.testToTake.getQuestions().size();
                        }
                        updateSelectedButton(nextquestion);
                        createOptions(nextquestion);
                    }
        });
    }    
}
