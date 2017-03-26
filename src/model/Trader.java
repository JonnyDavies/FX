package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Trader 
{
  
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private BigDecimal equity;
  private ArrayList<Order> orders;
  
  private ArrayList<Double> priceAlertsEUR; // this suitable for storing price alerts
  private ArrayList<Double> priceAlertsUSD; // this suitable for storing price alerts
  private ArrayList<Double> priceAlertsGBP; // this suitable for storing price alerts
  private ArrayList<Double> priceAlertsCHF; // this suitable for storing price alerts

  
  
  public Trader ()
  {
    this.email = "";
    this.password = "";
    this.equity = new BigDecimal("11000.00");
    this.firstName = "";
    this.lastName = "";
    
    this.orders = new ArrayList<Order>();
    this.priceAlertsEUR = new ArrayList<Double>();
    this.priceAlertsUSD = new ArrayList<Double>();
    this.priceAlertsGBP = new ArrayList<Double>();
    this.priceAlertsCHF = new ArrayList<Double>();

  }
  
  public Trader (String email, String password, String firstName, String lastName,  BigDecimal equity)
  {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.equity = equity;
    this.orders = new ArrayList<Order>();
    this.priceAlertsEUR = new ArrayList<Double>();
    this.priceAlertsUSD = new ArrayList<Double>();
    this.priceAlertsGBP = new ArrayList<Double>();
    this.priceAlertsCHF = new ArrayList<Double>();  
  }
  
  public void setEmail(String email)
  {
      this.email = email;
  }
  
  public String getEmail()
  {
      return this.email;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getPassword()
  {
    return this.password;
  }
  
  public void setFirstName(String firstName)
  {
      this.firstName = firstName;
  }

  public String getFirstName()
  {
      return this.firstName;
  }
  
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
  
  public String getLastName()
  {
      return lastName;
  }

  public void setEquity(BigDecimal equity)
  {
      this.equity = equity;
  }

  public BigDecimal getEquity()
  {
      return equity;
  }
  
  public ArrayList<Order> getOrders()
  {
    return orders;
  }
  
  public void addOrder(Order o)
  {
    orders.add(o);
  }
  
  public void removeAllOrders()
  {
    orders.clear();
  }
  
  public void removeOrder(int index)
  {
    orders.remove(index);
  }
  
  public void removeSingleOrder(Order o)
  {
    orders.remove(o);
  }
  
  
  public ArrayList<Double> getPriceAlertsEUR()
  {
    return priceAlertsEUR;
  }
 
  public boolean getPriceAlertsEURConatins(Double o)
  {
    return priceAlertsEUR.contains(o);
  }
  
  public boolean getPriceAlertsEURRemove(Double o)
  {
    return priceAlertsEUR.remove(o);
  }
 
  public ArrayList<Double> getPriceAlertsUSD()
  {
    return priceAlertsUSD;
  }
  
  public boolean getPriceAlertsUSDConatins(Double o)
  {
    return priceAlertsUSD.contains(o);
  }
  
  public boolean getPriceAlertsUSDRemove(Double o)
  {
    return priceAlertsUSD.remove(o);
  }
  
  public ArrayList<Double> getPriceAlertsGBP()
  {
    return priceAlertsGBP;
  }
  
  public boolean getPriceAlertsGBPConatins(Double o)
  {
    return priceAlertsGBP.contains(o);
  }
  
  public boolean getPriceAlertsGBPRemove(Double o)
  {
    return priceAlertsGBP.remove(o);
  }
  
  
  public ArrayList<Double> getPriceAlertsCHF()
  {
    return priceAlertsCHF;
  }
  
  public boolean getPriceAlertsCHFConatins(Double o)
  {
    return priceAlertsCHF.contains(o);
  }
  
  public boolean getPriceAlertsCHFRemove(Double o)
  {
    return priceAlertsCHF.remove(o);
  }
  
  public void makePriceAlert(String currency, Double price)
  {
    switch(currency)
    {
      case "EUR/USD":
        this.priceAlertsEUR.add(price);
        break;
      case "USD/JPY":
        this.priceAlertsUSD.add(price);
        break;
      case "GBP/USD":
        this.priceAlertsGBP.add(price);
        break;
      case "USD/CHF":
        this.priceAlertsCHF.add(price);
        break;
    }
   
  }
  
  public void removePriceAlert(String currency, Double price)
  {

    switch(currency)
    {
      case "EUR/USD":
        this.priceAlertsEUR.remove(price);
        break;
      case "USD/JPY":
        this.priceAlertsUSD.remove(price);
        break;
      case "GBP/USD":
        this.priceAlertsGBP.remove(price);
        break;
      case "USD/CHF":
        this.priceAlertsCHF.remove(price);
        break;
    }
   
  }
 
  public String toString(){
    return "Trader[username= " + email + " password=" + password + " firstName=" + firstName + " lastName=" + lastName + " Equity=" + equity + " ]";
  }



}
