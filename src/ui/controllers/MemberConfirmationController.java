package ui.controllers;

import business.AccountService;
import business.Book;
import business.BookService;
import business.LibraryMember;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
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

    private ObservableList<DashboardController.DashboardTableEntry> dashboardTableEntryObservableList;

    private AccountService accountService = new AccountService();

    public void initializeData(Book book, ObservableList<DashboardController.DashboardTableEntry> dashboardTableEntryObservableList){
        this.book = book;
        this.dashboardTableEntryObservableList = dashboardTableEntryObservableList;
    }

    public void checkout() throws Exception{
        LibraryMember member =  accountService.findMemberByMemberId(memberId.getText());
        if(member == null){
            errorScene();
        } else {
            showCheckoutPage(member);
            closeWindow();
        }
    }

    public void errorScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/PopUp.fxml"));
        Parent root = loader.load();
        PopUpController ctrl = loader.getController();
        ctrl.initializeData(false, "HALT! Member doesn't exist.");
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Error");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }

    private void showCheckoutPage(LibraryMember member) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/CheckoutConfirmation.fxml"));
            Parent root = loader.load();
            ConfirmationController ctrl = loader.getController();
            ctrl.initializeDate(member, book, dashboardTableEntryObservableList);
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
