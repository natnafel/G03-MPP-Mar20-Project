package ui.controllers;

import business.AccountService;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CreateMemberController {

    private AccountService accountService = new AccountService();

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField memberId;

    @FXML
    private JFXTextField telephoneField;

    @FXML
    private JFXTextField cityField;

    @FXML
    private JFXTextField stateField;

    @FXML
    private JFXTextField streetField;

    @FXML
    private JFXTextField zipField;

    @FXML
    private Label errorMsg;

    public void addMember(Event event){
        // simple validation
        if (firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                memberId.getText().isEmpty() ||
                telephoneField.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                stateField.getText().isEmpty() ||
                streetField.getText().isEmpty() ||
                zipField.getText().isEmpty()){
            errorMsg.setVisible(true);
            return;
        }

        accountService.createMember(firstNameField.getText(), lastNameField.getText(),
                memberId.getText(), telephoneField.getText(), cityField.getText(), stateField.getText(), zipField.getText(), streetField.getText());

        //TODO show success popup
        closeWindow();

    }

    public void closeWindow(){
        ((Stage) firstNameField.getScene().getWindow()).close();
    }

}
