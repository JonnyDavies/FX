package model;

public class Order {
  
  private String currencyPair;
  private String quantity;
  private String direction;
  private double price;
  private double currentPrice;
  private double takeProfit;
  private double stopLoss;
  private Integer result;  
  
  public Order(){
    this.currencyPair = "";
    this.quantity = "0";
    this.direction = "";
    this.price = 0.0;
    this.currentPrice = 0.0;
    this.takeProfit = 0.0;
    this.stopLoss = 0.0;
    this.result = 0;
    }
  
  public Order (String currencyPair, String quantity, String direction, double price, double currentPrice, double takeProfit, double stopLoss, Integer result){    
    this.currencyPair = currencyPair;
    this.quantity = quantity;
    this.direction = direction;
    this.price = price;
    this.currentPrice = currentPrice;
    this.takeProfit = takeProfit;
    this.stopLoss = stopLoss;
    this.result = result;   
  }
  
  
  public String getCurrencyPair(){
    return currencyPair;
  }
  
  public void setCurrencyPair(String currencyPair){
    this.currencyPair = currencyPair;
  }
  
  public void setQuantity(String quantity){
    this.quantity = quantity;
  }
  
  public String getQuantity(){
    return quantity;
  }
  
  public String getDirection(){
    return direction;
  }
  
  public void setDirection(String direction){
    this.direction = direction;
  }
  
  public void setPrice(double price){
    this.price = price;
  }
  
  public double getCurrentPrice(){
    return currentPrice;
  }
  
  public void setCurrentPrice(double currentPrice){
    this.currentPrice = currentPrice;
  }
  
  public double getPrice(){
    return price;
  }
  
  public void setTakeProfit(double takeProfit){
    this.takeProfit = takeProfit;
  }
  
  public double getTakeProfit(){
    return takeProfit;
  }

  public void setStopLoss(double stopLoss){
    this.stopLoss = stopLoss;
  }
  
  public double getStopLoss(){
    return stopLoss;
  }
  
  public void setResult(Integer result){
    this.result = result;
  }
  
  public Integer getResult(){
    return result;
  }
  
  public String toString(){
    return "Order[quantity=" + quantity + " direction=" + direction + " price=" + price + " takeProfit=" + takeProfit + " stopLoss=" + stopLoss + " result=" + result + " ]";
  }
}
