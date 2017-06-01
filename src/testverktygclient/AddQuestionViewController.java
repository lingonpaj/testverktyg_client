/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import testverktygclient.models.Option;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AddQuestionViewController implements Initializable {
    
    @FXML
    private CheckBox option1Correct, option2Correct, option3Correct, option4Correct, MultiCorrect;
    
    @FXML
    private Text errorText;
    
    @FXML
    private Button finishButton;
    
    @FXML
    private TextField QuestionNameField, option1Field, option2Field, option3Field, option4Field;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void addQuestion(){
        ArrayList<QuestionOptions> question = new ArrayList<QuestionOptions>();
        ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
        checkboxes.add(option1Correct);
        checkboxes.add(option2Correct);
        checkboxes.add(option3Correct);
        checkboxes.add(option4Correct);
        checkboxes.add(MultiCorrect);
        
        ArrayList<TextField> optionField = new ArrayList<TextField>();
        optionField.add(option1Field);
        optionField.add(option2Field);
        optionField.add(option3Field);
        optionField.add(option4Field);
        
        boolean multi = MultiCorrect.isSelected();
        int amountofRight = 0;
        
        for(CheckBox c : checkboxes){
            if (c.isSelected()){
                amountofRight += 1;
            }
        }
        
        System.out.println("Amount of right is: " + amountofRight);
        
        boolean firstPass = true;
        
        if(amountofRight < 1){
            errorText.setText("At least one option must be right.");
            firstPass = false;
        }
        
        if(amountofRight != 1 && !multi){
            errorText.setText("You must pick one option.");
            firstPass = false;
        }
        
        if(multi && amountofRight < 2){
            errorText.setText("You must have several options for Multi");
            firstPass = false;
        }
        
        if(QuestionNameField.getText().length() < 1){
            errorText.setText("A question must have a Question!");
            firstPass = false;
        }
        
        int count = 0;
        for(TextField t : optionField){
            if (t.getText().length() > 0){
                count += 1;
            }
        }
        
        
        boolean pass = false;
        if(count < 2){
            errorText.setText("Must have at least two options");
            
        }
        else{
            pass = true;
        }
        if(pass && firstPass){
        errorText.setText("");
        
        int count3 = 0;
        ArrayList<Option> ourOptions = new ArrayList<Option>();
        for(TextField t : optionField){
            boolean assign = checkboxes.get(count3).isSelected();
            if(assign){
                System.out.println("Assign is true for: " + count3);
            }
            Option option = new Option(1, t.getText(), assign);
            count3 += 1;
            ourOptions.add(option);
        }
        
        Option option2 = new Option(1, "NONE GIVEN", false);
        Option option3 = new Option(2, "NONE GIVEN", false);
        Option option4 = new Option(3, "NONE GIVEN", false);
        ArrayList<QuestionOptions> toAdd = new ArrayList<QuestionOptions>();
        switch(count3){
            
                case 1:
                    
                    toAdd.add(new QuestionOptions(QuestionNameField.getText(), ourOptions.get(0), option2, option3, option4));
                    AddTestViewController.toReturn = toAdd;
                    //AddTestViewController.toReturn
                    break;
                case 2:
                    toAdd.add(new QuestionOptions(QuestionNameField.getText(), ourOptions.get(0), ourOptions.get(1), option3, option4));
                    AddTestViewController.toReturn = toAdd;
                    break;
                case 3:
                    toAdd.add(new QuestionOptions(QuestionNameField.getText(), ourOptions.get(0), ourOptions.get(1), ourOptions.get(2), option4));
                    AddTestViewController.toReturn = toAdd;
                    break;
                case 4:
                    toAdd.add(new QuestionOptions(QuestionNameField.getText(), ourOptions.get(0), ourOptions.get(1), ourOptions.get(2), ourOptions.get(3)));
                    AddTestViewController.toReturn = toAdd;
                    break;
        }
        
        Stage ourStage = (Stage) finishButton.getScene().getWindow();
        ourStage.close();
        
        }        
        
        
    }
        
}
