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
import javafx.scene.control.ToolBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FXViewMenuPane extends VBox {
  
  private Button b1, b2, b3, b4;
  private ComboBox <String> cb1, cb2;
  private Label l1, l2;
  
  public FXViewMenuPane(){
    
    

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
    
    l1 = new Label("Chart ");
    l1.setFont(new Font("Arial", 16));
    l1.setTextFill(Color.BLACK);
    
 
    
    
    HBox leftSection = new HBox();
    HBox rightSection = new HBox(l1,cb1,b3,b2,b4,b1 );

    /* Center all sections and always grow them. Has the effect known as JUSTIFY. */
    HBox.setHgrow( leftSection, Priority.ALWAYS );
    HBox.setHgrow( rightSection, Priority.ALWAYS );

    leftSection.setAlignment( Pos.CENTER_LEFT );
    rightSection.setAlignment( Pos.CENTER_RIGHT );

   /* It might be harder to propagate some properties: */
   final int spacing = 8;
   ToolBar toolBar = new ToolBar();

   toolBar.setPadding( new Insets( 2, spacing, 2, spacing ) );
   leftSection.setSpacing( spacing );
   rightSection.setSpacing( spacing );
   toolBar.getItems().addAll(leftSection, rightSection);

   this.getChildren().add(toolBar);
   
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
