package ui.controllers;

import business.AccountService;
import business.Book;
import business.BookService;
import business.LibraryMember;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by natnafel on 3/9/20.
 */
public class MemberConfirmationController {
    @FXML
    private JFXTextField memberId;

    private Book book;

    private AccountService accountService = new AccountService();

    public void initializeData(Book book){
        this.book = book;
    }

    public void checkout(){
        LibraryMember member =  accountService.findMemberByMemberId(memberId.getText());
        if(member == null){
            //TODO Error popup (means member id doesn't exist)
        } else {
            showCheckoutPage(member);
            closeWindow();
        }
    }

    private void showCheckoutPage(LibraryMember member) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/CheckoutConfirmation.fxml"));
            Parent root = loader.load();
            ConfirmationController ctrl = loader.getController();
            ctrl.initializeDate(member, book);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Checkout");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeWindow(){
        ((Stage) memberId.getScene().getWindow()).close();
    }

}
