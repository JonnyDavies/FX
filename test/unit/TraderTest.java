package unit;
import junit.framework.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import model.Order;
import model.Trader;
import org.junit.Test;



public class TraderTest 
{
  private Trader trader = new Trader(); 
  
  @Test
  public void gettingAndSettingEmail()
  {
      trader.setEmail("jonathandavies27@gmail.com");      
      assertEquals("jonathandavies27@gmail.com", trader.getEmail());
  } 
  @Test 
  public void gettingAndSettingFirstName()
  {
      trader.setFirstName("Jonny");
      assertEquals("Jonny", trader.getFirstName());
  } 
  @Test 
  public void gettingAndSettingLastName()
  {
      trader.setLastName("Davies");
      assertEquals("Davies", trader.getLastName());
  } 
  @Test 
  public void gettingAndSettingPassword()
  {
      trader.setPassword("$2a$12$RTBQM4vR/NTRJVX8saUAmeXE7pMWL8KI5aS7rkZgWN1Qer3q895nW");
      assertEquals("$2a$12$RTBQM4vR/NTRJVX8saUAmeXE7pMWL8KI5aS7rkZgWN1Qer3q895nW", trader.getPassword());
  }
  @Test 
  public void gettingAndSettingEquity()
  {
      trader.setEquity(new BigDecimal("1234.00"));
      assertEquals(new BigDecimal("1234.00"), trader.getEquity());
  } 
  @Test 
  public void addingOrder()
  {
      trader.addOrder(new Order("EUR/USD", "1000", "Buy", 1.230, 1.234, 1.500, 1.000, new BigDecimal("25.00"), true, 1));   
      assertEquals(1, trader.getOrders().size());
  }
  @Test
  public void removeOrder()
  {
      trader.addOrder(new Order("EUR/USD", "1000", "Buy", 1.230, 1.234, 1.500, 1.000, new BigDecimal("25.00"), true, 1));   
      trader.removeOrder(0);   
      assertEquals(0, trader.getOrders().size());
  }
} 
