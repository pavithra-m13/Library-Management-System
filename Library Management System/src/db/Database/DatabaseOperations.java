package db;
import model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DatabaseOperations 
{
private Connection connection;
public DatabaseOperations(Connection connection) {
this.connection = connection;
}
public ObservableList<Book> retrieveBooksFromDatabase() {
ObservableList<Book> books = FXCollections.observableArrayList();
try {
PreparedStatement statement = connection.prepareStatement("SELECT * FROM books");
ResultSet resultSet = statement.executeQuery();
while (resultSet.next()) {
String isbn = resultSet.getString("isbn");
String title = resultSet.getString("title");
String author = resultSet.getString("author");
double price = resultSet.getDouble("price");
books.add(new Book(isbn, title, author, price));
}
resultSet.close();
statement.close();
} catch (SQLException e) {
e.printStackTrace();
}
return books;
}
public void addBookToDatabase(Book book) {
try {
PreparedStatement statement = connection.prepareStatement("INSERT INTO books (isbn, title, author, price) VALUES (?, ?, ?, ?)");
statement.setString(1, book.getIsbn());
statement.setString(2, book.getTitle());
statement.setString(3, book.getAuthor());
statement.setDouble(4, book.getPrice());
statement.executeUpdate();
statement.close();
} catch (SQLException e) {
e.printStackTrace();
}
}
public void updateBookInDatabase(Book book, String isbn, String title, String author, double price) {
try {
PreparedStatement statement = connection.prepareStatement(
"UPDATE books SET isbn=?, title=?, author=?, price=? WHERE isbn=?");
statement.setString(1, isbn);
statement.setString(2, title);
statement.setString(3, author);
statement.setDouble(4, price);
statement.setString(5, book.getIsbn());
statement.executeUpdate();
statement.close();
} catch (SQLException e) {
e.printStackTrace();
}
}
public void deleteBookFromDatabase(Book book) {
try {
PreparedStatement statement = connection.prepareStatement(
"DELETE FROM books WHERE isbn=?");
statement.setString(1, book.getIsbn());
statement.executeUpdate();
statement.close();
} catch (SQLException e) {
e.printStackTrace();
}
}
}
