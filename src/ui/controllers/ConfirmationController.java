package ui.controllers;

import business.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ConfirmationController {

    private BookService bookService = new BookService();

    private LibraryMember libraryMember;
    private Book book;

    public void initializeDate(LibraryMember libraryMember, Book book){
        this.libraryMember = (new AccountService()).findMemberByMemberId("REG-1");// TODO replace hard code
        this.book = book;
    }

    @FXML
    private AnchorPane confirmationPane;

    @FXML
    private void confirm(Event event) throws IOException {
        showAfterCheckoutStage(bookService.createCheckoutRecord(libraryMember, book));
        closeWindow(null);
    }

    @FXML
    private void closeWindow(Event event){
        ((Stage) confirmationPane.getScene().getWindow()).close();
    }

    private void showAfterCheckoutStage(CheckoutRecord checkoutRecord) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/AfterCheckout.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Successful Checkout");
        stage.setScene(new Scene(loader.load()));
        AfterCheckoutController controller = loader.getController();
        controller.initializeData(checkoutRecord);
        stage.show();
        stage.setResizable(false);
    }
}
