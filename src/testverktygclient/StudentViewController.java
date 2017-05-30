/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testverktygclient;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static testverktygclient.StudentViewController.ObservableCompletedTestList;
import testverktygclient.models.CompletedTest;
import testverktygclient.models.Course;
import testverktygclient.models.Student;
import testverktygclient.models.Test;
import testverktygclient.serverconnection.ServerConnection;

/**
 * FXML Controller class
 *
 * @author bujam
 */
public class StudentViewController implements Initializable {

    @FXML
    private Label loggedInAsLabel;
    @FXML
    private Button signOutButton;
    @FXML
    private TableView<CompletedTest> completedTestTable;
    @FXML
    private TableColumn<Test, String> availableTestColumn;
    @FXML
    private TableColumn<CompletedTest, String> completedTestColumn;
    @FXML
    private ChoiceBox chooseCourseDropDown;
    @FXML
    private TableView<Test> availableTestTable;

    private Course selectedCourse;

    public static List<Course> courseArrayList = new ArrayList<>();
    public static List<CompletedTest> completedTestArrayList = new ArrayList<>();
    public static ObservableList<Course> ObservableCourseList = FXCollections.observableArrayList();
    public static ObservableList<CompletedTest> ObservableCompletedTestList = FXCollections.observableArrayList();
    public static ObservableList<Test> ObservableTestList = FXCollections.observableArrayList();

    ServerConnection serverConnection = new ServerConnection();
    Student std = new Student();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        availableTestColumn.setCellValueFactory(new PropertyValueFactory<Test, String>("name"));
        ObservableCourseList = FXCollections.observableArrayList(serverConnection.hardCodedCourses);
        chooseCourseDropDown.setItems(ObservableCourseList);

        chooseCourseDropDown.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(oldValue + " " + newValue);
                int newValueCasted = (int) newValue;
                selectedCourse = (Course) chooseCourseDropDown.getItems().get(newValueCasted);

                ObservableTestList = FXCollections.observableArrayList(selectedCourse.getTests());
                availableTestTable.setItems(ObservableTestList);
            }

        });
        ServerConnection.loggedInUser = serverConnection.hardCodedUsers.get(0);
        Student std = (Student) ServerConnection.loggedInUser;
        completedTestColumn.setCellValueFactory(new PropertyValueFactory<CompletedTest, String>("TestName"));
        ObservableCompletedTestList = FXCollections.observableArrayList(std.getCompletedTests());
        completedTestTable.setItems(ObservableCompletedTestList);
        loggedInAsLabel.setText("Logged in as: " + ServerConnection.loggedInUser.getFirstName()
                + " " + ServerConnection.loggedInUser.getLastName());
    }

    @FXML
    private void artistInfoButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene s = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Hämta knappen, hämta stagen
        stage.setScene(s); // byter ut gamla stage mot nya, sätt en ny stage
        stage.show();
        stage.setTitle("Newton Testverktyg Grupp 1 2017");
    }
}
