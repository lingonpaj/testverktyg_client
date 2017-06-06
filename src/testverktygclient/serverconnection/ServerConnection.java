package testverktygclient.serverconnection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Course;
import testverktygclient.models.Answer;
import testverktygclient.models.Question;
import testverktygclient.models.Student;
import testverktygclient.models.Teacher;
import testverktygclient.models.Test;
import testverktygclient.models.User;

public class ServerConnection implements Serializable{
    private static ServerConnection instance;
    //HARDCODED to test frontend
    public ArrayList<User> hardCodedUsers;
    public ArrayList<Course> hardCodedCourses;
    public User loggedInUser;
    public String courseNameOfTest;
    public Test testToTake;
    public ArrayList<Test> hardCodedTests = new ArrayList();
    
    //Creates hardcoded users and courses so we can test front end
    private ServerConnection() {
        hardCodedCourses = new ArrayList();
        hardCodedUsers = new ArrayList();
        
        CompletedTest completedTest = new CompletedTest(1, "Code 1","Completed Test", 34, 22);
        ArrayList<CompletedTest> hardCodeCTest = new ArrayList();
        hardCodeCTest.add(completedTest);
        //Adding users
        hardCodedUsers.add(new Student(hardCodeCTest, 1, "skarl", "1", "Sofia", "Karlsson"));
        hardCodedUsers.add(new Teacher(2, "kjoha", "2", "Kalle", "Johansson"));
        
        //Adding courses (that has test->Questions->Options)
        ArrayList<Answer> hardcodedOptions = new ArrayList();
        hardcodedOptions.add(new Answer(1, "Arsenal", true));
        hardcodedOptions.add(new Answer(2, "Chelsea", false));
        hardcodedOptions.add(new Answer(2, "Spurs", false));
        

        Question q1 = new Question(1, "Which team doesn't suck?", hardcodedOptions, false);

        
        ArrayList<Answer> hardcodedOptions2 = new ArrayList();
        hardcodedOptions2.add(new Answer(1, "Emirates Stadium", false));
        hardcodedOptions2.add(new Answer(2, "Old Trafford", true));
        hardcodedOptions2.add(new Answer(2, "White Hart Lane", false));
        
        Question q2 = new Question(2, "What is the name of Manchester Uniteds home arena?", hardcodedOptions2, false);

        
        ArrayList<Answer> hardcodedOptions3 = new ArrayList();
        hardcodedOptions3.add(new Answer(1, "Blue", false));
        hardcodedOptions3.add(new Answer(2, "Yellow", true));
        hardcodedOptions3.add(new Answer(2, "Green", true));
        
        Question q3 = new Question(3, "Color of the sun yo?", hardcodedOptions3, true);

        
        ArrayList<Question> hardCodedQuestions = new ArrayList();
        hardCodedQuestions.add(q1);
        hardCodedQuestions.add(q2);
        hardCodedQuestions.add(q3);
        

        Test premierLeagueTest = new Test(1, "Premier League Test", hardCodedQuestions, 5);

        
        ArrayList<Test> hardCodedTests = new ArrayList();
        hardCodedTests.add(premierLeagueTest);
        
        
        
        
        //Adding the course
        hardCodedCourses.add(new Course(1, "Basic Footall", hardCodedTests));
        
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
        Client client = ClientBuilder.newClient();
        Student loggedInStudent = client.target("http://localhost:8080/TestVerktygServer/webapi/students")
                .path(userName).path(password).request(MediaType.APPLICATION_JSON).get(Student.class);
        
        if(loggedInStudent == null) {
            Teacher loggedInTeacher = client.target("http://localhost:8080/TestVerktygServer/webapi/users")
                .path(userName).path(password).request(MediaType.APPLICATION_JSON).get(Teacher.class);
            client.close();
            return loggedInTeacher;
        }
        
        client.close();
        return loggedInStudent;
    }
    
    public CompletedTest getLastCompletedTest(){
        Client client = ClientBuilder.newClient();
        Student loggedInStudent = client.target("http://localhost:8080/TestVerktygServer/webapi/students")
                .path("" + loggedInUser.getId()).request(MediaType.APPLICATION_JSON).get(Student.class);
        
        //This to get the 
        loggedInUser = loggedInStudent;
        client.close();
        return loggedInStudent.getCompletedTests().get(loggedInStudent.getCompletedTests().size()-1);
    }
    
    public void addCompletedTest(CompletedTest newTest, int studentId) {
        Client client = ClientBuilder.newClient();
        client.target("http://localhost:8080/TestVerktygServer/webapi/students").path("" + studentId).path("completedtests")
                .request(MediaType.APPLICATION_JSON).post(Entity.json(newTest), CompletedTest.class);
        client.close();
    }
    
    public void addTest(Test newTest, int courseId) {
        Client client = ClientBuilder.newClient();
        client.target("http://localhost:8080/TestVerktygServer/webapi/courses").path("" + courseId).path("tests")
                .request(MediaType.APPLICATION_JSON).post(Entity.json(newTest), Test.class);
        client.close();
    }
    
    public List<Student> getStudents() {
        Client client = ClientBuilder.newClient();
        ArrayList<Student> students;
        students = client.target("http://localhost:8080/TestVerktygServer/webapi/students")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<Student>>(){});
        client.close();
        return students;
    }
    
    public List<Course> getCourses() {
        Client client = ClientBuilder.newClient();
        ArrayList<Course> courses;
        courses = client.target("http://localhost:8080/TestVerktygServer/webapi/courses")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<Course>>(){});
        client.close();
        return courses;
    }
    
    public void deleteTest(int testId) {
        Client client = ClientBuilder.newClient();
        client.target("http://localhost:8080/TestVerktygServer/webapi/courses/1/tests/")
                .path("" + testId).request(MediaType.APPLICATION_JSON)
                    .delete();
        client.close();
    }
}
