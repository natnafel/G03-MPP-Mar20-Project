package dataaccess;

import business.*;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    public Book findBookByIsbn(String isbn){
        Connection connection = null;
        try {
            connection = DBConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from Book b " +
                    "where b.isbn = ?");
            preparedStatement.setString(1, isbn);
            ResultSet rs = preparedStatement.executeQuery();
            connection.close();
            if(rs.first()){
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                Book.CheckoutLength checkoutLength = null;
                for (Book.CheckoutLength c : Book.CheckoutLength.values()){
                    if(c.getDays() == rs.getInt("maxCheckoutLength")){
                        checkoutLength = c;
                        break;
                    }
                }
                Connection authConn = DBConnectionHelper.getConnection();
                PreparedStatement preparedStatement2 = authConn.prepareStatement("SELECT a.*, address.* from author_book ab " +
                        "join author a on a.id = ab.author_id " +
                        "join address on a.address_id = address.id" +
                        "where ab.book_id = ?");
                preparedStatement2.setInt(1, bookId);
                ResultSet rs2 = preparedStatement.executeQuery();
                List<Author> authors = new ArrayList<>();
                while (rs2.next()){
                    Address address = new Address(rs2.getString("street"), rs2.getString("city"),
                            rs2.getString("state"), rs2.getString("zip"));

                    authors.add(new Author(rs2.getString("firstName"),
                            rs2.getString("lastName"), rs2.getString("telephone"), address, rs2.getString("bio")));
                }
                authConn.close();

                Book book = new Book(bookId, isbn, title, checkoutLength, authors);

                Connection copyConn = DBConnectionHelper.getConnection();
                PreparedStatement preparedStatement3 = copyConn.prepareStatement("SELECT * from book_copies bc " +
                        "where bc.book_id = ?");
                preparedStatement3.setInt(1, bookId);
                ResultSet rs3 = preparedStatement.executeQuery();
                List<BookCopy> bookCopies = new ArrayList<>();
                while (rs3.next()){
                    bookCopies.add(new BookCopy(book, rs3.getInt("id"), rs3.getInt("copyNum"), rs3.getBoolean("isAvailable")));
                }
                copyConn.close();

                return book;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public BookCopy createBookCopy(BookCopy bookCopy){
        Connection connection = null;
        try {
            connection = DBConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book_copies (copyNum, isAvailable, book_id) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bookCopy.getCopyNum());
            preparedStatement.setBoolean(2, bookCopy.isAvailable());
            preparedStatement.setInt(3, bookCopy.getBook().getId());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.first()){
                return new BookCopy(bookCopy.getBook(), keys.getInt(1), bookCopy.getCopyNum(), bookCopy.isAvailable());
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<Book> getAllBooks(){
        try {
            Connection connection = DBConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book");
            ResultSet booksRS = preparedStatement.executeQuery();
            connection.close();
            List<Book> bookList = new ArrayList<>();
            while(booksRS.next()){
                int bookId = booksRS.getInt("id");
                String title = booksRS.getString("title");
                String isbn = booksRS.getString("isbn");
                Book.CheckoutLength checkoutLength = null;
                for (Book.CheckoutLength c : Book.CheckoutLength.values()){
                    if(c.getDays() == booksRS.getInt("maxCheckoutLength")){
                        checkoutLength = c;
                        break;
                    }
                }
                Connection authConn = DBConnectionHelper.getConnection();
                preparedStatement = authConn.prepareStatement("SELECT a.*, address.* from author_book ab " +
                        "join author a on a.id = ab.author_id " +
                        "join address on a.address_id = address.id" +
                        "where ab.book_id = ?");
                preparedStatement.setInt(1, bookId);
                ResultSet rs2 = preparedStatement.executeQuery();
                List<Author> authors = new ArrayList<>();
                while (rs2.next()){
                    Address address = new Address(rs2.getString("street"), rs2.getString("city"),
                            rs2.getString("state"), rs2.getString("zip"));

                    authors.add(new Author(rs2.getString("firstName"),
                            rs2.getString("lastName"), rs2.getString("telephone"), address, rs2.getString("bio")));
                }
                authConn.close();

                Book book = new Book(bookId, isbn, title, checkoutLength, authors);

                Connection copyConn = DBConnectionHelper.getConnection();
                PreparedStatement preparedStatement3 = copyConn.prepareStatement("SELECT * from book_copies bc " +
                        "where bc.book_id = ?");
                preparedStatement3.setInt(1, bookId);
                ResultSet rs3 = preparedStatement.executeQuery();
                List<BookCopy> bookCopies = new ArrayList<>();
                while (rs3.next()){
                    bookCopies.add(new BookCopy(book, rs3.getInt("id"), rs3.getInt("copyNum"), rs3.getBoolean("isAvailable")));
                }
                copyConn.close();
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean createCheckoutRecord(CheckoutRecord checkoutRecord){
        Connection connection = null;
        try {
            connection = DBConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO checkoutRecord " +
                    "(book_id, member_id, checkoutDate, dueDate, returnedDate, paidDate, finePaid) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, checkoutRecord.getBook().getId());
            preparedStatement.setInt(2, checkoutRecord.getMember().getId());
            preparedStatement.setDate(3, Date.valueOf(checkoutRecord.getCheckoutDate()));
            preparedStatement.setDate(4, Date.valueOf(checkoutRecord.getDueDate()));
            preparedStatement.setDate(5, Date.valueOf(checkoutRecord.getReturnedDate()));
            preparedStatement.setDate(6, Date.valueOf(checkoutRecord.getPaidDate()));
            preparedStatement.setDouble(7, checkoutRecord.getFinePaid());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}