package ui.controllers;

import business.AccountService;
import business.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private Label loginErrorMsg;

    @FXML
    private JFXPasswordField passwordField;
    private AccountService accountService = new AccountService();


    public void login(Event event) throws Exception {
        User user = accountService.validateUser(usernameField.getText(), passwordField.getText() );
        if(user == null){
            loginErrorMsg.setVisible(true);
        }
        else{
            User.setLoggedInUser(user);
            loginErrorMsg.setVisible(false);
            Parent root = FXMLLoader.load(getClass().getResource("../resources/Dashboard.fxml"));
            Scene scene = new Scene(root, 901.0, 455.0);
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
            Stage stage1 = (Stage) loginErrorMsg.getScene().getWindow();
            stage1.close();
        }


    }

}
