package ui;

import business.AccountService;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;
    private AccountService accountService = new AccountService();


    public void login(Event event){
        boolean isValid = accountService.validateUser(usernameField.getText(), passwordField.getText() );
        System.out.println(isValid);
    }

}
