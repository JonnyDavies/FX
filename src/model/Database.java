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
  
  public void createTraderTable()
  {
    try
    {
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("CREATE TABLE trader (firstname STRING, lastname STRING, email STRING, password STRING, equity INTEGER, CONSTRAINT trader_PK PRIMARY KEY (email))");
    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    }

  }
  
  public void createOrdersTable()
  {
    try
    {
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      statement.executeUpdate("CREATE TABLE orders (id INTEGER, email STRING, currency STRING, quantity STRING, direction STRING, price DOUBLE, takeProfit DOUBLE, stopLoss DOUBLE, CONSTRAINT orders_PK PRIMARY KEY (id, email), CONSTRAINT orders_FK FOREIGN KEY (email) REFERENCES trader (email) ON DELETE CASCADE)");

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
  
  public void insertOrderDetails( int ids, String email, String direction, String currency, String quantity, double price, double takeProfit, double stopLoss)
  {
    // enter order details ========================
    
    try
    {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO orders values('" + ids + "', '"
                                                            + email + "','" 
                                                            + direction + "','" 
                                                            + currency + "', '"
                                                            + quantity + "','" 
                                                            + price + "','" 
                                                            + takeProfit + "','" 
                                                            + stopLoss + "' )");
    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    } 
  }
  
  
  public void deleteOrderDetails(int ids, String email)
  {
    // enter order details ========================
    //  DELETE FROM orders WHERE id=1 AND email='jonathandavies27@gmail.com';
    try
    {
      Statement statement = connection.createStatement();
      statement.execute("DELETE FROM orders WHERE id = '" + ids + "' AND  email ='"+ email + "' ");

    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    } 
  }
  
  
  public ResultSet returnOrders(String email)
  {
    
    ResultSet rs = null; 
    
    try
    {
      Statement statement = connection.createStatement();
      rs = statement.executeQuery("SELECT * FROM orders WHERE email = 'jonathandavies27@gmail.com'");
    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    } 
    
    return rs;
  }
  
  
  public int getLastOrderId()
  {
    // enter order details ========================
    //  DELETE FROM orders WHERE id=1 AND email='jonathandavies27@gmail.com';
    int lastId = 0;
    
    try
    {
      Statement statement = connection.createStatement();     
     
      ResultSet rs3 = statement.executeQuery(" SELECT COUNT(*) AS OrdersTotal FROM orders");
      
      if(rs3.getInt("OrdersTotal") > 0)
      {
        ResultSet rs2 = statement.executeQuery("SELECT * FROM orders WHERE id = (SELECT MAX(id) FROM orders)");
        lastId = rs2.getInt("id");
      }       
    }
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    } 
    
    return lastId;
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
