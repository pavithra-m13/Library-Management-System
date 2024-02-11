package application;
import GUI.GUIManager;
import db.DatabaseConnector;
import db.DatabaseOperations;
import javafx.application.Application;
import javafx.stage.Stage;
public class MainApp extends Application {
public static void main(String[] args) {
launch(args);
}
@Override
public void start(Stage primaryStage) {
// Instantiate DatabaseConnector
DatabaseConnector connector = new DatabaseConnector();
connector.connectToDatabase("jdbc:mysql://localhost:3306/bookshop", "root", "root");
// Instantiate DatabaseOperations
DatabaseOperations operations = new DatabaseOperations(connector.getConnection());
// Instantiate GUIManager
GUIManager guiManager = new GUIManager(operations);
guiManager.createGUI(primaryStage);
}
}

