package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
  
  public void createTable()
  {
    try
    {
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("CREATE TABLE trader (firstname STRING, lastname STRING, email STRING, password STRING, equity INTEGER)");
    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    }

  }
  
  public boolean doesTableExist(String SQLTableName)
  {
    boolean tableExists = false;
    
    try
    {
      Statement statement = connection.createStatement();
      ResultSet res = statement.executeQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='" + SQLTableName + "'");
      tableExists = (res.getInt("count(*)") != 0)? true : false;     
    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    }
    
    return tableExists;
  }
  
  public void insertTraderDetails(ArrayList<String> SQLTraderDetails)
  {
    int equity = 0;
    
    try
    {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO trader values('" + SQLTraderDetails.get(0) + "', '"
                                                            + SQLTraderDetails.get(1) + "','" 
                                                            + SQLTraderDetails.get(2) + "','" 
                                                            + SQLTraderDetails.get(3) + "','"
                                                            + equity + "' )");
      
    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    }
    
  }
  
  public boolean doesTraderExist(String traderEmail)
  {
    boolean traderExists = false;
    try 
    {
      Statement statement = connection.createStatement();
      ResultSet res = statement.executeQuery("SELECT count(*) FROM trader WHERE email = '" + traderEmail + "'");
      traderExists = (res.getInt("count(*)") != 0)? true : false;
    }
    catch (SQLException e)
    {
      System.err.println(e);
    }
    
    return traderExists;
  }
  
  public String retreiveTradersPassword(String traderEmail)
  {
    String hashedPassword = ""; 
    try
    {
      Statement statement = connection.createStatement();
      ResultSet res = statement.executeQuery("SELECT password FROM trader WHERE email = '" + traderEmail + "'");
      hashedPassword = res.getString("password");      
    }
    catch (SQLException e)
    {
      System.err.println(e);
    }
    
    return hashedPassword;
  }
  
  
  
  
  
  
  
  

}
