package ui.controllers;

import business.AccountService;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    public void addMember(Event event) throws Exception {
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

        successfulScene();
        closeWindow();


    }

    public void successfulScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/PopUp.fxml"));
        Parent root = loader.load();
        PopUpController ctrl = loader.getController();
        ctrl.initializeData(true, "Member added successfully");
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Success");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }

    public void closeWindow(){
        ((Stage) firstNameField.getScene().getWindow()).close();
    }

}
