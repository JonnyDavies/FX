package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database 
{
   
  private Connection connection;
  
  public Database()
  {
    try 
    {
      connection = DriverManager.getConnection("jdbc:sqlite:FX.db");
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
  }
  
  public Connection getConnection()
  {
    return connection;
  }
  
  public void createTable(String SQLString)
  {
    
  }
  
  public boolean doesTableExist(String SQLTableName)
  {

    try
    {
      Statement statement = connection.createStatement();
      ResultSet res = statement.executeQuery("SELECT * FROM sqlite_master WHERE type='table' AND name='" + SQLTableName + "'");
      System.out.println(res);
      
    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    }
    
    return true;
  }
  
  
  
  
  
  
  
  

}
