package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopUpController {
    @FXML
    private Label messageTitle;

    @FXML
    private Label messageBody;

    public void initializeData(boolean isSuccess, String body){
        if (isSuccess){
            messageTitle.setStyle("-fx-text-fill: green");
            messageTitle.setText("Success!");
        } else {
            messageTitle.setStyle("-fx-text-fill: red");
            messageTitle.setText("Error!");
        }
        messageBody.setText(body);
    }

    public void closeWindow(){
        ((Stage) messageBody.getScene().getWindow()).close();
    }
}
