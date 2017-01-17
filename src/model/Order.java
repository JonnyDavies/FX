package model;

public class Order {
  
  private int quantity;
  private String direction;
  private double price;
  private double takeProfit;
  private double stopLoss;
  private double margin;
  private int result;
  // private Currency pair
  
  // testing github desktop
  
  public Order(){
    this.quantity = 0;
    this.direction = "";
    this.price = 0.0;
    this.takeProfit = 0.0;
    this.stopLoss = 0.0;
    this.result = 0;
    // this.currencyPair = new CurrencyPair()
  }
  
  public Order (int quantity, String direction, double price, double takeProfit, double stopLoss, double margin, int result){    
    this.quantity = quantity;
    this.direction = direction;
    this.price = price;
    this.takeProfit = takeProfit;
    this.stopLoss = stopLoss;
    this.margin = margin;
    this.result = result;   
  }
  
  public void setQuantity(int quantity){
    this.quantity = quantity;
  }
  
  public int getQuantity(){
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
  
  public void setResult(int result){
    this.result = result;
  }
  
  public int getResult(){
    return result;
  }
  
  public String toString(){
    return "Order[quantity=" + quantity + " direction=" + direction + " price=" + price + " takeProfit=" + takeProfit + " stopLoss=" + stopLoss + " margin=" + margin + " result=" + result + " ]";
  }
}
