package testverktygclient.models;

import java.io.Serializable;

public class Teacher extends User implements Serializable{

    public Teacher() {
        super();
    }

    public Teacher(int userId, String userName, String password, 
            String firstName, String lastName) {
        super(userId, userName, password, firstName, lastName);
    }
}
