package model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Trader {
  
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private double equity;
  private ArrayList<Order> orders;
  
  
  public Trader (){
    this.email = "";
    this.password = "";
    this.equity = 0.00;
    this.firstName = "";
    this.lastName = "";
    this.orders = new ArrayList<Order>();
  }
  
  public Trader (String email, String password, String firstName, String lastName,  double equity){
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.equity = equity;
    this.orders = new ArrayList<Order>();
  }
  
  public void setEmail(String email){
      this.email = email;
  }
  
  public String getEmail(){
      return this.email;
  }
  
  public void setPassword(String password){
    this.password = password;
  }

  public String getPassword(){
    return this.password;
  }
  
  public void setFirstName(String firstName){
      this.firstName = firstName;
  }

  public String getFirstName(){
      return this.firstName;
  }
  
  public void setLastName(String lastName){
    this.lastName = lastName;
  }
  
  public String getLastName(){
      return lastName;
  }

  public void setEquity(double equity){
      this.equity = equity;
  }

  public double getEquity(){
      return equity;
  }
  
  public ArrayList<Order> getOrders(){
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
  
  public void removeOrders(int index)
  {
    orders.remove(index);
  }
  
  public String toString(){
    return "Trader[username= " + email + " password=" + password + " firstName=" + firstName + " lastName=" + lastName + " Equity=" + equity + " ]";
  }



}
