package ui.controllers;

import business.Author;
import business.Book;
import business.CheckoutRecord;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AfterCheckoutController implements Initializable{

    private CheckoutRecord checkoutRecord;

    @FXML
    private AnchorPane afterCheckoutPane;

    @FXML
    private TreeTableColumn<CheckoutEntry, String> isbnColumn;

    @FXML
    private TreeTableColumn<CheckoutEntry, String> titleColumn;

    @FXML
    private TreeTableColumn<CheckoutEntry, String> authorColumn;

    @FXML
    private TreeTableColumn<CheckoutEntry, String> checkoutDateColumn;

    @FXML
    private TreeTableColumn<CheckoutEntry, String> dueDateColumn;

    @FXML
    private JFXTreeTableView<CheckoutEntry> tableView;

    public void initializeData(CheckoutRecord checkoutRecord){
        this.checkoutRecord = checkoutRecord;
    }

    @FXML
    private void closeWindow(Event event){
        ((Stage) afterCheckoutPane.getScene().getWindow()).close();
    }

    @FXML
    private TreeTableColumn<CheckoutEntry, String> copyNumColumn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isbnColumn.setCellValueFactory(data -> data.getValue().getValue().isbn);
        titleColumn.setCellValueFactory(data -> data.getValue().getValue().title);
        authorColumn.setCellValueFactory(data -> data.getValue().getValue().author);
        checkoutDateColumn.setCellValueFactory(data -> data.getValue().getValue().checkoutDate);
        dueDateColumn.setCellValueFactory(data -> data.getValue().getValue().dueDate);

        ObservableList<CheckoutEntry> checkoutEntryObservableList = FXCollections.observableArrayList();
        Book book = checkoutRecord.getBookCopy().getBook();
        StringBuilder authorName = new StringBuilder();
        for (Author a :
                book.getAuthors()) {
            authorName.append(a.getFullName()).append(", ");
        }
        checkoutEntryObservableList.add(new CheckoutEntry(book.getIsbn(), book.getTitle(), authorName.toString(),
                checkoutRecord.getCheckoutDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                checkoutRecord.getDueDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));


        final TreeItem<CheckoutEntry> root = new RecursiveTreeItem<CheckoutEntry>(checkoutEntryObservableList, RecursiveTreeObject::getChildren);
        tableView.setRoot(root);

    }

    class CheckoutEntry extends RecursiveTreeObject<CheckoutEntry>{

        StringProperty isbn;
        StringProperty title;
        StringProperty author;
        StringProperty checkoutDate;
        StringProperty dueDate;

        CheckoutEntry(String isbn, String title, String author, String checkoutDate, String dueDate){
            this.isbn = new SimpleStringProperty(isbn);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.checkoutDate = new SimpleStringProperty(checkoutDate);
            this.dueDate = new SimpleStringProperty(dueDate);
        }
    }
}
