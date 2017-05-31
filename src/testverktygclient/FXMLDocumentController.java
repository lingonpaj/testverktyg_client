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
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent root = FXMLLoader.load(getClass().getResource("FXMLTakeTestView.fxml"));
        root.getStylesheets().add(getClass().getResource("taketestview.css").toExternalFo‌​rm());
        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sc = new ServerConnection();
    }    
    
}
