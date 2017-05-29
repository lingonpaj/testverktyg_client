/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import testverktygclient.serverconnection.ServerConnection;

/**
 *
 * @author Alexander
 */
public class FXMLDocumentController implements Initializable {
    ServerConnection sc;
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText(sc.hardCodedCourses.get(0).getTests().get(0).getQuestions().get(1).getQuestion());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sc = new ServerConnection();
    }    
    
}
