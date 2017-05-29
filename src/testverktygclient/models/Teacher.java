package testverktygclient.models;

public class Teacher extends User{

    public Teacher() {}

    public Teacher(int userId, String userName, String password, 
            String firstName, String lastName) {
        super(userId, userName, password, firstName, lastName);
    }
}
