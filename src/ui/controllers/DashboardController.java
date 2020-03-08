package ui.controllers;

import business.*;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.controls.JFXTreeTableView;

import dataaccess.BookRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.util.List;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.stream.Collectors;

public class DashboardController implements Initializable {

    private BookService bookService = new BookService();

    @FXML
    private Label userNameLabel;

    @FXML
    private JFXTreeTableView<DashboardTableEntry> treeViewJFX;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> isbnCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> titleCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> authorCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> avaCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> actionCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameLabel.setText(User.getLoggedInUser().getUsername());
        List<Book> books = bookService.getAllBooks();
        ObservableList<DashboardTableEntry> dashboardTableEntryObservableList = FXCollections.observableArrayList();

        dashboardTableEntryObservableList.setAll(books.stream().map(book -> {
            StringBuilder authorName = new StringBuilder();
            for (Author a :
                    book.getAuthors()) {
                authorName.append(a.getFullName()).append(", ");
            }
            return new DashboardTableEntry(book.getIsbn(), book.getTitle(), authorName.toString(), book.isAvailable()+"", " ");
        }).collect(Collectors.toList()));

        final TreeItem<DashboardTableEntry> root = new RecursiveTreeItem<>(dashboardTableEntryObservableList, RecursiveTreeObject::getChildren);

        treeViewJFX.setRoot(root);
        treeViewJFX.setShowRoot(false);

        isbnCol.setCellValueFactory(data -> data.getValue().getValue().isbn);
        titleCol.setCellValueFactory(data -> data.getValue().getValue().title);
        authorCol.setCellValueFactory(data -> data.getValue().getValue().author);
        avaCol.setCellValueFactory(data -> data.getValue().getValue().available);
        actionCol.setCellValueFactory(data -> data.getValue().getValue().available);

    }

    public void logout(Event event) throws Exception{
        User.setLoggedInUser(null);
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Login.fxml"));
        Scene scene = new Scene(root, 572, 491);
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        Stage stage1 = (Stage) userNameLabel.getScene().getWindow();
        stage1.close();
    }

    class DashboardTableEntry extends RecursiveTreeObject<DashboardTableEntry>{

        StringProperty isbn;
        StringProperty title;
        StringProperty author;
        StringProperty available;
        StringProperty action;

        public DashboardTableEntry(String isbn, String title, String author, String available, String action) {
            this.isbn = new SimpleStringProperty(isbn);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.available = new SimpleStringProperty(available);
            this.action = new SimpleStringProperty(action);
        }
    }
}
