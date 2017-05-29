package testverktygclient.serverconnection;

import java.util.ArrayList;
import testverktygclient.models.Course;
import testverktygclient.models.Option;
import testverktygclient.models.Question;
import testverktygclient.models.Student;
import testverktygclient.models.Teacher;
import testverktygclient.models.Test;
import testverktygclient.models.User;

public class ServerConnection {
    //HARDCODED to test frontend
    public ArrayList<User> hardCodedUsers;
    public ArrayList<Course> hardCodedCourses;
    
    //Creates hardcoded users and courses so we can test front end
    public ServerConnection() {
        hardCodedCourses = new ArrayList();
        hardCodedUsers = new ArrayList();
        
        //Adding users
        hardCodedUsers.add(new Student(null, 1, "skarl", "password1", "Sofia", "Karlsson"));
        hardCodedUsers.add(new Teacher(2, "kjoha", "password2", "Kalle", "Johansson"));
        
        //Adding courses (that has test->Questions->Options)
        ArrayList<Option> hardcodedOptions = new ArrayList();
        hardcodedOptions.add(new Option(1, "Arsenal", true));
        hardcodedOptions.add(new Option(2, "Chelsea", false));
        hardcodedOptions.add(new Option(2, "Spurs", false));
        
        Question q1 = new Question(1, "Wich team doesn't suck?", hardcodedOptions);
        
        ArrayList<Option> hardcodedOptions2 = new ArrayList();
        hardcodedOptions2.add(new Option(1, "Emirates Stadium", false));
        hardcodedOptions2.add(new Option(2, "Old Trafford", true));
        hardcodedOptions2.add(new Option(2, "White Hart Lane", false));
        
        Question q2 = new Question(2, "What is the name of Manchester Uniteds home arena?", hardcodedOptions2);
        
        ArrayList<Question> hardCodedQuestions = new ArrayList();
        hardCodedQuestions.add(q1);
        hardCodedQuestions.add(q2);
        
        Test premierLeagueTest = new Test(1, "Premier League Test", hardCodedQuestions);
        
        ArrayList<Test> hardCodedTests = new ArrayList();
        hardCodedTests.add(premierLeagueTest);
        
        //Adding the course
        hardCodedCourses.add(new Course(1, "Basic Footall", hardCodedTests));
        
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
