package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
        serverConnection.loggedInUser = userToLogin;
        
        String loggedInUserType = checkUserType();
        
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource(loggedInUserType + "View.fxml"));
            Scene s = new Scene(root);
            Stage stg = (Stage) userName.getScene().getWindow();
            stg.setScene(s);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Temporary, will be replaced by loading of page depending of type of user
        errorLogin.setText(userToLogin.getClass().getSimpleName() + " authorized");
    }
    
    private String checkUserType() {
        return serverConnection.loggedInUser.getClass().getSimpleName();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getInstance();
    }    


}
