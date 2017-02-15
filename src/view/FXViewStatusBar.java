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

public class FXViewStatusBar extends HBox {
  
  private Label l1, l2, l3;
  
  public FXViewStatusBar(){
    
    this.setStyle("-fx-background-color : grey");
    this.setPadding(new Insets(5, 10, 5, 10));
    this.setSpacing(8);
    this.setAlignment(Pos.CENTER_RIGHT);


    
    l1 = new Label("Equity: GBP 1000.89 ");
    l1.setFont(new Font("Arial", 12));
    l1.setTextFill(Color.GREEN);
    
    
    l2 = new Label("Live Result: GBP 0.00");
    l2.setFont(new Font("Arial", 12));
    l2.setTextFill(Color.RED);
    
    
    l3 = new Label("12/02/2017:  09.53.18");
    l3.setFont(new Font("Arial", 12));
    l3.setTextFill(Color.WHITE);
      
    this.getChildren().addAll(l3,l2,l1);
  }
  
}
