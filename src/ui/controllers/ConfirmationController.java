package ui.controllers;

import business.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConfirmationController {

    private BookService bookService = new BookService();

    private LibraryMember libraryMember;
    private Book book;

    private ObservableList<DashboardController.DashboardTableEntry> dashboardTableEntryObservableList;

    public void initializeDate(LibraryMember libraryMember, Book book, ObservableList<DashboardController.DashboardTableEntry> dashboardTableEntryObservableList){
        this.libraryMember = libraryMember;
        this.book = book;
        this.dashboardTableEntryObservableList = dashboardTableEntryObservableList;
    }

    @FXML
    private AnchorPane confirmationPane;

    @FXML
    private void confirm(Event event) throws IOException {
        showAfterCheckoutStage(bookService.createCheckoutRecord(libraryMember, book));
        List<Book> books = bookService.getAllBooks();
        List<DashboardController.DashboardTableEntry> bookEntries = books.stream().map(book -> {
            StringBuilder authorName = new StringBuilder();
            for (Author a :
                    book.getAuthors()) {
                authorName.append(a.getFullName()).append(", ");
            }
            return new DashboardController.DashboardTableEntry(book, book.getIsbn(), book.getTitle(), authorName.toString(), book.getNumCopies() + "");
        }).collect(Collectors.toList());

        dashboardTableEntryObservableList.setAll(bookEntries);
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
