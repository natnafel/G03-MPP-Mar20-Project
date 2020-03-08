package ui.controllers;


import com.jfoenix.controls.JFXTextField;

import business.AccountService;
import business.Address;
import business.LibraryMember;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class CreateMemberController {
    @FXML
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXTextField membereIdField;
    @FXML
    private JFXTextField telephoneField;
    @FXML
    private JFXTextField streetField;
    @FXML
    private JFXTextField cityField;
    @FXML
    private JFXTextField stateField;
    @FXML
    private JFXTextField zipField;

    @FXML
    private Label successMsg;
    @FXML
    private Label FailureMsg;

    @FXML    
    private AccountService accountService = new AccountService();
    //LibraryMember


    public void createMember(Event event) throws Exception {
    	Address address = new Address(streetField.toString(), cityField.toString(), stateField.toString(), zipField.toString());
    	LibraryMember libraryMember = new LibraryMember(membereIdField.toString(),  firstNameField.toString(), lastNameField.toString(), telephoneField.toString() ,address);
        boolean isMemberCreated = accountService.createMember(libraryMember);
        if(isMemberCreated){
        	successMsg.setVisible(true);
        }
        FailureMsg.setVisible(true);
    }
}
