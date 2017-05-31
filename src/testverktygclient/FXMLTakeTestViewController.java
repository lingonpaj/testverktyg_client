/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    Button next_button, previous_button, finishbutton;

    @FXML
    private Label timerLabel;

    ServerConnection serverconnection = new ServerConnection();

    Timeline timeline;

    private static CompletedTest completedtest;
    private static boolean timeOut = false;

    public static boolean isTimeOut() {
        return timeOut;
    }

    

    int timeLeft;
    int[] selectedoptions;

    public static CompletedTest getCompletedtest() {
        return completedtest;
    }

    public void finishTest(ActionEvent event) throws IOException {
        int score = 0;
        for (int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size(); i++) {
            if ((selectedoptions[i] - 1) > -1) {
                if (serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(i).getOptions().get(selectedoptions[i] - 1).isCorrect()) {
                    score++;
                }
            }
        }
        completedtest = new CompletedTest(0, serverconnection.getHardCodedCourses().get(0).getName(),
                serverconnection.getHardCodedCourses().get(0).getTests().get(0).getName(), score,
                serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size());

        System.out.println("Test completed: " + completedtest.getTestName() + completedtest.getUserScore() + "/" + completedtest.getTestMaxScore());

        //below the test should be added to the student currently logged in, then it should load the previous
        //student view page
        //add code here
        Parent root = FXMLLoader.load(getClass().getResource("FXMLTestSummaryView.fxml"));
        root.getStylesheets().add(getClass().getResource("taketestview.css").toExternalFo‌​rm());
        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
        timeline.stop();
        
    }

    public void createOptions(int value) {
        question_text.setText(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value - 1).getQuestion());
        question_number.setText("Question " + value);
        option_list.getChildren().clear();
        int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));
        ToggleGroup togglegroup = new ToggleGroup();
        for (int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value - 1).getOptions().size(); i++) {
            RadioButton newoption = new RadioButton(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(value - 1).getOptions().get(i).getOptionText());
            newoption.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (int i = 0; i < newoption.getToggleGroup().getToggles().size(); i++) {
                        if (newoption == newoption.getToggleGroup().getToggles().get(i)) {
                            selectedoptions[currentquestion - 1] = i + 1;
                            System.out.println(selectedoptions[currentquestion - 1]);
                        }
                    }
                }
            });
            newoption.setToggleGroup(togglegroup);
            option_list.getChildren().add(newoption);
            System.out.println(newoption.getToggleGroup().getToggles().size());
        }
        if (selectedoptions[currentquestion - 1] > 0) {
            togglegroup.getToggles().get(selectedoptions[currentquestion - 1] - 1).setSelected(true);
        }
    }

    public void createButtons() {
        for (int i = 0; i < serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size(); i++) {

            Button newbutton = new Button("Question " + (i + 1));
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServerConnection serverconnection = new ServerConnection();
        question_text.setText(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().get(0).getQuestion());
        question_number.setText("Question 1");
        question_name.setText(serverconnection.getHardCodedCourses().get(0).getTests().get(0).getName());
        option_list.getChildren().clear();

        selectedoptions = new int[serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size()];

        timeLeft = serverconnection.getHardCodedCourses().get(0).getTests().get(0).getTime()+1;
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

        next_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));

                int nextquestion = currentquestion + 1;

                if (nextquestion > serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size()) {
                    nextquestion = 1;
                }

                createOptions(nextquestion);
            }
        });

        previous_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int currentquestion = Integer.parseInt(question_number.getText().replaceAll("[^0-9]", ""));

                int nextquestion = currentquestion - 1;

                if (nextquestion < 1) {
                    nextquestion = serverconnection.getHardCodedCourses().get(0).getTests().get(0).getQuestions().size();
                }

                createOptions(nextquestion);
            }
        });
    }
}
