/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.util.ArrayList;
import testverktygclient.models.Option;

/**
 * @author User
 */
public class QuestionOptions {
    private Option option1, option2, option3, option4;
    private String question;
    private String correct = "";
    public boolean option1Correct, option2Correct, option3Correct, option4Correct = false;
    private boolean multi;
    
    public QuestionOptions(String question, Option option1, Option option2, Option option3, Option option4, boolean multi){
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.multi = multi;
        
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

    public Option getOption1() {
        return option1;
    }
    
    public void setOption1(Option option1) {
        this.option1 = option1;
    }

    public Option getOption2() {
        return option2;
    }

    public void setOption2(Option option2) {
        this.option2 = option2;
    }

    public Option getOption3() {
        return option3;
    }

    public void setOption3(Option option3) {
        this.option3 = option3;
    }

    public Option getOption4() {
        return option4;
    }

    public void setOption4(Option option4) {
        this.option4 = option4;
    } 
}
