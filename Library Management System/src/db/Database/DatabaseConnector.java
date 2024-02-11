package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnector 
{
private Connection connection;
public void connectToDatabase(String url, String username, String password) 
{
try 
{
connection = DriverManager.getConnection(url, username, password);
} 
catch (SQLException e) 
{
e.printStackTrace();
}
}
public Connection getConnection() 
{
return connection;
}
public void closeConnection() 
{
try 
{
if (connection != null) 
{
connection.close();
}
} 
catch (SQLException e) 
{
e.printStackTrace();
}
}
}

