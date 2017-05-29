package testverktygclient.models;

public class Teacher extends User{

    public Teacher() {}

    public Teacher(int userId, String userName, String password, 
            String firstName, String lastName, String role) {
        super(userId, userName, password, firstName, lastName, role);
    }
}
