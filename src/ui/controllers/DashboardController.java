package ui.controllers;

import business.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.controls.JFXTreeTableView;

import dataaccess.AccountRepository;
import javafx.animation.PauseTransition;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import java.net.URL;
import java.util.stream.Collectors;

public class DashboardController implements Initializable {

    private BookService bookService = new BookService();

    @FXML
    private Label userNameLabel;

    @FXML
    private Label adminOn;

    @FXML
    private Label libOn;

    @FXML
    private JFXTreeTableView<DashboardTableEntry> treeViewJFX;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> isbnCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> titleCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> authorCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> actionCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> addNewCol;

    @FXML
    private TreeTableColumn<DashboardTableEntry, String> copyNumCol;

    @FXML
    private JFXTextField searchField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userNameLabel.setText(User.getLoggedInUser().getUsername());

        //Roles visibility
        if(User.getLoggedInUser().hasAuth(Auth.ADMIN)) {
            adminOn.setVisible(true);
            libOn.setVisible((false));
        }
        else {
            adminOn.setVisible(false);
            libOn.setVisible(true);
        }

        List<Book> books = bookService.getAllBooks();
        ObservableList<DashboardTableEntry> dashboardTableEntryObservableList = FXCollections.observableArrayList();

        List<DashboardTableEntry> bookEntries = books.stream().map(book -> {
            StringBuilder authorName = new StringBuilder();
            for (Author a :
                    book.getAuthors()) {
                authorName.append(a.getFullName()).append(", ");
            }
            return new DashboardTableEntry(book, book.getIsbn(), book.getTitle(), authorName.toString() , book.getNumCopies()+"");
        }).collect(Collectors.toList());

        dashboardTableEntryObservableList.setAll(bookEntries);

        final TreeItem<DashboardTableEntry> root = new RecursiveTreeItem<>(dashboardTableEntryObservableList, RecursiveTreeObject::getChildren);

        treeViewJFX.setRoot(root);
        treeViewJFX.setShowRoot(false);

        isbnCol.setCellValueFactory(data -> data.getValue().getValue().isbn);
        titleCol.setCellValueFactory(data -> data.getValue().getValue().title);
        authorCol.setCellValueFactory(data -> data.getValue().getValue().author);

        copyNumCol.setCellValueFactory(data -> data.getValue().getValue().copyNum);

        //For Checkout column
        Callback<TreeTableColumn<DashboardTableEntry, String>, TreeTableCell<DashboardTableEntry, String>> cellFactory = o -> new TreeTableCell<DashboardTableEntry, String>() {

            final Button btn = new Button("Checkout");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btn.setOnAction(event -> {
                        DashboardTableEntry entry = getTreeTableView().getTreeItem(getIndex()).getValue();
                        showCheckoutPage(entry.book);
                    });
                    setGraphic(btn);
                    setText(null);
                }
            }
        };

        actionCol.setCellFactory(cellFactory);

        //
        Callback<TreeTableColumn<DashboardTableEntry, String>, TreeTableCell<DashboardTableEntry, String>> cellFactoryCopy = o -> new TreeTableCell<DashboardTableEntry, String>() {

            final Button btn = new Button("Add New");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btn.setOnAction(event -> {
                        DashboardTableEntry entry = getTreeTableView().getTreeItem(getIndex()).getValue();
                        showAddBookCopyPage(entry.book);
                    });
                    setGraphic(btn);
                    setText(null);
                }
            }
        };

        addNewCol.setCellFactory(cellFactoryCopy);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        searchField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    pause.setOnFinished(event -> {
                        dashboardTableEntryObservableList.setAll(bookEntries.stream().filter(item -> item.title.toString().contains(newValue)).collect(Collectors.toList()));
                    });
                    pause.playFromStart();
                }
        );

    }

    private void showCheckoutPage(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/MemberConfirmation.fxml"));
            Parent root = loader.load();
            MemberConfirmationController ctrl = loader.getController();
            ctrl.initializeData(book);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Associate member with checkout");
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAddBookCopyPage(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/CreateBookCopy.fxml"));
            Parent root = loader.load();
            CreateBookCopyController ctrl = loader.getController();
            ctrl.initializeData(book.getIsbn());
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Add New Book");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        StringProperty copyNum;
        Book book;

        public DashboardTableEntry(Book book, String isbn, String title, String author, String copyNum) {
            this.isbn = new SimpleStringProperty(isbn);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            //this.available = new SimpleStringProperty(available);
            this.copyNum = new SimpleStringProperty(copyNum);
            this.book = book;

        }
    }

    //Scene Navigation
    public void addMember(Event event) throws Exception {
        //Auth.LIBRARIAN =
        Parent root = FXMLLoader.load(getClass().getResource("../resources/CreateMember.fxml"));
        Scene scene = new Scene(root, 530, 368);
        Stage stage = new Stage();
        stage.setTitle("Add Member");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }



}
