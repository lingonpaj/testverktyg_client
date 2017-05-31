/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import testverktygclient.models.CompletedTest;
import testverktygclient.serverconnection.ServerConnection;

/**
 * FXML Controller class
 *
 * @author bujam
 *
 */
public class FXMLTestSummaryViewController implements Initializable {

    @FXML
    private Label scoreLabel;
    
    @FXML
    private Label timeOutOrFinishLabel;
    
    ServerConnection serverconnection = new ServerConnection();

    //serverconnection.getStudent().getTests().get(serverconnection.getStudent().getTests().size()-1);
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (FXMLTakeTestViewController.isTimeOut() == true) {
            timeOutOrFinishLabel.setText("Time is up!");
            scoreLabel.setText("Testname: " + FXMLTakeTestViewController.getCompletedtest().getTestName() + "." + "\n" + "Correct answers: "
                    + FXMLTakeTestViewController.getCompletedtest().getUserScore() + " out of "
                    + FXMLTakeTestViewController.getCompletedtest().getTestMaxScore());
            
        }else if(FXMLTakeTestViewController.isTimeOut() == false){
            timeOutOrFinishLabel.setText("You pressed finish!");
            scoreLabel.setText("Testname: " + FXMLTakeTestViewController.getCompletedtest().getTestName() + "." + "\n" + "Correct answers: "
                    + FXMLTakeTestViewController.getCompletedtest().getUserScore() + " out of "
                    + FXMLTakeTestViewController.getCompletedtest().getTestMaxScore());
        }
    }
}
