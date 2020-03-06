package ui;

import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private JFXTextField usernameField;
    private JFXTextField passwordField;
    //private AccountService accountService = new Acc();


    public void login(Event event){
       // accountService.login(usernameField.getText(), passwordField.getText() );
    }

}
