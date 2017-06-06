/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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
    
    ServerConnection serverconnection = ServerConnection.getInstance();
    @FXML
    private Label summaryLabel;

    //serverconnection.getStudent().getTests().get(serverconnection.getStudent().getTests().size()-1);
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (FXMLTakeTestViewController.isTimeOut() == true) {
            timeOutOrFinishLabel.setText("Time is up!");
            scoreLabel.setText("Testname: " + serverconnection.getLastCompletedTest().getTestName() + "." + "\n" + "Correct answers: "
                    + serverconnection.getLastCompletedTest().getUserScore() + " out of "
                    + serverconnection.getLastCompletedTest().getTestMaxScore());
            
        }else if(FXMLTakeTestViewController.isTimeOut() == false){
            timeOutOrFinishLabel.setText("You pressed finish!");
            scoreLabel.setText("Testname: " + serverconnection.getLastCompletedTest().getTestName() + "." + "\n" + "Correct answers: "
                    + serverconnection.getLastCompletedTest().getUserScore() + " out of "
                    + serverconnection.getLastCompletedTest().getTestMaxScore());
        }
    }

    @FXML
    private void finish(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StudentView.fxml"));
        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
    }
}
