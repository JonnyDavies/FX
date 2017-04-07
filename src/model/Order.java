package model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javafx.beans.property.SimpleStringProperty;

/**
 * The Order class models all of the 
 * orders that are made within the 
 * the FX Trading Simulator.
 *  @author JD
 */
public class Order {
  
  private String currencyPair;
  private String quantity;
  private String direction;
  private double price;
  private double currentPrice;
  private double takeProfit;
  private double stopLoss;
  private BigDecimal result;
  private boolean oneClickOrder;
  private int id;
  private int dbId;
  private static int numberOfOrders = 0;
  
  public Order()
  {
    this.currencyPair = "";
    this.quantity = "0";
    this.direction = "";
    this.price = 0.0;
    this.currentPrice = 0.0;
    this.takeProfit = 0.0;
    this.stopLoss = 0.0;
    this.result = new BigDecimal("0.00");
    this.oneClickOrder = false;
    this.id = ++numberOfOrders;
  }
  
  public Order (String currencyPair, String quantity, String direction, double price, double currentPrice, double takeProfit, double stopLoss, BigDecimal result, boolean oneClickOrder, int dbId)
  {    
    this.currencyPair = currencyPair;
    this.quantity = quantity;
    this.direction = direction;
    this.price = price;
    this.currentPrice = currentPrice;
    this.takeProfit = takeProfit;
    this.stopLoss = stopLoss;
    this.result = result;   
    this.oneClickOrder = oneClickOrder;
    this.id = ++numberOfOrders;
    this.dbId = dbId;
  }
   
  public String getCurrencyPair()
  {
    return this.currencyPair;
  }
  
  public void setCurrencyPair(String currencyPair)
  {
    this.currencyPair = currencyPair;
  }
  
  public void setQuantity(String quantity)
  {
    this.quantity = quantity;
  }
  
  public String getQuantity()
  {
    return this.quantity;
  }
  
  public String getDirection()
  {
    return this.direction;
  }
  
  public void setDirection(String direction)
  {
    this.direction = direction;
  }
  
  public void setPrice(double price)
  {
    this.price = price;
  }
  
  public double getCurrentPrice()
  {
    return this.currentPrice;
  }
  
  public void setCurrentPrice(double currentPrice)
  {  
    this.currentPrice = currentPrice;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public void setTakeProfit(double takeProfit)
  {
    this.takeProfit = takeProfit;
  }
  
  public double getTakeProfit()
  {
    return this.takeProfit;
  }

  public void setStopLoss(double stopLoss)
  {
    this.stopLoss = stopLoss;
  }
  
  public double getStopLoss()
  {
    return this.stopLoss;
  }
  
  public void setResult(BigDecimal result)
  {  
    this.result = result;
  }
  
  public BigDecimal getResult()
  {
    return this.result;
  }
  
  public void setOneClickOrder(boolean oneClickOrder)
  {
    this.oneClickOrder = oneClickOrder;
  }
  
  public boolean getOneClickOrder()
  {
    return this.oneClickOrder;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public int getDBId()
  {
    return this.dbId;
  }
  
  public String toString()
  {
    return "Order[ id=" + id +"quantity=" + quantity + " direction=" + direction + " price=" + price + " takeProfit=" + takeProfit + " stopLoss=" + stopLoss + " result=" + result  + " oneClickOrder=" + oneClickOrder +"]";
  }
}
