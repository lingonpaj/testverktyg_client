/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient.models;

import java.util.ArrayList;
import testverktygclient.models.Answer;

/**
 * @author User
 */
public class QuestionOptions {
    private Answer option1, option2, option3, option4;
    private String question;
    private String correct = "";
    public boolean option1Correct, option2Correct, option3Correct, option4Correct = false;
    private String option1string, option2string, option3string, option4string;
    private boolean multi;
    
    public QuestionOptions(String question, Answer option1, Answer option2, Answer option3, Answer option4, boolean multi){
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.multi = multi;
        
        this.option1string = option1.getOptionText();
        this.option2string = option2.getOptionText();
        this.option3string = option3.getOptionText();
        this.option4string = option4.getOptionText();
        
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

    public String getOption1string() {
        return option1string;
    }

    public void setOption1string(String option1string) {
        this.option1string = option1string;
    }

    public String getOption2string() {
        return option2string;
    }

    public void setOption2string(String option2string) {
        this.option2string = option2string;
    }

    public String getOption3string() {
        return option3string;
    }

    public void setOption3string(String option3string) {
        this.option3string = option3string;
    }

    public String getOption4string() {
        return option4string;
    }

    public void setOption4string(String option4string) {
        this.option4string = option4string;
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
    
    public String getQuestion(){
        return question;
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
