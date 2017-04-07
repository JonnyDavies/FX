package unit;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import model.Order;
import model.Trader;

public class OrderTest 
{
  private Order order = new Order();
  
  @Test
  public void gettingAndSettingCurrencyPair()
  {
      order.setCurrencyPair("EUR/USD");      
      assertEquals("EUR/USD", order.getCurrencyPair());
  }
  @Test 
  public void gettingAndSettingQuantity()
  {
    order.setQuantity("1000");      
    assertEquals("1000", order.getQuantity());
  }
  @Test 
  public void gettingAndSettingDirection()
  {
    order.setDirection("Buy");      
    assertEquals("Buy", order.getDirection());
  }  
  @Test 
  public void gettingAndSettingPrice()
  {
    order.setPrice(1.230);      
    assertTrue(1.230 == order.getPrice());
  }  
  @Test 
  public void gettingAndSettingCurrentPrice()
  {
    order.setCurrentPrice(1.234);      
    assertTrue(1.234 == order.getCurrentPrice());
  }  
  @Test 
  public void gettingAndSettingTakeProfit()
  {
    order.setTakeProfit(1.500);      
    assertTrue(1.500 == order.getTakeProfit());
  }
  @Test
  public void gettingAndSettingStopLoss()
  {
    order.setStopLoss(1.000);      
    assertTrue(1.000 == order.getStopLoss());
  } 
  @Test
  public void gettingAndSettingEquity()
  {
    order.setResult(new BigDecimal("25.00"));      
    assertTrue(new BigDecimal("25.00").equals(order.getResult()));
  } 
  @Test
  public void gettingAndSettingOneClickTrue()
  {
    order.setOneClickOrder(true);      
    assertTrue(true == order.getOneClickOrder());
  }  
  @Test
  public void gettingAndSettingOneClickFalse()
  {
    order.setOneClickOrder(false);      
    assertTrue(false == order.getOneClickOrder());
  }  
  @Test
  public void gettingDbId()
  {
    assertTrue(0 == order.getDBId());
  } 
}
