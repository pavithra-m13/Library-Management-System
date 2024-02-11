package GUI;
import db.DatabaseOperations;
import model.Book;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class GUIManager
{
private DatabaseOperations operations;
private ObservableList<Book> books;
public GUIManager(DatabaseOperations operations) {
this.operations = operations;
}
public void createGUI(Stage primaryStage) {
primaryStage.setTitle("Book Stock Management System");
// Create book list
books = operations.retrieveBooksFromDatabase();
// Create book list view
ListView<Book> bookListView = new ListView<>();
bookListView.setItems(books);
// Create book details labels
Label isbnLabel = new Label("ISBN:");
isbnLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
Label titleLabel = new Label("Title:");
titleLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
Label authorLabel = new Label("Author:");
authorLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
Label priceLabel = new Label("Price:");
priceLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
// Create book details text fields
TextField isbnTextField = new TextField();
TextField titleTextField = new TextField();
TextField authorTextField = new TextField();
TextField priceTextField = new TextField();
// Create buttons
Button addButton = new Button("Add");
Button updateButton = new Button("Update");
Button deleteButton = new Button("Delete");
// Create layout
GridPane gridPane = new GridPane();
gridPane.setHgap(10);
gridPane.setVgap(10);
gridPane.add(isbnLabel, 0, 0);
gridPane.add(isbnTextField, 1, 0);
gridPane.add(titleLabel, 0, 1);
gridPane.add(titleTextField, 1, 1);
gridPane.add(authorLabel, 0, 2);
gridPane.add(authorTextField, 1, 2);
gridPane.add(priceLabel, 0, 3);
gridPane.add(priceTextField, 1, 3);
HBox buttonBox = new HBox(10);
buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);
VBox rootPane = new VBox(10);
rootPane.setPadding(new Insets(10));
rootPane.getChildren().addAll(bookListView, gridPane, buttonBox);
// Set book selection listener
bookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
if (newValue != null) {
isbnTextField.setText(newValue.getIsbn());
titleTextField.setText(newValue.getTitle());
authorTextField.setText(newValue.getAuthor());
priceTextField.setText(String.valueOf(newValue.getPrice()));
}
});
// Add button action
addButton.setOnAction(event -> {
String isbn = isbnTextField.getText();
String title = titleTextField.getText();
String author = authorTextField.getText();
double price = Double.parseDouble(priceTextField.getText());
Book newBook = new Book(isbn, title, author, price);
books.add(newBook);
operations.addBookToDatabase(newBook);
// Clear text fields
clearTextFields(isbnTextField, titleTextField, authorTextField, priceTextField);
});
// Update button action
updateButton.setOnAction(event -> {
Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
if (selectedBook != null) {
String isbn = isbnTextField.getText();
String title = titleTextField.getText();
String author = authorTextField.getText();
double price = Double.parseDouble(priceTextField.getText());
operations.updateBookInDatabase(selectedBook, isbn, title, author, price);
selectedBook.setIsbn(isbn);
selectedBook.setTitle(title);
selectedBook.setAuthor(author);
selectedBook.setPrice(price);
clearTextFields(isbnTextField, titleTextField, authorTextField, priceTextField);
}
});
// Delete button action
deleteButton.setOnAction(event -> 
{
Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
if (selectedBook != null) {
operations.deleteBookFromDatabase(selectedBook);
books.remove(selectedBook);
clearTextFields(isbnTextField, titleTextField, authorTextField, priceTextField);
}
});
// Set background color and image
BackgroundImage backgroundImage = new BackgroundImage(
new Image("Downloads\\Book_store.jpg"),
BackgroundRepeat.NO_REPEAT,
BackgroundRepeat.NO_REPEAT,
BackgroundPosition.DEFAULT,
BackgroundSize.DEFAULT
);
rootPane.setBackground(new Background(backgroundImage));
Scene scene = new Scene(rootPane, 600, 400);
primaryStage.setScene(scene);
primaryStage.show();
}
private void clearTextFields(TextField... textFields) {
for (TextField textField : textFields) {
textField.clear();
}
}
}

