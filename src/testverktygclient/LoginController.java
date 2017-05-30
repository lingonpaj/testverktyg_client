package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import testverktygclient.models.User;
import testverktygclient.serverconnection.ServerConnection;

public class LoginController implements Initializable {
    
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Text errorLogin;
    
    private ServerConnection serverConnection;
    
    @FXML
    private void login(ActionEvent event) throws IOException {
        User user = serverConnection.loginAuthentication(userName.getText(), 
                password.getText());
        if(user == null) {
            errorLogin.setText("Authentication: Failed.");
        } else {
            loadNextPage(user);
        }
    }
    
    private void loadNextPage(User userToLogin) {
        //Temporary, will be replaced by loading of page depending of type of user
        errorLogin.setText(userToLogin.getClass().getSimpleName() + " authorized");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getInstance();
    }    

}
