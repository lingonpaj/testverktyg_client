/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient.models;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import testverktygclient.models.Answer;

/**
 * @author User
 */
public class QuestionOptions {
    private Answer option1, option2, option3, option4;
    private String correct = "";
    public boolean option1Correct, option2Correct, option3Correct, option4Correct = false;
    private SimpleStringProperty question, option1string, option2string, option3string, option4string;
    private boolean multi;
    
    public QuestionOptions(String question, Answer option1, Answer option2, Answer option3, Answer option4, boolean multi){
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.multi = multi;
        
        this.question = new SimpleStringProperty(question);
        this.option1string = new SimpleStringProperty(option1.getOptionText());
        this.option2string = new SimpleStringProperty(option2.getOptionText());
        this.option3string = new SimpleStringProperty(option3.getOptionText());
        this.option4string = new SimpleStringProperty(option4.getOptionText());
        
        if(option1.isCorrect()){
            correct += "(1)";
            option1Correct = true;
        }
        if(option2.isCorrect()){
            correct += "(2)";
            option2Correct = true;
        }
        if(option3.isCorrect()){
            correct += "(3)";
            option3Correct = true;
        }
        if(option4.isCorrect()){
            correct += "(4)";
            option4Correct = true;
        }
    }
    
    public String getQuestion(){
        return this.question.get();
    }

    public String getOption1string() {
        return this.option1string.get();
    }
    
    public String getOption2string() {
        return this.option2string.get();
    }
    
    public String getOption3string() {
        return this.option3string.get();
    }
    
    public String getOption4string() {
        return this.option4string.get();
    }
    
    public void setOption1string(String newValue){
       option1.setOptionText(newValue);
       this.option1string.set(newValue);
    }
    
    public void setOption2string(String newValue){
       option2.setOptionText(newValue);
       this.option2string.set(newValue);
    }
    
    public void setOption3string(String newValue){
        option3.setOptionText(newValue);
        this.option3string.set(newValue);
    }
    
    public void setOption4string(String newValue){
        option4.setOptionText(newValue);
        this.option4string.set(newValue);
    }
    
    public void setQuestion(String newValue){
        this.question.set(newValue);
    }
    
    public SimpleStringProperty questionProperty(){
        return question;
    }
    
    public SimpleStringProperty option1stringProperty() {
       return option1string;
    }
    
    public SimpleStringProperty option2stringProperty() {
       return option2string;
    }
    
    public SimpleStringProperty option3stringProperty() {
       return option3string;
    }
    
    public SimpleStringProperty option4stringProperty() {
       return option4string;
    }    
    
    public boolean getMulti(){
        return multi;
    }
    
    public void setMulti(){
        this.multi = multi;
    }
    public boolean getoption1Correct(){
        return option1Correct;
    }
    
    public boolean getoption2Correct(){
        return option2Correct;
    }
    
    public boolean getoption3Correct(){
        return option3Correct;
    }
    
    public boolean getoption4Correct(){
        return option4Correct;
    }
    
    public String getCorrect(){
        return correct;
    }

    public Answer getOption1() {
        return option1;
    }
    
    public void setOption1(Answer option1) {
        this.option1 = option1;
    }

    public Answer getOption2() {
        return option2;
    }

    public void setOption2(Answer option2) {
        this.option2 = option2;
    }

    public Answer getOption3() {
        return option3;
    }

    public void setOption3(Answer option3) {
        this.option3 = option3;
    }

    public Answer getOption4() {
        return option4;
    }

    public void setOption4(Answer option4) {
        this.option4 = option4;
    } 
}
