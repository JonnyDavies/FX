package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FXViewMenuPane extends HBox {
  
  private Button b1, b2, b3, b4;
  private ComboBox <String> cb1;
  private Label l1;
  
  public FXViewMenuPane(){
    
    this.setStyle("-fx-background-color : grey");
    this.setPadding(new Insets(5, 10, 5, 10));
    this.setSpacing(8);
    this.setAlignment(Pos.CENTER_RIGHT);

    b1 = new Button("Logout");
    b1.setPrefSize(100,30);
    
    b2 = new Button("New Order");
    b2.setPrefSize(100,30);
    
    b3 = new Button("Delete Order");
    b3.setPrefSize(100,30);
    
    b4 = new Button("Price Alert");
    b4.setPrefSize(100,30);
       
  
    
    ObservableList<String> options = 
        FXCollections.observableArrayList(
            "EUR/USD",
            "USD/JPY",
            "GBP/USD",
            "USD/CHF"
        );

    cb1 = new ComboBox<String>(options);
    cb1.setPrefSize(100,30);
    
    l1 = new Label("Open Chart ");
    l1.setFont(new Font("Arial", 16));
    l1.setTextFill(Color.WHITE);
      
    this.getChildren().addAll(l1,cb1,b3,b2,b4,b1);
  }
  
  public void addLogOutHandler(EventHandler<ActionEvent> handler) {
    b1.setOnAction(handler);
  }
  
  public void addNewOrderHandler(EventHandler<ActionEvent> handler) {
    b2.setOnAction(handler);
  }
  
  public void addDeleteOrderHandler(EventHandler<ActionEvent> handler) {
    b3.setOnAction(handler);
  }
  
  public void addPriceAlert(EventHandler<ActionEvent> handler) {
    b4.setOnAction(handler);
  }
  
  
  public void addOpenChartHandler(EventHandler<ActionEvent> handler) {
    cb1.setOnAction(handler);
  }
  
  public ComboBox <String> getChartCombo()
  {
    return cb1;
  }



}
