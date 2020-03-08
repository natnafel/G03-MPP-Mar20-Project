package ui.controllers;

import com.jfoenix.controls.JFXTextField;

import business.BookService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CreateBookCopyController {
	 @FXML
	  private JFXTextField copyNumField;
	 
	  @FXML
	  private JFXTextField isbnField;

	    @FXML
	    private Label successMsg;
	    
	    @FXML
	    private Label failureMsg;
	    
	    private BookService bookService = new BookService();
	    
	    public void createBookCopy(Event event) throws Exception {	    	
	        //boolean isBookCopyCreated = bookService.createBookCopy(isbnField.toString(), toInt(copyNumField.toString()));
	        if( bookService.createBookCopy("HPP-1", toInt(copyNumField.toString())) != null){
	        	successMsg.setVisible(true);
	        }
	        failureMsg.setVisible(true);
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
