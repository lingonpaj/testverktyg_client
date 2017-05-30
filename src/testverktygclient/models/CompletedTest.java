package testverktygclient.models;

public class CompletedTest {
    private int id;
    private String courseName;
    private String TestName;
    private int userScore;
    private int testMaxScore;

    public CompletedTest() {}

    public CompletedTest(int id, String courseName, String TestName, 
            int userScore, int testMaxScore) {
        this.id = id;
        this.courseName = courseName;
        this.TestName = TestName;
        this.userScore = userScore;
        this.testMaxScore = testMaxScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseCode(String courseName) {
        this.courseName = courseName;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String TestName) {
        this.TestName = TestName;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getTestMaxScore() {
        return testMaxScore;
    }

    public void setTestMaxScore(int testMaxScore) {
        this.testMaxScore = testMaxScore;
    }
}
