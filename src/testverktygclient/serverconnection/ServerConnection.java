package testverktygclient.serverconnection;

import java.util.ArrayList;
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Course;
import testverktygclient.models.Option;
import testverktygclient.models.Question;
import testverktygclient.models.Student;
import testverktygclient.models.Teacher;
import testverktygclient.models.Test;
import testverktygclient.models.User;

public class ServerConnection {
    private static ServerConnection instance;
    //HARDCODED to test frontend
    public ArrayList<User> hardCodedUsers;
    public ArrayList<Course> hardCodedCourses;
    public User loggedInUser;
    
    //Creates hardcoded users and courses so we can test front end
    private ServerConnection() {
        hardCodedCourses = new ArrayList();
        hardCodedUsers = new ArrayList();
        
        CompletedTest completedTest = new CompletedTest(1, "Code 1","Completed Test", 34, 22);
        ArrayList<CompletedTest> hardCodeCTest = new ArrayList();
        hardCodeCTest.add(completedTest);
        //Adding users
        hardCodedUsers.add(new Student(hardCodeCTest, 1, "skarl", "password1", "Sofia", "Karlsson"));
        hardCodedUsers.add(new Teacher(2, "kjoha", "password2", "Kalle", "Johansson"));
        
        //Adding courses (that has test->Questions->Options)
        ArrayList<Option> hardcodedOptions = new ArrayList();
        hardcodedOptions.add(new Option(1, "Arsenal", true));
        hardcodedOptions.add(new Option(2, "Chelsea", false));
        hardcodedOptions.add(new Option(2, "Spurs", false));
        
        Question q1 = new Question(1, "Wich team doesn't suck?", hardcodedOptions, false);
        
        ArrayList<Option> hardcodedOptions2 = new ArrayList();
        hardcodedOptions2.add(new Option(1, "Emirates Stadium", false));
        hardcodedOptions2.add(new Option(2, "Old Trafford", true));
        hardcodedOptions2.add(new Option(2, "White Hart Lane", false));
        
        Question q2 = new Question(2, "What is the name of Manchester Uniteds home arena?", hardcodedOptions2, false);
        
        ArrayList<Option> hardcodedOptions3 = new ArrayList();
        hardcodedOptions3.add(new Option(1, "Blue", false));
        hardcodedOptions3.add(new Option(2, "Yellow", true));
        hardcodedOptions3.add(new Option(2, "Green", true));
        
        Question q3 = new Question(3, "Color of the sun yo?", hardcodedOptions3, true);
        
        ArrayList<Question> hardCodedQuestions = new ArrayList();
        hardCodedQuestions.add(q1);
        hardCodedQuestions.add(q2);
        hardCodedQuestions.add(q3);
        
        
        Test premierLeagueTest = new Test(1, "Premier League Test", hardCodedQuestions);
        
        ArrayList<Test> hardCodedTests = new ArrayList();
        hardCodedTests.add(premierLeagueTest);
        
        
        
        
        //Adding the course
        hardCodedCourses.add(new Course(1, "Basic Footall", hardCodedTests));
        hardCodedCourses.add(new Course(2, "testthingy", hardCodedTests));
        
    }

    public ArrayList<User> getHardCodedUsers() {
        return hardCodedUsers;
    }

    public ArrayList<Course> getHardCodedCourses() {
        return hardCodedCourses;
    }
    
    
    
     public static ServerConnection getInstance(){
        if(instance == null) {
            instance = new ServerConnection();
        }
        return instance;
    }

    public User loginAuthentication(String userName, String password) {
        //Before we have a server - loop through hardcoded list
        for(User user : hardCodedUsers) {
            if(userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
    
}
