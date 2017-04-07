package unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import model.Database;

public class DatabaseTest 
{

  Database db =  new Database();
  
  ArrayList<String> dbUnitTest = new ArrayList<String>(); 
  
  
  @Test
  public void insertingTraderDetails()
  {
    dbUnitTest.add("Jonny"); dbUnitTest.add("Davies"); dbUnitTest.add("jonathandavies28@gmail.com"); 
    dbUnitTest.add("$2a$12$vkYxO4ShZLKPGW6W3r5tk.FsPPBL2YkE.unbkqAnKB/SSd//STsPG");      
    db.insertTraderDetails(dbUnitTest);
  }
  
  @Test 
  public void doesOrderTableExist()
  {
    assertEquals(true, db.doesTableExist("orders"));
  }
  
  @Test 
  public void doesTraderTableExist()
  {
    assertEquals(true, db.doesTableExist("trader"));
  }
  
  @Test 
  public void doesTraderExist()
  {
    assertEquals(true, db.doesTraderExist("jonathandavies28@gmail.com"));
  }
  
  @Test 
  public void retreiveTradersPassword()
  {
    assertEquals("$2a$12$vkYxO4ShZLKPGW6W3r5tk.FsPPBL2YkE.unbkqAnKB/SSd//STsPG", db.retreiveTradersPassword("jonathandavies28@gmail.com"));
  }
}
