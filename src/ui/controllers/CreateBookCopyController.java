package ui.controllers;

import business.Author;
import business.Book;
import business.BookService;
import business.LibrarySystemException;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.stream.Collectors;

public class CreateBookCopyController {

    private BookService bookService = new BookService();

    private String isbn;

    @FXML
    private Label errorMsg;

    @FXML
    private JFXTextField copyNumField;

    ObservableList<DashboardController.DashboardTableEntry> dashboardTableEntryObservableList;

    public void initializeData(String isbn, ObservableList<DashboardController.DashboardTableEntry> dashboardTableEntryObservableList){
        this.isbn = isbn;
        this.dashboardTableEntryObservableList = dashboardTableEntryObservableList;
    }

    public void addBookCopy() throws Exception {
        String copyNumString = copyNumField.getText();
        if (copyNumString.isEmpty()){
            errorMsg.setVisible(true);
        }
        try {
            int copyNum = Integer.parseInt(copyNumString);
            if(bookService.createBookCopy(isbn, copyNum) == null){
                errorScene();

            } else {
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
                closeWindow();
                successfulScene();
            }
        }catch (NumberFormatException ignored){
            errorMsg.setVisible(true);
        } catch (LibrarySystemException e){
            errorScene();
        }
    }

    public void closeWindow(){
        ((Stage) copyNumField.getScene().getWindow()).close();
    }

    public void successfulScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/PopUp.fxml"));
        Parent root = loader.load();
        PopUpController ctrl = loader.getController();
        ctrl.initializeData(true, "Book copy added successfully");
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Success");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }

    public void errorScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/PopUp.fxml"));
        Parent root = loader.load();
        PopUpController ctrl = loader.getController();
        ctrl.initializeData(false, "Please enter a valid book copy!");
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Error");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setResizable(false);
    }

}
