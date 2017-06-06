package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
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
    private static boolean timeOut;
    int possiblescore;
    int multiscore;
    int score;
    int currentquestion;
    
    public static boolean isTimeOut() {
        return timeOut;
    }
    int timeLeft;
    int[][] selectedoptions;
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    
    DialogPane dialogPane = alert.getDialogPane();

    
    public void finishTest(ActionEvent event) throws IOException {
        score = 0;
        for (int i = 0; i < serverconnection.testToTake.getQuestions().size(); i++) {
            possiblescore = 0;
            multiscore = 0;
            if (serverconnection.testToTake.getQuestions().get(i).isMulti()) {
                calculateScoreForMultiQuestion(i);
            } else {
                calculateScoreForSingleQuestion(i);
            }
        }
        
        CompletedTest completedTest = new CompletedTest();
        completedTest.setCourseName(serverconnection.courseNameOfTest);
        completedTest.setUserScore(score);
        completedTest.setTestName(serverconnection.testToTake.getName());
        completedTest.setTestMaxScore(serverconnection.testToTake.getQuestions().size());


        
        if (timeOut == true) {
            timeline.stop();
            serverconnection.addCompletedTest(completedTest, serverconnection.loggedInUser.getId());
            alert.hide();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLTestSummaryView.fxml"));
                Scene s = new Scene(root);
                Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stg.setScene(s);
            } catch (Exception e) {
            }
            
        }else if (timeOut == false) {
            alert.setTitle("Finish Test Confirmation");
            alert.setHeaderText("Note! You can not undo this!");
            alert.setContentText("Are you sure you want to finish the test ?");
            Optional<ButtonType> action = alert.showAndWait();
            
            if (action.get() == ButtonType.OK) {
                if(!timeOut){
                    serverconnection.addCompletedTest(completedTest, serverconnection.loggedInUser.getId());
                    Parent root = FXMLLoader.load(getClass().getResource("FXMLTestSummaryView.fxml"));
                    Scene s = new Scene(root);
                    Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stg.setScene(s);
                    timeline.stop();
                }
                
            }
        }
    }
    
    public void calculateScoreForMultiQuestion(int questionNumber) {
        
        for (int n = 0; n < selectedoptions[questionNumber].length; n++) {
            if (serverconnection.testToTake.getQuestions().get(questionNumber).getAnswers().get(n).isCorrect()) {
                
                if (selectedoptions[questionNumber][n] > 0) {
                    multiscore++;
                }
                possiblescore++;
            } else {
                if (selectedoptions[questionNumber][n] > 0) {
                    multiscore--;
                }
            }
        }
        if (multiscore == possiblescore) {
            score++;
        }
    }
    
    public void calculateScoreForSingleQuestion(int questionNumber) {
        if ((selectedoptions[questionNumber][0]) > 0) {
            for (int j = 0; j < selectedoptions[questionNumber].length; j++) {
                
                if (selectedoptions[questionNumber][0] - 1 == j) {
                    if (serverconnection.testToTake.getQuestions().get(questionNumber).getAnswers().get(j).isCorrect()) {
                        
                        score++;
                    }
                }
            }
        }
    }
    
    public void updateTimerLabel() {
        String minutes;
        String seconds;
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
    
    public void createOptions(int value) {
        question_text.setText(serverconnection.testToTake.getQuestions().get(value - 1).getQuestion());
        question_number.setText("Question " + value);
        option_list.getChildren().clear();
        currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
        
        if (!serverconnection.testToTake.getQuestions().get(value - 1).isMulti()) {
            createRadioButtons(value);
        } else {
            createCheckBoxes(value);
        }
    }

    public void createRadioButtons(int value) {
        ToggleGroup togglegroup = new ToggleGroup();
        for (int i = 0; i < serverconnection.testToTake.getQuestions().get(value - 1).getAnswers().size(); i++) {
            RadioButton newoption = new RadioButton(serverconnection.testToTake.getQuestions().get(value - 1).getAnswers().get(i).getOptionText());
            newoption.setOnAction((ActionEvent event) -> {
                for (int i1 = 0; i1 < newoption.getToggleGroup().getToggles().size(); i1++) {
                    if (newoption == newoption.getToggleGroup().getToggles().get(i1)) {
                        selectedoptions[currentquestion - 1][0] = i1 + 1;
                    }
                }
            });
            newoption.getStyleClass().add("radiobutton");
            newoption.setToggleGroup(togglegroup);
            option_list.getChildren().add(newoption);
        }
        if (selectedoptions[currentquestion - 1][0] > 0) {
            togglegroup.getToggles().get(selectedoptions[currentquestion - 1][0] - 1).setSelected(true);
        }
    }
    
    public void createCheckBoxes(int value) {
        currentcheckboxes = new CheckBox[serverconnection.testToTake.getQuestions().get(value - 1).getAnswers().size()];
        for (int i = 0; i < serverconnection.testToTake.getQuestions().get(value - 1).getAnswers().size(); i++) {
            CheckBox newoption = new CheckBox(serverconnection.testToTake.getQuestions().get(value - 1).getAnswers().get(i).getOptionText());
            newoption.setOnAction((ActionEvent event) -> {
                for (int i1 = 0; i1 < currentcheckboxes.length; i1++) {
                    if (newoption == currentcheckboxes[i1]) {
                        if (newoption.isSelected()) {
                            selectedoptions[currentquestion - 1][i1] = i1 + 1;
                        } else {
                            selectedoptions[currentquestion - 1][i1] = 0;
                        }
                    }
                }
            });
            currentcheckboxes[i] = newoption;
            newoption.getStyleClass().add("checkbox");
            option_list.getChildren().add(newoption);
            if (selectedoptions[currentquestion - 1][i] > 0) {
                newoption.setSelected(true);
            }
        }
    }

    public void updateSelectedButton(int value) {
        currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
        question_list.getChildren().get(currentquestion - 1).getStyleClass().remove("questioninactivebutton");
        question_list.getChildren().get(currentquestion - 1).getStyleClass().add("questioninactivebutton");
        question_list.getChildren().get(value - 1).getStyleClass().remove("questioninactivebutton");
        question_list.getChildren().get(value - 1).getStyleClass().add("questionactivebutton");
    }
    
    public void createButtons() {
        question_list.setSpacing(2);
        for (int i = 0; i < serverconnection.testToTake.getQuestions().size(); i++) {
            
            Button newbutton = new Button("Question " + (i + 1));
            if (i == 0) {
                newbutton.getStyleClass().add("questionactivebutton");
            } else {
                newbutton.getStyleClass().add("questioninactivebutton");
            }
            newbutton.prefWidthProperty().bind(question_list.widthProperty());
            
            newbutton.setOnAction((ActionEvent event) -> {
                int value = Integer.parseInt(newbutton.getText().replaceAll("[^0-9]", ""));
                updateSelectedButton(value);
                createOptions(value);
            });
            
            question_list.getChildren().add(newbutton);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeOut = false;
        serverconnection = ServerConnection.getInstance();
        question_text.setText(serverconnection.testToTake.getQuestions().get(0).getQuestion());
        question_number.setText("Question 1");
        question_name.setText(serverconnection.testToTake.getName());
        option_list.getChildren().clear();
        option_list.setSpacing(10);
        
        selectedoptions = new int[serverconnection.testToTake.getQuestions().size()][];
        
        for (int i = 0; i < serverconnection.testToTake.getQuestions().size(); i++) {
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
        
        next_button.setOnAction((ActionEvent event) -> {
            int currentquestion1 = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
            int nextquestion = currentquestion1 + 1;
            if (nextquestion > serverconnection.testToTake.getQuestions().size()) {
                nextquestion = 1;
            }
            updateSelectedButton(nextquestion);
            createOptions(nextquestion);
        });
        
        previous_button.setOnAction((ActionEvent event) -> {
            int currentquestion1 = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
            int nextquestion = currentquestion1 - 1;
            if (nextquestion < 1) {
                nextquestion = serverconnection.testToTake.getQuestions().size();
            }
            updateSelectedButton(nextquestion);
            createOptions(nextquestion);
        });
        
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("myAlert");
        dialogPane.setMinSize(300, 200);
    }
}
