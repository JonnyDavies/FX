package model;

import java.util.ArrayList;

public class Trader {
  
  private String username;
  private String password;
  private String fullName;
  private double equity;
  private ArrayList <Order> orders;
  
  
  public Trader (){
    this.username = "";
    this.password = "";
    this.equity = 0.00;
    this.fullName = "";
  }
  
  public Trader (String username, String password, String fullName, double equity){
    this.username = username;
    this.password = password;
    this.fullName = fullName;
    this.equity = equity;
  }
  
  public void setUsername(String username){
      this.username = username;
  }
  
  public String getUsername(){
      return username;
  }
  
  public void setPassword(String password){
    this.password = password;
  }

  public String getPassword(){
    return password;
  }
  
  public void setFullName(String fullName){
      this.fullName = fullName;
  }

  public String getFullName(){
      return fullName;
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
  
  public String toString(){
    return "Trader[username= " + username + " password=" + password + " fullName=" + fullName + " Equity=" + equity + " ]";
  }



}
