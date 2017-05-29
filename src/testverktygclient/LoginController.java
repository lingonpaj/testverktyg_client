/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Student;
import testverktygclient.models.Teacher;

/**
 *
 * @author Alexander
 */
public class LoginController implements Initializable {
    @FXML
    private Label label;
    
    @FXML
    private Button LoginButton;
    
    @FXML
    private TextField Username;
    
    @FXML
    private TextField Password;
    
    @FXML
    private Text ErrorLogin;
    
    public static Teacher loggedInTeacher;
    public static Student loggedInStudent;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        //Make queries to DB to find both Teacher and Students.
        
        //ArrayList<CompletedTest> completedTests, int userId, 
        //String userName, String password, String firstName, String lastName
        
        ArrayList<CompletedTest> complet = new ArrayList<CompletedTest>();
        Student lol = new Student(complet, 1, "lol", "dakka", "fok u", "u");
        
        
        //Modify to loop through lists, modify check against aquiring PW and Username of respective person
        if(Username.getText().equals("placeholder") && Password.getText().equals("placeholder")){
            
            ErrorLogin.setText("");
            
            String assign = "";
            
            
            //Based on whoever is logged in, create a object of that role
            //String role = whateverElementIsBeingIteratedUpon.getClass().getName();
            //if(role.contains("Teacher"){
            //    loggedInTeacher = whateverElementIsBeingIteratedUpon;
            //    assign = "Teacher";
            //}
            //else{
            //    loggedInStudent = whateverElementIsBeingIteratedUpon;
            //    assign = "Student";
            //}
            
           
            //Spawn the view to whatever respective view we need
            Stage stage;
            Parent root;
            stage = new Stage();
            
            //Depending on who the user is, we spawn that respective view
            
            //root = FXMLLoader.load(getClass().getResource(assign + "Controller.fxml"));
            //stage.setScene(new Scene(root));
            
            //Set the title to respective thing
            //stage.setTitle("The " + assign + " view!");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(LoginButton.getScene().getWindow());
            stage.showAndWait();
        
            }
        else{
            ErrorLogin.setText("Authentication: Failed.");
        }
            
        }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
