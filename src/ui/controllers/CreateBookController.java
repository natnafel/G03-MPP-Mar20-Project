package ui.controllers;

import com.jfoenix.controls.JFXTextField;
import business.BookService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CreateBookController {

	private String isbn;
	 
	@FXML
    private JFXTextField copyNumField;

    @FXML
    private Label successMsg;
    
    @FXML
    private Label FailureMsg;

     
   
    //LibraryMember


    public void initalizeData(String isbn) {
    	this.isbn = isbn;
    }
    
    public void createBook(Event event) throws Exception {
    	 BookService bookService = new BookService(); 
        if(bookService.createBookCopy("HPP-1", toInt(copyNumField.getText())) != null){
        	successMsg.setVisible(true);
        }
        FailureMsg.setVisible(true);
    }
    
   int  toInt(String copyNumStr){
	   int copyNumInt;
	   try {
		   copyNumInt = Integer.parseInt(copyNumStr);
	   }
	   catch (NumberFormatException e)
	   {
		   copyNumInt = 0;
	   }
    	return copyNumInt;
    }

}
