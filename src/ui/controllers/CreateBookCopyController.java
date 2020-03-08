package ui.controllers;

import business.BookService;
import business.LibrarySystemException;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CreateBookCopyController {

    private BookService bookService = new BookService();

    private String isbn;

    @FXML
    private Label errorMsg;

    @FXML
    private JFXTextField copyNumField;

    public void initializeData(String isbn){
        this.isbn = isbn;
    }

    public void addBookCopy(){
        String copyNumString = copyNumField.getText();
        if (copyNumString.isEmpty()){
            errorMsg.setVisible(true);
            return;
        }
        try {
            int copyNum = Integer.parseInt(copyNumString);
            if(bookService.createBookCopy(isbn, copyNum) == null){
                //TODO launch error screen popup
                // something unexpected happened
            } else {
                //TODO show success popup
                closeWindow();
            }
        }catch (NumberFormatException ignored){
            errorMsg.setVisible(true);
        } catch (LibrarySystemException e){
            //TODO launch error screen popup
            errorMsg.setVisible(true);
        }
    }

    public void closeWindow(){
        ((Stage) copyNumField.getScene().getWindow()).close();
    }

}
