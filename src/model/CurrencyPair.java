package model;

import java.time.LocalDateTime;

public class CurrencyPair {
  
  private String currencyPairName;
  private double buyPrice;
  private double sellPrice;
  private double spread;
  private LocalDateTime time;
  
  
  public CurrencyPair(){
    this.currencyPairName = "";
    this.buyPrice = 0.00;
    this.sellPrice = 0.00;
    this.spread = 0.00;
    // this.time = new LocalDateTime();  
  }
  
  public CurrencyPair(String currencyPairName, double buyPrice, double sellPrice, double spread, LocalDateTime time){
    this.currencyPairName = currencyPairName;
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
    this.spread = spread;
    this.time = time;
  }
  
  public void setCurrencyPairName(String currencyPairName){
    this.currencyPairName = currencyPairName;
  }
  
  public String getCurrencyPairName(){
    return currencyPairName;
  }
  
  public void setBuyPrice(double buyPrice){
    this.buyPrice = buyPrice;
  }
  
  public double getBuyPrice(){
    return buyPrice;
  }
  
  public void setSellPrice(double sellPrice){
    this.sellPrice = sellPrice;
  }
  
  public double getSellPrice(){
    return sellPrice;
  }

  public void setSpread(double spread){
    this.spread = spread;
  }
  
  public double getSpread(){
    return spread;
  }
  
  public String toString(){
    return "CurrencyPair[currencPairName= " + currencyPairName + " buyPrice= " + buyPrice + " sellPrice=" + " spread=" + "]";
  }
}
