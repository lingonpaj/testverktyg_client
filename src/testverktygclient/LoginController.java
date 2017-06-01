package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
    
    Stage stage;
    Parent root;
    
    @FXML
    private Button loginButton;
    
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
    
    private void loadNextPage(User userToLogin) throws IOException {
        //Temporary, will be replaced by loading of page depending of type of user
        errorLogin.setText(userToLogin.getClass().getSimpleName() + " authorized");
        
        Stage ourStage = (Stage) loginButton.getScene().getWindow();
        
        root = FXMLLoader.load(getClass().getResource("" + userToLogin.getClass().getSimpleName() + "View.fxml"));
        ourStage.setScene(new Scene(root));
        ourStage.setTitle("The " + userToLogin.getClass().getSimpleName() + " view!"); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverConnection = ServerConnection.getInstance();
    }    

}
